package com.liujian.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.common.BaseResponse;
import com.liujian.springbootinit.common.ErrorCode;
import com.liujian.springbootinit.common.ResultUtils;
import com.liujian.springbootinit.exception.BusinessException;
import com.liujian.springbootinit.model.dto.post.PostQueryRequest;
import com.liujian.springbootinit.model.dto.search.SearchRequest;
import com.liujian.springbootinit.model.dto.user.UserQueryRequest;
import com.liujian.springbootinit.model.entity.Picture;
import com.liujian.springbootinit.model.vo.PostVO;
import com.liujian.springbootinit.model.vo.SearchVo;
import com.liujian.springbootinit.model.vo.UserVO;
import com.liujian.springbootinit.service.PictureService;
import com.liujian.springbootinit.service.PostService;
import com.liujian.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private PostService postService;
    @Resource
    private UserService userService;
    @Resource
    private PictureService pictureService;

    /**
     * 聚合搜索接口
     * @param searchRequest
     * @param request
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        //获取聚合查询关键词
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        //采用异步编排的模式进行接口的并发查询，fork-join框架加CompletableFuture异步回调
        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            userQueryRequest.setCurrent(current);
            userQueryRequest.setPageSize(pageSize);
            return userService.listUserByPage(userQueryRequest);
        });

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            postQueryRequest.setCurrent(current);
            postQueryRequest.setPageSize(pageSize);
            return postService.listPostByPage(postQueryRequest, request);
        });

        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() ->
                pictureService.searchPicture(searchText, current, pageSize)
        );

        CompletableFuture.allOf(userTask,postTask,pictureTask).join();

        try {
            Page<UserVO> userList = userTask.get();
            Page<PostVO> postList = postTask.get();
            Page<Picture> pictureList = pictureTask.get();

            SearchVo searchVo = new SearchVo();
            searchVo.setUserList(userList.getRecords());
            searchVo.setPostList(postList.getRecords());
            searchVo.setPictureList(pictureList.getRecords());

            return ResultUtils.success(searchVo);
        } catch (Exception e) {
            log.error("出现异常",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

    }
}

package com.liujian.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.common.ErrorCode;
import com.liujian.springbootinit.datasource.DataSourceRegister;
import com.liujian.springbootinit.exception.BusinessException;
import com.liujian.springbootinit.model.dto.search.SearchRequest;
import com.liujian.springbootinit.model.entity.Picture;
import com.liujian.springbootinit.model.enums.SearchEnum;
import com.liujian.springbootinit.model.vo.PostVO;
import com.liujian.springbootinit.model.vo.SearchVo;
import com.liujian.springbootinit.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 用户搜索门面:
 *      聚合所有数据源接口，实现聚合查询和分类查询
 */
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private DataSourceRegister dataSourceRegister;

    /**
     * 不带类型的请求
     */
    public SearchVo getAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        //获取聚合查询关键词
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        //采用异步编排的模式进行接口的并发查询，fork-join框架加CompletableFuture异步回调
        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> (Page<UserVO>) dataSourceRegister.getInstance(SearchEnum.USER.getValue()).doSearch(searchText, current, pageSize, request));

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> (Page<PostVO>) dataSourceRegister.getInstance(SearchEnum.POST.getValue()).doSearch(searchText, current, pageSize, request));

        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> (Page<Picture>) dataSourceRegister.getInstance(SearchEnum.PICTURE.getValue()).doSearch(searchText, current, pageSize, request));

        CompletableFuture.allOf(userTask,postTask,pictureTask).join();

        try {
            Page<UserVO> userList = userTask.get();
            Page<PostVO> postList = postTask.get();
            Page<Picture> pictureList = pictureTask.get();

            SearchVo searchVo = new SearchVo();
            searchVo.setUserList(userList.getRecords());
            searchVo.setPostList(postList.getRecords());
            searchVo.setPictureList(pictureList.getRecords());

            return searchVo;
        } catch (Exception e) {
            log.error("出现异常",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 带类型的请求
     */
    public SearchVo getByType(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        //获取聚合查询关键词
        String searchText = "".equals(searchRequest.getSearchText()) ? "" : searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String searchType = searchRequest.getType();
        SearchEnum searchEnum = SearchEnum.getEnumByValue(searchType);

        //准备返回对象
        SearchVo searchVo = new SearchVo();

        assert searchEnum != null;
        Page<?> dataList = dataSourceRegister.getInstance(searchEnum.getValue()).doSearch(searchText, current, pageSize, request);
        searchVo.setDataList(dataList.getRecords());
        return searchVo;
    }
}

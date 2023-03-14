package com.liujian.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.liujian.springbootinit.annotation.AuthCheck;
import com.liujian.springbootinit.common.BaseResponse;
import com.liujian.springbootinit.common.DeleteRequest;
import com.liujian.springbootinit.common.ErrorCode;
import com.liujian.springbootinit.common.ResultUtils;
import com.liujian.springbootinit.constant.UserConstant;
import com.liujian.springbootinit.exception.BusinessException;
import com.liujian.springbootinit.exception.ThrowUtils;
import com.liujian.springbootinit.model.dto.picture.PictureQureyRequest;
import com.liujian.springbootinit.model.dto.post.PostAddRequest;
import com.liujian.springbootinit.model.dto.post.PostEditRequest;
import com.liujian.springbootinit.model.dto.post.PostQueryRequest;
import com.liujian.springbootinit.model.dto.post.PostUpdateRequest;
import com.liujian.springbootinit.model.entity.Picture;
import com.liujian.springbootinit.model.entity.Post;
import com.liujian.springbootinit.model.entity.User;
import com.liujian.springbootinit.model.vo.PostVO;
import com.liujian.springbootinit.service.PictureService;
import com.liujian.springbootinit.service.PostService;
import com.liujian.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQureyRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQureyRequest pictureQureyRequest) {
        long pageNum = pictureQureyRequest.getCurrent();
        long pageSize = pictureQureyRequest.getPageSize();
        String searchText = pictureQureyRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);

        Page<Picture> page = pictureService.searchPicture(searchText,pageNum,pageSize);
        return ResultUtils.success(page);
    }

}

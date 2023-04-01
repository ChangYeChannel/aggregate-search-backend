package com.liujian.springbootinit.controller;

import com.liujian.springbootinit.common.BaseResponse;
import com.liujian.springbootinit.common.ResultUtils;
import com.liujian.springbootinit.manager.SearchFacade;
import com.liujian.springbootinit.model.dto.search.SearchRequest;
import com.liujian.springbootinit.model.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private SearchFacade searchFacade;  //搜索门面
    /**
     * 聚合搜索接口
     */
    @PostMapping("/list")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchType = searchRequest.getType();
        if (!"".equals(searchType) && searchType != null) {
            System.out.println(searchType + "1");
            return ResultUtils.success(searchFacade.getByType(searchRequest,request));
        }else {
            System.out.println(searchType + "2");
            return ResultUtils.success(searchFacade.getAll(searchRequest,request));
        }
    }
}

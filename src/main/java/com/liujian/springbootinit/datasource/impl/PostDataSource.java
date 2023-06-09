package com.liujian.springbootinit.datasource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.datasource.DataSource;
import com.liujian.springbootinit.model.dto.post.PostQueryRequest;
import com.liujian.springbootinit.model.entity.Post;
import com.liujian.springbootinit.model.vo.PostVO;
import com.liujian.springbootinit.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 文章接口适配器
 */
@Service
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize, HttpServletRequest request) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setSearchText(searchText);
        // 引入ES查询
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage, request);
    }
}

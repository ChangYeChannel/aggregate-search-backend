package com.liujian.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据源接口
 * 想要接入系统的数据源必须实现该接口
 * 统一规范
 */
public interface DataSource<T> {
    /**
     * 搜索方法(带request请求)
     * @param searchText  搜索关键词
     * @param pageSize  当前页大小
     * @param pageNum  当前页码
     * @param request  请求体
     * @return  数据
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize, HttpServletRequest request);
}

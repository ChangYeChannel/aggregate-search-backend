package com.liujian.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口
 * 想要接入系统的数据源必须实现该接口
 * 统一规范
 */
public interface DataSource<T> {
    /**
     * 搜索方法
     * @param searchText  搜索关键词
     * @param pageSize  当前页大小
     * @param pageNum  当前页码
     * @return  数据
     */
    Page<T> doSearch(String searchText, long pageSize, long pageNum);
}

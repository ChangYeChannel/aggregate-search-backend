package com.liujian.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.model.entity.Picture;

public interface PictureService {

    /**
     * 分页查询图片信息
     * @param searchText  查询关键词
     * @param pageNum  当前页
     * @param pageSize  每页数量
     * @return  图片分页数据
     */
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}

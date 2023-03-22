package com.liujian.springbootinit.datasource.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.common.ErrorCode;
import com.liujian.springbootinit.datasource.DataSource;
import com.liujian.springbootinit.exception.BusinessException;
import com.liujian.springbootinit.model.entity.Picture;
import com.liujian.springbootinit.model.entity.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * 图片接口适配器
 */
@Service
public class PictureDataSource implements DataSource<Picture> {

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize, HttpServletRequest request) {
        long current = pageNum * pageSize;
        // bing 图片网址
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s",searchText,current);
        // 利用 Jsoup 解析 html 文件
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"图片数据获取失败！");
        }
        // 构建返回的实体列表
        ArrayList<Picture> pictureList = new ArrayList<>();

        // 拿到每一个 css 为 .iuscp.isv 的 div 标签，标签中包含了图片和图片描述
        for (Element element : doc.select(".iuscp.isv")) {
            if (pictureList.size() >= pageSize) {
                break;
            }
            // 拿到每一个图片标签的 m 描述字段， 其中包括图片详细地址 murl
            String m = element.select(".iusc").get(0).attr("m");
            // 描述字段是一个 json ，利用 hutool 工具将 JSON 转为 Map
            Map<String, Object> mResult = JSONUtil.toBean(m,Map.class);
            // 拿到图片地址
            String murl = (String) mResult.get("murl");
            // 拿到图片标题
            String title = element.select(".inflnk").get(0).attr("aria-label");

            // 封装实体类对象
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureList.add(picture);
        }

        //返回数据
        return new Page<Picture>(pageNum, pageSize, pageSize).setRecords(pictureList);
    }
}

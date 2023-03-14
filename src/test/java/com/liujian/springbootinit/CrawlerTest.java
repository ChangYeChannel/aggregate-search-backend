package com.liujian.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.liujian.springbootinit.model.entity.Picture;
import com.liujian.springbootinit.model.entity.Post;
import com.liujian.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 数据抓取
 */
@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;

    /**
     * 图片数据抓取
     */
    @Test
    public void testFetchPicture() throws IOException {
        // bing 图片网址
        String url = "https://cn.bing.com/images/search?q=%E5%B0%8F%E9%BB%91%E5%AD%90&qs=n&form=QBIR&sp=-1&lq=0&pq=%E5%B0%8F%E9%BB%91%E5%AD%90&sc=10-3&cvid=62E613FD504346F1AC8E513DA3DA709C&ghsh=0&ghacc=0&first=1";
        // 利用 Jsoup 解析 html 文件
        Document doc = Jsoup.connect(url).get();
        // 构建返回的实体列表
        ArrayList<Picture> pictureList = new ArrayList<>();

        // 拿到每一个 css 为 .iuscp.isv 的 div 标签，标签中包含了图片和图片描述
        for (Element element : doc.select(".iuscp.isv")) {
            // 拿到每一个图片标签的 m 描述字段， 其中包括图片详细地址 murl
            String m = element.select(".iusc").get(0).attr("m");
            // 描述字段是一个 json ，利用 hutool 工具将 JSON 转为 Map
            Map<String, Object> mResult = JSONUtil.toBean(m,Map.class);
            // 拿到图片地址
            String murl = (String) mResult.get("murl");
            // 拿到图片标题
            String title = element.select(".inflnk").get(0).attr("aria-label");

            // 测试打印
//            System.out.println(murl);
//            System.out.println(title);

            // 封装实体类对象
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureList.add(picture);
        }

    }

    /**
     * 文章数据抓取
     */
    @Test
    public void testFetchPassage() {
        // 1. 抓取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();

        // 2. 解析数据
//        System.out.println(JSONUtil.parseObj(result));
        ArrayList<Post> postList = new ArrayList<>();
        Map<String, Object> map = JSONUtil.toBean(JSONUtil.parseObj(result), Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        records.forEach((item) -> {
            JSONObject tempPost = (JSONObject) item;
            Post post = new Post();
            post.setTitle(tempPost.getStr("title"));
            post.setContent(tempPost.getStr("content"));
            JSONArray targsArr = (JSONArray) tempPost.get("tags");
            post.setTags(targsArr.toList(String.class).toString());
            post.setUserId(1635464278066716674L);
            postList.add(post);
        });

        // 3. 数据入库
        Assertions.assertTrue(postService.saveBatch(postList));
    }
}

package com.liujian.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.liujian.springbootinit.esdao.PostEsDao;
import com.liujian.springbootinit.model.dto.post.PostEsDTO;
import com.liujian.springbootinit.model.entity.Post;
import com.liujian.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取帖子初始化列表数据
 */
// todo 取消注释开启任务，开启注释后，程序会在服务启动时被执行一次，待执行一次文章（帖子）表有数据之后，将此注解再次注释掉，防止被调用多次
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;
    @Override
    public void run(String... args) {
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
        if (postService.saveBatch(postList)) {
            log.info("初始化文章数据成功！条数为：{}",postList.size());
        }else {
            log.error("初始化文章数据失败！");
        }
    }
}

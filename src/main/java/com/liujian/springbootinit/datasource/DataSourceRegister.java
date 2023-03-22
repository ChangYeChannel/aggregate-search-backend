package com.liujian.springbootinit.datasource;

import com.liujian.springbootinit.datasource.impl.PictureDataSource;
import com.liujian.springbootinit.datasource.impl.PostDataSource;
import com.liujian.springbootinit.datasource.impl.UserDataSource;
import com.liujian.springbootinit.model.enums.SearchEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegister {
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    private Map<String, DataSource<?>> typeDataSourceMap = new HashMap<>();

    /**
     * 被此注解标注的方法会在依赖注入之后，类的构造方法执行之前完成
     * 此方法的作用是在依赖注入（上方依赖数据源加载完成）后执行，初始化数据源map
     */
    @PostConstruct
    public void doInit() {
        typeDataSourceMap.put(SearchEnum.PICTURE.getValue(), pictureDataSource);
        typeDataSourceMap.put(SearchEnum.POST.getValue(), postDataSource);
        typeDataSourceMap.put(SearchEnum.USER.getValue(), userDataSource);
    }

    public DataSource<?> getInstance(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}

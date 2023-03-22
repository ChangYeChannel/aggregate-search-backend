package com.liujian.springbootinit.datasource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liujian.springbootinit.datasource.DataSource;
import com.liujian.springbootinit.model.dto.user.UserQueryRequest;
import com.liujian.springbootinit.model.vo.UserVO;
import com.liujian.springbootinit.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 用户接口适配器：
 *      原用户接口与统一接口所要的参数不相同
 *      可以在统一接口中封装一层适配器
 *      来进行接口参数之间的适配工作
 *      类似于微服务通信（调用其他微服务feign）
 */
@Service
public class UserDataSource implements DataSource<UserVO> {
    @Resource
    private UserService userService;
    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserByPage(userQueryRequest);
    }
}

package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;
/**
* @Description:    用户相关
* @Author:         taoxudong
* @CreateDate:     2019/8/23 15:55
* @Version:        1.0
*/
public interface UserService {
    /**
     * 通过用户ID获取用户对象的方法
     * @param id
     * @return
     */
    UserModel getUserById(Integer id);

    /**
     * 用户注册
     * @param userModel
     * @throws BusinessException
     */
    void register(UserModel userModel) throws BusinessException;

}

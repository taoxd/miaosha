package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @Description: 用户相关
 * @Author: taoxudong
 * @CreateDate: 2019/8/23 10:57
 * @Version: 1.0
 */
@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 通过Spring Bean包装的httpServletRequest，它的内部拥有ThreadLocal方式的map，
     * 去让用户在每个线程中处理自己对应的request，并且有ThreadLocal清除的机制。
     */
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户获取otp短信接口
     *
     * @param telphone
     */
    @RequestMapping("/getOtp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        //随机数范围[0,99999)
        int randomInt = random.nextInt(99999);
        //范围[10000,109999)
        randomInt += 10000;

        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应的用户的手机号关联,使用HttpSession的方式绑定 手机号 与 otpCode
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        //将OTP验证码通过短信通道发送给用户,省略
        System.out.println("telphone = " + telphone + " & otpCode = " + otpCode);

        return CommonReturnType.create(null);

    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户对象转化为可用UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);

        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    /**
     * 模型转换，有的信息没必要都传给前台
     * 注意 UserVO 和 UserModel 字段名类型都要一致，否则为null
     *
     * @param userModel
     * @return
     */
    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}

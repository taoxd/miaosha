package com.miaoshaproject.error;
/**
* @Description:    包装器业务异常类实现
* @Author:         taoxudong
* @CreateDate:     2019/8/22 16:39
* @Version:        1.0
*/
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参用于构造业务异常
     *
     * @param commonError
     */
    public BusinessException(CommonError commonError) {
        //Exception自身初始化
        super();
        this.commonError = commonError;
    }

    /**
     * 接收自定义errMsg的方式构造业务异常
     *
     * @param commonError
     * @param errMsg
     */
    public BusinessException(CommonError commonError, String errMsg) {
        //Exception自身初始化
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}

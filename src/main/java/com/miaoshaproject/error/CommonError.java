package com.miaoshaproject.error;

public interface CommonError {
    /**
     * 获取错误码
     * @return
     */
    int getErrCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrMsg();

    /**
     * 自定义错误信息
     * @param errMsg
     * @return
     */
    CommonError setErrMsg(String errMsg);
}

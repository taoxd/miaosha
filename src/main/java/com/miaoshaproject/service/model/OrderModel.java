package com.miaoshaproject.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 用户下单的交易模型
 * @Author: taoxudong
 * @CreateDate: 2019/9/4 9:54
 * @Version: 1.0
 */
@Data
public class OrderModel {

    //订单号
    private String id;

    //购买的用户id
    private Integer userId;

    //购买的商品id
    private Integer itemId;

    //购买的商品单价
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;

    //购买金额
    private BigDecimal orderPrice;


}

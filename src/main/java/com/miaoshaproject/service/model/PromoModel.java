package com.miaoshaproject.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Description: 秒杀活动模型
 * @Author: taoxudong
 * @CreateDate: 2019/9/4 16:49
 * @Version: 1.0
 */
@Data
public class PromoModel {

    private Integer id;

    //秒杀活动名称
    private String promoName;

    //秒杀活动开始时间
    private DateTime startDate;

    //秒杀活动的适用商品
    private Integer itemId;

    //秒杀活动的商品价格
    private BigDecimal promoItemPrice;

}

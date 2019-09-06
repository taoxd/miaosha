package com.miaoshaproject.dataobject;

import lombok.Data;

@Data
public class OrderDO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.id
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.user_id
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.item_id
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private Integer itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.item_price
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private Double itemPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.amount
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private Integer amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.order_price
     *
     * @mbg.generated Wed Sep 04 10:21:27 CST 2019
     */
    private Double orderPrice;

    private Integer promoId;

}
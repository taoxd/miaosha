package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买的数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不存在");
        }

        //校验活动信息
        if (promoId != null) {
            //(1)校验对应活动是否存在这个适用商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //(2)校验活动是否正在进行中
            } else if (itemModel.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息还未开始");
            }
        }


        //2.落单减库存，还有支付减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        //生产交易流水号，订单号
        orderModel.setId(generateOrderNo());

        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insert(orderDO);

        //加上商品的销量
        itemService.increaseSales(itemId, amount);

        //4.返回前端
        return orderModel;
    }

    /**
     * 不管外部事物成功与否，都新建一个事务
     * <p>
     * 假如外部事物失败，当前事务也会回滚，序列可能重复
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDate now = LocalDate.now();
        String nowDate = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        stringBuilder.append(nowDate);

        //中间6位为自增序列，如果日交益额超过6位数，再相应的增加位数
        //获取当前sequence
        Integer sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");

        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后2位为分库分表位 00-99,暂时写死
        stringBuilder.append("00");

/*        假设userId不变，产生的订单号永远落在固定的库固定的表,分库分表路由信息
        Integer userId = 1000122;
        int i = userId % 100;*/

        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }

}

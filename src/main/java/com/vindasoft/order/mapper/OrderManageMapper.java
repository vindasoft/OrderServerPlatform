package com.vindasoft.order.mapper;

import com.vindasoft.order.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 订单管理持久层接口
 *
 * @author: jwd
 */
@Mapper
public interface OrderManageMapper {
    OrderInfo getOrderInfo(@Param("orderId") String orderId);

    List<OrderInfo> getAllOrderInfos(@Param("orderName") String orderName);

    int insertOrderInfo(OrderInfo orderInfo);

    int updateOrderInfo(OrderInfo orderInfo);

    int deleteOrderInfo(@Param("orderId") String orderId);
}

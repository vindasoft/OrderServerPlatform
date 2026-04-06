package com.vindasoft.order.service;

import com.vindasoft.order.domain.OrderInfo;
import com.vindasoft.order.domain.request.QueryOrderInfoReq;

import java.util.List;

/**
 * @Description: 订单管理业务层接口
 *
 * @author: jwd
 */
public interface OrderManageService {
    OrderInfo getOrderInfo(QueryOrderInfoReq resquest);

    List<OrderInfo> getAllOrderInfos(QueryOrderInfoReq resquest);

    int insertOrderInfo(OrderInfo orderInfo);

    int updateOrderInfo(OrderInfo orderInfo);

    int deleteOrderInfo(String orderId);
}

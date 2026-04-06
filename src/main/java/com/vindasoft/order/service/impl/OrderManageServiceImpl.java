package com.vindasoft.order.service.impl;

import com.vindasoft.order.domain.OrderInfo;
import com.vindasoft.order.domain.request.QueryOrderInfoReq;
import com.vindasoft.order.mapper.OrderManageMapper;
import com.vindasoft.order.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 订单管理业务实现层
 * @author: jwd
 */
@Service
public class OrderManageServiceImpl implements OrderManageService {

    @Autowired
    private OrderManageMapper orderManageMapper;

    /**
     * 查询单个的订单信息
     *
     * @param request
     * @return list
     */
    @Override
    public OrderInfo getOrderInfo(QueryOrderInfoReq request) {
        OrderInfo orderInfo = orderManageMapper.getOrderInfo(request.getOrderId());
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAllOrderInfos(QueryOrderInfoReq request) {
        List<OrderInfo> resultList = orderManageMapper.getAllOrderInfos(request.getOrderName());
        return resultList;
    }

    @Override
    public int insertOrderInfo(OrderInfo orderInfo) {
        return orderManageMapper.insertOrderInfo(orderInfo);
    }

    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        return orderManageMapper.updateOrderInfo(orderInfo);
    }

    @Override
    public int deleteOrderInfo(String orderId) {
        return orderManageMapper.deleteOrderInfo(orderId);
    }
}

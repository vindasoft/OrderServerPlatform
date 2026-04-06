/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.controller;

import com.vindasoft.order.domain.OrderInfo;
import com.vindasoft.order.domain.request.QueryOrderInfoReq;
import com.vindasoft.order.domain.response.CommonResult;
import com.vindasoft.order.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description: 首页控制器，处理根路径请求
 * @author: jwd
 * @date: 2026-01-02
 */
@RestController //标记为REST API控制器（它会自动把返回值转换为json格式）
@RequestMapping("/index") // 查询访问的路径
public class IndexController {
    @Autowired
    private OrderManageService orderManageService;


    @PostMapping("/queryOrderInfo")
    public CommonResult queryOrderInfo(@RequestBody QueryOrderInfoReq resquest) {
        CommonResult result = CommonResult.success();
        OrderInfo orderInfo = orderManageService.getOrderInfo(resquest);
        result.put("data",orderInfo);
        return result;
    }

    @PostMapping("/queryAllOrderInfo")
    public CommonResult queryAllOrderInfo(@RequestBody QueryOrderInfoReq resquest) {
        CommonResult result = CommonResult.success();
        List<OrderInfo> list = orderManageService.getAllOrderInfos(resquest);
        result.put("data",list);
        return result;
    }

    @PostMapping("/insertOrderInfo")
    public CommonResult insertOrderInfo(@RequestBody OrderInfo orderInfo) {
        CommonResult result = CommonResult.success();
        int insertCount = orderManageService.insertOrderInfo(orderInfo);
        result.put("data",insertCount);
        return result;
    }

    @PostMapping("/updateOrderInfo")
    public CommonResult updateOrderInfo(@RequestBody OrderInfo orderInfo) {
        CommonResult result = CommonResult.success();
        int updateCount = orderManageService.updateOrderInfo(orderInfo);
        result.put("data",updateCount);
        return result;
    }

    @PostMapping("/deleteOrderInfo")
    public CommonResult deleteOrderInfo(@RequestBody OrderInfo orderInfo) {
        CommonResult result = CommonResult.success();
        int deleteCount = orderManageService.deleteOrderInfo(orderInfo.getOrderId());
        result.put("data",deleteCount);
        return result;
    }
}
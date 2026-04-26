/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain.request;

import lombok.Data;

/**
 * @Description: 查询订单请求参数类
 * @author: jwd
 * @date: 2026-01-02
 */
@Data
public class QueryOrderInfoReq extends PageInfoReq {
    private String orderId;

    private String orderName;
}

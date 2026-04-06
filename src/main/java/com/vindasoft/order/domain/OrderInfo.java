/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain;

import lombok.Data;

/**
 * @Description: 订单信息
 * @author: jwd
 * @date: 2026-01-02
 */
@Data
public class OrderInfo {
    private String orderId;
    private String userId;
    private String productId;
    private String status;
    private String createTime;
    private String updateTime;
    private String deleteTime;
    private String remark;
}

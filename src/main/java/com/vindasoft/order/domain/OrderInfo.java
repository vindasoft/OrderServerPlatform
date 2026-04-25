/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 订单信息
 * @author: jwd
 * @date: 2026-01-02
 */
@Data // 告诉lombok: 这个类包含getter和setter方法
@AllArgsConstructor // 告诉lombok: 这个类包含有参构造方法
@NoArgsConstructor // 告诉lombok: 这个类包含无参构造方法
public class OrderInfo {
    private String orderId;

    private String userId;

    private String productId;

    private String status;

    /**
     * 创建时间：当这个对象转为json返回给前端时，日期会被格式化为这个格式返回
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;

    private String remark;
}

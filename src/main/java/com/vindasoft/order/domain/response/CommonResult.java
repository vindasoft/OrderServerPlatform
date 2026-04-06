/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.domain.response;

import java.util.HashMap;

/**
 * @Description: TODO
 * @author: jwd
 * @date: 2026-01-02
 */
public class CommonResult extends HashMap<String, Object> {
    public CommonResult() {

    }

    public CommonResult(String code, String message) {
        super.put("code", code);
        super.put("message", message);
    }

    public CommonResult(String code, String message, Object data) {
        super.put("code", code);
        super.put("message", message);
        if (data != null) {
            super.put("data", data);
        }
    }

    public static CommonResult success() {
        return new CommonResult("200", "操作成功");
    }

    public static CommonResult success(String message, Object data) {
        return new CommonResult("200", message, data);
    }

    public static CommonResult error() {
        return CommonResult.error("操作失败");
    }

    public static CommonResult error(String message) {
        return new CommonResult("500", message);
    }

    public static CommonResult error(String message, Object data) {
        return new CommonResult("500", message, data);
    }
}

package com.yuyao.productadmin.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        // 用于表示业务错误，比如商品不存在、sku 重复。
        super(message);
    }
}

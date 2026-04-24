package com.yuyao.productadmin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 这个对象是后端返回给前端的商品结构。
public record ProductResponse(
        Long id,
        String name,
        String sku,
        Integer quantity,
        BigDecimal purchasePrice,
        BigDecimal salePrice,
        String location,
        String remark,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

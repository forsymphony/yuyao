package com.yuyao.productadmin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

// 前端新增或修改商品时，传进来的 JSON 会先接到这个对象里。
public record ProductRequest(
        @NotBlank(message = "商品名称不能为空")
        @Size(max = 100, message = "商品名称长度不能超过100")
        String name,

        @NotBlank(message = "商品编码不能为空")
        @Size(max = 64, message = "商品编码长度不能超过64")
        String sku,

        @NotNull(message = "商品数量不能为空")
        @Min(value = 0, message = "商品数量不能小于0")
        Integer quantity,

        @NotNull(message = "入价不能为空")
        @DecimalMin(value = "0.00", message = "入价不能小于0")
        BigDecimal purchasePrice,

        @NotNull(message = "出价不能为空")
        @DecimalMin(value = "0.00", message = "出价不能小于0")
        BigDecimal salePrice,

        @NotBlank(message = "地点不能为空")
        @Size(max = 120, message = "地点长度不能超过120")
        String location,

        @Size(max = 255, message = "备注长度不能超过255")
        String remark
) {
}

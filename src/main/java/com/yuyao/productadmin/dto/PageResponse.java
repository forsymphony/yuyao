package com.yuyao.productadmin.dto;

import java.util.List;

// 列表接口统一返回这个分页结构，而不是只返回一个数组。
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}

package com.yuyao.productadmin.controller;

import com.yuyao.productadmin.dto.PageResponse;
import com.yuyao.productadmin.dto.ProductRequest;
import com.yuyao.productadmin.dto.ProductResponse;
import com.yuyao.productadmin.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
// 这个类负责对外暴露商品相关接口，统一前缀是 /api/products。
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        // 接收前端传来的商品 JSON，交给 Service 做新增。
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        // id 从路径里拿，商品内容从请求体里拿。
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        // 删除成功时返回 204，不带响应体。
        productService.delete(id);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        // 查询单个商品详情。
        return productService.getById(id);
    }

    @GetMapping
    public PageResponse<ProductResponse> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "页码不能小于0") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页条数不能小于1")
            @Max(value = 100, message = "每页条数不能大于100") int size
    ) {
        // 列表接口支持分页，也支持按名称和地点筛选。
        return productService.list(name, location, page, size);
    }
}

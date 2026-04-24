package com.yuyao.productadmin.service;

import com.yuyao.productadmin.dto.PageResponse;
import com.yuyao.productadmin.dto.ProductRequest;
import com.yuyao.productadmin.dto.ProductResponse;
import com.yuyao.productadmin.entity.Product;
import com.yuyao.productadmin.exception.BusinessException;
import com.yuyao.productadmin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    // Service 通过 Repository 操作数据库。
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse create(ProductRequest request) {
        // 新增前先校验 sku，避免重复商品。
        validateSkuForCreate(request.sku());
        Product product = toEntity(request, new Product());
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        // 先确认商品存在，再做更新。
        Product product = getProduct(id);
        validateSkuForUpdate(request.sku(), id);
        product = toEntity(request, product);
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        // 删除前先查一次，避免删除不存在的数据。
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return toResponse(getProduct(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> list(String name, String location, int page, int size) {
        // page 从 0 开始，按创建时间倒序返回最新数据。
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ProductResponse> result = productRepository
                .findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(
                        normalizeKeyword(name),
                        normalizeKeyword(location),
                        pageable
                )
                .map(this::toResponse);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    private Product getProduct(Long id) {
        // 这个公共方法专门负责“查不到就报错”。
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("商品不存在，id=" + id));
    }

    private void validateSkuForCreate(String sku) {
        // 新增时只要 sku 已存在，就不允许保存。
        if (productRepository.existsBySku(sku)) {
            throw new BusinessException("商品编码已存在: " + sku);
        }
    }

    private void validateSkuForUpdate(String sku, Long id) {
        // 修改时要排除自己本身，再判断有没有和别的商品重复。
        if (productRepository.existsBySkuAndIdNot(sku, id)) {
            throw new BusinessException("商品编码已存在: " + sku);
        }
    }

    private String normalizeKeyword(String keyword) {
        // 查询条件为空时转成空字符串，方便做模糊查询。
        return keyword == null ? "" : keyword.trim();
    }

    private Product toEntity(ProductRequest request, Product product) {
        // 把前端传来的请求数据写入实体对象，供数据库保存。
        product.setName(request.name().trim());
        product.setSku(request.sku().trim());
        product.setQuantity(request.quantity());
        product.setPurchasePrice(request.purchasePrice());
        product.setSalePrice(request.salePrice());
        product.setLocation(request.location().trim());
        product.setRemark(request.remark() == null ? null : request.remark().trim());
        return product;
    }

    private ProductResponse toResponse(Product product) {
        // 把数据库实体转换成接口返回对象。
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getQuantity(),
                product.getPurchasePrice(),
                product.getSalePrice(),
                product.getLocation(),
                product.getRemark(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}

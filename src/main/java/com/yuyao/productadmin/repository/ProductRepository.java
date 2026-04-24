package com.yuyao.productadmin.repository;

import com.yuyao.productadmin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kdhe
 */
public interface ProductRepository extends JpaRepository<Product, String> {
    /**
     * Checks whether a product with the given SKU exists.
     *
     * @param sku product SKU
     * @return {@code true} if a product with the SKU exists, otherwise {@code false}
     */
    boolean existsBySku(String sku);
}

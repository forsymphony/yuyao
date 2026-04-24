package com.yuyao.productadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// 这个类对应数据库里的 product 表。
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 主键，由数据库自增生成。
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 64)
    // sku 作为商品编码，要求唯一，避免重复商品。
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal purchasePrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(length = 255)
    private String remark;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        // 新增数据时自动补上创建时间和更新时间。
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        // 更新数据时只刷新更新时间。
        this.updatedAt = LocalDateTime.now();
    }
}

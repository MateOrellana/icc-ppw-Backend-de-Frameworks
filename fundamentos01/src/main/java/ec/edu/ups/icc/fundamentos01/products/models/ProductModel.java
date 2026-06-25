package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.products.dto.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dto.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dto.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dto.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import java.time.LocalDateTime;

public class ProductModel {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    public ProductModel() {
    }

    public ProductModel(Long id, String name, Double price, Integer stock, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public static ProductModel fromDto(CreateProductDto dto) {
        ProductModel model = new ProductModel();
        model.setName(dto.getName());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());
        return model;
    }

    public static ProductModel fromEntity(ProductEntity entity) {
        ProductModel model = new ProductModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setStock(entity.getStock());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setPrice(price);
        entity.setStock(stock);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        entity.setDeleted(deleted);
        return entity;
    }

    public ProductResponseDto toResponseDto() {
        return new ProductResponseDto(id, name, price, stock, createdAt);
    }

    public void update(UpdateProductDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
    }

    public void partialUpdate(PartialUpdateProductDto dto) {
        if (dto.getName() != null) {
            this.name = dto.getName();
        }
        if (dto.getPrice() != null) {
            this.price = dto.getPrice();
        }
        if (dto.getStock() != null) {
            this.stock = dto.getStock();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

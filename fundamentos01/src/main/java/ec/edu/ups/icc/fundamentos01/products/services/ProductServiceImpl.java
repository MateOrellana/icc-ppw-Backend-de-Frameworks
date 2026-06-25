package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dto.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dto.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dto.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dto.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .filter(entity -> !entity.isDeleted())
                .map(ProductModel::fromEntity)
                .map(ProductModel::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(Long id) {
        ProductEntity entity = findActiveProduct(id);
        return ProductModel.fromEntity(entity).toResponseDto();
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        ensureProductNameIsAvailable(dto.getName(), null);

        ProductModel model = ProductModel.fromDto(dto);
        ProductEntity entity = model.toEntity();
        ProductEntity savedEntity = productRepository.save(entity);
        return ProductModel.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public ProductResponseDto update(Long id, UpdateProductDto dto) {
        ProductEntity entity = findActiveProduct(id);
        ensureProductNameIsAvailable(dto.getName(), id);

        ProductModel model = ProductModel.fromEntity(entity);
        model.update(dto);

        ProductEntity savedEntity = productRepository.save(model.toEntity());
        return ProductModel.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public ProductResponseDto partialUpdate(Long id, PartialUpdateProductDto dto) {
        ProductEntity entity = findActiveProduct(id);
        ProductModel model = ProductModel.fromEntity(entity);

        if (dto.getName() != null) {
            ensureProductNameIsAvailable(dto.getName(), id);
        }

        model.partialUpdate(dto);

        ProductEntity savedEntity = productRepository.save(model.toEntity());
        return ProductModel.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public void delete(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (entity.isDeleted()) {
            throw new NotFoundException("Product not found");
        }

        entity.setDeleted(true);
        productRepository.save(entity);
    }

    private ProductEntity findActiveProduct(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (entity.isDeleted()) {
            throw new NotFoundException("Product not found");
        }

        return entity;
    }

    private void ensureProductNameIsAvailable(String name, Long currentProductId) {
        if (currentProductId == null) {
            productRepository.findFirstByNameAndDeletedFalse(name)
                    .ifPresent(entity -> {
                        throw new ConflictException("Product name already registered");
                    });
            return;
        }

        productRepository.findFirstByNameAndDeletedFalseAndIdNot(name, currentProductId)
                .ifPresent(entity -> {
                    throw new ConflictException("Product name already registered");
                });
    }
}

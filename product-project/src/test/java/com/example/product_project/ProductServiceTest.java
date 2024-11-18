package com.example.product_project;

import com.example.product_project.entity.Product;
import com.example.product_project.exception.NoQuantityAvailableException;
import com.example.product_project.exception.ProductNotFoundException;
import com.example.product_project.repository.ProductRepository;
import com.example.product_project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    private Product sampleProduct1;
    private Product sampleProduct2;

    @BeforeEach
    void setUp() {
        sampleProduct1 = new Product(1L, "p1", "Sellable", "fc1", 10, 100);
        sampleProduct2 = new Product(2L, "p2", "Unfulfillable", "fc2", 20, 1000);
    }

    // Инициализация моков
    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        // Arrange
        List<Product> mockProducts = List.of(
                sampleProduct1,
                sampleProduct2
        );
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act
        List<Product> result = productService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getById_ProductExistsTest() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct1));

        // Act
        Product result = productService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("p1", result.getProduct_id());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getById_ProductNotFoundTest() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getById(1L)
        );
        assertEquals("product with id 1 doesn't not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByProductId() {
        // Arrange
        String productId = "p1";
        List<Product> productList = List.of(sampleProduct1);
        when(productRepository.findByProductId(productId)).thenReturn(productList);

        // Act
        List<Product> result = productService.getByProductId(productId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(sampleProduct1, result.get(0));
        verify(productRepository, times(1)).findByProductId(productId);
    }

    @Test
    void testSave_ValidProduct() {
        // Act
        productService.save(sampleProduct1);

        // Assert
        verify(productRepository, times(1)).save(sampleProduct1);
    }

    @Test
    void testSave_InvalidProduct_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(2L, "p2", "Sellable", "fc1", -1, 100);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.save(invalidProduct));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        // Act
        productService.delete(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByProductId_ProductExists() {
        // Arrange
        String productId = "p1";
        when(productRepository.findByProductId(productId)).thenReturn(List.of(sampleProduct1));

        // Act
        productService.deleteByProductId(productId);

        // Assert
        verify(productRepository, times(1)).deleteByProductId(productId);
    }

    @Test
    void testDeleteByProductId_ProductDoesNotExist() {
        // Arrange
        String productId = "p2";
        when(productRepository.findByProductId(productId)).thenReturn(List.of());

        // Act & Assert
        assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteByProductId(productId)
        );
        verify(productRepository, never()).deleteByProductId(any());
    }

    @Test
    void testGetTotalValueByStatusSellable() {
        // Arrange
        when(productRepository.getTotalValueByStatusSellable()).thenReturn(1000);

        // Act
        int totalValue = productService.getTotalValueByStatusSellable();

        // Assert
        assertEquals(1000, totalValue);
        verify(productRepository, times(1)).getTotalValueByStatusSellable();
    }

    @Test
    void testGetTotalValueByFulfillmentCenter() {
        // Arrange
        String fulfillmentCenter = "fc1";
        when(productRepository.getTotalValueByFulfillmentCenter(fulfillmentCenter)).thenReturn(500);

        // Act
        int totalValue = productService.getTotalValueByFulfillmentCenter(fulfillmentCenter);

        // Assert
        assertEquals(500, totalValue);
        verify(productRepository, times(1)).getTotalValueByFulfillmentCenter(fulfillmentCenter);
    }

    @Test
    void testDecreaseQuantityByProductId() {
        // Arrange
        String productId = "p2";
        when(productRepository.findByProductId(productId)).thenReturn(List.of(sampleProduct2));

        // Act
        productService.decreaseQuantityByProductId(productId);

        // Assert
        assertEquals(19, sampleProduct2.getQuantity());
        verify(productRepository, times(1)).saveAll(any());
    }

    @Test
    void testGetProductByStatus() {
        // Arrange
        String status = "Sellable";
        List<Product> productList = List.of(sampleProduct1);
        when(productRepository.getProductByStatus(status)).thenReturn(productList);

        // Act
        List<Product> result = productService.getProductByStatus(status);

        // Assert
        assertEquals(1, result.size());
        assertEquals(sampleProduct1, result.get(0));
        verify(productRepository, times(1)).getProductByStatus(status);
    }

    @Test
    void decreaseQuantityById_SuccessTest() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct1));

        // Act
        productService.decreaseQuantityById(1L);

        // Assert
        assertEquals(9, sampleProduct1.getQuantity());
        verify(productRepository, times(1)).save(sampleProduct1);
    }

    @Test
    void decreaseQuantityById_NoQuantityAvailableTest() {
        // Arrange
        Product mockProduct = new Product(1L, "p1", "Sellable", "fc1", 0, 500);
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct)); // Мок вернет объект с quantity = 0

        // Act & Assert
        NoQuantityAvailableException exception = assertThrows(
                NoQuantityAvailableException.class,
                () -> productService.decreaseQuantityById(1L) // Выполняем вызов
        );

        assertEquals(
                "the quantity of product with id 1 is equal to zero",
                exception.getMessage()
        );
        verify(productRepository, never()).save(any()); // Проверяем, что метод save не вызывался
    }

    @Test
    void update_SuccessTest() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct1));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct2);

        // Act
        productService.update(1L, sampleProduct2);

        // Assert
        assertEquals("p2", sampleProduct1.getProduct_id());
        assertEquals("Unfulfillable", sampleProduct1.getStatus());
        assertEquals("fc2", sampleProduct1.getFulfillment_center());
        assertEquals(20, sampleProduct1.getQuantity());
        assertEquals(1000, sampleProduct1.getValue());
        verify(productRepository, times(1)).save(sampleProduct1);
    }

    @Test
    void update_InvalidFieldsTest() {
        // Arrange
        Product invalidProduct = new Product(1L, null, null, null, -1, -100);

        // Act & Assert
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.update(1L, invalidProduct)
        );
        assertEquals("product with id 1 doesn't not exist", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
}
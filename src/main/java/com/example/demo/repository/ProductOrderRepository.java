package com.example.demo.repository;

import com.example.demo.ProductOrderPK;
import com.example.demo.entity.ProductOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderPK> {
    // ...

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductOrder po WHERE po.id.productId = :productId AND po.id.orderId = :orderId")
    void deleteProductOrder(int productId, int orderId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductOrder po SET po.quantity = :quantity, po.price = :price, po.var = :var WHERE po.id.productId = :productId AND po.id.orderId = :orderId")
    void updateProductOrder(int productId, int orderId, int quantity, BigDecimal price, BigDecimal var);


}

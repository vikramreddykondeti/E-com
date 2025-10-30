package com.intern.ecom_proj.repository;

import com.intern.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE CONCAT('%', LOWER(:keyword), '%') OR " +   // note trailing space
            "LOWER(p.description) LIKE CONCAT('%', LOWER(:keyword), '%') OR " +
            "LOWER(p.brand) LIKE CONCAT('%', LOWER(:keyword), '%') OR " +
            "LOWER(p.category) LIKE CONCAT('%', LOWER(:keyword), '%')")
   // List<Product> searchByKeyword(@Param("keyword") String keyword);

    List<Product> searchProduct(String keyword);
}

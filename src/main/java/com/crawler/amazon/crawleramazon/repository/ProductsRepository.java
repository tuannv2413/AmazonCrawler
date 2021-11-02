package com.crawler.amazon.crawleramazon.repository;

import com.crawler.amazon.crawleramazon.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
}

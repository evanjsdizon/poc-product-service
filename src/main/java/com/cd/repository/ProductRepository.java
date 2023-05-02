package com.cd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cd.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}

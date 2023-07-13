package com.mabaya.assignment.repository;

import com.mabaya.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "select * from product p inner join campaign_product cp on p.serial_number = cp.product_id inner join campaign c on c.id = cp.campaign_id where p.category=?1 and c.status = 'ACTIVE' order by c.bid desc limit 1",nativeQuery = true)

    Product getHighestBidProductByCategory(String category);

    @Query(value = "select * from product p inner join campaign_product cp on p.serial_number = cp.product_id inner join campaign c on c.id = cp.campaign_id where c.status = 'ACTIVE' order by c.bid desc limit 1",nativeQuery = true)
    Product getHighestBidProduct();


}

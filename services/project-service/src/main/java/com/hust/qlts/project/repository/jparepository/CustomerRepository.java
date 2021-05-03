package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "select * from customer", nativeQuery = true)
    List<CustomerEntity> findAllCustomer();
}

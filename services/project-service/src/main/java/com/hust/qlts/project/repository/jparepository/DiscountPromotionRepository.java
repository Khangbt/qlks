package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.DiscountPromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountPromotionRepository extends JpaRepository<DiscountPromotionEntity, Long> {
}
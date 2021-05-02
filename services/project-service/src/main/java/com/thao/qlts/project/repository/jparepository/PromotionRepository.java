package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.promotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository  extends JpaRepository<promotionEntity, Long> {
    @Query(value = "select * from promotion where promotion_code=?1 and STATUS= 1 ", nativeQuery = true)
    promotionEntity findByCode(String code);

    @Query(value = "select * from promotion where promotion_id=?1 and STATUS= 1 ", nativeQuery = true)
    promotionEntity findByID(Long id);
}

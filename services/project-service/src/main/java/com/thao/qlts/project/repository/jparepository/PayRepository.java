package com.thao.qlts.project.repository.jparepository;

import com.thao.qlts.project.entity.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<PayEntity,Long> {
}

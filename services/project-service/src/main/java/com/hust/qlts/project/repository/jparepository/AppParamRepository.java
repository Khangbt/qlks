package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.AppParamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppParamRepository extends JpaRepository<AppParamEntity, Long> {
}

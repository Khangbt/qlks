package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.GroupPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermissionEntity, Long> {
}

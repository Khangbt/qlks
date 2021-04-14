package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.RoomServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomServiceEntity, Long> {
}

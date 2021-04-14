package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.AssetRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRoomRepository extends JpaRepository<AssetRoomEntity, Long> {
}
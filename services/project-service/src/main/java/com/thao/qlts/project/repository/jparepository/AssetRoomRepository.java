package com.thao.qlts.project.repository.jparepository;

import com.thao.qlts.project.entity.AssetRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRoomRepository extends JpaRepository<AssetRoomEntity, Long> {
}

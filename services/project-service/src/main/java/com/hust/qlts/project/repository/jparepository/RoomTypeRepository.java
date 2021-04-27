package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.RoomEntity;
import com.hust.qlts.project.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {
    @Query(value = "select * from room_type where service_code=?1 and status= 1 ", nativeQuery = true)
    RoomTypeEntity findByCode(String code);

    @Query(value = "select * from room_type where service_id=?1 and status= 1 ", nativeQuery = true)
    RoomTypeEntity findByID(Long id);
}

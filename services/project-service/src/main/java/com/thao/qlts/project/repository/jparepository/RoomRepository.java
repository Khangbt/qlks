package com.thao.qlts.project.repository.jparepository;

import com.thao.qlts.project.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query(value = "select * from room where room_code=?1 and STATUS= 1 ", nativeQuery = true)
    RoomEntity findByCode(String code);

    @Query(value = "select * from room where room_id=?1 and STATUS= 1 ", nativeQuery = true)
    RoomEntity findByID(Long id);
}

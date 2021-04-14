package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.BookingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoomEntity, Long> {

}

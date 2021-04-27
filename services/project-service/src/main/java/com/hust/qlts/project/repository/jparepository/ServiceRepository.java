package com.hust.qlts.project.repository.jparepository;

import com.hust.qlts.project.entity.RoomTypeEntity;
import com.hust.qlts.project.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    @Query(value = "select * from service where code=?1 and status= 1 ", nativeQuery = true)
    ServiceEntity findByCode(String code);

    @Query(value = "select * from service where service_id=?1 and status= 1 ", nativeQuery = true)
    ServiceEntity findByID(Long id);
}

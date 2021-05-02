package com.thao.qlts.project.repository.customreporsitory;


import com.thao.qlts.project.dto.RoomDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomCustomRepository {
    private final Logger log = LogManager.getLogger(RoomCustomRepository.class);
    @Value("${valueDB}")
    private String valueDb;
    @Autowired
    private EntityManager em;

    public List<RoomDTO> searchAsser(RoomDTO dto){
        log.info("---------------------sql get kho nhan su------------------");

        StringBuilder sql = new StringBuilder();
        sql.append("select r.room_id,      " +
                " r.room_code,     " +
                "        r.room_name,     " +
                "        r.floor_number,     " +
                "        r.max_number,     " +
                "        r.note,     " +
                "        r.room_type     " +
                "from room r " +
                " where 1 = 1 and r.status = 1 "
        );

        if (StringUtils.isNotBlank(dto.getRoomCode())){
            sql.append("  and (( lower(r.room_code) LIKE :roomCode ) or ( lower(r.room_name) LIKE :roomCode ))");
        }
        if (dto.getRoomType() != null){
            sql.append(" and r.room_type = :roomType ");
        }


        Query query = em.createNativeQuery(sql.toString());
        Query queryCount = em.createNativeQuery(sql.toString());
        if (StringUtils.isNotBlank(dto.getRoomCode())){
            query.setParameter("roomCode", "%" + dto.getRoomCode() + "%");
            queryCount.setParameter("roomCode", "%" + dto.getRoomCode() + "%");
        }
        if (dto.getRoomType() != null){
            query.setParameter("roomType", dto.getRoomType());
            queryCount.setParameter("roomType", dto.getRoomType());
        }


        if (dto.getPage() != null && dto.getPageSize() != null) {
            query.setFirstResult((dto.getPage().intValue() - 1) * dto.getPageSize().intValue());
            query.setMaxResults(dto.getPageSize().intValue());
            dto.setTotalRecord((long) queryCount.getResultList().size());
        }

        List<Object[]> objectList = query.getResultList();
        return converEntytoDTO(objectList);
    }
    private List<RoomDTO> converEntytoDTO(List<Object[]> objects){
        List<RoomDTO> list = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(objects)) {
            for (Object[] obj : objects) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoomId(((BigInteger) obj[0]).longValue());
                roomDTO.setRoomCode((String) obj[1]);
                roomDTO.setRoomName((String) obj[2]);
                roomDTO.setFloorNumber((Integer) obj[3]);
                roomDTO.setMaxNumber((Integer) obj[4]);
                roomDTO.setNote((String) obj[5]);
                roomDTO.setRoomType((Integer) obj[6]);
                list.add(roomDTO);
            }
        }
        return list;
    }


}

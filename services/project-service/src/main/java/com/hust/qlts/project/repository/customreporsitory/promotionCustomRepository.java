package com.hust.qlts.project.repository.customreporsitory;

import com.hust.qlts.project.dto.promotionDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class promotionCustomRepository {
    private final Logger log = LogManager.getLogger(promotionCustomRepository.class);
    @Value("${valueDB}")
    private String valueDb;
    @Autowired
    private EntityManager em;

    public List<promotionDTO> searchPromotion(promotionDTO dto) {
        log.info("---------------------sql get khuye mai------------------");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.promotion_id,        " +
                "s.promotion_code,        " +
                "s.promotion_name,        " +
                "s.start_Date,  " +
                "  s.end_Date,    " +
                "s.note,        " +
                "s.status,        " +
                "s.percent_Promotion, "+
                "s.room_type_ID "+
                "FROM promotion s        " +
                " where 1 = 1 and s.status = 1  "
        );

//        if (StringUtils.isNotBlank(dto.getCode())){
//            sql.append("  and (( lower(r.room_code) LIKE :roomCode ) or ( lower(r.room_name) LIKE :roomCode ))");
//        }
//        if (dto.getRoomType() != null){
//            sql.append(" and r.room_type = :roomType ");
//        }

        Query query = em.createNativeQuery(sql.toString());
        Query queryCount = em.createNativeQuery(sql.toString());
//        if (StringUtils.isNotBlank(dto.getRoomCode())){
//            query.setParameter("roomCode", "%" + dto.getRoomCode() + "%");
//            queryCount.setParameter("roomCode", "%" + dto.getRoomCode() + "%");
//        }
//        if (dto.getRoomType() != null){
//            query.setParameter("roomType", dto.getRoomType());
//            queryCount.setParameter("roomType", dto.getRoomType());
//        }

        if (dto.getPage() != null && dto.getPageSize() != null) {
            query.setFirstResult((dto.getPage().intValue() - 1) * dto.getPageSize().intValue());
            query.setMaxResults(dto.getPageSize().intValue());
            dto.setTotalRecord((long) queryCount.getResultList().size());
        }

        List<Object[]> objectList = query.getResultList();
        return converEntytoDTO(objectList);
    }

    private List<promotionDTO> converEntytoDTO(List<Object[]> objects) {
        List<promotionDTO> list = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(objects)) {
            for (Object[] obj : objects) {
                promotionDTO dto = new promotionDTO();
                dto.setPromotionId(((BigInteger) obj[0]).longValue());
                dto.setPromotionCode(obj[1].toString());
                dto.setPromotionName(obj[2].toString());
                dto.setStartDate((Date) obj[3]);
                dto.setEndDate((Date) obj[4]);
                dto.setNote(obj[5].toString());
                dto.setStatus((Integer) obj[6]);
                dto.setPercentPromotion((Integer) obj[7]);
//                dto.setRoomTypeID(((BigInteger)  obj[8]).longValue());

                list.add(dto);
            }
        }
        return list;
    }
}

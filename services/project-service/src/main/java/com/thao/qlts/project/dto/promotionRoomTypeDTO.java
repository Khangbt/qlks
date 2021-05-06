package com.thao.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class promotionRoomTypeDTO extends AuditingDTO<String> {

    private Long promotionRoomTypeId;

    private Long roomTypeID;

    private Long promotion;

    private Integer isActive;
}


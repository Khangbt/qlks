package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomTypeDTO extends AuditingDTO<String>{
    private Long roomTypeId;

    private Long code;

    private Long name;

    private Double dayPrice;

    private Long nightPrice;

    private Long hourPrice;
}

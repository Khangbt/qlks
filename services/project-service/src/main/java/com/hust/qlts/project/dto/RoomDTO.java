package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomDTO extends AuditingDTO<String>{
    private Long roomId;

    private Integer max_number;

    private Integer floor_number;

    private String room_code;

    private Integer status;

    private Integer room_type;

    private Integer note;
}

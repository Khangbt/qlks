package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDTO extends AuditingDTO<String>{
    private Long serviceId;

    private Integer service_code;

    private Integer service_name;

    private Double price;

    private String unit;

    private String note;
}

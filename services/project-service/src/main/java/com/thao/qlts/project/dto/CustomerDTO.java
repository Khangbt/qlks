package com.thao.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDTO extends AuditingDTO<String>{

    private Long customerId;

    private String fullname;

    private String cmt;

    private String phone_number;

    private String email;

    private String address;

    private Integer status;
}


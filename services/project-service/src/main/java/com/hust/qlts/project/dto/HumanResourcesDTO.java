package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HumanResourcesDTO extends AuditingDTO<String>{
    private Long humanResourceId;

    private String code;

    private String fullName;

    private String email;

    private Integer status;

    private String note;

    private String username;

    private String password;

    private Integer isNew;

    private String verifyKey;

    private String phone;

    private Date dateOfBirth;

    private String role;

}

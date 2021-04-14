package com.hust.qlts.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "code")
    private String code;

    @Column(name = "cmt")
    private String cmt;

    @Column(name = "contract_number")
    private String contract_number;

    @Column(name = "tax_code")
    private String tax_code;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "position")
    private String position;

    @Column(name = "address")
    private String address;

    @Column(name = "is_new")
    private Integer isNew;

    @Column(name = "verify_key")
    private String verifyKey;
    @Transient
    public List<GrantedAuthority> authorities;
}

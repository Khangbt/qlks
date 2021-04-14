package com.hust.qlts.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "human_resources")

@NoArgsConstructor

public class HumanResourcesEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "human_resource_id")
    private Long humanResourceId;

    @Column(name = "code")
    private String code;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Integer status;

    @Column(name = "note")
    private String note;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_new")
    private Integer isNew;

    @Column(name = "verify_key")
    private String verifyKey;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "cmt")
    private String cmt;

    @Column(name = "address")
    private String address;

    @Column(name = "ROLE")
    private String role;
    @Transient
    public List<GrantedAuthority> authorities;

}
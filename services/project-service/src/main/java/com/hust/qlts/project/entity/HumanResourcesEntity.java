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

public class HumanResourcesEntity extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HUMAN_RESOURCES_ID")
    private Long humanResourceId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_NEW")
    private Integer isNew;

    @Column(name = "VERIFY_KEY")
    private String verifyKey;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "ROLE")
    private String role;
    @Transient
    public List<GrantedAuthority> authorities;

}

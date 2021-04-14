package com.hust.qlts.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name ="service")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_code")
    private Integer service_code;

    @Column(name = "service_name")
    private Integer service_name;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "note")
    private String note;
}

package com.hust.qlts.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "max_number")
    private Integer max_number;

    @Column(name = "room_name")
    private String room_name;

    @Column(name = "floor_number")
    private Integer floor_number;

    @Column(name = "room_code")
    private String room_code;

    @Column(name = "status")
    private Integer status;

    @Column(name = "room_type")
    private Integer room_type;

    @Column(name = "note")
    private Integer note;

    //verson
}

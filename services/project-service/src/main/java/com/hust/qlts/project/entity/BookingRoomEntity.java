package com.hust.qlts.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "booking_room")
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoomEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_room_id")
    private Long bookingroomId;

    @Column(name = "custormer_id")
    private Long customerId;

    @Column(name = "employee_id")
    private Long EmployeeId;

    @Column(name = "booking_date")
    private Date booking_date;

    @Column(name = "booking_checkin")
    private Date booking_checkin;

    @Column(name = "booking_checkout")
    private Date booking_checkout;

    @Column(name = "advance_amount")
    private Double advance_amount;

    @Column(name = "old_room_code")
    private Long old_room_code;

    @Column(name = "status")
    private Integer status;
}

package com.hust.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookingRoomDTO extends AuditingDTO<String>{

    private Long bookingroomId;

    private Long customerId;

    private Long EmployeeId;

    private Date booking_date;

    private Date booking_checkin;

    private Date booking_checkout;

    private Double advance_amount;

    private Long old_room_code;

    private Integer status;
}

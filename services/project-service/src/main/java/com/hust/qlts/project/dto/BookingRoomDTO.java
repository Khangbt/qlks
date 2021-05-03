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

    private Long bookingDate;

    private Long bookingDateOut;

    private Long bookingCheckin;

    private Long bookingCheckout;

    private Double advanceAmount;

    private Long oldRoomCode;

    private Integer status;

    private Long roomId;

    private Long bookingType;

    private String promotionCode;
}

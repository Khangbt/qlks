package com.thao.qlts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookingRoomDTO extends AuditingDTO<String>{

    private Long bookingroomId;

    private Long customerId;

    private Long EmployeeId;

    private Date bookingDate;

    private Date bookingDateOut;

    private Date bookingCheckin;

    private Date bookingCheckout;

    private Double advanceAmount;

    private Long oldRoomCode;

    private Integer status;

    private Long roomId;

    private Long bookingType;

    private String promotionCode;

    private List<BookingRoomServiceDTO> listService;
}

package com.thao.qlts.project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PayDto {
    private Long idBooking;
    private List<BookingRoomServiceDTO> list;
    private List<TimeBookDTO> timeBookDTOList;
    private Long EmployeeId;
    private String nameEmployee;
    private Date bookingDate;
    private Date bookingDateOut;
    private Date bookingCheckin;
    private Date bookingCheckout;
    private Long promotionId;
}

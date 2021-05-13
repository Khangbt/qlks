package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.dto.PayDto;
import com.thao.qlts.project.entity.BookingRoomEntity;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;

public class PayServiceImpl implements PayService {
    @Autowired
    private BookingRoomService bookingRoomService;
    @Override
    public PayDto getServiceRoom(Long bookRoomId) {
        PayDto payDto=new PayDto();
        BookingRoomEntity bookingRoomEntity=bookingRoomService.getIdBookRoom(bookRoomId);

        String listArray=bookingRoomEntity.getOldBookRoom();


        return null;
    }
}

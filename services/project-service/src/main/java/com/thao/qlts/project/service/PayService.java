package com.thao.qlts.project.service;

import com.thao.qlts.project.dto.PayDto;

import java.util.List;

public interface PayService {
    PayDto getServiceRoom(Long bookRoomId);
    boolean billBookroom(PayDto payDto);
    List<?> getList(String date,Integer status);
}

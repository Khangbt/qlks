package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.repository.jparepository.BookingRoomRepository;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.mapper.BookingRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "bookingRoomService")
public class BookingRoomServiceImpl implements BookingRoomService {
    @Autowired
    private BookingRoomMapper bookingRoomMapper;
    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    @Override
    public BookingRoomDTO add(BookingRoomDTO bookingRoomDTO) {
        return bookingRoomMapper.toDto(bookingRoomRepository.save(bookingRoomMapper.toEntity(bookingRoomDTO)));
    }
}

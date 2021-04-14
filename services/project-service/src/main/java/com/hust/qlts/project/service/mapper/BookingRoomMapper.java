package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.BookingRoomDTO;
import com.hust.qlts.project.entity.BookingRoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingRoomMapper extends EntityMapper<BookingRoomDTO, BookingRoomEntity>{
}

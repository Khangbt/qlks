package com.hust.qlts.project.service;

import com.hust.qlts.project.dto.DataPage;
import com.hust.qlts.project.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    DataPage<RoomDTO> searchAsser(RoomDTO dto);
    DataPage<RoomDTO> getPagePartSeach(RoomDTO dto);
    RoomDTO create(RoomDTO dto);
    RoomDTO update(RoomDTO dto);
    RoomDTO delete (Long id);
    RoomDTO findById(Long Id);
    RoomDTO findByCode(String code);
    DataPage<RoomDTO> onSearch(RoomDTO dto);
    List<RoomDTO> getAll();

}

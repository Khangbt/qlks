package com.hust.qlts.project.service;

import com.hust.qlts.project.dto.DataPage;
import com.hust.qlts.project.dto.RoomTypeDTO;

import java.util.List;

public interface RoomTypeService {
    DataPage<RoomTypeDTO> searchRoom(RoomTypeDTO dto);
    DataPage<RoomTypeDTO> getPagePartSeach(RoomTypeDTO dto);
    RoomTypeDTO create(RoomTypeDTO dto);
    RoomTypeDTO update(RoomTypeDTO dto);
    RoomTypeDTO delete (Long id);
    RoomTypeDTO findById(Long Id);
    RoomTypeDTO findByCode(String code);
    List<RoomTypeDTO> getRomtype();
}

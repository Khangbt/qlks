package com.hust.qlts.project.service.impl;

import com.hust.qlts.project.dto.RoomDTO;
import com.hust.qlts.project.dto.DataPage;
import com.hust.qlts.project.dto.RoomDTO;
import com.hust.qlts.project.entity.AssetEntity;
import com.hust.qlts.project.entity.RoomEntity;
import com.hust.qlts.project.repository.customreporsitory.AssetCustomRepository;
import com.hust.qlts.project.repository.customreporsitory.RoomCustomRepository;
import com.hust.qlts.project.repository.jparepository.AssetRepository;
import com.hust.qlts.project.repository.jparepository.RoomRepository;
import com.hust.qlts.project.service.RoomService;
import common.ErrorCode;
import exception.CustomExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomCustomRepository roomCustomRepository;
    @Autowired
    RoomRepository roomRepository;

    @Override
    public DataPage<RoomDTO> searchAsser(RoomDTO dto) {
        DataPage<RoomDTO> dtoDataPage = new DataPage<>();

        dto.setPage(null != dto.getPage() ? dto.getPage().intValue() : 1);
        dto.setPageSize(null != dto.getPageSize() ? dto.getPageSize().intValue() : 10);
        List<RoomDTO> list = new ArrayList<>();
        try {
            list = roomCustomRepository.searchAsser(dto);
            dtoDataPage.setData(list);

        }catch (Exception e){
            throw e;
        }
        dtoDataPage.setPageIndex(dto.getPage());
        dtoDataPage.setPageSize(dto.getPageSize());
        dtoDataPage.setDataCount(dto.getTotalRecord());
        dtoDataPage.setPageCount(dto.getTotalRecord() / dto.getPageSize());
        if (dtoDataPage.getDataCount() % dtoDataPage.getPageSize() != 0) {
            dtoDataPage.setPageCount(dtoDataPage.getPageCount() + 1);
        }
        return dtoDataPage;
    }


    @Override
    public DataPage<RoomDTO> getPagePartSeach(RoomDTO dto) {
        return null;
    }

    @Override
    public RoomDTO create(RoomDTO dto) {
        RoomEntity roomEntity = roomRepository.findByID(dto.getRoomId());

        if (null != roomEntity && dto.getRoomId() == null) {
            throw new CustomExceptionHandler(ErrorCode.CREATED_HR_FALSE.getCode(), HttpStatus.BAD_REQUEST);
        }
        else if (null != roomEntity) {
            //TODO: Update  phong
            roomEntity.setRoomId(Long.valueOf(dto.getRoomId()));
            roomEntity.setMaxNumber(dto.getMaxNumber());
            roomEntity.setRoomCode(dto.getRoomCode());
            roomEntity.setRoomName(dto.getRoomName());
            roomEntity.setFloorNumber(dto.getFloorNumber());
            roomEntity.setRoomType(dto.getRoomType());
            roomEntity.setNote(dto.getNote());
            roomEntity.setStatus(1);
        }
        else if (dto.getRoomId() == null) {
            //TODO: create phong
            roomEntity = new RoomEntity();
            roomEntity.setMaxNumber(dto.getMaxNumber());
            roomEntity.setRoomCode(dto.getRoomCode());
            roomEntity.setRoomName(dto.getRoomName());
            roomEntity.setFloorNumber(dto.getFloorNumber());
            roomEntity.setRoomType(dto.getRoomType());
            roomEntity.setNote(dto.getNote());
            roomEntity.setStatus(1);
        }
        roomRepository.save(roomEntity);
        return convertEntitytoDTO(roomEntity);

    }

    @Override
    public RoomDTO update(RoomDTO partnerDTO) {
        return null;
    }

    @Override
    public RoomDTO delete(Long id) {
        RoomDTO dto =  convertEntitytoDTO(roomRepository.findById(id).get());
        if (null == dto) {
            throw new CustomExceptionHandler(ErrorCode.CREATED_HR_FALSE.getCode(), HttpStatus.BAD_REQUEST);
        }else{
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setRoomId(Long.valueOf(dto.getRoomId()));
            roomEntity.setStatus(0);
            roomRepository.save(roomEntity);
            return dto;
        }
    }

    @Override
    public RoomDTO findById(Long Id) {
        return convertEntitytoDTO(roomRepository.findById(Id).get());

    }

    @Override
    public RoomDTO findByCode(String code) {
        return null;
    }

    public RoomDTO convertEntitytoDTO(RoomEntity roomEntity) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomId(roomEntity.getRoomId());
        dto.setRoomCode(roomEntity.getRoomCode());
        dto.setRoomName(roomEntity.getRoomName());
        dto.setMaxNumber(roomEntity.getMaxNumber());
        dto.setRoomType(roomEntity.getRoomType());
        dto.setStatus(roomEntity.getStatus());
        dto.setNote(roomEntity.getNote());
        dto.setFloorNumber(roomEntity.getFloorNumber());
        return dto;
    }
}

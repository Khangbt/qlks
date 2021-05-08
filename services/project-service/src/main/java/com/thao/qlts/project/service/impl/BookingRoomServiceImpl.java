package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.dto.BookingRoomServiceDTO;
import com.thao.qlts.project.dto.DataPage;
import com.thao.qlts.project.dto.RoomDTO;
import com.thao.qlts.project.entity.BookingRoomEntity;
import com.thao.qlts.project.entity.BookingRoomServiceEntity;
import com.thao.qlts.project.repository.customreporsitory.BookingRoomCustomRepository;
import com.thao.qlts.project.repository.jparepository.*;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.mapper.BookingRoomMapper;
import com.thao.qlts.project.service.mapper.BookingRoomServiceMapper;
import common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "bookingRoomService")
public class BookingRoomServiceImpl implements BookingRoomService {
    @Autowired
    private BookingRoomMapper bookingRoomMapper;
    @Autowired
    private BookingRoomRepository bookingRoomRepository;
    @Autowired
    private BookingRoomCustomRepository bookingRoomCustomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRoomServiceRepository bookingRoomServiceRepository;
    @Autowired
    private BookingRoomServiceMapper bookingRoomServiceMapper;

    @Override
    public BookingRoomDTO add(BookingRoomDTO bookingRoomDTO) {
        return bookingRoomMapper.toDto(bookingRoomRepository.save(bookingRoomMapper.toEntity(bookingRoomDTO)));
    }

    @Override
    public DataPage<BookingRoomDTO> onSearch(BookingRoomDTO dto) {
        DataPage<BookingRoomDTO> dtoDataPage = new DataPage<>();
        dto.setPage(null != dto.getPage() ? dto.getPage().intValue() : 1);
        dto.setPageSize(null != dto.getPageSize() ? dto.getPageSize().intValue() : 10);
        List<BookingRoomDTO> list;
        try {
            list = bookingRoomCustomRepository.onSearch(dto);
            for (BookingRoomDTO bookingRoomDTO : list){
                if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingDate())){
                    bookingRoomDTO.setComein_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingDate()));
                }
                if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingDateOut())){
                    bookingRoomDTO.setComeout_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingDateOut()));
                }
                if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getStatus())){
                    if (bookingRoomDTO.getStatus() == 1){
                        bookingRoomDTO.setStatusName("Đã đặt");
                    }else if (bookingRoomDTO.getStatus() == 2){
                        bookingRoomDTO.setStatusName("Đang đặt");
                    }else if (bookingRoomDTO.getStatus() == 3){
                        bookingRoomDTO.setStatusName("Đã thanh toán");
                    }else if (bookingRoomDTO.getStatus() == 4){
                        bookingRoomDTO.setStatusName("Đã hủy");
                    }
                }
            }
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
    public ResultResp addService(BookingRoomDTO dto) {
        if (CommonUtils.isEqualsNullOrEmpty(dto.getBookingroomId())){
            return ResultResp.badRequest(new ObjectError("BK001", "Lỗi không tìm thấy mã đặt phòng"));
        }else {
            BookingRoomEntity curr = bookingRoomRepository.findById(dto.getRoomId()).get();
            List<BookingRoomServiceEntity> listBookingService = bookingRoomServiceRepository.findByBookingId(curr.getBookingroomId());
            if (CommonUtils.isEqualsNullOrEmpty(listBookingService) && listBookingService.size() > 0){
                bookingRoomServiceRepository.deleteAll(listBookingService);
            }
            if (CommonUtils.isEqualsNullOrEmpty(dto.getListService()) && dto.getListService().size() > 0){
                bookingRoomServiceRepository.saveAll(bookingRoomServiceMapper.toEntity(dto.getListService()));
            }
            return ResultResp.success("Thêm mới dịch vụ thành công");
        }
    }

    @Override
    public List<BookingRoomServiceDTO> getServiceByBookingId(Long bookingId) {
        return bookingRoomServiceMapper.toDto(bookingRoomServiceRepository.findByBookingId(bookingId));
    }
}
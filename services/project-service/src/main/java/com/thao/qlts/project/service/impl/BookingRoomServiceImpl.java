package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.controller.roomController;
import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.dto.BookingRoomServiceDTO;
import com.thao.qlts.project.dto.DataPage;
import com.thao.qlts.project.entity.BookingRoomEntity;
import com.thao.qlts.project.entity.BookingRoomServiceEntity;
import com.thao.qlts.project.entity.RoomEntity;
import com.thao.qlts.project.repository.customreporsitory.BookingRoomCustomRepository;
import com.thao.qlts.project.repository.jparepository.BookingRoomRepository;
import com.thao.qlts.project.repository.jparepository.BookingRoomServiceRepository;
import com.thao.qlts.project.repository.jparepository.RoomRepository;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.mapper.BookingRoomMapper;
import com.thao.qlts.project.service.mapper.BookingRoomServiceMapper;
import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private BookingRoomServiceRepository bookingRoomServiceRepository;
    @Autowired
    private BookingRoomServiceMapper bookingRoomServiceMapper;
    private final Logger logger = LogManager.getLogger(BookingRoomServiceImpl.class);

    @Override
    public ResultResp add(BookingRoomDTO bookingRoomDTO) {
        List<BookingRoomEntity> listEntity = null;
        if (CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingroomId())){
            logger.info("Thêm mới lịch đặt phòng khách sạn");
            if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_CURRENT)){
                logger.info("Đặt phòng lẻ, phòng số "+bookingRoomDTO.getRoomId());
                listEntity = bookingRoomRepository.checkExistAdd(
                        bookingRoomDTO.getRoomId(),
                        bookingRoomDTO.getBookingCheckin(),
                        bookingRoomDTO.getBookingCheckout());
            }else if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_FUTURE)){
                logger.info("Đặt phòng trước, phòng số "+bookingRoomDTO.getRoomId());
                listEntity = bookingRoomRepository.checkExistAdd(
                        bookingRoomDTO.getRoomId(),
                        bookingRoomDTO.getBookingDate(),
                        bookingRoomDTO.getBookingDateOut());
            }
            if (!CommonUtils.isEqualsNullOrEmpty(listEntity) && listEntity.size() > 0){
                logger.error("Lỗi đặt phòng: Thời gian đặt phòng trùng với thời gian đã đặt trong hệ thống");
                return ResultResp.serverError(new ObjectError("Exists","Đặt phòng thất bại, Thời gian đặt phòng đã trùng so với thời gian đặt trong hệ thống, vui lòng kiểm tra lại"));
            }else {
                BookingRoomEntity bookingEntity = bookingRoomMapper.toEntity(bookingRoomDTO);
                RoomEntity roomEntity = roomRepository.findById(bookingEntity.getRoomId()).get();
                if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_CURRENT)){
                    bookingEntity.setStatus(Enums.BOOKING_TYPE.DANG_DAT.value());
                    roomEntity.setStatus(Enums.ROOM_TYPE.DANG_DAT_PHONG.value());
                    roomRepository.save(roomEntity);
                }else if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_FUTURE)){
                    bookingEntity.setStatus(Enums.BOOKING_TYPE.DA_DAT.value());
                }
                bookingRoomRepository.save(bookingEntity);
                logger.info("Thêm mới lịch đặt phòng thành công");
                return ResultResp.success(new ObjectSuccess("Complete","Đặt phòng thành công"));
            }
        }else {
            BookingRoomEntity currEntity = bookingRoomRepository.findById(bookingRoomDTO.getBookingroomId()).get();
            if (currEntity.getRoomId().equals(bookingRoomDTO.getRoomId())){
                logger.info("Cập nhật lịch đặt phòng khách sạn");
                if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_CURRENT)){
                    logger.info("Cập nhật lịch đặt phòng lẻ, phòng số "+bookingRoomDTO.getRoomId());
                    listEntity = bookingRoomRepository.checkExistUpdate(
                            bookingRoomDTO.getRoomId(),
                            bookingRoomDTO.getBookingCheckin(),
                            bookingRoomDTO.getBookingCheckout(),
                            bookingRoomDTO.getBookingroomId());
                }else if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_FUTURE)){
                    logger.info("Cập nhật lịch đặt phòng trước, phòng số "+bookingRoomDTO.getRoomId());
                    listEntity = bookingRoomRepository.checkExistUpdate(
                            bookingRoomDTO.getRoomId(),
                            bookingRoomDTO.getBookingDate(),
                            bookingRoomDTO.getBookingDateOut(),
                            bookingRoomDTO.getBookingroomId());
                }
                if (!CommonUtils.isEqualsNullOrEmpty(listEntity) && listEntity.size() > 0){
                    logger.error("Lỗi cập nhật: Thời gian đặt phòng trùng với thời gian đã đặt trong hệ thống");
                    return ResultResp.serverError(new ObjectError("Exists","Cập nhật thất bại, Thời gian đặt phòng đã trùng so với thời gian đặt trong hệ thống, vui lòng kiểm tra lại"));
                }else {
                    BookingRoomEntity bookingEntity = bookingRoomMapper.toEntity(bookingRoomDTO);
                    bookingRoomRepository.save(bookingEntity);
                    logger.info("Cập nhật lịch đặt phòng thành công");
                    return ResultResp.success(new ObjectSuccess("Complete","Cập nhật đặt phòng thành công"));
                }
            }else {
                logger.info("Chuyển phòng khách sạn");
                if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_CURRENT)){
                    logger.info("Chuyển phòng khách sạn, phòng số "+currEntity.getRoomId()+" sang phòng số "+bookingRoomDTO.getRoomId());
                    listEntity = bookingRoomRepository.checkExistAdd(
                            bookingRoomDTO.getRoomId(),
                            bookingRoomDTO.getBookingCheckin(),
                            bookingRoomDTO.getBookingCheckout());
                }else if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_FUTURE)){
                    logger.info("Cập nhật thông tin đặt phòng trước, chuyển phòng số "+currEntity.getRoomId()+" sang phòng số "+bookingRoomDTO.getRoomId());
                    listEntity = bookingRoomRepository.checkExistAdd(
                            bookingRoomDTO.getRoomId(),
                            bookingRoomDTO.getBookingDate(),
                            bookingRoomDTO.getBookingDateOut());
                }
                if (!CommonUtils.isEqualsNullOrEmpty(listEntity) && listEntity.size() > 0){
                    logger.error("Lỗi chuyển phòng: Thời gian đặt phòng trùng với thời gian đã đặt trong hệ thống");
                    return ResultResp.serverError(new ObjectError("Exists","Chuyển phòng thất bại, Thời gian đặt phòng đã trùng so với thời gian đặt trong hệ thống, vui lòng kiểm tra lại"));
                }else {
                    if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_CURRENT)){
                        //currentEntity: findById(dto)
                        BookingRoomEntity newEntity = bookingRoomMapper.toEntity(bookingRoomDTO);
                        RoomEntity newRoom = roomRepository.findById(newEntity.getRoomId()).get();
                        newEntity.setStatus(Enums.BOOKING_TYPE.DANG_DAT.value());
                        newRoom.setStatus(Enums.ROOM_TYPE.DANG_DAT_PHONG.value());
                        String oldBookRoom = "";
                        if (CommonUtils.isEqualsNullOrEmpty(currEntity.getOldBookRoom())){
                            oldBookRoom = currEntity.getBookingroomId()+"";
                        }else {
                            oldBookRoom = currEntity.getOldBookRoom()+","+currEntity.getBookingroomId();
                        }
                        newEntity.setOldBookRoom(oldBookRoom);

                        currEntity.setBookingCheckout(newEntity.getBookingCheckin());
                        RoomEntity currRoom = roomRepository.findById(currEntity.getRoomId()).get();
                        currEntity.setStatus(Enums.BOOKING_TYPE.DA_CHUYEN.value());
                        currRoom.setStatus(Enums.ROOM_TYPE.HOAT_DONG.value());

                        bookingRoomRepository.save(currEntity);
                        bookingRoomRepository.save(newEntity);
                        roomRepository.save(currRoom);
                        roomRepository.save(newRoom);

                        logger.info("Chuyển phòng thành công");
                        return ResultResp.success(new ObjectSuccess("Complete","Chuyển phòng thành công"));
                    }else if (bookingRoomDTO.getBookType().equals(Constants.BOOKING_TYPE_FUTURE)){
                        BookingRoomEntity bookingEntity = bookingRoomMapper.toEntity(bookingRoomDTO);
                        bookingRoomRepository.save(bookingEntity);
                        logger.info("Cập nhật lịch đặt phòng thành công");
                        return ResultResp.success(new ObjectSuccess("Complete","Cập nhật thông tin đặt phòng thành công"));
                    }
                }
            }
        }
        return ResultResp.badRequest(new ObjectError("aaaa", "aaaaaaaaa"));
    }

    @Override
    public DataPage<BookingRoomDTO> onSearch(BookingRoomDTO dto) {
        DataPage<BookingRoomDTO> dtoDataPage = new DataPage<>();
        dto.setPage(null != dto.getPage() ? dto.getPage().intValue() : 1);
        dto.setPageSize(null != dto.getPageSize() ? dto.getPageSize().intValue() : 10);
        List<BookingRoomDTO> list;
        try {
            list = bookingRoomCustomRepository.onSearch(dto);
            for (BookingRoomDTO bookingRoomDTO : list) {
                if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingDate())) {
                    bookingRoomDTO.setComein_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingDate()));
                    if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingDateOut())){
                        bookingRoomDTO.setComeout_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingDateOut()));
                    }
                }else {
                    bookingRoomDTO.setComein_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingCheckin()));
                    if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getBookingCheckout())){
                        bookingRoomDTO.setComeout_timeshow(DateUtils.formatDateTime(bookingRoomDTO.getBookingCheckout()));
                    }
                }
                if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomDTO.getStatus())) {
                    if (bookingRoomDTO.getStatus() == 1) {
                        bookingRoomDTO.setStatusName("Đã đặt");
                    } else if (bookingRoomDTO.getStatus() == 2) {
                        bookingRoomDTO.setStatusName("Đang đặt");
                    } else if (bookingRoomDTO.getStatus() == 3) {
                        bookingRoomDTO.setStatusName("Đã thanh toán");
                    } else if (bookingRoomDTO.getStatus() == 4) {
                        bookingRoomDTO.setStatusName("Đã hủy");
                    }
                }
            }
            dtoDataPage.setData(list);
        } catch (Exception e) {
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
        if (CommonUtils.isEqualsNullOrEmpty(dto.getBookingroomId())) {
            return ResultResp.badRequest(new ObjectError("BK001", "Lỗi không tìm thấy mã đặt phòng"));
        } else {
            BookingRoomEntity curr = bookingRoomRepository.findById(dto.getRoomId()).get();
            List<BookingRoomServiceEntity> listBookingService = bookingRoomServiceRepository.findByBookingId(curr.getBookingroomId());
            if (CommonUtils.isEqualsNullOrEmpty(listBookingService) && listBookingService.size() > 0) {
                bookingRoomServiceRepository.deleteAll(listBookingService);
            }
            if (CommonUtils.isEqualsNullOrEmpty(dto.getListService()) && dto.getListService().size() > 0) {
                bookingRoomServiceRepository.saveAll(bookingRoomServiceMapper.toEntity(dto.getListService()));
            }
            return ResultResp.success("Thêm mới dịch vụ thành công");
        }
    }

    @Override
    public List<BookingRoomServiceDTO> getServiceByBookingId(Long bookingId) {
        return bookingRoomServiceMapper.toDto(bookingRoomServiceRepository.findByBookingId(bookingId));
    }

    @Override
    public ResultResp receive(Long bookingRoomId) {
        if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomId)){
            BookingRoomEntity bookingEntity = bookingRoomRepository.findById(bookingRoomId).get();
            if (!CommonUtils.isEqualsNullOrEmpty(bookingEntity.getRoomId())){
                RoomEntity roomEntity = roomRepository.findById(bookingEntity.getRoomId()).get();
                bookingEntity.setStatus(Enums.BOOKING_TYPE.DANG_DAT.value());
                roomEntity.setStatus(Enums.ROOM_TYPE.DANG_DAT_PHONG.value());
                bookingRoomRepository.save(bookingEntity);
                roomRepository.save(roomEntity);
                return ResultResp.success(new ObjectSuccess("Complete","Nhận phòng thành công!"));
            }else {
                return ResultResp.serverError(new ObjectError("Error","Không tồn tại số phòng đã đặt"));
            }
        }else {
            return ResultResp.serverError(new ObjectError("Error","Không tồn tại bản ghi trong hệ thống"));
        }
    }

    @Override
    public ResultResp delete(Long bookingRoomId) {
        if (!CommonUtils.isEqualsNullOrEmpty(bookingRoomId)){
            BookingRoomEntity bookingEntity = bookingRoomRepository.findById(bookingRoomId).get();
            bookingEntity.setStatus(Enums.BOOKING_TYPE.DA_HUY.value());
            bookingRoomRepository.save(bookingEntity);
            return ResultResp.success(new ObjectSuccess("Complete","Xóa lịch đặt phòng thành công!"));
        }else {
            return ResultResp.serverError(new ObjectError("Error","Không tồn tại bản ghi trong hệ thống"));
        }
    }

    @Override
    public BookingRoomDTO getInfo(Long bookingRoomId) {
        BookingRoomEntity bookingEntity = bookingRoomRepository.findById(bookingRoomId).get();
        if (!CommonUtils.isEqualsNullOrEmpty(bookingEntity)){
            return bookingRoomMapper.toDto(bookingEntity);
        }
        return null;
    }

    @Override
    public BookingRoomEntity getIdBookRoom(Long bookingRoomId) {
        if(!bookingRoomRepository.findById(bookingRoomId).isPresent()){
            return bookingRoomRepository.findById(bookingRoomId).get();
        }
        return null;
    }
}
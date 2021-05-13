package com.thao.qlts.project.service.impl;

<<<<<<< HEAD
import com.thao.qlts.project.dto.BookingRoomServiceDTO;
import com.thao.qlts.project.dto.PayDto;
import com.thao.qlts.project.dto.TimeBookDTO;
import com.thao.qlts.project.entity.*;
import com.thao.qlts.project.repository.jparepository.DiscountPromotionRepository;
import com.thao.qlts.project.repository.jparepository.PayRepository;
import com.thao.qlts.project.repository.jparepository.RoomRepository;
import com.thao.qlts.project.repository.jparepository.RoomTypeRepository;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private BookingRoomService bookingRoomService;

    @Autowired
    private PayRepository payRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DiscountPromotionRepository discountPromotionRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public PayDto getServiceRoom(Long bookRoomId) {
        PayDto payDto = new PayDto();
        BookingRoomEntity bookingRoomEntity = bookingRoomService.getIdBookRoom(bookRoomId);

        String listArray = bookingRoomEntity.getOldBookRoom();
        List<Long> list = new ArrayList<>();
        list.add(bookRoomId);
        if (listArray != null) {
            String[] s = listArray.split(",");
            Arrays.asList(s).forEach(s1 -> list.add(Long.valueOf(s1)));
        }

        payDto.setBookingCheckin(bookingRoomEntity.getBookingCheckin());
        payDto.setBookingCheckout(bookingRoomEntity.getBookingCheckout());
        payDto.setBookingDate(bookingRoomEntity.getBookingDate());
        payDto.setBookingDateOut(bookingRoomEntity.getBookingDateOut());
        payDto.setEmployeeId(bookingRoomEntity.getEmployeeId());

        //list
        List<TimeBookDTO> timeBookDTOS = new ArrayList<>();
        List<BookingRoomEntity> entities = bookingRoomService.getListBook(list);

        entities.forEach(bookingRoomEntity1 -> {
            TimeBookDTO timeBookDTO = new TimeBookDTO();
            timeBookDTO.setStart(bookingRoomEntity1.getBookingCheckin());
            timeBookDTO.setEnd(bookingRoomEntity1.getBookingCheckout());
            long time = bookingRoomEntity1.getBookingCheckout().getTime() - bookingRoomEntity1.getBookingCheckin().getTime();
            timeBookDTO.setIdRoom(bookingRoomEntity1.getRoomId());
            timeBookDTO.setIdBookRoom(bookingRoomEntity1.getBookingroomId());
            timeBookDTO.setTypeBook(bookingRoomEntity1.getBookingType());
            double aDouble = 0;
            switch (bookingRoomEntity1.getBookingType()) {
                case 1:
                    aDouble = (double) (time / 3600000);
                    break;
                case 2:
                    aDouble = (double) (time / 1800000);
                    break;
                default:
                    aDouble = (double) (time / 6000);
                    break;
            }
            if (roomTypeRepository.getType(bookingRoomEntity1.getRoomId()).size() > 0) {
                RoomTypeEntity roomTypeEntity = roomTypeRepository.getType(bookingRoomEntity1.getRoomId()).get(0);
                timeBookDTO.setDayPrice(roomTypeEntity.getDayPrice());
                timeBookDTO.setHourPrice(roomTypeEntity.getHourPrice());
                timeBookDTO.setNightPrice(roomTypeEntity.getNightPrice());
            }
            timeBookDTO.setUnit(aDouble);
            timeBookDTOS.add(timeBookDTO);
        });

        List<BookingRoomServiceDTO> dtos = new ArrayList<>();
        List<BookingRoomServiceEntity> entityList = bookingRoomService.getListService(list);
        entityList.forEach(entity -> {
            BookingRoomServiceDTO dto = new BookingRoomServiceDTO();
            dto.setQuantity(entity.getQuantity());
            dto.setPrice(entity.getPrice());
            dto.setServiceId(entity.getServiceId());
            dto.setBookingId(entity.getBookingId());
            dtos.add(dto);
        });
        payDto.setListService(dtos);
        payDto.setTimeBookDTOList(timeBookDTOS);
        payDto.setIdBooking(bookRoomId);


        if (payRepository.findById(bookRoomId).isPresent()) {
            PayEntity dto = payRepository.findById(bookRoomId).get();
            payDto.setIdBooking(dto.getIdBookingRoom());
            payDto.setAdvanceManey(dto.getAdvanceManey());
            payDto.setPayChang(dto.getPayChang());
            payDto.setSumBookRoom(dto.getSumBookRoom());
            payDto.setDiscountId(dto.getIdDiscount());
            payDto.setDatePay(dto.getDatePay());
            if (discountPromotionRepository.findById(dto.getIdDiscount()).isPresent()) {
                DiscountPromotionEntity promotionEntity = discountPromotionRepository.findById(dto.getIdDiscount()).get();
                payDto.setCodeDiscount(promotionEntity.getCode());
            }
        }

        return payDto;
    }

    @Override
    public boolean billBookroom(PayDto payDto) {

        BookingRoomEntity bookingRoomEntity = bookingRoomService.getIdBookRoom(payDto.getIdBooking());
        bookingRoomEntity.setStatus(3);

        if (roomRepository.findById(bookingRoomEntity.getRoomId()).isPresent()) {
            RoomEntity roomEntity = roomRepository.findByID(bookingRoomEntity.getRoomId());
            roomEntity.setStatus(1);
            roomRepository.save(roomEntity);
        }
        PayEntity payEntity = new PayEntity();
        Long bookRoomId = payDto.getIdBooking();
        payEntity.setIdBookingRoom(bookRoomId);
        payEntity.setAdvanceManey(payDto.getAdvanceManey());
        payEntity.setPayChang(payDto.getPayChang());
        payEntity.setIdDiscount(payDto.getIdDiscount());
        payEntity.setStartBook(payDto.getBookingDate());
        payEntity.setDatePay(new Date());
        try {
            bookingRoomService.addEntity(bookingRoomEntity);
            payRepository.save(payEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<?> getList(String date, Integer status) {
=======
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
>>>>>>> update code


        return null;
    }
}

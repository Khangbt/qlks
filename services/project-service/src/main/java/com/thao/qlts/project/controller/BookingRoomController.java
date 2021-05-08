package com.thao.qlts.project.controller;

import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.dto.BookingRoomServiceDTO;
import com.thao.qlts.project.dto.HumanResourcesDTO;
import com.thao.qlts.project.dto.RoomDTO;
import com.thao.qlts.project.service.BookingRoomService;
import common.ErrorCode;
import common.ResultResp;
import exception.CustomExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingRoomController {
    private final Logger logger = LogManager.getLogger(roomController.class);
    @Autowired
    private BookingRoomService bookingRoomService;

    @PostMapping("/add")
    public ResultResp createBooking(@RequestBody BookingRoomDTO bookingRoomDTO) {
        logger.info("Add booking room");
        try {
            return ResultResp.success(bookingRoomService.add(bookingRoomDTO));
        } catch (CustomExceptionHandler e) {
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }
    }

    @PostMapping("/onSearch")
    public ResponseEntity<List<BookingRoomDTO>> onSearch(@RequestBody BookingRoomDTO bookingRoomDTO){
        logger.info("----------------search booking room-----------------");
        try {
            logger.info("----------------api search booking room Ok-----------------");
            return new ResponseEntity(bookingRoomService.onSearch(bookingRoomDTO), HttpStatus.OK);
        }catch (Exception e){
            logger.info("----------------api search booking room thất bại-----------------");
            throw  e;
        }
    }

    @PostMapping("/addService")
    public ResultResp addService(@RequestBody BookingRoomDTO bookingRoomDTO) {
        logger.info("Add service booking room");
        return bookingRoomService.addService(bookingRoomDTO);
    }

    @GetMapping("/getAllServiceBooking/{bookingId}")
    public ResponseEntity<List<BookingRoomServiceDTO>> getServiceByBookingId(@PathVariable Long bookingRoomId){
        logger.info("----------------get Service by booking room Id-----------------");
        try {
            logger.info("----------------Start-----------------");
            return new ResponseEntity(bookingRoomService.getServiceByBookingId(bookingRoomId), HttpStatus.OK);
        }catch (Exception e){
            logger.info("----------------Error-----------------");
            throw  e;
        }
    }
}

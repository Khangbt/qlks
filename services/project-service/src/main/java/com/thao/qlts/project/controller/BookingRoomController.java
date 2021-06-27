package com.thao.qlts.project.controller;

import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.dto.BookingRoomServiceDTO;
import com.thao.qlts.project.dto.PayDto;
import com.thao.qlts.project.service.BookingRoomService;
import com.thao.qlts.project.service.PayService;
import common.ErrorCode;
import common.ResultResp;
import exception.CustomExceptionHandler;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingRoomController {
    private final Logger logger = LogManager.getLogger(roomController.class);
    @Autowired
    private BookingRoomService bookingRoomService;
    @Autowired
    private PayService payService;
    @PostMapping("/add")
    public ResultResp createBooking(@RequestBody BookingRoomDTO bookingRoomDTO) {
        logger.info("Add booking room");
        try {
            logger.info("Add booking room, room "+bookingRoomDTO.getRoomId());
            return bookingRoomService.add(bookingRoomDTO);
        } catch (CustomExceptionHandler e) {
            logger.error("Add booking room fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @PutMapping("/receive")
    public ResultResp receiveBooking(@RequestBody BookingRoomDTO bookingRoomDTO) {
        try {
            logger.info("Receive booking room, id "+bookingRoomDTO.getBookingroomId());
            return bookingRoomService.receive(bookingRoomDTO.getBookingroomId());
        } catch (CustomExceptionHandler e) {
            logger.error("Receive booking room fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @GetMapping("/getInfo/{bookingRoomId}")
    public ResultResp receiveBooking(@PathVariable Long bookingRoomId) {
        try {
            return ResultResp.success(bookingRoomService.getInfo(bookingRoomId));
        } catch (CustomExceptionHandler e) {
            logger.error("Get by ID booking room fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{bookingRoomId}")
    public ResultResp deleteBooking(@PathVariable Long bookingRoomId) {
        logger.info("Delete booking room");
        try {
            return bookingRoomService.delete(bookingRoomId);
        } catch (CustomExceptionHandler e) {
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @PostMapping("/onSearch")
    public ResponseEntity<List<BookingRoomDTO>> onSearch(@RequestBody BookingRoomDTO bookingRoomDTO) {
        try {
            return new ResponseEntity(bookingRoomService.onSearch(bookingRoomDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Search booking room fail by error: "+e.getMessage());
            throw e;
        }
    }

    @PostMapping("/addService")
    public ResultResp addService(@RequestBody BookingRoomDTO bookingRoomDTO) {
        try {
            logger.info("Add service for booking room, id "+bookingRoomDTO.getBookingroomId());
            return bookingRoomService.addService(bookingRoomDTO);
        } catch (Exception e) {
            logger.error("Add service for booking room, id "+bookingRoomDTO.getBookingroomId()+" fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }

    }

    @GetMapping("/getAllServiceBooking/{bookingId}")
    public ResponseEntity<List<BookingRoomServiceDTO>> getServiceByBookingId(@PathVariable Long bookingRoomId) {
        try {
            return new ResponseEntity(bookingRoomService.getServiceByBookingId(bookingRoomId), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Get service by booking room, id "+bookingRoomId+" fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @GetMapping("/getPay/{bookingRoomId}")
    public ResponseEntity<PayDto> getPay(@PathVariable Integer bookingRoomId) {
        try {
            return new ResponseEntity(payService.getServiceRoom(Long.valueOf(bookingRoomId)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Pay for booking room, id "+bookingRoomId+" fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }
    @PostMapping("/discountPay/{bookingRoomId}")
    public ResponseEntity<PayDto> billPlease(@RequestBody PayDto payDto) {
        try {
            return new ResponseEntity(payService.billBookroom(payDto), HttpStatus.OK);
        } catch (Exception e) {
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }

    @PutMapping("/getService/{bookingRoomId}")
    public ResponseEntity<PayDto> getService(@PathVariable PayDto payDto) {
        try {
            return new ResponseEntity(payService.billBookroom(payDto), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Get service for booking room fail by error: "+e.getMessage());
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }
    @PostMapping("/getChart")
    public ResponseEntity<?> getDataChart(@RequestBody Char aChar){
        try {
            return new ResponseEntity(payService.getList(aChar.quy,aChar.nam), HttpStatus.OK);
        } catch (Exception e) {
            return ResultResp.serverError(ErrorCode.SERVER_ERROR);
        }
    }
}

@Data
class Char{
    List<Integer> quy;
    Integer nam;
}
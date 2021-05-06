package com.thao.qlts.project.controller;

import com.thao.qlts.project.dto.BookingRoomDTO;
import com.thao.qlts.project.dto.HumanResourcesDTO;
import com.thao.qlts.project.service.BookingRoomService;
import common.ErrorCode;
import common.ResultResp;
import exception.CustomExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
}

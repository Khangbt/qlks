package com.hust.qlts.project.controller;

import com.hust.qlts.project.service.BookingRoomService;
import common.ErrorCode;
import common.ResultResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingRoomController {
    private final Logger log = LogManager.getLogger(roomController.class);
    @Autowired
    private BookingRoomService bookingRoomService;
}

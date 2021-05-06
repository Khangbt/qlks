package com.thao.qlts.project.controller;

import com.thao.qlts.project.service.CustomerService;
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
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
    private final Logger log = LogManager.getLogger(roomController.class);
    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-all-customer")
    private ResultResp getAllCustomer(){
        log.info("get all customer");
        try {
            return ResultResp.success(ErrorCode.CREATED_HR_OK, customerService.getAllCustomer());
        } catch (Exception e) {
            log.error("<--- api find AssetResources: error, ");
            e.printStackTrace();
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }
    }
}

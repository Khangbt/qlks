package com.thao.qlts.project.controller;

import com.thao.qlts.project.dto. ServiceDTO;
import com.thao.qlts.project.entity.RoomTypeEntity;
import com.thao.qlts.project.service.ServiceService;
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
@RequestMapping("/service")
@CrossOrigin("*")
public class ServiceController {
    private final Logger log = LogManager.getLogger(roomController.class);
    @Autowired
    ServiceService searchService;

    @PostMapping("/searchService")
    public ResponseEntity<List<RoomTypeEntity>> searchAseer(@RequestBody  ServiceDTO dto){
        log.info("----------------api searchAsser-----------------");
        try {
            log.info("----------------api searchRoom Ok-----------------");

            return new ResponseEntity(searchService.searchService(dto), HttpStatus.OK);

        }catch (Exception e){
            log.info("----------------api searchRoom thất bại-----------------");

            throw  e;
        }
    }
    @PostMapping("/add")
    public ResultResp createHR(@RequestBody  ServiceDTO dto, HttpServletRequest request) {
        log.info("----------------api addService-----------------");
        try {
            return ResultResp.success(ErrorCode.CREATED_HR_OK, searchService.create(dto));

        } catch (CustomExceptionHandler e) {
            if (e.getMsgCode().equalsIgnoreCase(ErrorCode.CREATED_HR_EXIST.getCode()))
                return ResultResp.badRequest(ErrorCode.CREATED_HR_EXIST);
        }
        return ResultResp.badRequest(ErrorCode.CREATED_HR_FALSE);
    }

    @GetMapping("/get-service-by-id/{id}")
    public ResultResp getOneById(@PathVariable("id") Long id) {
        log.info("<-- api updateRoom: start, ", id);
        try {
            return ResultResp.success(searchService.findById(id));

        } catch (CustomExceptionHandler e) {
            return ResultResp.badRequest(ErrorCode.USERNAME_NOT_FOUND);
        } catch (Exception e) {
            log.error("<--- api find AssetResources: error, ");
            e.printStackTrace();
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }

    }
    @GetMapping("/deleteService/{id}")
    public ResultResp deleteProject(@PathVariable("id") Long id,HttpServletRequest request) {
        log.info("----------------api delete phong -----------------");
        try {
            return ResultResp.success(searchService.delete(id));
        } catch (CustomExceptionHandler e) {
            log.info("----------------api delete nhan su faile-----------------");
            return ResultResp.badRequest(ErrorCode.DELETE_HR_FAIL);
        }
    }
    @GetMapping("/getAllService")
    public ResultResp getAllService() {
        log.info("----------------get all service-----------------");
        try {
            return ResultResp.success(searchService.findAllService());
        } catch (CustomExceptionHandler e) {
            log.info("----------------api delete nhan su faile-----------------");
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }
    }
}


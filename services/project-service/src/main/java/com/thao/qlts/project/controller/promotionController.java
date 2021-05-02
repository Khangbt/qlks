package com.hust.qlts.project.controller;

import com.hust.qlts.project.dto.promotionDTO;
import com.hust.qlts.project.service.PromotionService;
import com.hust.qlts.project.service.RoomService;
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
@RequestMapping("/promotion")
@CrossOrigin("*")public class promotionController {
    private final Logger log = LogManager.getLogger(promotionController.class);
    @Autowired
    PromotionService service;

    @PostMapping("/searchPromotion")
    public ResponseEntity<List<promotionDTO>> searchPromotion(@RequestBody promotionDTO promotionDTO){
        log.info("----------------api searchPromotion-----------------");
        try {
            log.info("----------------api searchRoom Ok-----------------");

            return new ResponseEntity(service.searchPromotion(promotionDTO), HttpStatus.OK);

        }catch (Exception e){
            log.info("----------------api searchRoom thất bại-----------------");

            throw  e;
        }
    }
    @PostMapping("/add")
    public ResultResp createHR(@RequestBody promotionDTO partnerDTO, HttpServletRequest request) {
        log.info("----------------api addPromotion-----------------");
        try {
            return ResultResp.success(ErrorCode.CREATED_HR_OK, service.create(partnerDTO));

        } catch (CustomExceptionHandler e) {
            if (e.getMsgCode().equalsIgnoreCase(ErrorCode.CREATED_HR_EXIST.getCode()))
                return ResultResp.badRequest(ErrorCode.CREATED_HR_EXIST);
        }
        return ResultResp.badRequest(ErrorCode.CREATED_HR_FALSE);
    }

    @GetMapping("/get-promotion-by-id/{id}")
    public ResultResp getOneById(@PathVariable("id") Long id) {
        log.info("<-- api updatePromotion: start, ", id);
        try {
            return ResultResp.success(service.findById(id));
        } catch (CustomExceptionHandler e) {
            return ResultResp.badRequest(ErrorCode.USERNAME_NOT_FOUND);
        } catch (Exception e) {
            log.error("<--- api find PromotionResources: error, ");
            e.printStackTrace();
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }
    }
    @GetMapping("/deletePromotion/{id}")
    public ResultResp deleteProject(@PathVariable("id") Long id,HttpServletRequest request) {
        log.info("----------------api delete phong -----------------");
        try {
            return ResultResp.success(service.delete(id));

        } catch (CustomExceptionHandler e) {
            log.info("----------------api delete nhan su faile-----------------");
            return ResultResp.badRequest(ErrorCode.DELETE_HR_FAIL);
        }
    }
}

package com.hust.qlts.project.controller;



import com.hust.qlts.project.common.CoreUtils;
import com.hust.qlts.project.dto.AssetDTO;
import com.hust.qlts.project.service.AssetService;
import common.ErrorCode;
import common.ResultResp;
import exception.CustomExceptionHandler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset")
@CrossOrigin("*")
public class AssetControler {
    private final Logger log = LogManager.getLogger(HumanResourcesController.class);

    @Autowired
    AssetService assetService;

    @PostMapping ("/searchAseet")
    public ResponseEntity<List<AssetDTO>> searchAseer(@RequestBody AssetDTO assetDTO){
        log.info("----------------api searchAsser-----------------");
        try {
            log.info("----------------api searchAsser Ok-----------------");

            return new ResponseEntity(assetService.searchAsser(assetDTO), HttpStatus.OK);

        }catch (Exception e){
            log.info("----------------api searchAsser thất bại-----------------");

            throw  e;
        }
    }
    @PostMapping("/add")
    public ResultResp createHR(@RequestBody AssetDTO partnerDTO, HttpServletRequest request) {
        log.info("----------------api searchAsser-----------------");


        try {
            return ResultResp.success(ErrorCode.CREATED_HR_OK, assetService.create(partnerDTO));

        } catch (CustomExceptionHandler e) {
            if (e.getMsgCode().equalsIgnoreCase(ErrorCode.CREATED_HR_EXIST.getCode()))
                return ResultResp.badRequest(ErrorCode.CREATED_HR_EXIST);
        }
        return ResultResp.badRequest(ErrorCode.CREATED_HR_FALSE);
    }

}

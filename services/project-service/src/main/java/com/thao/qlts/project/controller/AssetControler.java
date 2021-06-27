package com.thao.qlts.project.controller;

import com.thao.qlts.project.dto.AssetDTO;
import com.thao.qlts.project.service.AssetService;
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
@RequestMapping("/asset")
@CrossOrigin("*")
public class AssetControler {
    private final Logger log = LogManager.getLogger(AssetControler.class);
    @Autowired
    AssetService assetService;
    @PostMapping ("/searchAseet")
    public ResponseEntity<List<AssetDTO>> searchAseer(@RequestBody AssetDTO assetDTO){
        try {
            return new ResponseEntity(assetService.searchAsser(assetDTO), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR: "+e.getMessage());
            throw e;
        }
    }
    @PostMapping("/add")
    public ResultResp createAssert(@RequestBody AssetDTO assetDTO, HttpServletRequest request) {
        try {
            log.info("Add asset: Code "+assetDTO.getAssetCode()+", Name "+assetDTO.getAssetname());
            AssetDTO dto = assetService.create(assetDTO);
            if (dto != null){
                log.info("Add asset "+dto.getAssetname()+" success");
            }
            return ResultResp.success(ErrorCode.CREATED_SUCCESS, dto);
        } catch (CustomExceptionHandler e) {
            log.error("ERROR: "+e.getMessage());
        }
        return ResultResp.badRequest(ErrorCode.CREATED_FALSE);
    }

    @GetMapping("/get-asset-by-id/{id}")
    public ResultResp getOneById(@PathVariable("id") Long id) {
        try {
            return ResultResp.success(assetService.findById(id));
        } catch (Exception e) {
            log.error("Get by ID Asset ERROR: "+e.getMessage());
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }

    }
    @GetMapping("/deleteAsset/{id}")
    public ResultResp deleteProject(@PathVariable("id") Long id,HttpServletRequest request) {
        log.info("Deletee asset: ID "+id);
        try {
            AssetDTO dto = assetService.delete(id);
            if (dto != null){
                log.error("Delete asset "+dto.getAssetname()+" success!");
            }
            return ResultResp.success(dto);
        } catch (CustomExceptionHandler e) {
            log.info("Delete asset fail by error: "+e.getMessage());
            return ResultResp.badRequest(ErrorCode.DELETE_FAIL);
        }
    }

    @GetMapping("/get-asset")
    public ResultResp getAsset() {
        try {
            return ResultResp.success(assetService.getAsset());
        } catch (Exception e) {
            log.error("API get-asset fail, ERRROR: "+ e.getMessage());
            return ResultResp.badRequest(ErrorCode.SERVER_ERROR);
        }

    }
}

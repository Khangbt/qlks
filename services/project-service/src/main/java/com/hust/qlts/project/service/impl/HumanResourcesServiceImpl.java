package com.hust.qlts.project.service.impl;

import com.hust.qlts.project.dto.DTOSearch;
import com.hust.qlts.project.dto.HistoryDTO;
import com.hust.qlts.project.dto.HumanResourcesDTO;
import com.hust.qlts.project.dto.ICusTomDto;
import com.hust.qlts.project.entity.HumanResourcesEntity;
import com.hust.qlts.project.repository.jparepository.HumanResourcesRepository;
import com.hust.qlts.project.service.HumanResourcesService;
import common.ErrorCode;
import common.ResultResp;
import exception.CustomExceptionHandler;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "humanResourcesService")
public class HumanResourcesServiceImpl implements HumanResourcesService, UserDetailsService {
    @Autowired
    private HumanResourcesRepository repository;
    @Override
    public List<HumanResourcesDTO> getListHumanResourceByNameOrCode(DTOSearch dto) {
        return null;
    }

    @Override
    public HumanResourcesDTO getUserInfo(String username) {

        HumanResourcesEntity humanResourcesEntity = repository.findByEmail2(username);
        HumanResourcesDTO humanResourcesDTO = new HumanResourcesDTO();
        BeanUtils.copyProperties(humanResourcesEntity, humanResourcesDTO);


        return humanResourcesDTO;
    }

    @Override
    public HumanResourcesDTO create(String username, HumanResourcesDTO humanResourcesDTO) {
        return null;
    }

    @Override
    public void sendMailChangeEmail(HumanResourcesDTO humanResourcesDTO, HumanResourcesEntity oldEmail) {

    }

    @Override
    public HumanResourcesDTO update(HumanResourcesDTO humanResourcesDTO) {
        return null;
    }

    @Override
    public HumanResourcesDTO findById(Long Id) {
        return null;
    }

    @Override
    public List<HistoryDTO> getHumanHistory() {
        return null;
    }

    @Override
    public List<HistoryDTO> getHumanHistoryById(Long Id) {
        return null;
    }

    @Override
    public HumanResourcesDTO findByCode(String code) {
        return null;
    }

    @Override
    public List<HumanResourcesDTO> findByEmail(String email) {
        return null;
    }

    @Override
    public HumanResourcesDTO getByEmail(String email) {
        return null;
    }

    @Override
    public ResultResp resetPassword(Long userID, String usernameAdmin) {
        return null;
    }

    @Override
    public Boolean deleteHumanResources(Long id, String name) {
        return null;
    }

    @Override
    public Boolean lockHumanResources(Long id, String name) {
        return null;
    }

    @Override
    public Integer getActiveFromHumanResourceId(Long id) {
        return null;
    }

    @Override
    public List<HumanResourcesDTO> getHumanResources(DTOSearch dto) {
        return null;
    }

    @Override
    public Long changePassword(HumanResourcesDTO humanResourcesDTO) {
        return null;
    }

    @Override
    public Long checkPassword(HumanResourcesDTO humanResourcesDTO) {
        return null;
    }

    @Override
    public List<HumanResourcesDTO> getListHumanResources(Long projectId) {
        return null;
    }

    @Override
    public String getLeaderNameFromProject(Long projectId) {
        return null;
    }

    @Override
    public byte[] importExcel(MultipartFile file) throws IOException {
        return new byte[0];
    }

    @Override
    public List<ICusTomDto> listAll() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HumanResourcesEntity humanResourcesEntity = repository.findByEmail2(email);
        if (humanResourcesEntity == null) {
            throw new CustomExceptionHandler(ErrorCode.USERNAME_NOT_FOUND.getCode(), HttpStatus.UNAUTHORIZED);
        }
        List<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority(humanResourcesEntity.getRole()));
        humanResourcesEntity.setAuthorities(roleList);
        return new org.springframework.security.core.userdetails.User(humanResourcesEntity.getEmail(), humanResourcesEntity.getPassword(), humanResourcesEntity.getAuthorities());
    }
}

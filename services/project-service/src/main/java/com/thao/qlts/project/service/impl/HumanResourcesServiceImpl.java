package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.dto.*;
import com.thao.qlts.project.entity.HumanResourcesEntity;
import com.thao.qlts.project.repository.jparepository.HumanResourcesRepository;
import com.thao.qlts.project.repository.jparepository.PositionRepository;
import com.thao.qlts.project.service.HumanResourcesService;
import com.thao.qlts.project.service.mapper.HumanResourcesMapper;
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
import com.thao.qlts.project.repository.customreporsitory.HumanResourcesCustomRepository;
//import com.hust.qlts.project.

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "humanResourcesService")
public class HumanResourcesServiceImpl implements HumanResourcesService, UserDetailsService {
    @Autowired
    private HumanResourcesRepository repository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private HumanResourcesMapper humanResourcesMapper;
    @Autowired
    private HumanResourcesCustomRepository customRepository;
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
    public List<IPositionDTO> position() {
        return positionRepository.getPosition();
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
    public DataPage<HumanResourcesShowDTO> getPageHumanResourcesSeach(HumanResourcesShowDTO dto) {
        DataPage<HumanResourcesShowDTO> data = new DataPage<>();
        dto.setPage(null != dto.getPage() ? dto.getPage().intValue() : 1);
        dto.setPageSize(null != dto.getPageSize() ? dto.getPageSize().intValue() : 10);
        List<HumanResourcesShowDTO> listProject;
        if (CollectionUtils.isNotEmpty(customRepository.getlistHumanResources(dto))) {
            listProject = customRepository.getlistHumanResources(dto);
            data.setData(listProject);
        }
        data.setPageIndex(dto.getPage());
        data.setPageSize(dto.getPageSize());
        data.setDataCount(dto.getTotalRecord());
        data.setPageCount(dto.getTotalRecord() / dto.getPageSize());
        if (data.getDataCount() % data.getPageSize() != 0) {
            data.setPageCount(data.getPageCount() + 1);
        }
        return data;
    }

    @Override
    public HumanResourcesDTO findById(Long Id) {
        if (!repository.findById(Id).isPresent()) {
            throw new CustomExceptionHandler(ErrorCode.USERNAME_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST);
        }
        if(!repository.findById(Id).isPresent()){
            return null;
        }
        HumanResourcesEntity entity = repository.findById(Id).get();
        HumanResourcesDTO dto = humanResourcesMapper.toDto(repository.findById(Id).get());
        return humanResourcesMapper.toDto(repository.findById(Id).get());
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
    public void sendMailResetPassword(HumanResourcesDTO humanResourcesDTO, String password) {

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
        List<HumanResourcesDTO> HumanResourcesList = customRepository.getHumanResources(dto);
        if (CollectionUtils.isNotEmpty(HumanResourcesList)) {
            return HumanResourcesList;
        } else {
            return new ArrayList<>();
        }
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
        List<HumanResourcesDTO> listDTO = humanResourcesMapper.toDto(repository.getListHumanResources(projectId));
        return listDTO;
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

package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.controller.HumanResourcesController;
import com.thao.qlts.project.dto.*;
import com.thao.qlts.project.entity.HumanResourcesEntity;
import com.thao.qlts.project.repository.customreporsitory.HumanResourcesCustomRepository;
import com.thao.qlts.project.repository.jparepository.HumanResourcesRepository;
import com.thao.qlts.project.repository.jparepository.PositionRepository;
import com.thao.qlts.project.service.HumanResourcesService;
import com.thao.qlts.project.service.mapper.HumanResourcesMapper;
import common.ErrorCode;
import common.HistoryConstants;
import common.ResultResp;
import exception.CustomExceptionHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service(value = "humanResourcesService")
public class HumanResourcesServiceImpl implements HumanResourcesService, UserDetailsService {
    @Autowired
    private HumanResourcesRepository repository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private HumanResourcesMapper humanResourcesMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;
    @Autowired
    private JavaMailSender javaMailSender;
    @Qualifier("freeMarkerConfiguration")
    @Autowired
    private Configuration config;
    @Autowired
    private HumanResourcesCustomRepository customRepository;
    private final Logger logger = LogManager.getLogger(HumanResourcesController.class);
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
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = CustomExceptionHandler.class)
    public HumanResourcesDTO create(String username, HumanResourcesDTO humanResourcesDTO) {
        HumanResourcesEntity humanResourcesEntity = repository.findByCode(humanResourcesDTO.getCode());
        if (null != humanResourcesEntity && humanResourcesDTO.getHumanResourceId() == null) {
            throw new CustomExceptionHandler(ErrorCode.CREATED_HR_FALSE.getCode(), HttpStatus.BAD_REQUEST);
        } else if (null != humanResourcesEntity) {
            logger.info("<--- Service UPDATE human-resources Start");
            HumanResourcesEntity oldEntity = new HumanResourcesEntity();
            oldEntity.setHumanResourceId(humanResourcesEntity.getHumanResourceId());
            oldEntity.setCode(humanResourcesEntity.getCode());
            oldEntity.setEmail(humanResourcesEntity.getEmail());
            oldEntity.setFullName(humanResourcesEntity.getFullName());
            oldEntity.setPositionId(humanResourcesEntity.getPositionId());
            oldEntity.setStatus(humanResourcesEntity.getStatus());
            oldEntity.setNote(humanResourcesEntity.getNote());
            oldEntity.setPhone(humanResourcesEntity.getPhone());
            oldEntity.setDateOfBirth(humanResourcesEntity.getDateOfBirth());
            oldEntity.setCmt(humanResourcesEntity.getCmt());
            oldEntity.setAddress(humanResourcesEntity.getAddress());
            oldEntity.setContractCode(humanResourcesEntity.getContractCode());
            oldEntity.setTaxCode(humanResourcesEntity.getTaxCode());

            humanResourcesEntity.setCode(humanResourcesDTO.getCode());
            humanResourcesEntity.setEmail(humanResourcesDTO.getEmail());
            humanResourcesEntity.setFullName(humanResourcesDTO.getFullName());
            humanResourcesEntity.setPositionId(humanResourcesDTO.getPositionId());
            humanResourcesEntity.setStatus(humanResourcesDTO.getStatus());
            humanResourcesEntity.setNote(humanResourcesDTO.getNote());
            humanResourcesEntity.setPhone(humanResourcesDTO.getPhone());
            humanResourcesEntity.setDateOfBirth(humanResourcesDTO.getDateOfBirth());
            humanResourcesEntity.setCmt(humanResourcesDTO.getCmt());
            humanResourcesEntity.setAddress(humanResourcesDTO.getAddress());
            humanResourcesEntity.setContractCode(humanResourcesDTO.getContractCode());
            humanResourcesEntity.setTaxCode(humanResourcesDTO.getTaxCode());
            /*check email changed*/
            if (!oldEntity.getEmail().equals(humanResourcesEntity.getEmail())) {
                sendMailChangeEmail(humanResourcesDTO, oldEntity);
            }
//            humanHistory(username, oldEntity, humanResourcesEntity);
        } else if (humanResourcesDTO.getHumanResourceId() == null) {
            humanResourcesEntity = humanResourcesMapper.toEntity(humanResourcesDTO);
            String usernameFE = humanResourcesDTO.getUsername();
            humanResourcesEntity.setUsername(usernameFE);
            humanResourcesEntity = repository.save(humanResourcesEntity);
            /*lưu lịch vào bảng lịch sử*/
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setValueId(humanResourcesEntity.getHumanResourceId());
            historyDTO.setAction(HistoryConstants.ACTION_ADD);
            historyDTO.setTypeScreen(HistoryConstants.SCREEN_HUMAN_RESOURCES);
            historyDTO.setValueNew(humanResourcesDTO.toJSON());
//            historyService.saveHistoryCommon(historyDTO);
        }
        humanResourcesEntity = repository.save(humanResourcesEntity);
        return humanResourcesMapper.toDto(humanResourcesEntity);
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
        logger.info("---> RESET PASSWORD: UserID " + userID + " confirm reset password START");
        if(!repository.findById(userID).isPresent()){
            return null;
        }
        HumanResourcesEntity humanResource = repository.findById(userID).get();
        String olPassWord = humanResource.getPassword();
        // generate random password
        String SALTCHARS = "ABCDEefgh!@ijklFGH123IJKL!@#$MNOPQRS012345TUVWXYZabcdmnopqrstuvwxyz6789%^&*";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();
        while (salt.length() < 15) { // length of the random string.
            logger.info("salt.length()" + salt.length());
            int lengthStr =SALTCHARS.length();
            salt.append(SALTCHARS.charAt(rand.nextInt(lengthStr)));
        }
        String saltStr = salt.toString();
        try {
            humanResource.setPassword(passwordEncode.encode(saltStr));
        } catch (Exception ex) {
            logger.error("<--- Reset Password Fail by Error: ", ex.getMessage());

        }
        repository.save(humanResource);
        sendMailResetPassword(humanResourcesMapper.toDto(humanResource),saltStr);
        logger.info("<--- Reset Password Complete");
        logger.info("<--- Send email notification to user start!");
        return  ResultResp.success(ErrorCode.RESET_PASSWORD_OK);
    }

    @Async("asyncExecutor")
    @Override
    public void sendMailResetPassword(HumanResourcesDTO humanResource, String password){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            String email = humanResource.getEmail();
            Map<String, Object> models = new HashMap<>();
            models.put("link", "http://localhost:9003/#/system-categories/");
            models.put("fullname", humanResource.getFullName());
            models.put("email", email);
            models.put("newpass", password);

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template template = config.getTemplate("resetPassword.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, models);

            helper.setTo(email);
            helper.setSubject("[ACTVN-QLKS] THÔNG TIN TÀI KHOẢN HỆ THỐNG QUẢN LÝ KHÁCH SẠN");
            helper.setText(html, true);
            javaMailSender.send(message);
            logger.info("<--- Send email success!");
            /*return ResultResp.success(humanResource);*/
        } catch (MessagingException | MailException | TemplateException | IOException  ex) {
            logger.error("Send email notification fail by Error ", ex.getMessage());

        }
    }

    @Override
    public Boolean deleteHumanResources(Long id, String name) {
        logger.info("-----------------Xoa nhan su---------------");
        HumanResourcesEntity humanResourcesEntity = repository.findByHumanResourceId(id);
        humanResourcesEntity.setStatus(3);
        repository.save(humanResourcesEntity);
//            humanHistoryCustom(name, 5, humanResourcesEntity, null);
        logger.info("<--- DELETE HUMAN_RESOURCES COMPLETE");
        return true;
    }

    @Override
    public Boolean lockHumanResources(Long id, String name) {
        logger.info("-----------------khoa nhan su---------------");
        if (id != null) {
            HumanResourcesEntity humanResourcesEntity = repository.findByHumanResourceId(id);
            if (humanResourcesEntity.getStatus() == 2) {
                humanResourcesEntity.setStatus(1);
                repository.save(humanResourcesEntity);
                /*humanHistoryCustom(name, 7, humanResourcesEntity, null);
                log.info("<--- Unlock Human Resources with id = " + id);*/
                return true;
            } else if (humanResourcesEntity.getStatus() == 1) {
                humanResourcesEntity.setStatus(2);
                repository.save(humanResourcesEntity);
                /*humanHistoryCustom(name, 6, humanResourcesEntity, null);
                log.info("<--- LOCK HUMAN_RESOURCES COMPLETE");*/
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getActiveFromHumanResourceId(Long id) {
        if(!repository.findById(id).isPresent()){
            return null;
        }
        return repository.findById(id).get().getStatus();
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

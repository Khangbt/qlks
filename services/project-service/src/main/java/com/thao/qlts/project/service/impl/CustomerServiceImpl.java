package com.thao.qlts.project.service.impl;

import com.thao.qlts.project.dto.CustomerDTO;
import com.thao.qlts.project.repository.jparepository.CustomerRepository;
import com.thao.qlts.project.service.CustomerService;
import com.thao.qlts.project.service.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> getAllCustomer() {
        return customerMapper.toDto(repository.findAllCustomer());
    }
}
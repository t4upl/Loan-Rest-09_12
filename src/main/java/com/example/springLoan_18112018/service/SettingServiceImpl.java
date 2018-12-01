package com.example.springLoan_18112018.service;

import com.example.springLoan_18112018.model.Setting;
import com.example.springLoan_18112018.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingServiceImpl {

    @Autowired
    SettingRepository settingRepository;

    public List<Setting> findAll(){
        return settingRepository.findAll();
    }



}

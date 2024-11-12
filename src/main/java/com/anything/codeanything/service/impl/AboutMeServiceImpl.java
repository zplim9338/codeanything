package com.anything.codeanything.service.impl;

import com.anything.codeanything.mapper.aboutme.AboutMeMapper;
import com.anything.codeanything.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AboutMeServiceImpl  implements AboutMeService {
    private final AboutMeMapper aboutMeMapper;

    @Autowired
    public AboutMeServiceImpl(@Qualifier("aboutMeMapper") AboutMeMapper aboutMeMapper) {
        this.aboutMeMapper = aboutMeMapper;
    }
    @Override
    public String getAboutMe(String user_id) {
        String jsonData = aboutMeMapper.getAboutMe(user_id);
        return jsonData;
    }
}

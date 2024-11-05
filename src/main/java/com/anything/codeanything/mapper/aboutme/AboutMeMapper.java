package com.anything.codeanything.mapper.aboutme;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AboutMeMapper {
//    String getAboutMe(@Param("user_id") String user_id);
    String getAboutMe(@Param("user_id") String user_id);

}

package com.anything.codeanything.mapper.aboutme;

import com.anything.codeanything.model.TFiles;
import com.anything.codeanything.model.TProfile;
import com.anything.codeanything.model.TAuditTrail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AboutMeMapper {
    String getAboutMe(@Param("user_id") String user_id);
    void insertFile(TFiles file);
    TFiles getFileByUuid(String uuid);
    TProfile getProfileBySlug(String slug);
    TFiles getProfileResume(String slug);
    void insertAuditTrail(TAuditTrail auditTrail);
}

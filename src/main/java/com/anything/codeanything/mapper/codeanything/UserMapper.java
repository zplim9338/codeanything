package com.anything.codeanything.mapper.codeanything;

import com.anything.codeanything.model.TUserAccount;
import com.anything.codeanything.model.UserProfileResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //    @Select("SELECT UA.email, UA.username, UP.first_name, UP.first_name, UP.last_name, UP.nickname, UP.gender, UP.age, UP.phone_no, UA.account_status FROM T_USER_ACCOUNT UA INNER JOIN T_USER_PROFILE UP ON UA.profile_id = UP.profile_id WHERE UA.user_id = #{user_id};")
//    UserProfileResponse getUserProfileById(@Param("id") long user_id);
    UserProfileResponse getUserProfileById(@Param("user_id") long user_id);
    TUserAccount getTUserAccountById(@Param("user_id") long user_id);
    int updateUserAccountById(@Param("tUserAccount") TUserAccount tUserAccount);

}

package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("select * from CREDENTIALS where userid = #{userid}")
    List<Credentials> getCredentialByUserId(@Param("userid") int userId);

    @Insert("insert into CREDENTIALS(url, username, password, key, userid) values (#{url}, #{username}, #{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void insertCredentilas(Credentials credential);

    @Delete("delete from CREDENTIALS where credentialid = #{id}")
    void deleteCredentilas(int id);

    @Insert("update CREDENTIALS set url = #{url}, username = #{username}, key = #{key}, password = #{password} where credentialid = #{credentialid}")
    void updateCredentilas(Credentials credential);

}


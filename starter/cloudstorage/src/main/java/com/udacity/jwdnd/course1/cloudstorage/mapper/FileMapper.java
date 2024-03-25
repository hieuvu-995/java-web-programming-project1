package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from FILES where userid = #{userid}")
    List<File> getFileByUser(int userId);

    @Select("select * from FILES where fileId = #{id}")
    File getFileById(int id);

    @Select("select * from FILES where filename = #{filename} and userid = #{userid}")
    File getByUserIdAndFileName(int userid, String filename);

    @Select("select * from FILES where filename = #{filename}")
    File getByFileName(String filename);

    @Insert("insert into FILES(filename, contenttype, filesize, userid) values (#{filename}, #{contenttype}, #{filesize}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Delete("delete from FILES where fileId = #{id}")
    void deleteFile(int id);
}

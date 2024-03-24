package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from NOTES where userid = #{userid}")
    List<Note> getNoteByUserId(int userId);

    @Insert("insert into NOTES(notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    void insertNote(Note note);

    @Delete("delete from NOTES where noteid = #{id}")
    void deleteNote(int id);

    @Insert("update NOTES set notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid}")
    void updateNote(Note note);

    @Select("select * from NOTES where noteid = #{id}")
    Note findById(int id);
}

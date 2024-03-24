package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note, int userId){
        Note newNote = new Note();
        newNote.setUserid(userId);
        newNote.setNotedescription(note.getNotedescription());
        newNote.setNotetitle(note.getNotetitle());

        noteMapper.insertNote(newNote);
    }

    public List<Note> getNoteByUserId(int userId) {
        return noteMapper.getNoteByUserId(userId);
    }
    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(int noteid){
        noteMapper.deleteNote(noteid);
    }
}

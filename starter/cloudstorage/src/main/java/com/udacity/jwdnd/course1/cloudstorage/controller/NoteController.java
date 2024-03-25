package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String createOrUpdate(@ModelAttribute Note note, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        int userId = user.getUserId();

        if(note.getNoteid() == 0) {
            noteService.addNote(note, userId);
        } else
            noteService.updateNote(note);
        return "redirect:/result?success";
    }
    @GetMapping("/delete/{noteid}")
    public String delete(@PathVariable("noteid") int noteId) {
        noteService.deleteNote(noteId);
        return "redirect:/result?success";
    }
}

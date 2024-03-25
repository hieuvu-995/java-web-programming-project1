package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Authentication auth, Model model) throws IOException {
        User user = userService.getUserByUsername(auth.getName());
        int userId = user.getUserId();
        String filename = fileUpload.getName();

        if (!fileService.fileExisted(userId, filename)) {
            model.addAttribute("error", "file existed");
            model.addAttribute("success", false);
            return "result";
        } else {
            fileService.addFile(
                    File.builder()
                            .userid(userId)
                            .filename(filename)
                            .contenttype(fileUpload.getContentType())
                            .filedata(fileUpload.getBytes())
                            .filesize(String.valueOf(fileUpload.getSize())).build());
            model.addAttribute("success", true);
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId) {
        fileService.deleteFile(fileId);
        return "redirect:/result";
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity<Resource> viewFile(@PathVariable int id) {
        Resource resource = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

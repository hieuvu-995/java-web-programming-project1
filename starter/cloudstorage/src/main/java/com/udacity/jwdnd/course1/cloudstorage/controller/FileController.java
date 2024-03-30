package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    private static final long fileSizeMax = 5000000;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Authentication auth, Model model) {
        User user = userService.getUserByUsername(auth.getName());
        int userId = user.getUserId();
        String filename = fileUpload.getOriginalFilename();

        if (!fileService.fileExisted(userId, filename)) {
            model.addAttribute("error", "File already exists");
            return "redirect:/result?error";
        }

        try {
            fileService.addFile(
                    File.builder()
                            .userid(userId)
                            .filename(filename)
                            .contenttype(fileUpload.getContentType())
                            .filedata(fileUpload.getBytes())
                            .filesize(String.valueOf(fileUpload.getSize())).build());
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while processing the file");
            return "redirect:/result?error";
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
    public ResponseEntity<byte[]> viewFile(@PathVariable int id) {
        File fileUploaded = fileService.getFileById(id);
        if (fileUploaded == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileUploaded.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUploaded.getFilename() + "\"")
                    .body(fileUploaded.getFiledata());
        }
    }
}

package com.udacity.jwdnd.course1.cloudstorage.handling;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleException(MaxUploadSizeExceededException ex, RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("error", "File size exceeds allowable limit.");
        return "redirect:/result?error";
    }
}

package com.quizapp.quizapp.quiz.controller;

import com.quizapp.quizapp.quiz.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {

    private final QuestionService questionService;

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            questionService.processExcelFile(file);
//            return ResponseEntity.ok("File uploaded successfully");
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

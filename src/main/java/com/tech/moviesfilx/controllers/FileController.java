package com.tech.moviesfilx.controllers;

import com.tech.moviesfilx.ServiceImpl.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/fileService")
public class FileController {


    @Autowired
    MovieService movieService;

    @Value("${project.poster}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) throws IOException {
        String fileName = movieService.uploadFile(path, file);
        return ResponseEntity.ok("poster store " + fileName);

    }

    @GetMapping("/{filename}")
    public void getResouceFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        InputStream getResourcefile = movieService.getResourceFile(path, filename);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(getResourcefile, response.getOutputStream());
    }
}

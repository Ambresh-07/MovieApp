package com.tech.moviesfilx.ServiceImpl;

import com.tech.moviesfilx.service.Filmservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DemoServicePractice implements Filmservice {

    @Value("${project.poster}")
    private String path;
    @Override

    public String uploadFile(String path, MultipartFile file) throws IOException {
//        get file original name
        String fileName=file.getOriginalFilename();
        //create uploaded path
        String filePath=path + File.separator+fileName;

        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override

    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {

        String filename=path+File.separator+fileName;
        return new FileInputStream(filename);
    }
}

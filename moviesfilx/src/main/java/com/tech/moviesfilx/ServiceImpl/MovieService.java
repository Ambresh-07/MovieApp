package com.tech.moviesfilx.ServiceImpl;

import com.tech.moviesfilx.repository.MovieRepository;
import com.tech.moviesfilx.service.Filmservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class MovieService implements Filmservice {
    @Autowired
    MovieRepository movieRepository;


    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        //getting name of file
        String fileName = file.getOriginalFilename();


        ///get file path
        String filepath = path + File.separator + fileName;

        File f = new File(path);

        if (!f.exists()) {
            f.mkdir();
        }


        Files.copy(file.getInputStream(), Paths.get(filepath));


        //create the
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {

        String filepath = path + File.separator + fileName;
        return new FileInputStream(filepath);
    }
}

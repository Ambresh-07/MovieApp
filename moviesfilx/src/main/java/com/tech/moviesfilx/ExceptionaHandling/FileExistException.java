package com.tech.moviesfilx.ExceptionaHandling;

public class FileExistException extends RuntimeException{
    public FileExistException(String message){
        super(message);
    }
}

package com.tech.moviesfilx.ExceptionaHandling;

public class EmptyFileException extends RuntimeException{

    public EmptyFileException(String message){
        super(message);

    }
}

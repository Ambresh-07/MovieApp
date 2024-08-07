package com.tech.moviesfilx.ExceptionaHandling;

public class MovieNotFoundException extends  RuntimeException{
    public MovieNotFoundException(String message) throws MovieNotFoundException{
        super(message);
    }

}

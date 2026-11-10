package com.codecool.brewery;

import java.util.Objects;

public final class Validator {

    public static String validateName(String name){
        Objects.requireNonNull(name, ErrorMessages.NAME_NULL_MESSAGE);
        if(name.isBlank()){
            throw new IllegalArgumentException(ErrorMessages.NAME_BLANK_MESSAGE);
        }
        return name;
    }

    private Validator(){}
}

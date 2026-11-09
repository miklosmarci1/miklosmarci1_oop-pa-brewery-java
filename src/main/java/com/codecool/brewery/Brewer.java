package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Objects;

public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;
    private static final int ZERO_EXPERIENCE = 0;

    Brewer(String name, int yearsOfExperience) {
        this.name = Objects.requireNonNull(name, ErrorMessages.NAME_NULL_MESSAGE);
        if(name.isBlank()){
            throw new IllegalArgumentException(ErrorMessages.NAME_BLANK_MESSAGE);
        }

        if(yearsOfExperience < ZERO_EXPERIENCE){
            throw new IllegalArgumentException("Experience can't be less than " + ZERO_EXPERIENCE + ".");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public abstract boolean isAvailableOn(DayOfWeek day);

    public abstract Role getRole();
}

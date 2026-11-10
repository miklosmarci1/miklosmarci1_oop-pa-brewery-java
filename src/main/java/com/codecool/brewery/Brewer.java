package com.codecool.brewery;

import java.time.DayOfWeek;

public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;
    private static final int ZERO_EXPERIENCE = 0;

    Brewer(String name, int yearsOfExperience) {
        this.name = Validator.validateName(name);

        if(yearsOfExperience < ZERO_EXPERIENCE){
            throw new IllegalArgumentException(ErrorMessages.EXPERIENCE_BELOW_MINIMUM_MESSAGE + ZERO_EXPERIENCE);
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public abstract boolean isAvailableOn(DayOfWeek day);

    public abstract Role getRole();
}

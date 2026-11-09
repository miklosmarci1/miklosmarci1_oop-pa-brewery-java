package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Objects;

/**
 * Represents a brewer working at the brewery.
 * You need to extend this class to support different types of brewers.
 */
public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;

    Brewer(String name, int yearsOfExperience) {
        this.name = Objects.requireNonNull(name);
        if(name.isBlank()){
            throw new IllegalArgumentException("Name can't be empty.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    // TODO: Add method to check if brewer is available on a specific day
    // TODO: Consider how different types of brewers have different schedules
    // TODO: Consider isAvailableOn may accept parameters for day checks
    public abstract boolean isAvailableOn(DayOfWeek day);
}

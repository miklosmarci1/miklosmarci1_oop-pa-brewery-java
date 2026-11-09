package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Objects;

public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;

    Brewer(String name, int yearsOfExperience) {
        this.name = Objects.requireNonNull(name, "Name can't be null.");
        if(name.isBlank()){
            throw new IllegalArgumentException("Name can't be blank.");
        }

        if(yearsOfExperience < 0){
            throw new IllegalArgumentException("Experience can't be less than 0.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public abstract boolean isAvailableOn(DayOfWeek day);

    public String getName(){
        return name;
    }

    public int getYearsOfExperience(){
        return yearsOfExperience;
    }

    public abstract Role getRole();
}

package com.codecool.brewery;

import java.time.DayOfWeek;

public class MasterBrewer extends Brewer{

    MasterBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        return true;
    }

}

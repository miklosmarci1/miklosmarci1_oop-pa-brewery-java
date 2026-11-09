package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Set;

public class HelperBrewer extends Brewer{

    private final Set<DayOfWeek> workingDays;

    HelperBrewer(String name, int yearsOfExperience, Set<DayOfWeek> workingDays) {
        super(name, yearsOfExperience);
        this.workingDays = Set.copyOf(workingDays);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        return workingDays.contains(day);
    }

    @Override
    public Role getRole() {
        return Role.HELPER;
    }
}

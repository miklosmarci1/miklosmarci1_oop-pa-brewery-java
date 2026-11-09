package com.codecool.brewery;

import java.time.LocalDate;

public class Lager extends Beer{

    private static final int LAGER_FERMENTATION_DAYS = 21;
    private static final double LAGER_MAX_STORAGE_TEMPERATURE = 5;

    Lager(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays(){
        return LAGER_FERMENTATION_DAYS;
    }

    @Override
    public boolean canBeStoredAt(double temp) {
        return temp < LAGER_MAX_STORAGE_TEMPERATURE;
    }

}

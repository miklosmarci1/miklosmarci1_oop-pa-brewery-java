package com.codecool.brewery;

import java.time.LocalDate;

public class Ale extends Beer{

    private static final int ALE_FERMENTATION_DAYS = 14;
    private static final double ALE_MIN_STORAGE_TEMPERATURE = 19;
    private static final double ALE_MAX_STORAGE_TEMPERATURE = 25;

    Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays(){
        return ALE_FERMENTATION_DAYS;
    }

    @Override
    public boolean canBeStoredAt(double temp) {
        return temp < ALE_MAX_STORAGE_TEMPERATURE && temp > ALE_MIN_STORAGE_TEMPERATURE;
    }

}

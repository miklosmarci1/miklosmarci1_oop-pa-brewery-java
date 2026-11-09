package com.codecool.brewery;

import java.time.LocalDate;

public class Ale extends Beer{

    private static final int ALE_FERMENTATION_DAYS = 14;

    Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays(){
        return ALE_FERMENTATION_DAYS;
    }

}

package com.codecool.brewery;

import java.time.LocalDate;

public class Lager extends Beer{

    private static final int LAGER_FERMENTATION_DAYS = 21;

    Lager(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays(){
        return LAGER_FERMENTATION_DAYS;
    }

}

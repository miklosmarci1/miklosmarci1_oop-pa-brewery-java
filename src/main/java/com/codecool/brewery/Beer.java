package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;
    private static final double MIN_ALCOHOL_CONTENT = 0;
    private static final double MAX_ALCOHOL_CONTENT = 100;

    Beer(String name, double alcoholContent, LocalDate productionDate) {

        this.name = Validator.validateName(name);

        if(alcoholContent < MIN_ALCOHOL_CONTENT || alcoholContent > MAX_ALCOHOL_CONTENT){
            throw new IllegalArgumentException(ErrorMessages.ALCOHOL_CONTENT_OUT_OF_RANGE_MESSAGE);
        }

        this.alcoholContent = alcoholContent;

        this.productionDate = Objects.requireNonNull(productionDate, ErrorMessages.PRODUCTION_DATE_NULL_MESSAGE);
    }

    public int getDaysUntilReady() {
        LocalDate beerReadyDate = productionDate.plusDays(getFermentationDays());
        return Math.toIntExact(Math.max(0, ChronoUnit.DAYS.between(LocalDate.now(), beerReadyDate)));
    }

    abstract boolean canBeStoredAt(double temp);

    abstract int getFermentationDays();

    public String getName(){
        return name;
    }

    public LocalDate getProductionDate(){
        return productionDate;
    }
}

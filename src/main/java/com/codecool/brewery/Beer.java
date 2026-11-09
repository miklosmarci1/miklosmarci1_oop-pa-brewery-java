package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;

    Beer(String name, double alcoholContent, LocalDate productionDate) {

        this.name = Objects.requireNonNull(name, ErrorMessages.NAME_NULL_MESSAGE);
        if(this.name.isBlank()){
            throw new IllegalArgumentException(ErrorMessages.NAME_BLANK_MESSAGE);
        }

        if(alcoholContent < 0 || alcoholContent > 100){
            throw new IllegalArgumentException("Alcohol content must be between 0 and 100.");
        }

        this.alcoholContent = alcoholContent;

        this.productionDate = Objects.requireNonNull(productionDate, "Production date can't be null.");
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

    public double getAlcoholContent(){
        return alcoholContent;
    }

    public LocalDate getProductionDate(){
        return productionDate;
    }
}

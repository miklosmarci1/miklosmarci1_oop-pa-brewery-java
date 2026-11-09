package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;

    Beer(String name, double alcoholContent, LocalDate productionDate) {

        this.name = Objects.requireNonNull(name, "Name can't be null.");
        if(this.name.isBlank()){
            throw new IllegalArgumentException("Name can't be blank.");
        }

        if(alcoholContent < 0 || alcoholContent > 100){
            throw new IllegalArgumentException("Alcohol content must be between 0 and 100.");
        }

        this.alcoholContent = alcoholContent;

        this.productionDate = Objects.requireNonNull(productionDate, "Production date can't be null.");
    }

    // TODO: Add method to calculate days until beer is ready for sale
    // Lagers require cold storage (below 5°C) and have longer fermentation times (21 days)
    // Ales can be stored at room temperature and have shorter fermentation times (14 days)

    // TODO: Consider how to handle different fermentation times for different beer types
    public int getDaysUntilReady() {
        LocalDate beerReadyDate = productionDate.plusDays(getFermentationDays());
        return Math.toIntExact(Math.max(0, ChronoUnit.DAYS.between(LocalDate.now(), beerReadyDate)));
    }

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    abstract boolean canBeStoredAt(double temp);

    // TODO: Add method to get fermentation days
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

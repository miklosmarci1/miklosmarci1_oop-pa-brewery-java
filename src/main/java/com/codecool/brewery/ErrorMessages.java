package com.codecool.brewery;

public final class ErrorMessages {

    // common
    static final String NAME_NULL_MESSAGE = "Name can't be null.";
    static final String NAME_BLANK_MESSAGE = "Name can't be blank.";

    // beer
    static final String ALCOHOL_CONTENT_OUT_OF_RANGE_MESSAGE = "Alcohol content must be between 0 and 100.";
    static final String PRODUCTION_DATE_NULL_MESSAGE = "Production date can't be null.";
    static final String BEER_NULL_MESSAGE = "Beer can't be null.";
    static final String BEER_STORAGE_TEMPERATURE_INVALID_MESSAGE = "This beer can't be stored at this temperature.";

    // brewer
    static final String EXPERIENCE_BELOW_MINIMUM_MESSAGE = "Experience can't be less than: ";
    static final String BREWER_NULL_MESSAGE = "Brewer can't be null.";
    static final String ONLY_ONE_MASTER_BREWER_ALLOWED_MESSAGE = "Only one master brewer is allowed!";
    static final String MASTER_NOT_AVAILABLE_MESSAGE = "Master isn't available.";

    // stock
    static final String QUANTITY_MUST_BE_POSITIVE_MESSAGE = "Quantity must be greater than 0.";
    static final String INVALID_BEER_NAME_MESSAGE = "No beer with this name is in stock.";
    static final String INVALID_AMOUNT_OF_BEERS_MESSAGE = "There are not enough beers in stock.";

    // production
    static final String DAYS_NULL_MESSAGE = "Days can't be null";

    private ErrorMessages(){}
}

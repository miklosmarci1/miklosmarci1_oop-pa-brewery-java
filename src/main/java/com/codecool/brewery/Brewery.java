package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class Brewery {
    private final String name;
    private final List<Brewer> brewers = new ArrayList<>();
    private final Map<String, List<Beer>> stock = new HashMap<>();
    private static final int OLD_BEER_THRESHOLD_DAYS = 180;

    Brewery(String name) {
        this.name = Validator.validateName(name);
    }

    public void addBrewer(Brewer brewer) {
        Objects.requireNonNull(brewer, ErrorMessages.BREWER_NULL_MESSAGE);

        if(isRole(brewer, Role.MASTER) &&
        hasBrewerWithRole(Role.MASTER)
        ){
            throw new IllegalStateException(ErrorMessages.ONLY_ONE_MASTER_BREWER_ALLOWED_MESSAGE);
        }

        brewers.add(brewer);
    }

    public void addBeerToStock(Beer beer, double temp) {

        Objects.requireNonNull(beer, ErrorMessages.BEER_NULL_MESSAGE);

        if(!beer.canBeStoredAt(temp)){
            throw new IllegalArgumentException(ErrorMessages.BEER_STORAGE_TEMPERATURE_INVALID_MESSAGE);
        }

        stock.computeIfAbsent(beer.getName(), key -> new ArrayList<>())
                .add(beer);
    }

    public List<Beer> retrieveBeerFromStock(String name, int quantity) {

        Validator.validateName(name);

        validateQuantity(quantity);

        validateStockKey(name);

        List<Beer> beers = stock.get(name);

        validateAvailableBeerQuantity(beers, quantity);

        List<Beer> retrievedBeers = retrieveBeers(beers, quantity);

        removeBeersIfStockIsEmpty(beers, name);

        return retrievedBeers;
    }

    private void validateQuantity(int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException(ErrorMessages.QUANTITY_MUST_BE_POSITIVE_MESSAGE);
        }
    }

    private void validateStockKey(String name){
        if(!stock.containsKey(name)){
            throw new NoSuchElementException(ErrorMessages.INVALID_BEER_NAME_MESSAGE);
        }
    }

    private void validateAvailableBeerQuantity(List<Beer> list, int quantity){
        if(list.size() < quantity){
            throw new IllegalStateException(ErrorMessages.INVALID_AMOUNT_OF_BEERS_MESSAGE);
        }
    }

    private void removeBeersIfStockIsEmpty(List<Beer> list, String name){
        if(list.isEmpty()){
            stock.remove(name);
        }
    }

    private List<Beer> retrieveBeers(List<Beer> beers, int quantity){
        List<Beer> retrievedBeers = new ArrayList<>();
        for (int i = 0; i < quantity; i++){
            retrievedBeers.add(beers.remove(0));
        }
        return retrievedBeers;
    }

    public int calculateProductionCapacity(List<DayOfWeek> days){
        Objects.requireNonNull(days, ErrorMessages.DAYS_NULL_MESSAGE);
        boolean hasMasterBrewer = hasBrewerWithRole(Role.MASTER);

        if(!hasMasterBrewer){
            throw new IllegalStateException(ErrorMessages.MASTER_NOT_AVAILABLE_MESSAGE);
        }

        return days.stream()
                .mapToInt(this::countAvailableHelpers)
                .sum();
    }

    public List<String> findMaturedBeers() {

        return stock.values().stream()
                .flatMap(List::stream)
                .filter(beer -> ChronoUnit.DAYS.between(beer.getProductionDate(), LocalDate.now()) > OLD_BEER_THRESHOLD_DAYS)
                .map(Beer::getName)
                .toList();
    }

    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);

        // Get all days in range
        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);

        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }

        // Map each day to its helper count
        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(
                        day -> day,
                        this::countAvailableHelpers
                ));

        // Find maximum helper count
        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        // Return days with maximum helper count
        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    private List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;

        // Handle wrap-around week
        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            // Prevent infinite loop
            if (days.size() > 7) {
                break;
            }
        }

        return days;
    }

    private int countAvailableHelpers(DayOfWeek day) {
        return (int) brewers.stream()
                .filter(b -> isRole(b, Role.HELPER))
                .filter(b -> b.isAvailableOn(day))
                .count();
    }

    private boolean isRole(Brewer brewer, Role role){
        return brewer.getRole() == role;
    }

    private boolean hasBrewerWithRole(Role role){
        return brewers.stream().anyMatch(b -> isRole(b, role));
    }
}

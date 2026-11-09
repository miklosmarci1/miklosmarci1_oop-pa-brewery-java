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
        this.name = Objects.requireNonNull(name, "Name can't be null.");
        if(name.isBlank()){
            throw new IllegalArgumentException("Name can't be empty.");
        }
    }

    public void addBrewer(Brewer brewer) {
        Objects.requireNonNull(brewer, "Brewer can't be null.");

        if(brewer.getRole() == Role.MASTER &&
        brewers.stream().anyMatch(b -> b.getRole() == Role.MASTER)
        ){
            throw new IllegalStateException("Only one master brewer is allowed!");
        }

        brewers.add(brewer);
    }

    public void addBeerToStock(Beer beer, double temp) {

        Objects.requireNonNull(beer, "Beer can't be null");

        if(!beer.canBeStoredAt(temp)){
            throw new IllegalArgumentException("This beer can't be stored at this temperature.");
        }

        stock.computeIfAbsent(beer.getName(), key -> new ArrayList<>())
                .add(beer);
    }

    public List<Beer> retrieveBeerFromStock(String name, int quantity) {

        List<Beer> retrievedBeers = new ArrayList<>();

        Objects.requireNonNull(name, "Name can't be null.");

        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        if(!stock.containsKey(name)){
            throw new IllegalArgumentException("No beer with this name is in stock.");
        }

        List<Beer> beers = stock.get(name);

        if(beers.size() < quantity){
            throw new IllegalArgumentException("There are not enough beers in stock.");
        }

        for ( int i = 0; i < quantity; i++){
            retrievedBeers.add(beers.remove(0));
        }

        if(beers.isEmpty()){
            stock.remove(name);
        }

        return retrievedBeers;
    }

    public int calculateProductionCapacity(List<DayOfWeek> days){
        Objects.requireNonNull(days, "Days can't be null");
        boolean isMasterAvailable = brewers.stream()
                .anyMatch(b -> b.getRole() == Role.MASTER);

        if(!isMasterAvailable){
            throw new IllegalStateException("Master isn't available.");
        }

        return days.stream()
                .mapToInt(this::countAvailableHelpers)
                .sum();
    }

    public List<String> findMaturedBeers() {

        return stock.values().stream()
                .flatMap(List::stream)
                .filter(a -> ChronoUnit.DAYS.between(a.getProductionDate(), LocalDate.now()) > OLD_BEER_THRESHOLD_DAYS)
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
                .filter(b -> b.getRole() == Role.HELPER)
                .filter(b -> b.isAvailableOn(day))
                .count();
    }
}

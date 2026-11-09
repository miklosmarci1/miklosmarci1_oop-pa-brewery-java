package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
class Brewery {
    private final String name;
    private final List<Brewer> brewers = new ArrayList<>();

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

    void addBeerToStock() {
        // TODO: Implement proper null checking and validation
        // TODO: add beer to stock
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    void retrieveBeerFromStock() {
        // TODO: Implement
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
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

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findMaturedBeers() {
        // TODO: Implement
        return null;
    }

    /**
     * Finds the optimal days for production within a given range.
     * Returns days with maximum helper availability.
     */
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

    /**
     * Helper method to parse day names.
     */
    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    /**
     * Helper method to get days in a range.
     */
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

    /**
     * Counts available helpers on a specific day.
     */
    // TODO: Implement countAvailableHelpers method
    private int countAvailableHelpers(DayOfWeek day) {
        return (int) brewers.stream()
                .filter(b -> b.getRole() == Role.HELPER)
                .filter(b -> b.isAvailableOn(day))
                .count();
    }
}

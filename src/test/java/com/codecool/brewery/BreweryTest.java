package com.codecool.brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer = new MasterBrewer("Master John", 15);;
    private Brewer helper1 = new HelperBrewer("Helper Alice", 3, Set.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY
    ));
    private Brewer helper2 = new HelperBrewer("Helper Bob", 2, Set.of(
            DayOfWeek.MONDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    ));
    private Brewer helper3 = new HelperBrewer("Helper Charlie", 4, Set.of(
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
    ));

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");

        brewery.addBrewer(masterBrewer);
        brewery.addBrewer(helper1);
        brewery.addBrewer(helper2);
        brewery.addBrewer(helper3);
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {

        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY
        ));

        assertEquals(4, capacity);
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                brewery.findOptimalProductionDays("MNODAY", "FRIDAY"));

        assertEquals("Invalid day name: MNODAY", e.getMessage());
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {

        Beer freshBeer = new Lager("Fresh", 5.0, LocalDate.now().minusDays(100));
        Beer oldAle = new Ale("Old Ale", 6.0, LocalDate.now().minusDays(200));
        Beer oldLager = new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190));

        brewery.addBeerToStock(freshBeer, 4);
        brewery.addBeerToStock(oldAle, 20);
        brewery.addBeerToStock(oldLager, 3);

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        List<String> optimalProductionDays = brewery.findOptimalProductionDays("MONDAY", "FRIDAY");

        List<String> expectedDays = new ArrayList<>();
        expectedDays.add("MONDAY");
        expectedDays.add("TUESDAY");
        expectedDays.add("WEDNESDAY");
        expectedDays.add("THURSDAY");

        assertEquals(expectedDays, optimalProductionDays);
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {

        Beer testLager = new Lager("Test Lager", 5.0, LocalDate.now());
        Beer testAle = new Ale("Test Ale", 5.5, LocalDate.now());

        assertEquals(21, testLager.getDaysUntilReady());
        assertEquals(14, testAle.getDaysUntilReady());
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {

        Beer testLager = new Lager("Test Lager", 5.0, LocalDate.now());
        Beer testAle = new Ale("Test Ale", 5.5, LocalDate.now());

        assertTrue(testLager.canBeStoredAt(2));
        assertTrue(testLager.canBeStoredAt(4.9));
        assertFalse(testLager.canBeStoredAt(5));
        assertFalse(testLager.canBeStoredAt(6));

        assertTrue(testAle.canBeStoredAt(20));
        assertTrue(testAle.canBeStoredAt(24.9));
        assertTrue(testAle.canBeStoredAt(19.01));
        assertFalse(testAle.canBeStoredAt(19));
        assertFalse(testAle.canBeStoredAt(25));
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {

        for (DayOfWeek day : DayOfWeek.values()){
            assertTrue(masterBrewer.isAvailableOn(day));
        }

        assertTrue(helper1.isAvailableOn(DayOfWeek.MONDAY));
        assertTrue(helper1.isAvailableOn(DayOfWeek.TUESDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.FRIDAY));

    }

}

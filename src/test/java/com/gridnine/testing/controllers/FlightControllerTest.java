package com.gridnine.testing.controllers;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;
import com.gridnine.testing.services.impl.FlightServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightControllerTest {

    private FlightController flightController;


    @Test
    void excludeFlightsBeforeCurrentTimeTesting() {

        flightController = new FlightController(new FlightServiceImpl());

        List<Segment> segments1 = new ArrayList<>();
        segments1.add(new Segment(LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1)));
        Flight flight1 = new Flight(segments1);

        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)));
        Flight flight2 = new Flight(segments2);

        List<Flight> result1 = flightController.excludeFlightsBeforeCurrentTime(
                List.of(flight1, flight2));
        List<Flight> expected = new ArrayList<>(Collections.singletonList(flight2));

        List<Flight> result2 = flightController.excludeFlightsBeforeCurrentTime(null);

        assertEquals(result1, expected);
        assertEquals(result2, new ArrayList<>());



    }


    @Test
    void excludeSegmentArrivalDateBeforeDepartureDate() {

        flightController = new FlightController(new FlightServiceImpl());

        Segment badSegment = new Segment(LocalDateTime.now().plusHours(2), LocalDateTime.now());
        Segment okSegment1 = new Segment(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1));
        Segment okSegment2 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1));

        List<Segment> allSegments = new ArrayList<>(List.of(badSegment, okSegment1, okSegment2));

        Flight badFlight1 = new Flight(allSegments);
        Flight badFlight2 = new Flight(Collections.singletonList(badSegment));
        Flight goodFlight = new Flight(List.of(okSegment1, okSegment2));

        List<Flight> result = flightController.excludeSegmentArrivalDateBeforeDepartureDate(
                List.of(goodFlight, badFlight2, badFlight1));

        List<Flight> expected = new ArrayList<>(Collections.singletonList(goodFlight));

        List<Flight> result2 = flightController.excludeFlightsBeforeCurrentTime(null);

        assertEquals(result, expected);
        assertEquals(result2, new ArrayList<>());
    }



    @Test
    void excludeFlightsTakeMoreThanTwoHoursOnGround() {

        flightController = new FlightController(new FlightServiceImpl());

        Segment segment1 = new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5));
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(8), LocalDateTime.now().plusHours(10));
        Segment segment3 = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        Flight badFlight = new Flight(List.of(segment1, segment2));
        Flight goodFlight = new Flight(List.of(segment3, segment1));

        List<Flight> result = flightController.excludeFlightsTakeMoreThanTwoHoursOnGround(
                List.of(badFlight, goodFlight));

        List<Flight> result2 = flightController.excludeFlightsTakeMoreThanTwoHoursOnGround(null);

        assertEquals(result, List.of(goodFlight));
        assertEquals(result2, new ArrayList<>());
    }
}
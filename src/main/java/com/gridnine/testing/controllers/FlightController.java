package com.gridnine.testing.controllers;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.services.FlightService;

import java.util.List;

public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<Flight> excludeFlightsBeforeCurrentTime(List<Flight> flights) {
        return flightService.excludeFlightsBeforeCurrentTime(flights);
    }

    public List<Flight> excludeSegmentArrivalDateBeforeDepartureDate(List<Flight> flights) {
        return flightService.excludeSegmentArrivalDateBeforeDepartureDate(flights);
    }

    public List<Flight> excludeFlightsTakeMoreThanTwoHoursOnGround(List<Flight> flights) {
        return flightService.excludeFlightsTakeMoreThanTwoHoursOnGround(flights);
    }
}

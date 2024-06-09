package com.gridnine.testing.services.impl;


import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;
import com.gridnine.testing.services.FlightService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlightServiceImpl implements FlightService {

    private final LocalDateTime currentTime;

    {
        currentTime = LocalDateTime.now();
    }


    @Override
    public List<Flight> excludeFlightsBeforeCurrentTime(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .anyMatch(segment ->
                                currentTime.isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> excludeSegmentArrivalDateBeforeDepartureDate(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> checkBadSegments(flight.getSegments()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Flight> excludeFlightsTakeMoreThanTwoHoursOnGround(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> IntStream.range(0, flight.getSegments().size() - 1)
                                          .mapToLong(i -> ChronoUnit.HOURS.between(
                                                  flight.getSegments().get(i).getArrivalDate(),
                                                  flight.getSegments().get(i + 1).getDepartureDate()))
                                          .sum() <= 2)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param segments - список сегментов
     * @return - возвращает true, если список не имеет сегментов,
     * где дата вылета позже даты прилета
     */
    private boolean checkBadSegments(List<Segment> segments) {
        for(Segment segment : segments) {
            if (segment.getDepartureDate().isAfter(segment.getArrivalDate()))
                return false;
        }
        return true;
    }
}

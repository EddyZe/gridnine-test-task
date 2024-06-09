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

public class FlightServiceImpl implements FlightService {

    /**
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, до текущего времени
     */
    @Override
    public List<Flight> excludeFlightsBeforeCurrentTime(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> checkingCurrentDateBeforeDepartureDate(flight.getSegments()))
                .collect(Collectors.toList());
    }

    /**
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, где сегменты с датой
     * прилета раньше даты вылета
     */
    @Override
    public List<Flight> excludeSegmentArrivalDateBeforeDepartureDate(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> checkingSegmentDepartureDateAfterArrivalDate(flight.getSegments()))
                .collect(Collectors.toList());
    }


    /**
     *
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, где время проведенное на земле
     * больше, чем 2 часа. Так же включает перелеты у которых количество сегментов меньше, чем 2.
     */
    @Override
    public List<Flight> excludeFlightsTakeMoreThanTwoHoursOnGround(List<Flight> flights) {
        return Optional.ofNullable(flights).orElse(new ArrayList<>())
                .stream()
                .filter(flight -> !checkingTimeOnEarthIsMoreThanTwoHours(flight.getSegments()))
                .collect(Collectors.toList());
    }


    /**
     *
     * @param segments - список сегментов
     * @return - возвращает true, если список не имеет сегментов,
     * где дата вылета позже даты прилета
     */
    private boolean checkingSegmentDepartureDateAfterArrivalDate(List<Segment> segments) {
        for(Segment segment : segments) {
            if (segment.getDepartureDate().isAfter(segment.getArrivalDate()))
                return false;
        }
        return true;
    }


    /**
     *
     * @param segments - список сегментов
     * @return - возвращает true, если разница между временем прибытия текущего сегмента
     * и вылета следующего больше двух часов
     */
    private boolean checkingTimeOnEarthIsMoreThanTwoHours(List<Segment> segments) {
        long result = 0;

        for (int i = 0; i < segments.size() -1; i++) {
            result += ChronoUnit.MINUTES.between(
                    segments.get(i).getArrivalDate(),
                    segments.get(i + 1).getDepartureDate());
        }
        return result > 120;
    }


    /**
     *
     * @param segments - список сегментов
     * @return - возвращает true, если все сегменты идут после текущего времени
     */
    private boolean checkingCurrentDateBeforeDepartureDate(List<Segment> segments) {
        LocalDateTime currentTime = LocalDateTime.now();

        for (Segment segment : segments) {
            if (currentTime.isAfter(segment.getDepartureDate()))
                return false;
        }
        return true;
    }

}

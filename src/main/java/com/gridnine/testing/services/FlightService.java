package com.gridnine.testing.services;



import com.gridnine.testing.models.Flight;

import java.util.List;

public interface FlightService {
    /**
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, до текущего времени
     */
    List<Flight> excludeFlightsBeforeCurrentTime(List<Flight> flights);

    /**
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, где сегменты с датой
     * прилета раньше даты вылета
     */
    List<Flight> excludeSegmentArrivalDateBeforeDepartureDate(List<Flight> flights);

    /**
     *
     * @param flights - список полетов
     * @return - список полетов, который не включает полеты, где время проведенное на земле
     * больше, чем 2 часа. Так же включает перелеты у которых количество сегментов меньше, чем 2.
     */
    List<Flight> excludeFlightsTakeMoreThanTwoHoursOnGround(List<Flight> flights);

}

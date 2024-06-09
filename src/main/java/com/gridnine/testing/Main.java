package com.gridnine.testing;


import com.gridnine.testing.controllers.FlightController;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.services.impl.FlightServiceImpl;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {



    public static void main(String[] args) {

        FlightController flightController = new FlightController(
                new FlightServiceImpl());


        List<Flight> flights = FlightBuilder.createFlights();


        System.out.println("\nСписок всех полетов:");
        flights.forEach(System.out::println);

        System.out.println("\nСписок полетов, которые не включают полеты," +
                           " где вылет до текущего момента времени:");
        flightController.excludeFlightsBeforeCurrentTime(flights)
                .forEach(System.out::println);

        System.out.println("\nСписок полетов, который не включает полеты," +
                           " где сегменты с датой прилета раньше даты вылета:");
        flightController.excludeSegmentArrivalDateBeforeDepartureDate(flights)
                .forEach(System.out::println);

        System.out.println("\nСписок полетов, который не включает полеты," +
                           " где общее проведенное время на земле больше чем 2 часа." +
                           "Так же в список входят перелеты, где количество сегментов меньше чем 2.");
        flightController.excludeFlightsTakeMoreThanTwoHoursOnGround(flights)
                .forEach(System.out::println);


    }
}
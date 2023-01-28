package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        try {


            if (!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()) {
                throw new Exception("Cannot make reservation");
            }
            User user = userRepository3.findById(userId).get();
            Reservation reservation = new Reservation();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
            int j = 0;
            SpotType spotType;
            if (numberOfWheels > 4) {
                spotType = SpotType.OTHERS;
            } else if (numberOfWheels > 2) {
                spotType = SpotType.FOUR_WHEELER;
            } else spotType = SpotType.TWO_WHEELER;
            Spot spot = null;
            int minimumPrice = Integer.MAX_VALUE;
            for (Spot i : parkingLot.getSpotList()) {
                if (spotType.equals(SpotType.OTHERS) && i.getSpotType().equals(SpotType.OTHERS)) {
                    if (!i.getOccupied() && i.getPricePerHour() * timeInHours < minimumPrice) {
                        minimumPrice = i.getPricePerHour() * timeInHours;
                        j = 1;
                        spot = i;
                    }
                } else if (spotType.equals(SpotType.FOUR_WHEELER) && i.getSpotType().equals(SpotType.OTHERS) || i.getSpotType().equals(SpotType.FOUR_WHEELER)) {
                    if (!i.getOccupied() && i.getPricePerHour() * timeInHours < minimumPrice) {
                        minimumPrice = i.getPricePerHour() * timeInHours;
                        j = 1;
                        spot = i;
                    }
                } else if (spotType.equals(SpotType.TWO_WHEELER)) {
                    if (!i.getOccupied() && i.getPricePerHour() * timeInHours < minimumPrice) {
                        minimumPrice = i.getPricePerHour() * timeInHours;
                        j = 1;
                        spot = i;
                    }
                }
            }
            if (j == 0) throw new Exception("cannot make reservation");
            spot.setOccupied(true);
            reservation.setSpot(spot);
            reservation.setNumberOfHours(timeInHours);
            reservation.setUser(user);
            spot.getReservationList().add(reservation);
            user.getReservationList().add(reservation);
            userRepository3.save(user);
            spotRepository3.save(spot);
            return reservation;
        }
        catch (Exception e){
            throw new Exception("Reservation can not be made");

        }

    }
}

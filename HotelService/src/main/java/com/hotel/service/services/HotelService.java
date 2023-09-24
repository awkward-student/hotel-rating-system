package com.hotel.service.services;

import com.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {
    // create
    Hotel create(Hotel hotel);
    // get all hotels
    List<Hotel> getAllHotels();
    // get single hotel
    Hotel getHotel(String id);
}

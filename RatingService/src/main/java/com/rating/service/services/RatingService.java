package com.rating.service.services;

import com.rating.service.entities.Rating;

import java.util.List;

public interface RatingService {
    // create rating
    Rating createRating(Rating rating);

    // get all ratings
    List<Rating> getAllRatings();

    // get all by user
    List<Rating> getAllRatingsByUser(String userId);

    // get all by hotel
    List<Rating> getAllRatingsByHotel(String hotelId);
}

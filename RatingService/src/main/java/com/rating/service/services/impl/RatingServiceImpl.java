package com.rating.service.services.impl;

import com.rating.service.entities.Rating;
import com.rating.service.repositories.RatingRepo;
import com.rating.service.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo ratingRepo;
    @Override
    public Rating createRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<Rating> getAllRatingsByUser(String userId) {
        return ratingRepo.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatingsByHotel(String hotelId) {
        return ratingRepo.findByHotelId(hotelId);
    }
}

package com.rating.service.controllers;

import com.rating.service.entities.Rating;
import com.rating.service.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // create rating
    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
    }

    // get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    // get all ratings by user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getAllRatingsByUser(@PathVariable String userId){
        return ResponseEntity.ok(ratingService.getAllRatingsByUser(userId));
    }

    // get all ratings by hotel
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getAllRatingsByHotel(@PathVariable String hotelId){
        return ResponseEntity.ok(ratingService.getAllRatingsByHotel(hotelId));
    }
}

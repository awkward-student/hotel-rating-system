package com.user.service.services.impl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repositories.UserRepo;
import com.user.service.services.UserService;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    // for testing the fetch logger is used
    // private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        // generate unique user Id.
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String userId) {
        // fetching user from db with user repo
        User user =  userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));
        // fetching ratings given by user from Rating Service
        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        // logger.info("{} ", ratings);
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // API call to hotel service to get hotel details
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            // setting hotel to rating
            rating.setHotel(hotel);
            // return rating containing hotel details
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userUpdated = getUser(user.getUserId());
        userUpdated.setName(user.getName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setAbout(user.getAbout());
        return userUpdated;
    }

    @Override
    public void deleteUser(String userId) {
        this.userRepo.deleteById(userId);
    }
}

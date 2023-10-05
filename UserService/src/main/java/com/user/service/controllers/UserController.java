package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserService userService;

    // create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userNew = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    int retryCount = 1;
    // get single user
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "ratingAndHotelBreaker", fallbackMethod = "ratingAndHotelFallback")
    @Retry(name = "ratingAndHotelRetry", fallbackMethod = "ratingAndHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        System.out.println(retryCount);
        ++retryCount;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // creating fallback for circuit breaker
    public ResponseEntity<User> ratingAndHotelFallback(String userId, Exception ex){
        User user = new User("service status: down","service status: down","service status: down","This is a fallback service: Main service down");
        System.err.println("Rating/Hotel service is down: fallback executed");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}

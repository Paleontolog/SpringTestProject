package com.example.testproject.controllers;

import com.example.testproject.dto.ChangeQuoteDto;
import com.example.testproject.dto.QuoteDto;
import com.example.testproject.dto.UserDto;
import com.example.testproject.services.UserQuoteService;
import com.example.testproject.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserQuoteService userQuoteService;

    public UserController(UserService userService, UserQuoteService userQuoteService) {
        this.userService = userService;
        this.userQuoteService = userQuoteService;
    }

    @PutMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{user}/quote")
    public ResponseEntity<ChangeQuoteDto> saveVote(@PathVariable String user,
                                                   @Valid @RequestBody ChangeQuoteDto quote) {
        return new ResponseEntity<>(userQuoteService.saveQuote(quote, user), HttpStatus.CREATED);
    }

    @PostMapping("/{user}/quote")
    public ResponseEntity<ChangeQuoteDto> updateVote(@PathVariable String user,
                                                     @Valid @RequestBody ChangeQuoteDto quote) {
        return ResponseEntity.ok(userQuoteService.updateQuote(quote, user));
    }

    @DeleteMapping("/{user}/quote")
    public ResponseEntity<ChangeQuoteDto> deleteVote(@PathVariable String user,
                                                     @RequestParam Long id) {
        return ResponseEntity.ok(userQuoteService.deleteQuote(id, user));
    }


    @GetMapping("/{user}/quote")
    public ResponseEntity<List<QuoteDto>> getAllVotes(@PathVariable String user,
                                                      @RequestParam @Min(0) Integer limit) {
        return ResponseEntity.ok(userQuoteService.getQuotes(user, limit));
    }
}


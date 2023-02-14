package com.example.testproject.controllers;

import com.example.testproject.dto.QuoteDto;
import com.example.testproject.dto.QuoteHistoryDto;
import com.example.testproject.services.QuoteService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/{quoteId}")
    public ResponseEntity<QuoteDto> getVoteById(@PathVariable Long quoteId) {
        return ResponseEntity.ok(quoteService.getQuoteById(quoteId));
    }

    @GetMapping("/random")
    public ResponseEntity<QuoteDto> getRandomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }

    @GetMapping("/{quoteId}/history")
    public ResponseEntity<QuoteHistoryDto> getVoteHistory(@PathVariable Long quoteId) {
        return ResponseEntity.ok(quoteService.getQuoteHistory(quoteId));
    }

    @GetMapping
    public ResponseEntity<List<QuoteDto>> getQuotes(@RequestParam("limit") @Min(0) Integer limit,
                                                    @RequestParam("order") Sort.Direction order) {
        return ResponseEntity.ok(quoteService.getQuotes(limit, order));
    }


    @PostMapping("/{quoteId}/up")
    public ResponseEntity<QuoteDto> voteUp(@PathVariable @Min(0) Long quoteId) {
        return ResponseEntity.ok(quoteService.voteUp(quoteId));
    }

    @PostMapping("/{quoteId}/down")
    public ResponseEntity<QuoteDto> voteDown(@PathVariable @Min(0) Long quoteId) {
        return ResponseEntity.ok(quoteService.voteDown(quoteId));
    }
}

package com.example.testproject.services;

import com.example.testproject.dto.QuoteDto;
import com.example.testproject.dto.QuoteHistoryDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuoteService {
    QuoteDto getRandomQuote();
    QuoteDto getQuoteById(Long id);
    QuoteDto voteUp(Long quoteId);
    QuoteDto voteDown(Long quoteId);
    List<QuoteDto> getQuotes(Integer limit,  Sort.Direction order);
    QuoteHistoryDto getQuoteHistory(Long id);
}
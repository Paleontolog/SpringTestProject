package com.example.testproject.services;

import com.example.testproject.dto.ChangeQuoteDto;
import com.example.testproject.dto.QuoteDto;

import java.util.List;

public interface UserQuoteService {
    ChangeQuoteDto saveQuote(ChangeQuoteDto quoteDto, String user);

    ChangeQuoteDto updateQuote(ChangeQuoteDto quoteDto, String user);

    ChangeQuoteDto deleteQuote(Long id, String user);

    List<QuoteDto> getQuotes(String userName, Integer limit);
}

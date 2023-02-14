package com.example.testproject.mappers;

import com.example.testproject.dto.ChangeQuoteDto;
import com.example.testproject.dto.QuoteDto;
import com.example.testproject.entities.Quote;

public interface QuoteMapper {
    Quote dtoToQuote(ChangeQuoteDto quoteDto);
    ChangeQuoteDto quoteToChangeDto(Quote quote);
    QuoteDto quoteToDto(Quote quote);
}

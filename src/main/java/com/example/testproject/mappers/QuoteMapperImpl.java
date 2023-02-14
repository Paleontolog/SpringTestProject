package com.example.testproject.mappers;

import com.example.testproject.dto.QuoteDto;
import com.example.testproject.dto.ChangeQuoteDto;
import com.example.testproject.entities.Quote;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapperImpl implements QuoteMapper {

    @Override
    public Quote dtoToQuote(ChangeQuoteDto quoteDto) {
        var quote = new Quote();
        quote.setContent(quoteDto.content());
        return quote;
    }

    @Override
    public QuoteDto quoteToDto(Quote quote) {
        var currentVote = quote.getCurrentVote();
        return new QuoteDto(
                quote.getId(),
                quote.getContent(),
                quote.getUser().getName(),
                quote.getLastChangeTime(),
                currentVote != null ? currentVote.getCount() : 0
        );
    }

    @Override
    public ChangeQuoteDto quoteToChangeDto(Quote quote) {
        return new ChangeQuoteDto(
                quote.getId(),
                quote.getContent()
        );
    }
}

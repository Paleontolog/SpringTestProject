package com.example.testproject.services;

import com.example.testproject.dto.QuoteDto;
import com.example.testproject.dto.QuoteHistoryDto;
import com.example.testproject.entities.Quote;
import com.example.testproject.entities.Vote;
import com.example.testproject.exceptions.DataNotFound;
import com.example.testproject.mappers.QuoteMapper;
import com.example.testproject.repository.QuoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.example.testproject.exceptions.ErrorMessages.QUOTE_INFO;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final Random random;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
        this.random = new Random();
    }


    private Long getCurrentScore(Quote quote) {
        var currentVote = quote.getCurrentVote();
        return currentVote != null ? currentVote.getCount() : 0;
    }


    private void updateCurrentScore(Quote quote, Long addValue) {
        var score = getCurrentScore(quote);
        var newVote = new Vote();
        newVote.setCount(score + addValue);
        quote.newVote(newVote);
    }

    private Quote getByQuoteId(Long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new DataNotFound(QUOTE_INFO.format(id)));
    }

    @Override
    @Transactional
    public QuoteDto getQuoteById(Long id) {
        return quoteMapper.quoteToDto(getByQuoteId(id));
    }

    @Override
    @Transactional
    public QuoteDto getRandomQuote() {
        var maxId = quoteRepository.getMaxQuoteId()
                .orElse(0L);
        var randomId = random.nextLong(0L, maxId + 1);
        return quoteRepository
                .findFirstByIdGreaterThanEqual(randomId)
                .map(quoteMapper::quoteToDto)
                .orElseThrow(() -> new DataNotFound("Quotes"));
    }

    @Override
    @Transactional
    public QuoteDto voteUp(Long quoteId) {
        var quote = getByQuoteId(quoteId);
        updateCurrentScore(quote, 1L);
        return quoteMapper.quoteToDto(quote);
    }

    @Override
    @Transactional
    public QuoteDto voteDown(Long quoteId) {
        var quote = getByQuoteId(quoteId);
        updateCurrentScore(quote, -1L);
        return quoteMapper.quoteToDto(quote);
    }

    @Override
    public List<QuoteDto> getQuotes(Integer limit, Sort.Direction order) {
        return quoteRepository.findBestQuotes(
                        PageRequest.of(0, limit, order, "lastChangeTime")
                ).stream()
                .map(quoteMapper::quoteToDto)
                .toList();
    }

    @Override
    public QuoteHistoryDto getQuoteHistory(Long id) {
        return new QuoteHistoryDto(
                getByQuoteId(id).getVotes().stream()
                        .map(Vote::getCount)
                        .toList()
        );
    }
}
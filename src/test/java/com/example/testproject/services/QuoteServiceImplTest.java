package com.example.testproject.services;

import com.example.testproject.dto.QuoteDto;
import com.example.testproject.entities.Quote;
import com.example.testproject.entities.User;
import com.example.testproject.entities.Vote;
import com.example.testproject.mappers.QuoteMapper;
import com.example.testproject.mappers.QuoteMapperImpl;
import com.example.testproject.repository.QuoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class QuoteServiceImplTest {

    private static final QuoteMapper quoteMapper = new QuoteMapperImpl();

    private final List<Quote> quotes = List.of(
            createQuote(1L, "Content-1",
                    new ArrayList<>() {
                        {
                            add(createVote(1L, 1L));
                            add(createVote(2L, 2L));
                        }
                    }),
            createQuote(2L, "Content-2",
                    new ArrayList<>() {
                        {
                            add(createVote(1L, 1L));
                            add(createVote(2L, 2L));
                        }
                    }
            )
    );


    private final List<QuoteDto> quoteDtos = quotes.stream()
            .map(quoteMapper::quoteToDto)
            .toList();

    private static QuoteService quoteService;
    private static QuoteRepository employeeRepository;

    private static Quote createQuote(Long id, String content, List<Vote> votes) {
        var quote = new Quote();
        quote.setId(id);
        quote.setContent(content);
        quote.setVotes(votes);
        quote.setUser(createUser());
        quote.setCurrentVote(votes.get(votes.size() - 1));
        return quote;
    }

    private static Vote createVote(Long id, Long count) {
        var vote = new Vote();
        vote.setId(id);
        vote.setCount(count);
        return vote;
    }

    private static User createUser() {
        var user = new User();
        user.setName("name");
        user.setEmail("mail");
        user.setPassword("pass");
        return user;
    }

    @BeforeAll
    public static void setUp() {
        employeeRepository = Mockito.mock(QuoteRepository.class);
        quoteService = new QuoteServiceImpl(employeeRepository, quoteMapper);

    }

    @Test
    void getQuoteById() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(quotes.get(0)));

        var quote = quoteService.getQuoteById(1L);
        Assertions.assertEquals(quote, quoteDtos.get(0));
    }

    @Test
    void voteUp() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(quotes.get(0)));

        var quote = quoteService.voteUp(1L);
        Assertions.assertEquals(quote.count(), quoteDtos.get(0).count() + 1);
    }

    @Test
    void voteDown() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(quotes.get(0)));

        var quote = quoteService.voteDown(1L);
        Assertions.assertEquals(quote.count(), quoteDtos.get(0).count() - 1);
    }


    @Test
    void getQuoteHistory() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(quotes.get(0)));

        var quote = quoteService.getQuoteHistory(1L);
        Assertions.assertEquals(
                quote.voteHistory(),
                quotes.get(0).getVotes().stream().map(Vote::getCount).toList()
        );
    }
}
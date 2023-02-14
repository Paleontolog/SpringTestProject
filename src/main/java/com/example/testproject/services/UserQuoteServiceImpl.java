package com.example.testproject.services;

import com.example.testproject.dto.ChangeQuoteDto;
import com.example.testproject.dto.QuoteDto;
import com.example.testproject.entities.User;
import com.example.testproject.exceptions.DataAlreadyExist;
import com.example.testproject.exceptions.DataNotFound;
import com.example.testproject.mappers.QuoteMapper;
import com.example.testproject.repository.QuoteRepository;
import com.example.testproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testproject.exceptions.ErrorMessages.QUOTE_INFO;

@Service
public class UserQuoteServiceImpl implements UserQuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final UserRepository userRepository;


    public UserQuoteServiceImpl(QuoteRepository quoteRepository, QuoteMapper quoteMapper, UserRepository userRepository) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
        this.userRepository = userRepository;
    }

    private ChangeQuoteDto saveOrUpdate(ChangeQuoteDto quoteDto, User user) {
        var quote = quoteMapper.dtoToQuote(quoteDto);
        quote.setUser(user);
        return quoteMapper.quoteToChangeDto(quoteRepository.save(quote));
    }

    private User getUserOrThrow(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new DataNotFound("User '%s'".formatted(userName)));
    }

    @Override
    @Transactional
    public ChangeQuoteDto saveQuote(ChangeQuoteDto quoteDto, String userName) {
        var user = getUserOrThrow(userName);

        if (quoteDto.id() != null && quoteRepository.existsById(quoteDto.id())) {
            throw new DataAlreadyExist(QUOTE_INFO.format(quoteDto.id()));
        }

        return saveOrUpdate(quoteDto, user);
    }

    @Override
    @Transactional
    public ChangeQuoteDto updateQuote(ChangeQuoteDto quoteDto, String userName) {
        var user = getUserOrThrow(userName);

        if (quoteDto.id() == null || !quoteRepository.existsById(quoteDto.id())) {
            throw new DataNotFound(QUOTE_INFO.format(quoteDto.id()));
        }

        return saveOrUpdate(quoteDto, user);
    }

    @Override
    @Transactional
    public ChangeQuoteDto deleteQuote(Long id, String userName) {
        getUserOrThrow(userName);

        var quote = quoteRepository.findById(id);

        if (quote.isEmpty()) {
            throw new DataNotFound(QUOTE_INFO.format(id));
        }

        var responce = quoteMapper.quoteToChangeDto(quote.get());
        quoteRepository.delete(quote.get());
        return responce;
    }

    @Override
    @Transactional
    public List<QuoteDto> getQuotes(String userName, Integer limit) {
        getUserOrThrow(userName);
        return quoteRepository.findQuotesByUserNameOrderByLastChangeTimeDesc(
                        userName,
                        PageRequest.of(0, limit)
                ).stream()
                .map(quoteMapper::quoteToDto)
                .toList();
    }
}

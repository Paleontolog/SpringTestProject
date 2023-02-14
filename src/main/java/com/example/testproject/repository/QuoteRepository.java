package com.example.testproject.repository;

import com.example.testproject.entities.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface QuoteRepository extends CrudRepository<Quote, Long> {

    List<Quote> findQuotesByUserNameOrderByLastChangeTimeDesc(String userName, Pageable pageable);

    Optional<Quote> findQuoteByUserIdAndId(Long userId, Long id);

    @Query("SELECT q from Quote q LEFT JOIN fetch Vote v ON q.id = v.quote.id")
    List<Quote> findBestQuotes(Pageable pageable);

    Optional<Quote> findFirstByIdGreaterThanEqual(Long id);

    @Query("SELECT max(q.id) FROM Quote q")
    Optional<Long> getMaxQuoteId();
}

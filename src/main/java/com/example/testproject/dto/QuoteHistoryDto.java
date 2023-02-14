package com.example.testproject.dto;

import java.util.List;

public record QuoteHistoryDto(
        List<Long> voteHistory
){}

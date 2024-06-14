package com.travel.withtrip.service;

import com.travel.withtrip.mapper.FeedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedMapper feedMapper;

}

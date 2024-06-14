package com.travel.withtrip.service;

import com.travel.withtrip.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageMapper imageMapper;

    // 이미지 수정 시 순서(order) 주의
    // 1 2 3 4 중 2를 삭제하고 다른 이미지 추가하면 1 3 4 5 or 1 5 3 4


}

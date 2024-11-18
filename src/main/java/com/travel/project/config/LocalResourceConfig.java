package com.travel.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 로컬 서버에 저장된 이미지를 웹브라우저에서 불러올 수 있도록
// 로컬 서버 파일경로를 웹 서버 URL로 변경하는 설정
@Configuration
public class LocalResourceConfig implements WebMvcConfigurer {

    @Value("${file.upload.root-path}")
    private String rootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** 요청을 외부 경로로 매핑
        registry
                .addResourceHandler("/uploads/**") // 브라우저에서 접근할 URL 패턴
                .addResourceLocations("file:" + rootPath + "/"); // 실제 파일 경로
    }
}
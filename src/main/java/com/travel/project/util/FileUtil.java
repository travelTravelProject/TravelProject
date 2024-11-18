package com.travel.project.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    private static String rootPath;

    @Value("${file.upload.root-path}")
    public void setRootPath(String rootPath) {
        FileUtil.rootPath = rootPath; // 외부 경로를 설정
    }

    public static String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("업로드 파일이 비어 있습니다.");
            return null;
        }

        if (rootPath == null || rootPath.isBlank()) {
            log.error("파일 저장 경로(rootPath)가 설정되지 않았습니다.");
            throw new IllegalStateException("파일 저장 경로가 올바르지 않습니다. rootPath: " + rootPath);
        }

        // 파일명을 안전하게 정리
        String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
        String newFileName = UUID.randomUUID() + "_" + sanitizedFileName;

        // 날짜별 디렉토리 생성
        String newUploadPath = makeDateFormatDirectory(rootPath);

        try {
            // 파일 저장
            File destination = new File(newUploadPath, newFileName);
            file.transferTo(destination);
            log.info("파일 저장 성공: {}", destination.getAbsolutePath());
        } catch (IOException e) {
            log.error("파일 저장 실패: {}", e.getMessage());
            return null;
        }

        // URL 경로 반환: /uploads/yyyy/MM/dd/파일명
        return "/uploads" + newUploadPath.substring(rootPath.length()) + "/" + newFileName;
    }

    // 날짜별 디렉토리 생성
    private static String makeDateFormatDirectory(String rootPath) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<String> dateList = List.of(String.valueOf(year), len2(month), len2(day));

        String newDirectoryPath = rootPath;

        for (String dir : dateList) {
            newDirectoryPath += "/" + dir;
            File directory = new File(newDirectoryPath);
            if (!directory.exists() && !directory.mkdirs()) {
                log.error("디렉토리 생성 실패: {} (권한 문제 또는 경로 오류)", newDirectoryPath);
                throw new IllegalStateException("디렉토리 생성 실패: " + newDirectoryPath);
            }
        }

        return newDirectoryPath;
    }

    // 한자리수에 0을 더해 두자리로 변경
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }

    // 파일명을 정규화하여 안전하게 변환
    private static String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return "unknown";
        }

        // 확장자가 없는 경우 기본 확장자를 추가
        if (!fileName.contains(".")) {
            fileName += ".file"; // 기본 확장자
        }

        // 파일명에서 알파벳, 숫자, 한글, 점(.)을 제외한 모든 문자들을 밑줄(_)로 변환
        fileName = fileName.replaceAll("[^a-zA-Z0-9가-힣.]", "_");

        // 파일명이 모두 밑줄('_')로만 이루어진 경우 기본 파일명 추가
        if (fileName.replace("_", "").isEmpty()) {
            fileName = "unknown.file";
        }

        // 파일명 길이를 255자로 제한
        if (fileName.length() > 255) {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.substring(0, 255 - extension.length()) + extension;
        }

        return fileName;
    }
}

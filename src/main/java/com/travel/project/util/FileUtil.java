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
        FileUtil.rootPath = rootPath;
    }

    public static String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null; // 이미지 업로드가 없는 경우 null 반환
        }
        // 원본파일명을 중복이 없는 랜덤파일명으로 변경
        String newFileName = UUID.randomUUID() +"_"+ file.getOriginalFilename();
        log.debug("루트: {}", rootPath);
        log.debug("스태틱루트: {}", rootPath);

        // 이 첨부파일을 날짜별로 관리하기 위해 날짜 폴더를 생성
//        String newUploadPath = makeDateFormatDirectory(rootPath);
        String newUploadPath = makeDateFormatDirectory(rootPath);

        // 파일 업로드 수행
        try {
            file.transferTo(new File(newUploadPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 파일 전체 경로
        String fullPath = newUploadPath + "/" + newFileName;
        log.debug("풀패스: {}", fullPath);

        // url-path: /local/2024/06/05/dsfsdfd_dog.png
        String urlPath = "/local" + fullPath.substring(rootPath.length());

        return urlPath;
    }
    private static String makeDateFormatDirectory(String rootPath) {

        // 오늘 날짜 정보를 추출
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<String> dateList = List.of(year+"", len2(month), len2(day));

        // rootPath - E:/spring_prj/upload
        String newDirectoryPath = rootPath;

        // rootPath/2024/06/05
        for (String s : dateList) {
            newDirectoryPath += "/" + s;
            File f = new File(newDirectoryPath);
            if (!f.exists()) f.mkdir();
        }

        return newDirectoryPath;
    }

    // 한자리수에 0을 더해 두자리로 변경
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }
}

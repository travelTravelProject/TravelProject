package com.travel.project.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    static String rootPath = System.getProperty("user.dir")
            + "/src/main/resources/static/assets/upload";
    // C:/Users/user/Desktop/travel/TravelProject (git clone으로 생긴 루트폴더까지의 경로)
    // System.getProperty("user.dir")을 통해
    // 현재 working dir까지 가져오고 그 다음 경로 추가해 줌

    public static String uploadFile(MultipartFile file) {
        // 원본파일명을 중복이 없는 랜덤파일명으로 변경
        String newFileName = UUID.randomUUID() +"_"+ file.getOriginalFilename();

        // 이 첨부파일을 날짜별로 관리하기 위해 날짜 폴더를 생성
        String newUploadPath = makeDateFormatDirectory(rootPath);

        // 파일 업로드 수행
        try {
            file.transferTo(new File(newUploadPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 파일 전체 경로
        String fullPath = newUploadPath + "/" + newFileName;

        // url-path: /assets/upload/2024/06/05/dsfsdfd_dog.png
        String urlPath = "/assets/upload" + fullPath.substring(rootPath.length()); // 2024~

        // 업로드가 완료되면 데이터베이스에 파일의 경로 위치를 저장
        // ex) /local/20204/06/05/dsfsdfd_dog.png

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

        // rootPath - E:/spring_prj/upload/2024/06/05
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

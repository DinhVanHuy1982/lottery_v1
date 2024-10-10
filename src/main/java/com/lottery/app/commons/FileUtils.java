package com.lottery.app.commons;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static int validateAttachFileExcel(MultipartFile attachFile, String fileName) {
        String fileExt = "xlsx,xls";
        return validate(attachFile, fileName, fileExt);
    }

    private static int validate(MultipartFile attachFile, String fileName, String fileExt) {
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));

        String fileType = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
        if (!lstValidFileExt.contains(fileType)) {
            return 24; //24-wrongFileType
        }

        return 0; //0-validate ok
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}

package com.lottery.app.commons;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(FileExcelUtil.class);

    @Value("${template.folder}")
    private String templateFolder;

    public static final String DOCUMENT_TYPE_TEMPLATE = "danh-sach-cac-loai-van-ban-trong-ho-so-biet-thu.xlsx";
    public static final String VILLA_TEMPLATE = "File mẫu_Danh sách biệt thự.xlsx";
    public static final String VILLA_EXPORT = "danh-sach-biet-thu.xlsx";
    public static final int TYPE_OOD = 1;
    public static final int TYPE_EVEN = 0;
    private static final String PROPERTY_HIDDEN = "hidden";
}

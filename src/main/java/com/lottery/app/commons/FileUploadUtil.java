package com.lottery.app.commons;

import com.lottery.app.config.Constants;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ITSOL maint Oct 8, 2018 ITSOL maint
 * @author ThangNM-ITSOL
 * @version Oct 10, 2018 ThangNM-ITSOL Init.
 */
public class FileUploadUtil {

    public static final int HEADER_ROW_INDEX = 0;
    public static final String HTML = ".html";
    public static final String JSP = ".jsp";
    public static final String HTM = ".htm";
    public static final String SVG = ".svg";

    public static final String PHP = ".php";

    private FileUploadUtil() {}

    /**
     * check file upload not null or not empty
     *
     * @param files CommonsMultipartFile[]
     * @return return true if files upload is not null or not empty else return false
     */
    public static boolean isNotNullOrEmpty(MultipartFile files) {
        return files != null && StringUtils.isNotBlank(files.getOriginalFilename());
    }

    /**
     * Read excel in sheet 0
     *
     * @param excelFile MultipartFile
     * @return List data in excel file
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static List<List<String>> excelReader(MultipartFile excelFile) throws IOException {
        List<List<String>> datas = new ArrayList<>();
        InputStream inputStream = excelFile.getInputStream();
        Workbook workbook;
        workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            List<String> cells = new ArrayList<>();

            for (int i = 0; i < currentRow.getLastCellNum(); i++) {
                Cell currCell = currentRow.getCell(i);
                String cellVal;
                if (currCell != null) {
                    if (currCell.getCellType() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(currCell)) {
                            Date date = currCell.getDateCellValue();
                            DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DDMMYYY);
                            cellVal = dateFormat.format(date);
                        } else {
                            cellVal = formatter.formatCellValue(currCell);
                            cellVal = cellVal.replaceAll(Constants.COMMA_DELIMITER, StringUtils.EMPTY);
                        }
                    } else {
                        cellVal = formatter.formatCellValue(currCell);
                    }
                } else {
                    cellVal = StringUtils.EMPTY;
                }

                cells.add(cellVal);
            }

            datas.add(cells);
        }

        return datas;
    }

    /**
     *
     * @param excelFile MultipartFile
     * @param iSheet
     * @param iBeginRow
     * @param iFromCol
     * @param iToCol
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static List<List<String>> excelReader(Sheet worksheet, int iBeginRow, int iFromCol, int iToCol) throws IOException {
        List<List<String>> result = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD);
        for (int i = iBeginRow; i <= worksheet.getLastRowNum(); i++) {
            List<String> list = new ArrayList<>();
            Row row = worksheet.getRow(i);

            if (row != null) {
                String cellValue;
                for (int j = iFromCol; j < iToCol; j++) {
                    Cell cell = row.getCell(j);

                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        if (cellType == CellType.STRING) {
                            cellValue = cell.getStringCellValue().trim();
                        } else if (cellType == CellType.NUMERIC) {
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Double doubleValue = (Double) cell.getNumericCellValue();
                                Date date = HSSFDateUtil.getJavaDate(doubleValue);
                                cellValue = simpleDateFormat.format(date);
                            } else {
                                cellValue = formatter.formatCellValue(cell);
                                cellValue = cellValue.replaceAll(Constants.COMMA_DELIMITER, StringUtils.EMPTY);
                            }
                        } else {
                            cellValue = formatter.formatCellValue(cell);
                        }
                        list.add(cellValue);
                    } else {
                        list.add(null);
                    }
                }
                result.add(list);
            }
        }
        return result;
    }

    @SuppressWarnings("resource")
    public static void removeEmptyRows(Sheet worksheet, int iBeginRow, int iFromCol, int iToCol) {
        int lastRowNum = worksheet.getLastRowNum();
        for (int i = lastRowNum; i >= iBeginRow; i--) {
            Row row = worksheet.getRow(i);
            if (row != null) {
                boolean hasDataOrBorder = false;

                for (int j = iFromCol; j < iToCol; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        String cellValue = cell.toString().trim();
                        CellStyle cellStyle = cell.getCellStyle();

                        if (
                            !cellValue.isEmpty() ||
                            cellStyle.getBorderTop() != BorderStyle.NONE ||
                            cellStyle.getBorderBottom() != BorderStyle.NONE ||
                            cellStyle.getBorderLeft() != BorderStyle.NONE ||
                            cellStyle.getBorderRight() != BorderStyle.NONE
                        ) {
                            hasDataOrBorder = true;
                            break;
                        }
                    }
                }

                if (!hasDataOrBorder) {
                    worksheet.removeRow(row);
                    if (i < lastRowNum) {
                        worksheet.shiftRows(i + 1, lastRowNum, -1);
                    }
                    lastRowNum--;
                }
            }
        }
    }

    //    public static List<List<String>> excelReaderWithOriginalDate( MultipartFile excelFile, int iSheet, int iBeginRow, int iFromCol,
    //                                                                  int iToCol) throws IOException {
    //        List<List<String>> result = new ArrayList<>();
    //        InputStream inputStream = excelFile.getInputStream();
    //        String extention = FilenameUtils.getExtension(excelFile.getOriginalFilename());
    //        Workbook workbook;
    //        if (Constants.EXTENSION_XLSX.equalsIgnoreCase(extention)) {
    //            workbook = new XSSFWorkbook(inputStream);
    //        } else {
    //            workbook = new HSSFWorkbook(inputStream);
    //        }
    //        Sheet worksheet = workbook.getSheetAt(iSheet);
    //        DataFormatter formatter = new DataFormatter();
    //        for (int i = iBeginRow; i <= worksheet.getLastRowNum(); i++) {
    //            List<String> list = new ArrayList<>();
    //            Row row = worksheet.getRow(i);
    //
    //            if (row != null) {
    //                String cellValue;
    //                for (int j = iFromCol; j < iToCol; j++) {
    //
    //                    Cell cell = row.getCell(j);
    //
    //                    if (cell != null) {
    //                        CellType cellType = cell.getCellType();
    //                        if (cellType == CellType.STRING) {
    //                            cellValue = cell.getStringCellValue().trim();
    //                        } else if (cellType == CellType.NUMERIC) {
    //                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
    //                                cellValue = formatter.formatCellValue(cell);
    //                            } else {
    //                                cellValue = formatter.formatCellValue(cell);
    //                                cellValue = cellValue.replaceAll(Constants.COMMA_DELIMITER, StringUtils.EMPTY);
    //                            }
    //                        } else {
    //                            cellValue = formatter.formatCellValue(cell);
    //                        }
    //                        list.add(cellValue);
    //                    } else {
    //                        list.add(null);
    //                    }
    //                }
    //                result.add(list);
    //            }
    //        }
    //        return result;
    //    }

    public static boolean checkValidFileFormat(String filename) {
        String fileExt = "7z,rar,zip,txt,ppt,pptx,doc,docx,xls,xlsx,pdf,jpg,jpeg,png,bmp,gif";
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1 || lastIndexOf == filename.length() - 1) {
            return false;
        }
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));
        //        List<String> lstFileName = Arrays.asList(filename.split("\\."));
        List<String> lstFileName = Arrays.asList(filename.substring(lastIndexOf + 1));
        //        if(lstFileName.size()<=1)
        if (lstFileName.size() < 1) return false;
        //        for(int i = 1; i < lstFileName.size();i++){
        for (int i = 0; i < lstFileName.size(); i++) {
            if (!lstValidFileExt.contains(lstFileName.get(i))) return false;
        }
        return true;
    }
}

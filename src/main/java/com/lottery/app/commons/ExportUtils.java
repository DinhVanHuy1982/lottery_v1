package com.lottery.app.commons;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class ExportUtils {

    private static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";

    public Font getFontHeaderBold(Workbook workbook) {
        Font fontHeaderBold = workbook.createFont();
        fontHeaderBold.setBold(true);
        fontHeaderBold.setFontName(FONT_TIMES_NEW_ROMAN);
        fontHeaderBold.setFontHeightInPoints(Short.parseShort("12"));
        return fontHeaderBold;
    }

    public Font getFontHeader(Workbook workbook) {
        Font fontHeaderBold = workbook.createFont();
        fontHeaderBold.setBold(false);
        fontHeaderBold.setFontName(FONT_TIMES_NEW_ROMAN);
        fontHeaderBold.setFontHeightInPoints(Short.parseShort("12"));
        return fontHeaderBold;
    }

    public Font getFontContent(Workbook workbook) {
        Font fontHeaderBold = workbook.createFont();
        fontHeaderBold.setBold(false);
        fontHeaderBold.setFontName(FONT_TIMES_NEW_ROMAN);
        fontHeaderBold.setFontHeightInPoints(Short.parseShort("12"));
        return fontHeaderBold;
    }

    public Font getFontTimeNewRoman(Workbook workbook) {
        Font fontTimeNewRoman = workbook.createFont();
        fontTimeNewRoman.setBold(true);
        fontTimeNewRoman.setFontName(FONT_TIMES_NEW_ROMAN);
        fontTimeNewRoman.setFontHeightInPoints(Short.parseShort("12"));
        return fontTimeNewRoman;
    }

    public Font getFontTimeNewRomanErr(Workbook workbook) {
        Font fontTimeNewRoman = workbook.createFont();
        fontTimeNewRoman.setBold(false);
        fontTimeNewRoman.setFontName(FONT_TIMES_NEW_ROMAN);
        fontTimeNewRoman.setFontHeightInPoints(Short.parseShort("12"));
        fontTimeNewRoman.setColor(Font.COLOR_RED);
        return fontTimeNewRoman;
    }
}

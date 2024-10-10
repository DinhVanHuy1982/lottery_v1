/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.lottery.app.commons;

import com.lottery.app.config.Constants;
import com.lottery.app.config.ServiceResult;
import jakarta.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;

/**
 * @author Admin
 * @version 1.0
 */
public class DataUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    static final String YYYY_PT = "yyyy";
    static final String YYYYMM_PT = "yyyyMM";

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private DataUtil() {
        throw new IllegalStateException("DataUtils class Not Need To Initial");
    }

    public static String makeLikeQuery(String s) {
        if (StringUtils.isEmpty(s)) return null;
        s =
            s
                .trim()
                .toLowerCase()
                .replace("!", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "!")
                .replace("%", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "%")
                .replace("_", Constants.DEFAULT_ESCAPE_CHAR_QUERY + "_");
        return "%" + s + "%";
    }

    /**
     * Copy du lieu tu bean sang bean moi
     * Luu y chi copy duoc cac doi tuong o ngoai cung, list se duoc copy theo tham chieu
     * <p>
     * Chi dung duoc cho cac bean java, khong dung duoc voi cac doi tuong dang nhu String, Integer, Long...
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static boolean isNullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */

    /**
     * Upper first character
     *
     * @param input
     * @return
     */
    public static String upperFirstChar(String input) {
        if (DataUtil.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static BigDecimal safeToBigDecimal(Object obj1, BigDecimal defaultValue) {
        BigDecimal result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return (BigDecimal) obj1;
            }
            if (obj1 instanceof Double) {
                return BigDecimal.valueOf((Double) obj1);
            }

            if (obj1 instanceof Integer) {
                return BigDecimal.valueOf((Integer) obj1);
            }

            try {
                result = new BigDecimal(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static BigDecimal safeToBigDecimal(Object obj1) {
        return safeToBigDecimal(obj1, null);
    }

    public static Long safeToLong(Object obj1, Long defaultValue) {
        Long result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, null);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static Short safeToShort(Object obj1, Short defaultValue) {
        Short result = defaultValue;
        if (obj1 != null) {
            try {
                result = Short.parseShort(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1
     * @param defaultValue
     * @return
     * @author phuvk
     */
    public static int safeToInt(Object obj1, int defaultValue) {
        int result = defaultValue;
        if (obj1 != null) {
            try {
                result = Integer.parseInt(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return int
     */
    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }

    public static Integer safeToIntCus(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return defaultValue;
        }

        return obj1.toString();
    }

    public static String safeToInteger(Object obj1) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return null;
        }

        return obj1.toString();
    }

    public static Boolean safeToBoolean(Object obj1) {
        if (obj1 == null || obj1 instanceof Boolean) {
            return (Boolean) obj1;
        }
        return false;
    }

    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static Instant safeToInstant(Object obj) {
        if (obj != null) {
            if (obj instanceof Instant) {
                return (Instant) obj;
            } else if (obj instanceof Timestamp) {
                return ((Timestamp) obj).toInstant();
            }
        }
        return null;
    }

    public static Date safeToDate(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        return null;
    }

    /**
     * safe equal
     *
     * @param obj1 String
     * @param obj2 String
     * @return boolean
     */
    public static boolean safeEqual(String obj1, String obj2) {
        return obj1.equals(obj2);
    }

    /**
     * check null or empty
     * Su dung ma nguon cua thu vien StringUtils trong apache common lang
     *
     * @param cs String
     * @return boolean
     */
    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * @param obj1
     * @return
     * @deprecated Ham nay mac du nhan tham so truyen vao la object nhung gan nhu chi hoat dong cho doi tuong la string
     * Chuyen sang dung isNullOrEmpty thay the
     */
    @Deprecated
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || "".equals(obj1.toString().trim());
    }

    public static BigInteger length(BigInteger from, BigInteger to) {
        return to.subtract(from).add(BigInteger.ONE);
    }

    /**
     * add
     *
     * @param obj1 BigDecimal
     * @param obj2 BigDecimal
     * @return BigDecimal
     */
    public static BigInteger add(BigInteger obj1, BigInteger obj2) {
        if (obj1 == null) {
            return obj2;
        } else if (obj2 == null) {
            return obj1;
        }

        return obj1.add(obj2);
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        return false;
    }

    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }

    public static String makeLikeParam(String s) {
        if (StringUtils.isEmpty(s)) return s;
        s = s.trim().toLowerCase();
        return "%" + s + "%";
    }

    /**
     * @param date
     * @param format yyyyMMdd, yyyyMMddhhmmss,yyyyMMddHHmmssSSS only
     * @return
     */
    public static Integer getDateInt(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return Integer.parseInt(dateStr);
    }

    private static void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static Date getFirstDateOfMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) / 3) * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Thang 1 thi calendar.MONTH = 0
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static boolean isDate(String str, String format) {
        if (StringUtils.isEmpty(str)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            return str.equals(sdf.format(date));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static Date getDatePattern(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    public static String formatDatePattern(Integer prdId, String pattern) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(prdId.toString());

            SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
            result = sdf2.format(date);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static String formatQuarterPattern(Integer prdId) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(prdId.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            String result2 = sdf2.format(date);

            result = (cal.get(Calendar.MONTH) / 3 + 1) + "/" + result2;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static Long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return -1L;
        }
    }

    public static Date add(Date fromDate, int num, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(type, num);
        return cal.getTime();
    }

    public static String dateToString(Date fromDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fromDate);
    }

    public static String dateToStringQuater(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(fromDate);
        return (cal.get(Calendar.MONTH) / 3 + 1) + "/" + year;
    }

    public static String getExternalUrl(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
            logger.error(ignored.getMessage(), ignored);
        }
        return String.format("%s://%s:%s%s", protocol, hostAddress, serverPort, contextPath);
    }

    public static Instant toInstant(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof java.sql.Date) {
            return Instant.ofEpochMilli(((java.sql.Date) object).getTime());
        } else if (object instanceof Timestamp) {
            return Instant.ofEpochMilli(((Timestamp) object).getTime());
        }
        return null;
    }

    public static String nvl(Object objInput, String strNullValue) {
        if (objInput == null) return strNullValue;
        if ("null".equalsIgnoreCase(objInput.toString())) return strNullValue;
        return objInput.toString().trim();
    }

    public static boolean validString(String temp) {
        return temp != null && !"".equals(temp.trim());
    }

    public static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();

        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return sb.toString();
    }

    //    public static Document parseXmlString(String inputString) {
    //        try {
    //            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    //            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    //            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
    //            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    //            dbFactory.setXIncludeAware(false);
    //            dbFactory.setExpandEntityReferences(false);
    //
    //            DocumentBuilder db = dbFactory.newDocumentBuilder();
    //            InputSource is = new InputSource(new StringReader(inputString));
    //            return db.parse(is);
    //        } catch (ParserConfigurationException | SAXException | IOException e) {
    //            throw new DataUtilException(ErrorMessage.PARSE_XML_ERROR.name());
    //        }
    //    }

    /**
     * trim() value for String field of Object
     *
     * @param t
     * @param <T>
     */
    public static <T> void trim(T t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (String.class.equals(field.getType())) {
                try {
                    Object value = field.get(t);
                    if (value != null) {
                        field.set(t, ((String) value).trim());
                    }
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }

    public static boolean isValidMsisdn(String msisdn, List<String> prefixList) {
        if (!validString(msisdn) || prefixList.isEmpty()) {
            return false;
        }

        if (isNumeric(msisdn)) {
            for (String prefix : prefixList) {
                String[] spl = prefix.split("-"); //Ex: 8498-11. 8498: prefix, 11: length of msisdn
                if (spl.length != 2 || !isNumeric(spl[1])) {
                    continue;
                }
                if (msisdn.startsWith(spl[0]) && Integer.parseInt(spl[1]) == msisdn.length()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getSafeFileName(String fileName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (c != '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean validatePattern(String pattern, String value) {
        return Pattern.matches(pattern, value);
    }

    //    public static ServiceResult convertDataFromException(String data) throws JSONException {
    //        Gson gson = new Gson();
    //        JSONObject jsonResponse = new JSONObject(data);
    //        return gson.fromJson(jsonResponse.toString(), ServiceResult.class);
    //    }

    public static String validateKeySearch(String str) {
        if (Objects.isNull(str) || StringUtils.isBlank(str)) {
            return "%%";
        }
        return "%" + str.replace("&", "&&").replace("%", "&%").replace("_", "&_").trim() + "%";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);

        BigDecimal valueDecimal = new BigDecimal(value + "");
        value = valueDecimal.multiply(new BigDecimal(factor + "")).doubleValue();

        long tmp = Math.round(value);
        return (double) tmp / (factor);
    }

    public static String formatFileServer(String file) {
        if (file == null || file.equals("")) {
            return null;
        } else {
            String[] items = file.split("/");
            String fileName = items[items.length - 1];
            String[] fileTypes = fileName.split("\\.");
            String fileConvert = "file." + fileTypes[1];
            String result = "";
            for (int i = 0; i < items.length - 1; i++) {
                if (!items[i].equals("")) {
                    result += "/" + items[i];
                }
            }
            result += "/" + fileConvert;
            return result;
        }
    }

    public static String escapeSpecialCharacters(String value) {
        return value.replace("/", "\\/").replace("%", "\\%").replace("_", "\\_").replace("&", "\\&").replace(".", "\\.");
    }

    /**
     * Hàm check số thập phân khi nhập 1.000,02
     *
     * @param str String
     * @return Boolean
     * @author LeAnh
     * @since 18/09/2024
     */
    public static Boolean checkNumberDecimal(String str) {
        if (StringUtils.isBlank(str)) {
            return Boolean.FALSE;
        } else if (str.contains(Constants.MINUS)) {
            return Boolean.FALSE;
        } else if (str.contains(Constants.COMMA_DELIMITER)) {
            str = str.replace(Constants.DOT, StringUtils.EMPTY);
            str = str.replace(Constants.COMMA_DELIMITER, Constants.DOT);
            if (str.substring(0, 1).equalsIgnoreCase(Constants.CHAR_ZERO)) {
                str = str.substring(1);
            }
            return NumberUtils.isCreatable(str);
        } else {
            return true;
        }
    }

    /**
     * Hàm check độ dài ký tự của số thập phân
     *
     * @param str String
     * @return Boolean
     * @author LeAnh
     * @since 23/09/2024
     */
    public static Boolean checkLengthDecimal(String str) {
        if (StringUtils.isBlank(str)) {
            return Boolean.FALSE;
        } else if (str.contains(Constants.MINUS)) {
            return Boolean.FALSE;
        } else if (str.contains(Constants.COMMA_DELIMITER)) {
            String start = str.substring(0, str.indexOf(Constants.COMMA_DELIMITER)).replace(Constants.DOT, StringUtils.EMPTY);
            String end = str.substring(str.indexOf(Constants.COMMA_DELIMITER) + 1); // không lấy dấu phẩy
            if (start.length() > 10) {
                return Boolean.FALSE;
            }
            if (end.length() > 2) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } else if (str.contains(Constants.DOT)) {
            str = str.replace(Constants.DOT, StringUtils.EMPTY);
            if (str.length() > 10) return false;
            return true;
        } else {
            if (str.length() > 10) return false; else return true;
        }
    }

    /**
     * kiểm tra định dạng số thập phân được ngăn cách nhau bằng dấu phẩy
     *
     * @param str: giá trị kiểm tra; positiveIntegers số lượng số phần nguyên, decimalNumbers: số lượng số phần thập phân
     * @return boolean
     * @throws
     * @author DVHuy
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     * @since 9/24/2024
     */
    public static Boolean checkFormatDecimal(String str) {
        if (str.contains(Constants.COMMA_DELIMITER)) {
            List<String> lstValueDelimiter = Arrays.stream(str.split(Constants.COMMA_DELIMITER)).toList();
            if (lstValueDelimiter.size() > 2) return false; // nếu có trên 2 dấu phẩy

            if (lstValueDelimiter.get(1).contains(Constants.DOT)) return false; // nếu phần thập phân có kí tự .

            try {
                Long.parseLong(lstValueDelimiter.get(0).replace(Constants.DOT, StringUtils.EMPTY));
                Long.parseLong(lstValueDelimiter.get(1));
                return Boolean.TRUE;
            } catch (Exception ex) {
                return false;
            }
        } else {
            str = str.replace(Constants.DOT, StringUtils.EMPTY);
            try {
                Long.parseLong(str);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Append Like And Trim Space Expression
     *
     * @param value String
     * @return String Result
     * @author Vo Hoang Duong
     * @since 02/10/2024
     */
    public static String appendLikeAndTrimSpaceExpression(String value) {
        return String.format("%%%s%%", value.replaceAll("( +)", " ").trim());
    }

    /**
     * Remove Accent
     *
     * @param s String
     * @return String Result
     * @author Vo Hoang Duong
     * @since 02/10/2024
     */
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static void setQueryParameters(Map<String, Object> queryParams, Query query) {
        if (MapUtils.isNotEmpty(queryParams)) {
            queryParams.forEach(query::setParameter);
        }
    }
}

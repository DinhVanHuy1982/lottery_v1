package com.lottery.app.config;

import java.util.List;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "vi";

    private Constants() {}

    public static final String ALIGN_LEFT = "LEFT";
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String STRING = "STRING";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String DATE_FORMAT_DDMMYYY = "dd/MM/yyyy";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String CONDITION = "TT";
    public static final String ROOF_STRUCTURE = "KCM";
    public static final String GROUP_VILLA = "NBT";
    public static final String OWNERSHIP = "HTSH";
    public static final String APARTMENT_NUMBER = "apartmentNumber";
    public static final String VILLAOWNERSHIP = "villaOwnership";
    public static final String DOCUMENTS_TYPE = "documentType";
    public static final String AUTO_PROCESS = "auto-process";

    public static class CODE_STATUS {

        public static final String SUCCESS = "00"; //Thành công
        public static final String ERROR = "01"; // Thất bại
        public static final String INTERNAL_SERVER_ERROR = "02"; // Lỗi do code
        public static final String NOT_FOUND = "03";
        public static final String BAD_REQUEST = "04";
        public static final String NOT_BLANK = "05";
        public static final String NOT_VALID = "06";
        public static final String VALIDATE = "07";
        public static final String MAX_LENGTH = "08";
        public static final String NOT_EXISTS = "09";
        public static final String CANNOT_ACTION = "10";

        // tiep tuc tư ma 20, cac ma khac da su dung ben duoi
        public static final String REQUIRES_CORRECT_FORMAT = "20";
        public static final String DUPLICATE_NAME = "21"; // trùng tên
        public static final String DUPLICATE_IDENTIFIER = "22"; // trùng mã định danh
    }

    public static class CODE_MESSAGE {

        public static final String SUCCESS = "message.success";
    }

    public static class FONT {

        public static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";
    }

    public static class FileExtension {

        public static final String VIDEO = "mp4,wmv,flv,mov,avi,mkv,vob,webm";
        public static final String DOC = "doc,docx";
        public static final String PDF = "pdf";
        public static final String IMAGE = "jpg,jpeg,png,tiff,bmp";
    }

    public static class TypeUpFile {

        public static final String AUDIO = "AUDIO";
        public static final String VIDEO = "VIDEO";
    }

    // response login
    public static class ErrorCodeJwtResponse {

        public static final String SUCCESS = "00"; //Thành công
        public static final String ACCOUNT_INVALID = "11"; // Tài khoản mật khẩu không chính xác
        public static final String ACCOUNT_NOT_ACTIVE = "12"; // Tài khoản đã ngừng hoạt động
        public static final String FAIL = "13"; // Lỗi Authen

        private ErrorCodeJwtResponse() {}
    }

    public static class UserJWTControllerCode {

        public static class ApiForgotPassword {

            public static final String USER_NOT_FOUND = "14"; //Không tìm thấy user
            public static final String USER_NOT_ACTIVE = "15"; //User không acvite
            public static final String MAX_RESET_COUNT = "16"; //Số lần reset mật khẩu đã vượt quá giới hạn
            public static final String EMAIL_NULL = "17"; // khong thay email
        }

        public static class ApiForgotPasswordOtp {

            public static final String OTP_INVALID = "18"; //OTP không hợp lệ
            public static final String OTP_EXPIRED = "19"; //OTP đã hết hạn
        }
    }

    public static class TypeAction {

        public static final String CREATE = "create";
        public static final String UPDATE = "update";
    }

    public static final String ERROR_RETURN = "errorReturn";

    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static final String NUMERIC = "NUMERIC";

    public static final char DEFAULT_ESCAPE_CHAR_QUERY = '\\';
    public static final String COMMA_DELIMITER = ",";
    public static final String DOT = ".";
    public static final String EXTENSION_XLSX = "xlsx";
    public static final String EXTENSION_XLS = "xls";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_dd_HH_mm_ss_SS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYY_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String CHAR_STAR = "*";
    public static final String CHAR_ZERO = "0";
    public static final String MINUS = "-";

    public static final String REGEX_DATA =
        "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";

    // Villa
    public static class VILLA_OWNERSHIP {

        public static final String NATION = "NATION";
    }

    public static class VILLA_CLASSIFY {

        public static final Long OWNER_BY_ONE = 0L;
        public static final Long OWNER_BY_MANY_PEOPLE = 1L;
    }

    public static class PUBLIC_VILLA_INFOMATION {

        public static final Long PRIVATE = 0L;
        public static final Long PUBLIC = 1L;
    }

    public static class VILLA_INFO_HOUSE {

        public static final Long MAIN_HOUSE = 0L;
        public static final Long OUTGOINGS = 1L;
    }

    // File_server_check
    public static class IS_SCAN {

        public static final Long NOT_SCANED = 0L;
        public static final Long SCANED = 1L;
    }

    public static class FILE_EXAMPLE {

        public static final String LIST_DOCUMENT_PROFILE = "File-mau-danh-dach-tai-lieu-ho-so.xlsx";
    }

    // loai ban
    public static class DOCUMENT_TYPE {

        public static final Long ORIGINAL = 0L;
        public static final Long NOTARIZED_COPY = 1L;
        public static final Long NON_NOTARIZED_COPY = 2L;
        public static final List<Long> LIST_TYPE = List.of(
            new Long[] { DOCUMENT_TYPE.ORIGINAL, DOCUMENT_TYPE.NOTARIZED_COPY, DOCUMENT_TYPE.NON_NOTARIZED_COPY }
        );
    }

    public static class DOCUMENT_TYPE_STR {

        public static final String ORIGINAL_STR = "BẢN GỐC";
        public static final String NOTARIZED_COPY_STR = "BẢN SAO CÓ CÔNG CHỨNG";
        public static final String NON_NOTARIZED_COPY_STR = "BẢN SAO KHÔNG CÓ CÔNG CHỨNG";
        public static final List<String> LIST_TYPE_STR = List.of(
            new String[] { DOCUMENT_TYPE_STR.ORIGINAL_STR, DOCUMENT_TYPE_STR.NOTARIZED_COPY_STR, DOCUMENT_TYPE_STR.NON_NOTARIZED_COPY_STR }
        );
    }

    // kieu du lieu
    public static class FORMAT_TYPE {

        public static final Long TEXT = 0L;
        public static final Long NUMBER = 1L;
        public static final Long DATE = 2L;
    }

    public static class TYPE {

        public static final String NAVIGATE = "navigate";
        public static final String IMAGE_INFO = "image_info";
        public static final String PDF_INFO = "pdf_info";
    }
}

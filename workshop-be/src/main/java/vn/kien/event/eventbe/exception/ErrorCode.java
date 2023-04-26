package vn.kien.event.eventbe.exception;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:51
 */
public class ErrorCode {
    public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
    public static final String ACCOUNT_EXISTED = "ACCOUNT_EXISTED";
    public static final String ACCOUNT_NOT_ACTIVE = "ACCOUNT_NOT_ACTIVE";
    public static final String MAIL_SENDING_ERROR = "MAIL_SENDING_ERROR";
    public static final String INVALID_ACCOUNT = "INVALID_ACCOUNT";
    public static final String INVALID_CODE_ACTIVE_ACCOUNT = "INVALID_CODE_ACTIVE_ACCOUNT";
    public static final String INVALID_CODE = "INVALID_CODE";
    public static final String ID_IS_NOT_EMPTY = "ID_IS_NOT_EMPTY";
    public static final String MAJOR_NOT_FOUND = "MAJOR_NOT_FOUND";
    public static final String COURSE_NOT_FOUND = "COURSE_NOT_FOUND";

    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String OLD_PASSWORD_INCORRECT = "OLD_PASSWORD_INCORRECT";
    public static final String USER_NOT_REGISTER_EVENT = "USER_NOT_REGISTER_EVENT";

    public static final String EVENT_NOT_FOUND = "EVENT_NOT_FOUND";
    public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";

    public static final String RECOMMEND_NOT_FOUND = "RECOMMEND_NOT_FOUND";
    public static final String RECOMMEND_NOT_ALLOW_USER_UPDATE = "RECOMMEND_NOT_ALLOW_USER_UPDATE";

    public static final String TIME_START_MUST_BEFORE_TIME_END = "TIME_START_MUST_BEFORE_TIME_END";
    public static final String TIME_START_MUST_AFTER_NOW = "TIME_START_MUST_AFTER_NOW";
    public static final String STATUS_NOT_FOUND = "STATUS_NOT_FOUND";
    public static final String STATUS_MUST_IN = "STATUS_MUST_IN";
    public static final String ADMIN_NOT_SEND_MESSAGE_EVENT = "ADMIN_NOT_SEND_MESSAGE_EVENT";
    public static final String NOTIFICATION_NOT_FOUND = "NOTIFICATION_NOT_FOUND";

    public static final String USER_IN_EVENT = "USER_IN_EVENT";
    public static final String TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_UPCOMING = "TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_UPCOMING";
    public static final String TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENING = "TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENING";
    public static final String TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENED = "TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENED";
    public static final String USER_REGISTERED_EVENT = "USER_REGISTERED_EVENT";
    public static final String PASSWORD_OLD_NOT_MATCH_PASSWORD_CONFIRM = "PASSWORD_OLD_NOT_MATCH_PASSWORD_CONFIRM";
}

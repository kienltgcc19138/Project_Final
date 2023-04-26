package vn.kien.event.eventbe.common;


public class EnumConst {
    public enum UserStatusEnum {
        ENABLE,
        DISABLE
    }

    public enum UserPreferenceTypeEnum {
        ACTIVE_ACCOUNT,
        RESET_PASSWORD,
        VERIFY_LOGIN,
    }

    public enum UserRolesEnum {
        ROLE_ADMIN,
        ROLE_USER,
    }

    public enum TicketTypeEnum {
        FEED_BACK,
        QUESTION
    }

    public enum EventStatusEnum {
        UPCOMING,
        HAPPENING,
        HAPPENED
    }

    public enum MessageType {
        RECEIVE,
        SEND,
    }

    public enum DEFAULT_MESSAGE {
        EVENT_TIME("Event time"),
        EVENT_LOCATION("The event takes place"),
        EVENT_INFORMATION("Event"),
        EVENT_LIST_HAPPENING("List of happening events");
        private final String description;

        DEFAULT_MESSAGE(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}

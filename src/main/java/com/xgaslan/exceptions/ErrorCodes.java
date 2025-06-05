package com.xgaslan.exceptions;

public final class ErrorCodes {

    public static final class System {
        public static final String SYSTEM_ERROR = "System.Error";
        public static final String INTERNAL_SERVER_ERROR = "System.Internal.Server.Error";
        public static final String DATABASE_ERROR = "System.Database.Error";
        public static final String CONFIGURATION_ERROR = "System.Configuration.Error";
        public static final String UNAUTHORIZED_ERROR = "System.Unauthorized.Error";
        public static final String FORBIDDEN_ERROR = "System.Forbidden.Error";
        public static final String NOT_IMPLEMENTED_ERROR = "System.Not.Implemented.Error";
        public static final String SESSION_MODEL_ERROR = "System.Session.Model.Error";
        public static final String SESSION_NOT_FOUND_ERROR = "System.Session.Not.Found.Error";
        public static final String SESSION_EXPIRED_ERROR = "System.Session.Expired.Error";
        public static final String SESSION_INVALID_ERROR = "System.Session.Invalid.Error";
        public static final String SESSION_NOT_ACTIVE_ERROR = "System.Session.Not.Active.Error";

        private System() {}
    }

    public static final class Business {

        public static final class Common {
            public static final String NOT_FOUND_ERROR = "Business.Not.Found.Error";
            public static final String NULL_ERROR = "Business.Null.Error";
            public static final String FORBIDDEN_ERROR = "Business.Forbidden.Error";
            public static final String ALREADY_EXISTS_ERROR = "Business.Already.Exists.Error";
            public static final String UNAUTHORIZED_ERROR = "Business.Unauthorized.Error";
            public static final String INTERNAL_SERVER_ERROR = "Business.Internal.Server.Error";
            public static final String VALIDATION_ERROR = "Business.Validation.Error";
            public static final String INVALID_REQUEST_ERROR = "Business.Invalid.Request.Error";
            public static final String INVALID_TOKEN_ERROR = "Business.Invalid.Token.Error";

            private Common() {}
        }

        public static final class User {
            public static final String INVALID_EMAIL_OR_PASSWORD = "Business.User.Invalid.Email.Or.Password";
            public static final String USER_NOT_FOUND = "Business.User.Not.Found.Error";
            public static final String USER_ALREADY_EXISTS = "Business.User.Already.Exists.Error";
            public static final String USER_FORBIDDEN = "Business.User.Forbidden.Error";

            private User() {}
        }

        private Business() {}
    }

    private ErrorCodes() {}
}

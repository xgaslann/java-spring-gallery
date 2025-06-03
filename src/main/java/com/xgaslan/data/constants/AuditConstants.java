package com.xgaslan.data.constants;

import java.util.UUID;

public final class AuditConstants {
    private AuditConstants() {
        // Prevent instantiation
    }

    public static final class Audit{
        private Audit() {}

        public static final UUID CREATED_BY = UUID.fromString("00000000-0000-0000-0000-000000000000");
        public static final UUID CHANGED_BY = UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
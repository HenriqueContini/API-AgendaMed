package com.henrique.APIAgendaMed.models.enums;

public enum Status {
    BOOKED(1),
    CANCELLED(2),
    COMPLETED(3);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Status valueOf(int code) {
        for (Status value : Status.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid status code");
    }
}

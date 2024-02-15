package com.henrique.APIAgendaMed.exceptions.handler;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String path) {
}

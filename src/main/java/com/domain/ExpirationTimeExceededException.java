package com.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpirationTimeExceededException extends RuntimeException {

    public final static String ERROR_MESSAGE = "Expiration note time was exceeded";
}

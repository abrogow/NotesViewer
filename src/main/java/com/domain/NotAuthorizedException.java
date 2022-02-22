package com.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotAuthorizedException extends RuntimeException {

    public final static String ERROR_MESSAGE = "No rights to access this resource";
}

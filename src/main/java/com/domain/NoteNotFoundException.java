package com.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoteNotFoundException extends RuntimeException {

    public final static String ERROR_MESSAGE = "Note was not found";
}

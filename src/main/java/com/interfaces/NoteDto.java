package com.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoteDto {

    private final Long id;
    private final String content;
}

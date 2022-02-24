package com.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
public class DefaultNoteExpirationTimeProvider {

    @Value("${app.note.default-expiration-time-in-mins}")
    private Integer defaultExpirationTime;
}

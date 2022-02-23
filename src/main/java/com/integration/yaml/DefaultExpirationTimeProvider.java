package com.integration.yaml;

import com.domain.DefaultNoteExpirationTimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultExpirationTimeProvider implements DefaultNoteExpirationTimeProvider {

    @Value("${app.note.default-expiration-time-in-mins}")
    private Integer defaultExpirationTime;

    public Integer getDefaultExpirationTime() {
        return this.defaultExpirationTime;
    }
}

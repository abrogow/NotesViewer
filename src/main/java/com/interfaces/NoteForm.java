package com.interfaces;

import com.domain.NoteProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class NoteForm implements NoteProvider {

    @NotNull
    @NotBlank
    private final String content;

    @Nullable
    @Min(value = 1, message = "The value must be positive and min 1")
    private Integer lifeLengthInMin;
}

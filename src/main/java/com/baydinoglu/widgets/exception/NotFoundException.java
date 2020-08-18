package com.baydinoglu.widgets.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

    private final String id;

    @Override
    public String getMessage() {
        return String.format("Widget with id=%s not found", id);
    }
}

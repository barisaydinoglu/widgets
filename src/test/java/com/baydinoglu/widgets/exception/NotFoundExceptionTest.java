package com.baydinoglu.widgets.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundExceptionTest {

    @Test
    void testInitialization() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        //WHEN
        final var expectedException = new NotFoundException(id);
        //THEN
        assertEquals(String.format("Widget with id=%s not found", id), expectedException.getMessage());
    }
}

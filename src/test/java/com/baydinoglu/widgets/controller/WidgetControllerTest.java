package com.baydinoglu.widgets.controller;

import com.baydinoglu.widgets.model.Widget;
import com.baydinoglu.widgets.service.WidgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class WidgetControllerTest {

    @MockBean
    private WidgetService widgetService;

    @MockBean
    private Widget widget;

    private WidgetController widgetController;

    @BeforeEach
    void init() {
        widgetController = new WidgetController(widgetService);
    }

    @Test
    void testCreate() {
        //GIVEN
        Mockito.when(widgetService.create(widget)).thenReturn(widget);
        //WHEN
        final var result = widgetController.create(widget);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.CREATED, result.getStatusCode()),
                () -> assertEquals(widget, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).create(widget)
        );
    }

    @Test
    void testGet() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(widgetService.getById(id)).thenReturn(widget);
        //WHEN
        final var result = widgetController.get(id);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(widget, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).getById(id)
        );
    }

    @Test
    void testDelete() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        //WHEN
        final var result = widgetController.delete(id);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode()),
                () -> assertNull(result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).deleteById(id)
        );
    }

    @Test
    void testUpdate() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(widgetService.update(id, widget)).thenReturn(widget);
        //WHEN
        final var result = widgetController.update(id, widget);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(widget, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).update(id, widget)
        );
    }
}

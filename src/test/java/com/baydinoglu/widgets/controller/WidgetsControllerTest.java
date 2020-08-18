package com.baydinoglu.widgets.controller;

import com.baydinoglu.widgets.model.Widget;
import com.baydinoglu.widgets.service.WidgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class WidgetsControllerTest {

    @MockBean
    private WidgetService widgetService;

    @MockBean
    private List<Widget> widgetList;

    @MockBean
    private Page<Widget> widgetPage;

    private WidgetsController widgetsController;

    @BeforeEach
    void init() {
        widgetsController = new WidgetsController(widgetService);
    }

    @Test
    void testGetAll() {
        //GIVEN
        Mockito.when(widgetService.getAll()).thenReturn(widgetList);
        //WHEN
        final var result = widgetsController.getAll();
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(widgetList, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).getAll()
        );
    }

    @Test
    void testGetAllFiltered() {
        //GIVEN
        final var minX = 1;
        final var minY = 2;
        final var maxX = 3;
        final var maxY = 4;
        Mockito.when(widgetService.getAllFiltered(minX, minY, maxX, maxY)).thenReturn(widgetList);
        //WHEN
        final var result = widgetsController.getAllFiltered(minX, minY, maxX, maxY);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(widgetList, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).getAllFiltered(minX, minY, maxX, maxY)
        );
    }

    @Test
    void testGetWidgetsPage() {
        //GIVEN
        final var page = 1;
        final var size = 1;
        Mockito.when(widgetService.getPage(PageRequest.of(page, size))).thenReturn(widgetPage);
        //WHEN
        final var result = widgetsController.getWidgetsPage(page, size);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertNotNull(result.getBody()),
                () -> assertEquals(widgetPage, result.getBody()),
                () -> Mockito.verify(widgetService, Mockito.times(1)).getPage(PageRequest.of(page, size))
        );

    }
}

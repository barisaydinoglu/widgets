package com.baydinoglu.widgets.repository;

import com.baydinoglu.widgets.model.Widget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryRepositoryTest {

    private InMemoryRepository repository;

    private Widget widget;
    private Widget widget2;

    @BeforeEach
    void initTest() {
        repository = new InMemoryRepository();
        widget = Widget.builder().id(UUID.randomUUID().toString()).build();
        widget2 = Widget.builder().id(UUID.randomUUID().toString()).build();
        this.repository.saveAll(Arrays.asList(widget, widget2));
    }

    @Test
    void testSave() {
        //GIVEN
        final var expectedWidget = Widget.builder().id(UUID.randomUUID().toString()).build();
        //WHEN
        final var actualWidget = this.repository.save(expectedWidget);
        //THEN
        assertAll(
                () -> assertNotNull(actualWidget),
                () -> assertEquals(expectedWidget.getId(), actualWidget.getId())
        );
    }

    @Test
    void testSaveAll() {
        //GIVEN
        final var expectedWidget1 = Widget.builder().id(UUID.randomUUID().toString()).build();
        final var expectedWidget2 = Widget.builder().id(UUID.randomUUID().toString()).build();
        //WHEN
        final var actualWidgets = this.repository.saveAll(Arrays.asList(expectedWidget1, expectedWidget2));
        //THEN
        assertAll(
                () -> assertNotNull(actualWidgets),
                () -> assertEquals(2, actualWidgets.size()),
                () -> assertEquals(expectedWidget1.getId(), actualWidgets.get(0).getId()),
                () -> assertEquals(expectedWidget2.getId(), actualWidgets.get(1).getId())
        );
    }

    @Test
    void testFindById() {
        //WHEN
        final var actualWidget1 = this.repository.findById(widget.getId());
        final var actualWidget2 = this.repository.findById("nonExistId");
        //THEN
        assertAll(
                () -> assertNotNull(actualWidget1),
                () -> assertTrue(actualWidget1.isPresent()),
                () -> assertNotNull(actualWidget2),
                () -> assertFalse(actualWidget2.isPresent())
        );
    }

    @Test
    void testExistsById() {
        //WHEN
        final var result1 = this.repository.existsById(widget.getId());
        final var result2 = this.repository.existsById("nonExistId");
        //THEN
        assertAll(
                () -> assertTrue(result1),
                () -> assertFalse(result2)
        );
    }

    @Test
    void testFindAll() {
        //WHEN
        final var actualWidgets = this.repository.findAll();
        //THEN
        assertAll(
                () -> assertNotNull(actualWidgets),
                () -> assertEquals(2, actualWidgets.size()),
                () -> assertTrue(actualWidgets.stream().anyMatch(item -> widget.getId().equals(item.getId()))),
                () -> assertTrue(actualWidgets.stream().anyMatch(item -> widget2.getId().equals(item.getId())))
        );
    }

    @Test
    void testFindAllById() {
        //WHEN
        final var actualWidgets = this.repository.findAllById(Arrays.asList(widget.getId(), widget2.getId()));
        //THEN
        assertAll(
                () -> assertNotNull(actualWidgets),
                () -> assertEquals(2, actualWidgets.size()),
                () -> assertEquals(widget.getId(), actualWidgets.get(0).getId()),
                () -> assertEquals(widget2.getId(), actualWidgets.get(1).getId())
        );
    }

    @Test
    void testCount() {
        //WHEN
        final var result = this.repository.count();
        //THEN
        assertEquals(2, result);
    }

    @Test
    void testDeleteById() {
        assertTrue(this.repository.existsById(widget.getId()));
        //WHEN
        this.repository.deleteById(widget.getId());
        //THEN
        assertFalse(this.repository.existsById(widget.getId()));
    }

    @Test
    void testDelete() {
        assertTrue(this.repository.existsById(widget.getId()));
        //WHEN
        this.repository.delete(widget);
        //THEN
        assertFalse(this.repository.existsById(widget.getId()));
    }

    @Test
    void testDeleteAllByIds() {
        assertAll(
                () -> assertTrue(this.repository.existsById(widget.getId())),
                () -> assertTrue(this.repository.existsById(widget.getId())),
                () -> assertEquals(2, this.repository.count())
        );
        //WHEN
        this.repository.deleteAll(Arrays.asList(widget, widget2));
        //THEN
        assertAll(
                () -> assertFalse(this.repository.existsById(widget.getId())),
                () -> assertFalse(this.repository.existsById(widget.getId())),
                () -> assertEquals(0, this.repository.count())
        );
    }

    @Test
    void testDeleteAll() {
        assertEquals(2, this.repository.count());
        //WHEN
        this.repository.deleteAll();
        //THEN
        assertEquals(0, this.repository.count());
    }
}

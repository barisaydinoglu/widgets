package com.baydinoglu.widgets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WidgetTest {

    private Widget widget;

    private Validator validator;

    Random rand = new Random();

    @BeforeEach
    void initTest() {
        final var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        widget = Widget.builder()
                .id(UUID.randomUUID().toString())
                .height(Math.abs(rand.nextInt() + 1))
                .width(Math.abs(rand.nextInt() + 1))
                .x(Math.abs(rand.nextInt()))
                .y(Math.abs(rand.nextInt()))
                .z(Math.abs(rand.nextInt())).build();
    }

    @Test
    void testInitialization() {
        //GIVEN
        final var expectedId = UUID.randomUUID().toString();
        //WHEN
        final var actualWidget = new Widget();
        actualWidget.setId(expectedId);
        actualWidget.setWidth(Math.abs(rand.nextInt() + 1));
        actualWidget.setHeight(Math.abs(rand.nextInt() + 1));
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(expectedId, actualWidget.getId()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0, 200})
    void testXIndex(final int x) {
        //WHEN
        widget.setX(x);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(x, widget.getX()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0, 200})
    void testYIndex(final int y) {
        //WHEN
        widget.setY(y);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(y, widget.getY()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0, 200})
    void testZIndex(final int z) {
        //WHEN
        widget.setZ(z);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(z, widget.getZ()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 12, 200})
    void testHeightShouldBePositive(final int height) {
        //WHEN
        widget.setHeight(height);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(height, widget.getHeight()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -100})
    void testHeightNotBeZeroOrNegative(final int height) {
        //WHEN
        widget.setHeight(height);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(height, widget.getHeight()),
                () -> assertEquals(1, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 12, 200})
    void testWidthShouldBePositive(final int width) {
        //WHEN
        widget.setWidth(width);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(width, widget.getWidth()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -100})
    void testWidthShouldNotBeZeroOrNegative(final int width) {
        //WHEN
        widget.setWidth(width);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(width, widget.getWidth()),
                () -> assertEquals(1, constraintViolations.size())
        );
    }

    @Test
    void testLastModifiedDateShouldBePastOrPresent() {
        //GIVEN
        final LocalDateTime lastModifiedDate = LocalDateTime.now();
        //WHEN
        widget.setLastModifiedDate(lastModifiedDate);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(lastModifiedDate, widget.getLastModifiedDate()),
                () -> assertEquals(0, constraintViolations.size())
        );
    }

    @Test
    void testLastModifiedDateShouldNotBeFuture() {
        //GIVEN
        final LocalDateTime lastModifiedDate = LocalDateTime.now().plusDays(1);
        //WHEN
        widget.setLastModifiedDate(lastModifiedDate);
        //THEN
        final var constraintViolations = validator.validate(widget);
        assertAll(
                () -> assertNotNull(widget),
                () -> assertEquals(lastModifiedDate, widget.getLastModifiedDate()),
                () -> assertEquals(1, constraintViolations.size())
        );
    }
}

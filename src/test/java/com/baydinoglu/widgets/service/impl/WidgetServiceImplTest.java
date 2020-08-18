package com.baydinoglu.widgets.service.impl;

import com.baydinoglu.widgets.exception.NotFoundException;
import com.baydinoglu.widgets.model.Widget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class WidgetServiceImplTest {

    private WidgetServiceImpl service;

    @MockBean
    private CrudRepository<Widget, String> repository;

    @MockBean
    private Widget widget;

    @BeforeEach
    void initTest() {
        service = new WidgetServiceImpl(repository);
    }

    @Test
    void testCreate() {
        //GIVEN
        Mockito.when(widget.getZ()).thenReturn(null);
        //WHEN
        service.create(widget);
        //THEN
        assertAll(
                () -> Mockito.verify(widget, Mockito.times(1)).setLastModifiedDate(ArgumentMatchers.any(LocalDateTime.class)),
                () -> Mockito.verify(widget, Mockito.times(1)).setId(ArgumentMatchers.any(String.class)),
                () -> Mockito.verify(widget, Mockito.times(1)).setZ(ArgumentMatchers.any(Integer.class)),
                () -> Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Widget.class))
        );
    }

    @Test
    void testGetById() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(widget));
        //WHEN
        final var result = service.getById(id);
        //THEN
        assertAll(
                () -> Mockito.verify(repository, Mockito.times(1)).findById(id),
                () -> assertEquals(result, widget)
        );
    }

    @Test
    void testGetByIdNotFound() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        //WHEN
        //THEN
        assertThrows(NotFoundException.class, () -> service.getById(id));
    }

    @Test
    void testUpdate() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        final var givenWidget = Widget.builder()
                .id(id).width(10).height(11).x(12).y(13).z(14)
                .build();
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(widget));
        //WHEN
        service.update(id, givenWidget);
        //THEN
        assertAll(
                () -> Mockito.verify(widget, Mockito.times(1)).setWidth(givenWidget.getWidth()),
                () -> Mockito.verify(widget, Mockito.times(1)).setHeight(givenWidget.getHeight()),
                () -> Mockito.verify(widget, Mockito.times(1)).setX(givenWidget.getX()),
                () -> Mockito.verify(widget, Mockito.times(1)).setY(givenWidget.getY()),
                () -> Mockito.verify(widget, Mockito.times(1)).setZ(givenWidget.getZ()),
                () -> Mockito.verify(widget, Mockito.times(1)).setLastModifiedDate(ArgumentMatchers.any(LocalDateTime.class)),
                () -> Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Widget.class))
        );
    }

    @Test
    void testDeleteById() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenReturn(true);
        //WHEN
        service.deleteById(id);
        //THEN
        assertAll(
                () -> Mockito.verify(repository, Mockito.times(1)).existsById(id),
                () -> Mockito.verify(repository, Mockito.times(1)).deleteById(id)
        );
    }

    @Test
    void testDeleteByIdNotFound() {
        //GIVEN
        final var id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenReturn(false);
        //WHEN
        assertThrows(NotFoundException.class, () -> service.deleteById(id));
        //THEN
        assertAll(
                () -> Mockito.verify(repository, Mockito.times(1)).existsById(id),
                () -> Mockito.verify(repository, Mockito.times(0)).deleteById(id)
        );
    }

    @Test
    void testGetAll() {
        //WHEN
        service.getAll();
        //THEN
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllFiltered() {
        //GIVEN
        final var minX = 0;
        final var minY = 0;
        final var maxX = 100;
        final var maxY = 150;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(
                Widget.builder().x(0).y(0).height(100).width(100).build(),
                Widget.builder().x(0).y(50).height(100).width(100).build(),
                Widget.builder().x(50).y(50).height(100).width(100).build()
        ));
        //WHEN
        final var result = service.getAllFiltered(minX, minY, maxX, maxY);
        //THEN
        assertAll(
                () -> Mockito.verify(repository, Mockito.times(1)).findAll(),
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size())
        );
    }

    @Test
    void testGetPage() {
        //GIVEN
        final var pageSize = 2;
        final var pageable = PageRequest.of(0, pageSize);
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(
                Widget.builder().x(0).y(0).z(0).height(100).width(100).build(),
                Widget.builder().x(0).y(50).z(1).height(100).width(100).build(),
                Widget.builder().x(50).y(50).z(2).height(100).width(100).build()
        ));
        //WHEN
        final var result = service.getPage(pageable);
        //THEN
        assertAll(
                () -> Mockito.verify(repository, Mockito.times(1)).findAll(),
                () -> assertNotNull(result),
                () -> assertEquals(3, result.getTotalElements()),
                () -> assertEquals(2, result.getContent().size())
        );
    }
}

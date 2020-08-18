package com.baydinoglu.widgets.service.impl;

import com.baydinoglu.widgets.exception.NotFoundException;
import com.baydinoglu.widgets.model.Widget;
import com.baydinoglu.widgets.service.WidgetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WidgetServiceImpl implements WidgetService {

    private static final Integer DEFAULT_Z_INDEX = 0;
    private static final Integer MAX_PAGE_SIZE = 500;

    private final CrudRepository<Widget, String> repository;

    @Override
    public synchronized Widget create(final Widget widget) {
        widget.setLastModifiedDate(LocalDateTime.now());
        widget.setId(UUID.randomUUID().toString());
        correctZIndexes(widget);
        return repository.save(widget);
    }

    @Override
    public Widget getById(final String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public synchronized Widget update(final String id, final Widget widget) {
        final Widget existingWidget = getById(id);
        existingWidget.setX(widget.getX());
        existingWidget.setY(widget.getY());
        existingWidget.setHeight(widget.getHeight());
        existingWidget.setWidth(widget.getWidth());
        existingWidget.setZ(widget.getZ());
        existingWidget.setLastModifiedDate(LocalDateTime.now());
        correctZIndexes(widget);
        return repository.save(existingWidget);
    }

    @Override
    public void deleteById(final String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<Widget> getAll() {
        return (List<Widget>) repository.findAll();
    }

    @Override
    public List<Widget> getAllFiltered(final Integer minX, final Integer minY, final Integer maxX, final Integer maxY) {
        return getAll().stream()
                .filter(widget -> widget.getX() >= minX && widget.getY() >= minY && widget.getX() + widget.getWidth() <= maxX && widget.getY() + widget.getHeight() <= maxY)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Widget> getPage(final Pageable pageable) {
        final List<Widget> resultList = getAll();
        return new PageImpl<>(resultList.stream()
                .sorted(Comparator.comparingInt(Widget::getZ))
                .skip(pageable.getOffset())
                .limit(Math.min(pageable.getPageSize(), MAX_PAGE_SIZE))
                .collect(Collectors.toList()), pageable, resultList.size());
    }

    private void correctZIndexes(final Widget referenceWidget) {
        final List<Widget> widgets = getAll();
        if (referenceWidget.getZ() == null) {
            if (widgets.isEmpty()) {
                referenceWidget.setZ(DEFAULT_Z_INDEX);
            } else {
                final int maxZ = widgets.stream()
                        .max(Comparator.comparingInt(Widget::getZ))
                        .orElseThrow(() -> new NotFoundException(null))
                        .getZ();
                referenceWidget.setZ(maxZ + 1);
            }
        } else {
            widgets.stream()
                    .filter(widget -> widget.getZ().compareTo(referenceWidget.getZ()) >= 0)
                    .forEach(widget -> widget.setZ(widget.getZ() + 1));
        }
    }
}

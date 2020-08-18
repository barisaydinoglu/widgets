package com.baydinoglu.widgets.service;

import com.baydinoglu.widgets.model.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WidgetService {

    Widget create(Widget widget);

    Widget getById(String id);

    List<Widget> getAll();

    List<Widget> getAllFiltered(final Integer minX, final Integer minY, final Integer maxX, final Integer maxY);

    Page<Widget> getPage(final Pageable pageable);

    Widget update(String id, Widget widget);

    void deleteById(String id);

}

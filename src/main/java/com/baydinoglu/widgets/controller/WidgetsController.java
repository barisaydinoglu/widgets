package com.baydinoglu.widgets.controller;

import com.baydinoglu.widgets.model.Widget;
import com.baydinoglu.widgets.service.WidgetService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "widgets")
@AllArgsConstructor
@Api("widgets")
public class WidgetsController {

    private final WidgetService widgetService;

    @GetMapping
    public ResponseEntity<List<Widget>> getAll() {
        return new ResponseEntity<>(widgetService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Widget>> getAllFiltered(@RequestParam final Integer minX,
                                                       @RequestParam final Integer minY,
                                                       @RequestParam final Integer maxX,
                                                       @RequestParam final Integer maxY) {
        return new ResponseEntity<>(widgetService.getAllFiltered(minX, minY, maxX, maxY), HttpStatus.OK);
    }

    @GetMapping(path = "/page")
    public ResponseEntity<Page<Widget>> getWidgetsPage(@RequestParam(defaultValue = "0") final int page,
                                                       @RequestParam(defaultValue = "10") final int size) {
        return new ResponseEntity<>(widgetService.getPage(PageRequest.of(page, size)), HttpStatus.OK);
    }
}

package com.baydinoglu.widgets.controller;

import com.baydinoglu.widgets.model.Widget;
import com.baydinoglu.widgets.service.WidgetService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping(path = "widget")
@AllArgsConstructor
@Api("widget")
public class WidgetController {

    private final WidgetService widgetService;

    @PostMapping
    public ResponseEntity<Widget> create(@Valid @RequestBody final Widget widget) {
        return ResponseEntity.status(HttpStatus.CREATED).body(widgetService.create(widget));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Widget> get(@NotNull @PathVariable(value = "id") final String id) {
        return ResponseEntity.status(HttpStatus.OK).body(widgetService.getById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Widget> delete(@PathVariable("id") final String id) {
        widgetService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Widget> update(@PathVariable("id") final String id,
                                         @Valid @RequestBody final Widget widget) {
        return ResponseEntity.status(HttpStatus.OK).body(widgetService.update(id, widget));
    }
}

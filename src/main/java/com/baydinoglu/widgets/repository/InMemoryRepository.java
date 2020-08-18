package com.baydinoglu.widgets.repository;

import com.baydinoglu.widgets.model.Widget;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Profile("in_memory")
public class InMemoryRepository implements CrudRepository<Widget, String> {

    private final Map<String, Widget> repository = new HashMap<>();

    @Override
    public <S extends Widget> S save(final S entity) {
        repository.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Widget> List<S> saveAll(final Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Widget> findById(final String id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public boolean existsById(final String id) {
        return repository.containsKey(id);
    }

    @Override
    public List<Widget> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Widget> findAllById(final Iterable<String> ids) {
        final List<Widget> results = new ArrayList<>();
        for (final String id : ids) {
            findById(id).ifPresent(results::add);
        }
        return results;
    }

    @Override
    public long count() {
        return repository.values().size();
    }

    @Override
    public void deleteById(final String id) {
        repository.remove(id);
    }

    @Override
    public void delete(final Widget widget) {
        deleteById(widget.getId());
    }

    @Override
    public void deleteAll(final Iterable<? extends Widget> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        this.repository.clear();
    }
}

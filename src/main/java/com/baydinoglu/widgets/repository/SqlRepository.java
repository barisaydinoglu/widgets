package com.baydinoglu.widgets.repository;

import com.baydinoglu.widgets.model.Widget;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Profile("h2")
public interface SqlRepository extends JpaRepository<Widget, String> {

}

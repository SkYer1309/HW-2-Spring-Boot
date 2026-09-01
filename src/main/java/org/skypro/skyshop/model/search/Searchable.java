package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Searchable {
    UUID getId();

    String getName();

    @JsonIgnore
        // Скрываем из JSON
    String getSearchTerm();

    @JsonIgnore
        // Скрываем из JSON
    String getContentType();

}
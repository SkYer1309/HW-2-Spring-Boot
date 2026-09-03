
package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.search.SearchResult;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// Используется аннотация @ExtendWith
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SearchServiceTest {

    // использованы @Mock для StorageService и @InjectMocks для SearchService
    @Mock
    private StorageService storageService;

    @InjectMocks
    private org.skypro.skyshop.service.SearchService searchService;

    // Поиск в случае отсутствия объектов в StorageService
    @Test
    void search_whenStorageIsEmpty_returnsEmptyCollection() {
        // Arrange
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        // Act
        var results = searchService.search("any");

        // Assert
        // используется assertThat(results).isEmpty()
        assertThat(results).isEmpty();
    }

    // Поиск, если объекты есть, но нет подходящего
    @Test
    void search_whenNoMatch_returnsEmptyCollection() {
        // Arrange
        Searchable mockItem = createMockSearchable("Apple", "Apple", "PRODUCT");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockItem));

        // Act
        var results = searchService.search("Banana"); // pattern не совпадает

        // Assert
        // используется assertThat(results).isEmpty() при несовпадающем pattern
        assertThat(results).isEmpty();
    }

    // Поиск, когда есть подходящий объект
    @Test
    void search_whenMatchFound_returnsListOfSizeOne() {
        // Arrange
        // мок возвращает объект с именем "TestProduct"
        Searchable mockItem = createMockSearchable("TestProduct", "TestProductTerm", "PRODUCT");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockItem));

        // Act
        // поиск с pattern = "Test" возвращает список размером 1
        var results = searchService.search("Test");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getName()).isEqualTo("TestProduct");
    }

    // Вспомогательный метод для создания мока Searchable
    private Searchable createMockSearchable(String name, String searchTerm, String contentType) {
        Searchable mock = org.mockito.Mockito.mock(Searchable.class);
        when(mock.getName()).thenReturn(name);
        when(mock.getSearchTerm()).thenReturn(searchTerm);
        when(mock.getContentType()).thenReturn(contentType);
        when(mock.getId()).thenReturn(UUID.randomUUID());
        return mock;
    }
}
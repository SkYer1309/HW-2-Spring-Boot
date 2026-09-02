package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component          // аннотация @Component
@SessionScope       // аннотация @SessionScope (уникальна для каждой сессии)
public class ProductBasket {

    // private final, инициализировано HashMap
    private final Map<UUID, Integer> products = new HashMap<>();

    // добавление с увеличением количества, использован computeIfAbsent
    public void addProduct(UUID id) {
        // computeIfAbsent вернет 0, если ключа нет, затем мы прибавляем 1 и сохраняем
        products.put(id, products.computeIfAbsent(id, k -> 0) + 1);
    }

    // возвращает Collections.unmodifiableMap()
    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // аннотация @Service
public class BasketService {

    // private final поля, внедрение через конструктор
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    // Метод добавления товара в корзину
    public void addProductToBasket(UUID id) {
        // использован Optional с orElseThrow для проверки и выброса исключения
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар с таким ID не найден"));

        productBasket.addProduct(id);
    }

    // Метод отображения корзины пользователю
    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketMap = productBasket.getProducts();

        // использован Stream API для преобразования мапы в список BasketItem
        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductById(entry.getKey())
                            .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(items); // total посчитается внутри конструктора UserBasket
    }
}
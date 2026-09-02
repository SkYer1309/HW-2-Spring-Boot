package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;
import org.skypro.skyshop.exception.NoSuchProductException;

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
        // выбрасывается NoSuchProductException в случае отсутствия товара
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Товар с ID " + id + " не найден"));

        productBasket.addProduct(id);
    }

    // Метод отображения корзины пользователю
    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketMap = productBasket.getProducts();

        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductById(entry.getKey())
                            // Здесь тоже заменим на наше исключение для надежности
                            .orElseThrow(() -> new NoSuchProductException("Товар с ID " + entry.getKey() + " не найден"));
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}
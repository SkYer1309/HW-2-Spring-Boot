package org.skypro.skyshop.model.basket;

import java.util.List;

// немодифицируемый класс, private final, нет сеттеров
public class UserBasket {
    private final List<BasketItem> items;
    private final int total;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        // total вычисляется в конструкторе через Stream API
        this.total = items.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}

package org.skypro.skyshop.model.product;
import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basePrice;
    private final int discount;

    public DiscountedProduct(UUID id, String name, int basePrice, int discount) {
        super(id, name);
        if (basePrice <= 0) throw new IllegalArgumentException("Базовая цена должна быть > 0");
        if (discount < 0 || discount > 100) throw new IllegalArgumentException("Скидка от 0 до 100");
        this.basePrice = basePrice;
        this.discount = discount;
    }

    @Override public int getPrice() { return basePrice - (basePrice * discount / 100); }
    @Override public boolean isSpecial() { return true; }
    @Override public String toString() { return getName() + ": " + getPrice() + " (" + discount + "%)"; }
}
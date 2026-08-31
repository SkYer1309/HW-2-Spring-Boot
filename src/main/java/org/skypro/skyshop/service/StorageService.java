package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initTestData(); // Вызов приватного метода
    }

    private void initTestData() {
        Product apple = new SimpleProduct(UUID.randomUUID(), "Яблоки", 110);
        Product milk = new DiscountedProduct(UUID.randomUUID(), "Молоко", 300, 10);
        Product bread = new FixPriceProduct(UUID.randomUUID(), "Хлеб");

        products.put(apple.getId(), apple);
        products.put(milk.getId(), milk);
        products.put(bread.getId(), bread);

        Article a1 = new Article(UUID.randomUUID(), "Польза яблок", "Яблоки богаты витаминами");
        Article a2 = new Article(UUID.randomUUID(), "Молочные продукты", "Молоко содержит кальций");

        articles.put(a1.getId(), a1);
        articles.put(a2.getId(), a2);
    }

    public Collection<Product> getAllProducts() { return products.values(); }
    public Collection<Article> getAllArticles() { return articles.values(); }

    // Метод для объединения, нужен для поиска
    public Collection<Searchable> getAllSearchables() {
        List<Searchable> all = new ArrayList<>();
        all.addAll(products.values());
        all.addAll(articles.values());
        return all;
    }
}
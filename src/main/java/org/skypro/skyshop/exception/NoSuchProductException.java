package org.skypro.skyshop.exception;

// унаследовано от RuntimeException
public class NoSuchProductException extends RuntimeException {

    // конструктор с параметром, вызывающий super со значимым текстом
    public NoSuchProductException(String message) {
        super(message);
    }
}
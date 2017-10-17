package com.file.encryption;

/**
 * Created by Osama on 3/10/2016.
 */

public class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
package com.madkaos.core.errors;

public class BadArgumentException extends Exception {
    public BadArgumentException(String arg, String waiting) {
        super("Donde has introducido \"" + arg + "\" se esperaba " + waiting + ".");
    }
}

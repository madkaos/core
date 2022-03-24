package com.madkaos.core.errors;

public class PlayerOfflineException extends Exception {
    public PlayerOfflineException(String username) {
        super("El jugador " + username + " no se encuentra conectado.");
    }
}

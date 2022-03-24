package com.madkaos.core.errors;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(String username) {
        super("El jugador " + username + " no ha sido encontrado.");
    }
}

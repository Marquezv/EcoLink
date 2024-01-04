package com.ecolink.dev.server.exception;

import com.ecolink.dev.server.exception.handler.ExceptionObserver;

public class ExException implements ExceptionObserver {

    @Override
    public void handleException(Exception e) {
        // Lógica específica para lidar com a exceção no observador
        System.out.println("Observador tratou a exceção: " + e.getMessage());
    }
}

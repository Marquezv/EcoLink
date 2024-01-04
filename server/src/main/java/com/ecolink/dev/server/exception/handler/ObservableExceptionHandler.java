package com.ecolink.dev.server.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ObservableExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ObservableExceptionHandler.class);
    private List<ExceptionObserver> observers = new ArrayList<>();

    public void addObserver(ExceptionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ExceptionObserver observer) {
        observers.remove(observer);
    }

    public void handleException(Exception e) {
        try {
            // Decorator Pattern: Adiciona lógica adicional ao tratamento padrão de exceções
            logAdditionalInfo();

            // Observer Pattern: Notifica todos os observadores
            notifyObservers(e);

        } catch (Exception logException) {
            logException.printStackTrace();
        }
    }

    private void logAdditionalInfo() {
        // Lógica adicional decorativa aqui
        logger.info("Informações adicionais de log");
    }

    private void notifyObservers(Exception e) {
        for (ExceptionObserver observer : observers) {
            observer.handleException(e);
        }
    }
}

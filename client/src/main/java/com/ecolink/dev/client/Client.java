package com.ecolink.dev.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.console.Console;

import picocli.CommandLine;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Console console;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new CommandLine(new CommandControl(this.socket)).execute("-h");
            this.console = new Console();
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);
            // Console
            while (socket.isConnected()  ) {
                String messageToSend = scanner.nextLine();
                if (messageToSend != null && !socket.isClosed()) {
                    console.setSocket(socket);
                    String[] args = messageToSend.split(" ");
                    if (messageToSend.startsWith("/m")) {
                        console.onMessage();
                        continue;
                    }
                    if (messageToSend.startsWith("/coc")) {
                        console.onCommand();
                        continue;
                    }
                    console.processInput(socket, args);
                }
            }
            scanner.close();
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String msgFromGroupChat;
            while (socket.isConnected() && !socket.isClosed()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();

                    if (msgFromGroupChat != null) {
                        System.out.println(msgFromGroupChat);
                    }

                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    try {
                        // Aguarda um tempo antes de tentar reconectar
                        Thread.sleep(3000); // Aguarda 5 segundos (ajuste conforme necessário)
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // Tenta reconectar
                    reconnect();
                }
            }
        }).start();
    }

    private void reconnect() {
        while (!socket.isConnected() || socket.isClosed()) {
            try {
                System.out.println("Tentando reconectar...");
                socket = new Socket("localhost", 7000);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Adicione qualquer outra inicialização necessária
                System.out.println("Reconexão bem-sucedida!");
            } catch (IOException e) {
                // Falha na reconexão, aguarde antes de tentar novamente
                try {
                    Thread.sleep(5000); // Aguarda 5 segundos antes de tentar novamente (ajuste conforme necessário)
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        reconnect();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to EcoLinkCLI");

        while (true) {
            try {
                Socket socket = new Socket("localhost", 7000);
                Client client = new Client(socket);
                client.listenForMessage();
                client.sendMessage();
            } catch (ConnectException e) {
                System.out.println("Não foi possível conectar ao servidor. Tentando novamente...");
                try {
                    Thread.sleep(5000); // Aguarda 5 segundos antes de tentar novamente (ajuste conforme necessário)
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

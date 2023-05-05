package com.ecolink.dev.client.chat;

import java.net.Socket;

public interface ConsoleModeState {
   void processInput(String input, Socket socket);
}

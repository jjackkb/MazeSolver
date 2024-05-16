package com.beer;
import py4j.GatewayServer;

public class Main {
    public static void main(String[] args) {
            Game game = new Game(50, 50, 15, 15, 0.23);
            GatewayServer server = new GatewayServer(game);
            server.start();
    }
}
package com.beer;
import py4j.GatewayServer;

public class Main {
    public static void main(String[] args) {
            Game game = new Game(25, 25, 15, 15);
            GatewayServer server = new GatewayServer(game);
            server.start();
    }
}
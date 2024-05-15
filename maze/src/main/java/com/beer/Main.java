package com.beer;

import py4j.GatewayServer;

public class Main {
    public static void main(String[] args) {
            Window game = new Window(50, 50, 15, 15, 0.2);
            GatewayServer gs = new GatewayServer(game);
            gs.start();
    }
}
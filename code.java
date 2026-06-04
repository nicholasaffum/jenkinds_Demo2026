package com.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {

            String response = "<h1>Hello from Jenkins + Docker + Maven Pipeline</h1>";

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        });

        server.setExecutor(null);

        server.start();

        System.out.println("Application started on port 8080");
    }
}

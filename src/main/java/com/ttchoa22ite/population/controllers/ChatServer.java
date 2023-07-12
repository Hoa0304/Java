package com.ttchoa22ite.population.controllers;
import javafx.application.Platform;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatServer {
    private static final int BUFFER_SIZE = 1024;
    private static final int PORT = 1234;

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final ByteBuffer buffer;

    private final Map<SelectionKey, String> clientMap = new HashMap<>();

    public ChatServer() throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public void start() throws IOException {
        System.out.println("Server started on port " + PORT);
        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        clientMap.put(clientChannel.keyFor(selector), "");
        System.out.println("New client connected: " + clientChannel.getRemoteAddress());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        buffer.clear();
        int bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            disconnect(key);
            return;
        }

        String request = new String(buffer.array(), 0, bytesRead).trim();
        String response = handleRequest(request, key);
        if (response != null) {
            clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
        }
    }

    private String handleRequest(String request, SelectionKey key) {
        String[] parts = request.split(" ");
        String command = parts[0];
        switch (command) {
            case "JOIN":
                String username = parts[1];
                clientMap.put(key, username);
                System.out.println("Client joined: " + username);
                broadcastMessage("JOIN " + username);
                return "OK";
            case "LEAVE":
                String clientUsername = clientMap.get(key);
                clientMap.remove(key);
                System.out.println("Client left: " + clientUsername);
                broadcastMessage("LEAVE " + clientUsername);
                return "OK";
            case "MESSAGE":
                String message = parts[1];
                String sender = clientMap.get(key);
                System.out.println(sender + ": " + message);
                broadcastMessage("MESSAGE " + sender + " " + message);
                return "OK";
            default:
                return "ERROR";
        }
    }

    private void broadcastMessage(String message) {
        for (SelectionKey selectionKey : selector.keys()) {
            if (selectionKey.isValid() && selectionKey.channel() instanceof SocketChannel) {
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                try {
                    channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void disconnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String username = clientMap.get(key);
        clientMap.remove(key);
        channel.close();
        System.out.println("Client disconnected: " + username);
        broadcastMessage("LEAVE " + username);
        key.cancel();
    }

    public void shutdown() throws IOException {
        selector.close();
        serverSocketChannel.close();
        for (SelectionKey key : selector.keys()) {
            if (key.channel() instanceof SocketChannel) {
                SocketChannel channel = (SocketChannel) key.channel();
                channel.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
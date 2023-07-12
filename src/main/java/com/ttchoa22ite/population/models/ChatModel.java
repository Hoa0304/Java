package com.ttchoa22ite.population.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

public class ChatModel {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    private OnMessageReceivedListener messageReceivedListener;
    private boolean isRunning;

    public ChatModel() {
        isRunning = false;
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        messageReceivedListener = listener;
    }

    public void setServerAddress(String address, int port) {
        try {
            serverAddress = InetAddress.getByName(address);
            serverPort = port;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            socket = new DatagramSocket(1234); // Chọn cổng 1234 cho server
            isRunning = true;
            receiveMessages();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void connectToServer() {
        try {
            socket = new DatagramSocket();
            isRunning = true;
            receiveMessages();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            byte[] sendData = ("SEND " + message).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(String imageUrl) {
        try {
            byte[] sendData = ("IMAGE " + imageUrl).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        isRunning = false;
        socket.close();
    }

    private void receiveMessages() {
        Thread receiveThread = new Thread(() -> {
            while (isRunning) {
                try {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (messageReceivedListener != null) {
                        messageReceivedListener.onMessageReceived(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread.start();
    }

    public interface OnMessageReceivedListener {
        void onMessageReceived(String message);
    }
}
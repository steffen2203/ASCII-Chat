package com.ASCII_Club.ASCII_Chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 10-04-2015.
 */
public class Server implements Runnable
{
    private List<ServerClient> clients = new ArrayList<ServerClient>();
    private List<Integer> clientResponse = new ArrayList<Integer>();

    private DatagramSocket socket;
    private int port;
    private boolean running = false;
    private Thread run, manage, send, receive;
    private final int MAX_ATTEMPTS = 5;

    private boolean raw = false;

    public Server(int port)
    {
        this.port = port;
        try
        {
            socket = new DatagramSocket(port);
        } catch (SocketException e)
        {
            e.printStackTrace();
            return;
        }
        run = new Thread(this, "Server");
        run.start();
    }

    @Override
    public void run()
    {
        running = true;
        System.out.println("Server started on port " + port);
        manageClients();
        receive();
    }

    private void manageClients()
    {
        manage = new Thread("Manage")
        {
            public void run()
            {
                while (running)
                {
                    // TODO Managing
                }
            }
        };
        manage.start();
    }

    private void receive()
    {
        receive = new Thread("Receive")
        {
            public void run()
            {
                while (running)
                {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try
                    {
                        socket.receive(packet);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    process(packet);

                    clients.add(new ServerClient("Steffen", packet.getAddress(), packet.getPort(), 50));
                    System.out.println(clients.get(0).address.toString() + ": " + clients.get(0).port);
                }
            }
        };
        receive.start();
    }

    private void sendToAll(String message)
    {
        for (int i = 0; i < clients.size(); i++)
        {
            ServerClient client = clients.get(i);
            send(message.getBytes(), client.address, client.port);
        }
    }

    private void send(final byte[] data, final InetAddress address, final int port)
    {
       send = new Thread("Send")
       {
            public void run()
            {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                try
                {
                    socket.send(packet);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
       };
        send.start();
    }

    private void send(String massege, InetAddress address, int port)
    {
        massege += "/e/";
        send(massege.getBytes(), address, port);
    }

    private void process(DatagramPacket packet)
    {
        String string = new String(packet.getData());
        if (raw) System.out.println(string);
        if (string.startsWith("/c/"))
        {
            //UUID id = UUID.randomUUID();
            int id = UniqueIdentifier.getIdentifier();
            String name = string.split("/c/|/e/")[1];
            System.out.println(name + "(" + id + ") connected!");
            clients.add(new ServerClient(name, packet.getAddress(), packet.getPort(), id));
            String ID = "/c/" + id;
            send(ID, packet.getAddress(), packet.getPort());
        }
        else if (string.startsWith("/m/"))
        {
            sendToAll(string);
        }
        else if (string.startsWith("/d/"))
        {
            String id = string.split("/d/|/e/")[1];
        }

        else
        {
            System.out.println(string);
        }
    }
}

package com.ASCII_Club.ASCII_Chat.server;

import java.net.InetAddress;

/**
 * Created by Steffen on 10-04-2015.
 */
public class ServerClient
{
    public String name;
    public InetAddress address;
    public int port;
    private final int ID;
    public int attempt = 0;

    public ServerClient(String name, InetAddress address, int port, final int ID)
    {
        this.name = name;
        this.address = address;
        this.port = port;
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }
}

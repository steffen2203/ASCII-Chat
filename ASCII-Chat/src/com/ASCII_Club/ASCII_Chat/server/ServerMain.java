package com.ASCII_Club.ASCII_Chat.server;

import java.net.SocketException;

/**
 * Created by Steffen on 10-04-2015.
 */
public class ServerMain
{
    private int port;
    private Server server;

    public ServerMain(int port) throws SocketException
    {
        this.port = port;
        server = new Server(port);
    }

    public static void main(String[] args) throws SocketException
    {
        int port;
        if (args.length != 1)
        {
            System.out.println("Usage: java -jar ASCIIclubserver.jar [port]");
            return;
        }
        port = Integer.parseInt(args[0]);
        new ServerMain(port);
    }
}

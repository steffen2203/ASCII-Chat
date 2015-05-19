

package com.ASCII_Club.ASCII_Chat;

import java.io.IOException;
import java.net.*;

/**
 * @author Steffen Aller Jacobsen
 * @version 1.0
 */
public class Client
{
    private DatagramSocket socket;

    private String name, address;
    private int port;
    private InetAddress ip;
    private Thread send;
    private int ID = -1;

    public Client(String name, String address, int port)
    {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public int getPort()
    {
        return port;
    }

    public boolean openConnection(String address)
    {
        try
        {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        } catch (SocketException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String receive()
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

        String message = new String(packet.getData());
        return message;
    }

    public void send(final byte[] data)
    {
        send = new Thread("Send")
        {
            public void run()
            {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
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

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }
}

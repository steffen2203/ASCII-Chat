package com.ASCII_Club.ASCII_Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Steffen on 18-05-2015.
 */
public class ClientWindow extends JFrame implements Runnable
{
    private JScrollPane scrollPane1;
    private JTextArea history;
    private JTextField textMessage;
    private JButton buttonSend;
    private Thread run, listen;
    private Client client;

    private boolean running = false;



    public ClientWindow(String name, String address, int port)
    {
        client = new Client(name, address, port);
        boolean connect = client.openConnection(address);
        if (!connect)
        {
            System.err.println("Connection failed!");
            console("Connection failed!");
        }
        initComponents();
        console("Attempting a connection to " + address + ": " + port + ", user: " + name);
        String connection = "/c/" + name;
        client.send(connection.getBytes());
        running = true;
        run = new Thread(this, "Running");
        run.start();
    }

    private void initComponents()
    {

        scrollPane1 = new JScrollPane();
        history = new JTextArea();
        textMessage = new JTextField();
        buttonSend = new JButton();

        //======== this ========
        setTitle("ASCII Chat Client");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {14, 827, 30, 7, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {10, 498, 20, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //======== scrollPane1 ========
        {

            //---- history ----
            history.setEditable(false);
            history.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            scrollPane1.setViewportView(history);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- textMessage ----
        textMessage.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    send(textMessage.getText(), true);
                }
            }
        });
        contentPane.add(textMessage, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- buttonSend ----
        buttonSend.setText("Send");
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send(textMessage.getText(), true);
            }
        });
        contentPane.add(buttonSend, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        setSize(915, 585);
        setLocationRelativeTo(getOwner());

        textMessage.requestFocusInWindow();
    }


    @Override
    public void run()
    {
        listen();
    }

    private void send(String message, boolean text)
    {
        if (message.equals("")) return;
        if (text)
        {
            message = client.getName() + ": " + message;
            message = "/m/" + message + "/e/";
            textMessage.setText("");
        }
        client.send(message.getBytes());
    }

    public void listen()
    {
       listen = new Thread("Listen")
        {
            public void run()
            {
                while (running)
                {
                    String message = client.receive();
                    if (message.startsWith("/c/"))
                    {
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        console("Successfully connected to server! ID: " + client.getID());
                    }
                    else if (message.startsWith("/m/"))
                    {
                        String text = message.substring(3);
                        text = text.split("/e/")[0];
                        console(text);
                    }
                    else if (message.startsWith("/i/"))
                    {
                        String text = "/i/" + client.getID() + "/e/";
                        send(text, false);
                    }
                    else if (message.startsWith("/u/"))
                    {
                        String[] u = message.split("/u/|/n/|/e/");
                    }
                }
            }
        };
        listen.start();

    }

    public void console(String message)
    {
        history.append(message + "\n\r");
        history.setCaretPosition(history.getDocument().getLength());
    }

}

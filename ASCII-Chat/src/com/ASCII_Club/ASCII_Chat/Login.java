package com.ASCII_Club.ASCII_Chat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Wed Apr 08 23:04:24 CEST 2015
 */



/**
 * @author Steffen Aller Jacobsen
 */
public class Login extends JFrame
{

    public Login()
    {
        initComponents();
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void buttonLoginActionPerformed(ActionEvent e) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        int part = Integer.parseInt(txtPort.getText());
        login(name, address, part);
    }

    private void login(String name, String address, int port)
    {
        dispose();
        new ClientWindow(name, address, port);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Steffen Jacobsen
        txtName = new JTextField();
        labelName = new JLabel();
        txtAddress = new JTextField();
        labelipAddress = new JLabel();
        txtPort = new JTextField();
        labelPort = new JLabel();
        labelIPeg = new JLabel();
        labelPorteg = new JLabel();
        buttonLogin = new JButton();

        //======== this ========
        setTitle("Login");
        setResizable(false);
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(txtName);
        txtName.setBounds(56, 50, 166, txtName.getPreferredSize().height);

        //---- labelName ----
        labelName.setText("Name: ");
        labelName.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(labelName);
        labelName.setBounds(113, 30, 47, labelName.getPreferredSize().height);
        contentPane.add(txtAddress);
        txtAddress.setBounds(56, 120, 166, txtAddress.getPreferredSize().height);

        //---- labelipAddress ----
        labelipAddress.setText("IP Address");
        contentPane.add(labelipAddress);
        labelipAddress.setBounds(new Rectangle(new Point(110, 100), labelipAddress.getPreferredSize()));
        contentPane.add(txtPort);
        txtPort.setBounds(55, 200, 166, 24);

        //---- labelPort ----
        labelPort.setText("Port:");
        contentPane.add(labelPort);
        labelPort.setBounds(120, 180, 40, 16);

        //---- labelIPeg ----
        labelIPeg.setText("(eg. 192.168.0.2)");
        contentPane.add(labelIPeg);
        labelIPeg.setBounds(new Rectangle(new Point(95, 145), labelIPeg.getPreferredSize()));

        //---- labelPorteg ----
        labelPorteg.setText("(eg. 8192)");
        contentPane.add(labelPorteg);
        labelPorteg.setBounds(new Rectangle(new Point(110, 225), labelPorteg.getPreferredSize()));

        //---- buttonLogin ----
        buttonLogin.setText("Login");
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonLoginActionPerformed(e);
            }
        });
        contentPane.add(buttonLogin);
        buttonLogin.setBounds(new Rectangle(new Point(105, 300), buttonLogin.getPreferredSize()));

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(300, 380);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Steffen Jacobsen
    private JTextField txtName;
    private JLabel labelName;
    private JTextField txtAddress;
    private JLabel labelipAddress;
    private JTextField txtPort;
    private JLabel labelPort;
    private JLabel labelIPeg;
    private JLabel labelPorteg;
    private JButton buttonLogin;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

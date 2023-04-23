package flie_sharring_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Flie_Sharring_Server {

    ServerSocket serverSocke;

    public Flie_Sharring_Server() {
        try {
            serverSocke = new ServerSocket(5000);
            System.out.println("Starting Server....");
            System.out.println("####################LOGS######################");
            while (true) {
                System.out.println("whitting ...");
                Socket clientSocke = serverSocke.accept();
                System.out.println("Connected to "+clientSocke.getInetAddress().getHostName());
//              ServerConnectionThread conn = new ServerConnectionThread(clientSocket, this);
//              conn.start();
//              usersConnection.add(conn);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Flie_Sharring_Server();
    }
}

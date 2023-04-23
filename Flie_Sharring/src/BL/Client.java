package BL;

import java.net.Socket;

public class Client {

    Socket client;

    public Client() {
        try {
            client = new Socket("localhost", 5000);
            System.out.println("connected\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

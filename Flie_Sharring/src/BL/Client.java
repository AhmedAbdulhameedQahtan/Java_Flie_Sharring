package BL;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

public class Client extends Thread {

    File file;
    JTextArea statusArea;
    Socket client;
    
    public Client(){
        try {
             client = new Socket("localhost", 2468);
            //statusArea.append("Connected to server.\n");
            System.out.println("Connected to server.\n");
            this.start();
            //System.out.println("connected par\n");
            //sendFile(file, statusArea);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Client(File file, JTextArea statusArea) {
        this.file = file;
        this.statusArea = statusArea;
        try {
             client = new Socket("localhost", 2468);
            //statusArea.append("Connected to server.\n");
            System.out.println("Connected to server.\n");
            this.start();
            //System.out.println("connected par\n");
            //sendFile(file, statusArea);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {

            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            FileInputStream fis = new FileInputStream(file);

            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            int read;
            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
            }

            fis.close();
            dos.close();
            client.close();
            statusArea.append("File sent: " + file.getName() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            statusArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    //=======================================
//    public void sendFile(File file, JTextArea statusArea) {
//        Thread clientThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    Socket socket = new Socket("localhost", 8899);
//                    statusArea.append("Connected to server.\n");
//
//                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());
//                    FileInputStream fis = new FileInputStream(file);
//
//                    dos.writeUTF(file.getName());
//                    dos.writeLong(file.length());
//
//                    byte[] buffer = new byte[4096];
//                    int read;
//                    while ((read = fis.read(buffer)) > 0) {
//                        dos.write(buffer, 0, read);
//                    }
//
//                    fis.close();
//                    dos.close();
//                    client.close();
//                    statusArea.append("File sent: " + file.getName() + "\n");
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                    e.printStackTrace();
//                    statusArea.append("Error: " + e.getMessage() + "\n");
//                }
//            }
//        });
//        clientThread.start();
//    }
    //========================================
//    public static void main(String[] args) {
//        new Client();
//    }
}

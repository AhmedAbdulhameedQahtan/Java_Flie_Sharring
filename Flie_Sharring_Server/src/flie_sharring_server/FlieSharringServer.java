package flie_sharring_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class FlieSharringServer {

    ServerSocket sSocket;
    public static Socket socket;
    public DataInputStream dis;
    public DataOutputStream dos;
    static ServerForm serverform;
    static ArrayList<User> users = new ArrayList<User>();

    public FlieSharringServer() {
        try {
            // Openneing a port to listen to Clients
            sSocket = new ServerSocket(2468);
            System.out.println("SERVER IS RUNNING ");
            new Thread(new Runnable() {

                @Override
                public void run() {

                    // while server is running
                    while (!(sSocket.isClosed())) {
                        try {
                            // Waiting for a new Client to Connect accept him and return a socket object
                            socket = sSocket.accept();
                            serverform.writeToConsole("Server Started " + this.getClass());

                            // initialie data input stream to get INcoming Data from the Client
                            dis = new DataInputStream(socket.getInputStream());

                            // initialie data output stream
                            dos = new DataOutputStream(socket.getOutputStream());

                            // create object from lass User
                            User user = new User(dis, dos);

                            // start the userthread Thread
                            user.usethread.start();

                            // a flg to say that we are sending text
                            //user.dos.writeBoolean(false);
                            // flush the stream
                            //user.dos.flush();
                            // text to send
                            //user.dos.writeUTF("Welcome to chat!");
                            // flush the stream
                            //user.dos.flush();
                            // BroadCasting this message to all users
                            broadcast(socket.getInetAddress() + " Has Joined to the server");

                            // Flush the Stream
                            //user.dos.flush();
                            // add the new user to the Array List
                            users.add(user);

                            // Write the sentence in the Server Gui (Console)
                            serverform.writeToConsole(socket.getInetAddress().getHostName() + "  has connected");

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }

                    }
                }
                // Strat this thread to accept more than one client
            }).start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {
                sSocket.close();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
        }

        serverform = new ServerForm();
        serverform.show();
    }

    public static void main(String[] args) {
        new FlieSharringServer();
        //serverform.writeToConsole("Server Started");
    }

    // A function to broadcast a message to all clients
    public static void broadcast(String text) throws IOException {

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            // to check if the user is still connected or not
            if (!user.isConnected()) {

                // Writes to the server text area that a Client disconnected
                serverform.writeToConsole(socket.getInetAddress() + "  Disconnected");

                // Writes to the server Console that a Client disconnected
                System.out.println("Dis");

                user.dis.close();
                user.dos.close();

                // to remove offline users from the Array List
                users.remove(user);

            } else {
                try {
                    // A flag to indicate that we are sending a Message
                    user.dos.writeBoolean(false);

                    // Flush the Stream
                    user.dos.flush();

                    // Send the Msg
                    user.dos.writeUTF(text);

                    // Flush the Stream
                    user.dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void broadcastFile(int fileNameLength, byte[] fileNameBytes, int fileContentLength,
            byte[] fileContentBytes) throws IOException {

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            // to check if the user is still connected or not
            if (!user.isConnected()) {

                // Writes to the server text area that a Client disconnected
                serverform.writeToConsole(socket.getInetAddress() + "  Disconnected");

                // Writes to the server Console that a Client disconnected
                System.out.println("Dis");

                user.dis.close();
                user.dos.close();

                // to remove offline users from the Array List
                users.remove(user);

            } else {

                try {
                    // A flag to indicate that we are sending a file not a text
                    user.dos.writeBoolean(true);

                    // To flush the Stream
                    user.dos.flush();

                    // We First Send the length of the file name , to specify how much data we are gonna be Sending
                    user.dos.writeInt(fileNameLength);

                    // To Flush the Stream
                    user.dos.flush();

                    // Then We Send The File Name
                    user.dos.write(fileNameBytes);

                    // To Flush the Stream
                    user.dos.flush();

                    // Now We Send the length of the Content
                    user.dos.writeInt(fileContentLength);

                    // To Flush the Stream
                    user.dos.flush();

                    // Then We Send the Content of the file
                    user.dos.write(fileContentBytes);

                    // To Flush The Stream
                    user.dos.flush();

                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }
            }

        }
    }

}
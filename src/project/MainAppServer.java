package project;

import project.model.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class MainAppServer {

    static ServerSocket serverSocket;
    static Socket socket;
    static List<Socket> socketList;
    static List<ServerThread> threadList;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(4444);

        } catch (IOException e) {
            e.printStackTrace();
        }
        socketList = new ArrayList<Socket>();
        threadList = new ArrayList<ServerThread>();
        while (true){
            try {
                socket = null;
                System.out.println("before accept");
                socket = serverSocket.accept();
                System.out.println("Established connection");
                socketList.add(socket);
                ServerThread serverThread = new ServerThread(socket);
                threadList.add(serverThread);
                serverThread.start();

            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

}

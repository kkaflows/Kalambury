package project.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class ServerThread extends Thread {

    Socket socket;
    DataPackage dataPackage;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;


    public ServerThread(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        System.out.println("New Thread");
        try {
            System.out.println("1");

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("3");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("4");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("5");

        while (true) {
            try {
                System.out.println("run");
                dataPackage = (DataPackage) objectInputStream.readObject();
                System.out.println(dataPackage.getX());

                objectOutputStream.writeObject(dataPackage);
                objectOutputStream.flush();
                dataPackage = null;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}

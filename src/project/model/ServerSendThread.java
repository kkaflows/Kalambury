package project.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class ServerSendThread extends Thread {

    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    List<Socket> socketList;
    DataPackage dataPackage;

    public ServerSendThread(List<Socket> socketList, DataPackage dataPackage) {
        this.socketList = socketList;
        this.dataPackage = dataPackage;
    }

    @Override
    public void run() {

        for (Socket socket: socketList
             ) {
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(dataPackage);
                objectOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }
}

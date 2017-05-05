package project.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerThread extends Thread {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private static List<ObjectOutputStream> outputStreams = Collections.synchronizedList(new ArrayList<>());
    private static List<DataPackage> history = Collections.synchronizedList(new ArrayList<>());


    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("New Thread");
        connectPlayer();
        updatePlayers();
    }

    private void connectPlayer() {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStreams.add(objectOutputStream);
            for (DataPackage data : history) {
                objectOutputStream.writeObject(data);
            }

            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePlayers() {
        while (true) {
            try {
                System.out.println("run");
                DataPackage dataPackage = (DataPackage) objectInputStream.readObject();
                history.add(dataPackage);
                System.out.println(dataPackage.getX());

                for (ObjectOutputStream outputStream : outputStreams) {
                    outputStream.writeObject(dataPackage);
                }

                dataPackage = null;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}

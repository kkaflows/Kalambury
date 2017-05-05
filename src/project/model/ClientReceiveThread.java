package project.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class ClientReceiveThread extends Thread {

    private Socket socket;
    private DataPackage dataPackage;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    public ClientReceiveThread(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, GraphicsContext graphicsContext) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.graphicsContext = graphicsContext;
    }


    public ClientReceiveThread(Socket socket, GraphicsContext graphicsContext) throws IOException {
        this.socket = socket;
        this.graphicsContext = graphicsContext;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("New ClientReceiveThread");

        while (true) {
            try {
                if(objectInputStream.readObject() instanceof DataPackage) {
                    dataPackage = (DataPackage) objectInputStream.readObject();
                    System.out.println(dataPackage.getX());
                    graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);

                    dataPackage = null;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}

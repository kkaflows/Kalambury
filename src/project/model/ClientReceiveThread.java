package project.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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

    @Override
    public void run() {
        System.out.println("New ClientReceiveThread");

        while(true){
            while (true) {
                try {
                    dataPackage = (DataPackage) objectInputStream.readObject();
                    System.out.println(dataPackage.getX());
                    graphicsContext.fillOval(dataPackage.getX()+10, dataPackage.getY()+10, 5, 5);

                    dataPackage = null;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}

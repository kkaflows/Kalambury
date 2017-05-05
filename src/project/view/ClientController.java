package project.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import project.MainAppClient;
import project.model.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class ClientController {
    public DataPackage dataPackage;
    public MainAppClient mainAppClient;
    public Socket socket;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public GraphicsContext graphicsContext;

    @FXML
    public Canvas canvas;

    @FXML
    private TextField textField;

    @FXML
    private Button button;


    public void setMainAppClient(MainAppClient mainAppClient) {
        this.mainAppClient = mainAppClient;
    }


    @FXML
    private void initialize() {

        try {
            socket = new Socket("localhost", 4444);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawOnCanvas() {

        graphicsContext = canvas.getGraphicsContext2D();

        initDraw(graphicsContext);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    double x = event.getX();
                    double y = event.getY();
                    graphicsContext.fillOval(event.getX(), event.getY(), 5, 5);
                    dataPackage = new DataPackage("test", x, y);
//                System.out.println(dataPackage.getX());
//                System.out.println(dataPackage.getY());

                    objectOutputStream.writeObject(dataPackage);
                    objectOutputStream.flush();
                    System.out.println("wyslano wspolrzedne");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                graphicsContext.fillOval(event.getX(), event.getY(), 5, 5);
                dataPackage = new DataPackage("test", x, y);
                System.out.println(dataPackage.getX());
                System.out.println(dataPackage.getY());
                try {
                    objectOutputStream.writeObject(dataPackage);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
    }


}

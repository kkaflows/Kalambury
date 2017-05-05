package project.view;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
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
import java.util.ArrayList;
import java.util.List;

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
    String msg;
    String key;
    List<String> keyWords = new ArrayList<String>();
    static int keyWordNumber = 0;


    @FXML
    public Canvas canvas;

    @FXML
    private TextField textField;

    public void setMainAppClient(MainAppClient mainAppClient) {
        this.mainAppClient = mainAppClient;
    }


    @FXML
    private void initialize() {

        try {
            socket = new Socket("localhost", 4444);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            keyWords.add("dom");
            keyWords.add("rower");
            keyWords.add("");
            setKeyWords();



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

    @FXML
    private void handleSendButton() {
        msg = textField.getText();
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isWinner()){
            msg = "win";
            try {
                objectOutputStream.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Win");
            alert.setHeaderText("You win");
            alert.setContentText("You win");
            alert.showAndWait();
        }

    }

    private boolean isWinner() {
        if (msg.equals(key)){
            setKeyWords();
            return true;
        }else{
            return false;
        }
    }

    private void setKeyWords(){
        key = keyWords.get(keyWordNumber);
        keyWordNumber++;
    }




}

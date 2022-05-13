package fi.tuni.prog3.calc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Calculator extends Application {

    @Override
    public void start(Stage stage) {
        // Ohjelman pääikkunan otsikoksi asetetaan "Range Calculator"
        stage.setTitle("Calculator");

        // Käytetään käyttöliittymäkomponenttien asemointiin
        // ruutuasettelua
        GridPane grid = new GridPane();

        // Luodaan skene, johon ruudukko sijoitetaan
        Scene scene = new Scene(grid, 350, 275);
        stage.setScene(scene);

        // Syötekentät
        Label labelOp1 = new Label("First operand:");
        labelOp1.setId("fieldOp");
        grid.add(labelOp1, 0, 1);

        Label labelOp2 = new Label("Second operand: ");
        labelOp2.setId("labelOp2");
        grid.add(labelOp2, 0, 2);

        Label labelRes = new Label("Result:");
        labelRes.setId("labelRes");
        grid.add(labelRes, 0, 4);

        // Lisätään ruudukkoon vielä tekstikentät
        //
        TextField fieldOp1 = new TextField();
        fieldOp1.setId("fieldOp1");
        grid.add(fieldOp1, 1, 1);

        //
        TextField fieldOp2 = new TextField();
        fieldOp2.setId("fieldOp2");
        grid.add(fieldOp2, 1, 2);

        // ja tulokselle
        Label fieldRes = new Label("");
        fieldRes.setId("fieldRes");
        /*fieldRes.setBackground(new Background
                (new BackgroundFill
                        (Color.WHITE,
                                CornerRadii.EMPTY,
                                Insets.EMPTY)));*/
        fieldRes.setStyle("-fx-background-color : #FF3547;");
        fieldRes.setAlignment(Pos.CENTER);
        grid.add(fieldRes, 1, 4);

        // Ohjelman kolme nappulaa ladotaan vaakasuoraan
        // HBoxin avulla
        // Rakentajan parametri määrittää elementtien välisen tilan suuruuden
        HBox hbBtn1 = new HBox(10);
        grid.add(hbBtn1, 0, 3);
        hbBtn1.centerShapeProperty();
        HBox hbBtn2 = new HBox(10);
        grid.add(hbBtn2, 1, 3);

        Button btnAdd = new Button("Add");
        btnAdd.setId("btnAdd");
        hbBtn1.getChildren().add(btnAdd);

        Button btnSub = new Button("Subtract");
        btnSub.setId("btnSub");
        hbBtn1.getChildren().add(btnSub);

        Button btnMul = new Button("Multiply");
        btnMul.setId("btnMul");
        hbBtn2.getChildren().add(btnMul);

        Button btnDiv = new Button("Divide");
        btnMul.setId("btnDiv");
        hbBtn2.getChildren().add(btnDiv);

        // Nappula laskee käyttömatkan syötekenttien arvojen mukaisesti
        // Huom! ei sisällä virhetarkasteluja!
        btnAdd.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                var b = Double.parseDouble(fieldOp1.getText());
                var c = Double.parseDouble(fieldOp2.getText());
                var r = b+c;
                fieldRes.setText(String.format("%.2f", r));
            }
        });

        btnSub.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                var b = Double.parseDouble(fieldOp1.getText());
                var c = Double.parseDouble(fieldOp2.getText());
                var r = b-c;
                fieldRes.setText(String.format("%.2f", r));
            }
        });

        btnMul.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                var b = Double.parseDouble(fieldOp1.getText());
                var c = Double.parseDouble(fieldOp2.getText());
                var r = b*c;
                fieldRes.setText(String.format("%.2f", r));
            }
        });

        btnDiv.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                var b = Double.parseDouble(fieldOp1.getText());
                var c = Double.parseDouble(fieldOp2.getText());
                var r = b/c;
                fieldRes.setText(String.format("%.2f", r));
            }
        });

        // Stagen sulkeminen sulkee koko ohjelman
        stage.setOnCloseRequest((event) -> { Platform.exit();});

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
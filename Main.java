import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;


public class Main extends Application {
    ArrayList<String> words;

    public static void main(String[]args){

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        words=new ArrayList<>();
        words.add("HELLO");
        words.add("FLOWER");
        words.add("MONKEY");

        BorderPane panel=new BorderPane();
//enterence screen
        Scene scene = new Scene(panel, 500, 500);
        stage.setTitle("WHELL OF FORTUNE");
        stage.setScene(scene);
        stage.show();

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Button newgame = new Button("NEW GAME");
        Button settings = new Button("SETTINGS");
        Button exit = new Button("EXIT");
        newgame.setStyle("-fx-background-color: #FA8072");
        newgame.setPrefSize(100,50);
        settings.setStyle("-fx-background-color: #B0E0E6");
        settings.setPrefSize(100,50);
        exit.setStyle("-fx-background-color:#0000FF");
        exit.setPrefSize(100,50);
        box.getChildren().addAll(newgame,settings,exit);
        panel.setCenter(box);



        Label label = new Label("WHELL OF FORTUNE ");
        label.setTextAlignment(TextAlignment.CENTER);
        Font font = new Font("Arial", 38);
        label.setStyle("-fx-background-color:#DB7093");
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(500,100);
        panel.setTop(label);

        //new game button
        newgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Settings.setVocab(words.get((int)Math.random()* words.size()));


                Stage stageNewGame=new Stage();
                stage.getScene().getWindow().hide();//close stage

                try{
                    new NewGame().start(stageNewGame);//start new game
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage1=new Stage();
                stage.getScene().getWindow().hide();
                try {
                    new Settings().start(stage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.exit(0);
            }
        });




    }
}

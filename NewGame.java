import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import static javafx.scene.control.ButtonType.OK;

 class NewGame extends Application {

    HBox upBox=new HBox();

    BorderPane lowBot=new BorderPane();
    Label label;
    Button play=new Button("PLAY");
    Button exit=new Button("EXIT");

    BorderPane bottom=new BorderPane();
    BorderPane middle=new BorderPane();
    Group arrow=new Group();
    Group scores = new Group();

    static Wheel boxes;
    //letter collection
    static ArrayList<Label> chArr=new ArrayList<>();

    static TextField scorenum;

    static Integer randomScore;

    static RotateTransition rotate;

    static  Integer currentScore=9;






    @Override
    public void start(Stage stage) throws Exception {
        upBox.setAlignment(Pos.CENTER);
        upBox.setSpacing(5);

        play.setPrefSize(200,160);
        play.setStyle("-fx-background-color: #FF1493");
        play.setFont(new Font("Arial",50));

        exit.setPrefSize(100,50);
        exit.setStyle("-fx-background-color: #FF0000");
        exit.setFont(new Font("Arial",25));

        bottom.setCenter(play);
        bottom.setBottom(exit);
        lowBot.setBottom(bottom);
        middle.setCenter(arrow);

//upper boxes arrangment
        boxes=new Wheel();
        for(int i=0;i<Settings.getVocab().length();i++){
            Character current=Settings.getVocab().toUpperCase().charAt(i);
            label = new Label(current.toString());
            label.setFont(new Font("Arial", 30));
            label.setPrefSize(35,50);
            label.setBorder(new Border(new BorderStroke(Color.DARKOLIVEGREEN, BorderStrokeStyle.SOLID, null, null)));
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-text-fill: white; -fx-background-color: white;");
            upBox.getChildren().add(label);
            upBox.setHgrow(label, Priority.ALWAYS);
            chArr.add(label);
        }

//play button//and rotation
        play.setOnAction(event->{
            RandomRotation();
            rotate.setOnFinished(event1->{
                Platform.runLater(NewGame::Game);

            });
        });

//********--EXIT BOTTOM FOR CLOSE THE GAME--**********
        exit.setOnAction((event->{
            Alert alarm=new Alert(Alert.AlertType.CONFIRMATION);
            alarm.setContentText("THE GAME WILL NOT BE SAVE "+
                    "\nDO YOU STILL WANT TO EXIT?"
            );

            Optional<ButtonType> result=alarm.showAndWait();
            if(result.get()==OK){
                Stage stage1 = new Stage();
                stage.getScene().getWindow().hide();
                try{
                    new Main().start(stage1);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }));

//************-SCORE BOARD-************
        Label scoreBox=new Label("SCORE:");
        scoreBox.setFont(new Font("Arial",20));
        scorenum= new TextField("0");
        scorenum.setEditable(false);
        scorenum.setFont(new Font("Arial", 30));
        scorenum.setStyle("-fx-font-weight: Bold");
        scorenum.setPrefSize(100,50);
        scoreBox.setLabelFor(scorenum);
        scores.getChildren().addAll(scorenum,scoreBox);
        lowBot.setTop(scores);

//*****************-LEFT ARROW-******************
        Image imageA=new Image(new FileInputStream("/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/arrowg.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(imageA);
        imageView.setFitHeight(100.0);
        imageView.setFitWidth(100.0);
        imageView.setPreserveRatio(true);
        arrow.getChildren().addAll(imageView);

        lowBot.setCenter(boxes);
        BorderPane root=new BorderPane();
        root.setTop(upBox);
        root.setCenter(lowBot);
        root.setLeft(middle);
        Scene scene = new Scene(root,900,900);
        stage.setScene(scene);
        stage.setTitle("PLAY!!!");
        stage.show();


    }

    private static void Game() {
        String entered;
        TextInputDialog ent = new TextInputDialog();
        ent.setHeaderText("WHELL OF FORTUNE");
        ent.setTitle("LET'S START");
        ent.setContentText("GUESS A LETTER");

        Optional<String> result= ent.showAndWait();

//ARRANGE THE THREE STEPS
        entered= ent.getEditor().getText().toUpperCase();
        if(result.isPresent()){
            if(Settings.getVocab().toUpperCase().contains(entered)){
                for(Label c:chArr){
                    if(c.getText().contains(entered)&& c.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")){
                        c.setStyle("-fx-text-fill: Black; -fx-background-color: White");

                    }
                }
                //TRUE LETTER
                String path1 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/LetterT.mp3";
                Media media1 = new Media(new File(path1).toURI().toString());
                MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                mediaPlayer1.setAutoPlay(true);

                ScoreCalculate();

            }else{
                //FIRST WRONG
                String path3 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/WrongLe.mp3";
                Media media3 = new Media(new File(path3).toURI().toString());
                MediaPlayer mediaPlayer3 = new MediaPlayer(media3);
                mediaPlayer3.setAutoPlay(true);

                TextInputDialog e1 = new TextInputDialog();
                e1.setContentText("WRONG!!! \n 2 STEPS LEFT");
                e1.setHeaderText("GOOD LUCK!");
                e1.setTitle("TRY!!!");



                Optional<String> result1 = e1.showAndWait();

                entered=e1.getEditor().getText().toUpperCase();
                if(result1.isPresent()){
                    if(Settings.getVocab().toUpperCase().contains(entered)){
                        for (Label l : chArr) {
                            if (l.getText().contains(entered) && l.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                                l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                            }
                        }
                        //TRUE LETTER
                        String path1 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/LetterT.mp3";
                        Media media1 = new Media(new File(path1).toURI().toString());
                        MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                        mediaPlayer1.setAutoPlay(true);

                        ScoreCalculate();
                    }else{
                        //SECOND WRONG
                        String path4 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/WrongLe.mp3";
                        Media media4 = new Media(new File(path4).toURI().toString());
                        MediaPlayer mediaPlayer4 = new MediaPlayer(media3);
                        mediaPlayer4.setAutoPlay(true);

                        TextInputDialog e2 = new TextInputDialog();
                        e2.setContentText("WRONG!!! \n 1 STEPS LEFT");
                        e2.setHeaderText("GOOD LUCK!");
                        e2.setTitle("TRY!!!");



                        Optional<String> result2 = e2.showAndWait();
                        entered=e2.getEditor().getText().toUpperCase();
                        if(result2.isPresent()){
                            if(Settings.getVocab().toUpperCase().contains(entered)){
                                for (Label l : chArr) {
                                    if (l.getText().contains(entered) && l.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                                        l.setStyle("-fx-text-fill: Black; -fx-background-color: White");

                                    }
                                }
                                //TRUE LETTER
                                String path1 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/LetterT.mp3";
                                Media media1 = new Media(new File(path1).toURI().toString());
                                MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                                mediaPlayer1.setAutoPlay(true);

                                ScoreCalculate();
                            }else{
                                Alert alert=new Alert(Alert.AlertType.INFORMATION);

                                //THIRD WRONG
                                String path5 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/WrongLe.mp3";
                                Media media5 = new Media(new File(path5).toURI().toString());
                                MediaPlayer mediaPlayer5 = new MediaPlayer(media5);
                                mediaPlayer5.setAutoPlay(true);

                                alert.setTitle("OUT OF CHANCE!!!");
                                alert.setHeaderText("SORRY:(");
                                alert.setContentText(" TRY ONE MORE TIME");
                                alert.show();
                            }
                        }



                    }

                }



            }



        }
        if(!LabelCheck()){
            Alert win=new Alert(Alert.AlertType.INFORMATION);
            //WIN
            String path5 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/WinG.mp3";
            Media media5 = new Media(new File(path5).toURI().toString());
            MediaPlayer mediaPlayer5 = new MediaPlayer(media5);
            mediaPlayer5.setAutoPlay(true);

            win.setTitle("THE END");
            win.setHeaderText("BRAVO YOU WIN");
            win.setContentText("YOUR SCORE IS "+" "+scorenum.getText()+" "+"!!!");
            win.showAndWait();
        }
    }

    private void RandomRotation() {
        //WHEN THE WHELL ROTATE
        String path2 ="/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/Wheel of fortune wheel sounds.mp3";
        Media media2 = new Media(new File(path2).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
        mediaPlayer2.setAutoPlay(true);


        Integer[]degrees=new Integer[]{
                360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690
        };

        rotate=new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDuration(new Duration(2500));

        int   minimum = 0;
        int maximum = 11;
        Random rn = new Random();
        int range = maximum - minimum +1;
        int randomAngle = degrees[rn.nextInt(range) ];

        rotate.setByAngle(randomAngle);

        randomAngle%=360;

        randomAngle= randomAngle/30;

        randomScore = currentScore + randomAngle ;


        if(randomScore >= 12){
            randomScore = randomScore-12;
        }
        currentScore=randomScore;


        rotate.setNode(boxes);
        rotate.play();




    }

    public static void ScoreCalculate(){
        Integer current=Integer.parseInt(scorenum.getText());
        Integer sum=Wheel. scoreOfWheel[randomScore]+current;
        scorenum.setText(sum.toString());
    }
    public static Boolean LabelCheck(){
        for(Label c:chArr){
            if(c.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")){
                return true;

            }
        }return false;
    }
}

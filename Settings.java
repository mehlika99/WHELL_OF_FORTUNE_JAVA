import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.Optional;


 public class Settings extends Application {
    private static String vocab;
    String word=null;
    static ListView<String>view1=null;
    ListView<String>view2=null;

    Button add=new Button("ADD");
    Button moveR=new Button("->");
    Button moveL=new Button("<-");
    Button done=new Button("DONE");




    public static String getVocab() {

        return vocab;
    }

    public static void setVocab(String vocab) {

        Settings.vocab = vocab;
    }


    @Override
    public void start(Stage stage) throws Exception {
        ObservableList<String>inactiveList= FXCollections.observableArrayList();
        ObservableList<String>activeList=FXCollections.observableArrayList("FLOWER", "HELLO", "MONKEY");

        view1=new ListView<>(activeList);
        view2=new ListView<>(inactiveList);

        BorderPane root=new BorderPane();
        GridPane g=new GridPane();
        g.setPadding(new Insets(5, 20, 10, 20));
        g.setHgap(10);
        g.setVgap(10);

        VBox b=new VBox();
        b.getChildren().addAll(add,moveR,moveL, done);
        b.setSpacing(10);
        b.setAlignment(Pos.CENTER);

        Label labelA = new Label("ACTIVE");
        Label labelI= new Label("INACTIVE");
        labelA.setFont(new Font("Arial", 20));
        labelI.setFont(new Font("Arial", 20));
        GridPane.setHalignment(labelA, HPos.CENTER);
        GridPane.setHalignment(labelI, HPos.CENTER);



        ColumnConstraints c1 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        ColumnConstraints c2 = new ColumnConstraints(80);
        ColumnConstraints c3 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        c1.setHgrow(Priority.ALWAYS);
        c3.setHgrow(Priority.ALWAYS);
        g.getColumnConstraints().addAll(c1, c2, c3);





        BufferedReader br=new BufferedReader(new FileReader("/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/list.txt"));
        String s;
        while((s=br.readLine())!=null){
            if(!s.equals("")){
                inactiveList.add(s);
            }
        }
        br.close();



        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("NEW WORD");
        dialog.setHeaderText(("ENTER NEW WORD"));
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("WORDS IS TOO LONG");

//add button
        add.setOnAction(event->{
            Optional<String> result=dialog.showAndWait();
            if(result.isPresent()){
                word=dialog.getEditor().getText();
                dialog.getEditor().clear();
                if(word.length()<= 15){
                    inactiveList.add(word);
                    BufferedWriter bw=null;
                    try{
                        bw=new BufferedWriter(new FileWriter("/Users/mehlikabilgicli/IdeaProjects/WhellOf(BEN)/src/list.txt",true));
                        bw.write("\n" + word);
                        bw.flush();
                    }catch(IOException e){
                        e.printStackTrace();
                    }finally {
                        if(bw!=null)
                            try{
                                bw.close();
                            } catch(IOException e2){

                            }
                    }
                    dialog.getEditor().clear();
                }else{
                    alert.show();
                }

            }
        });
//MOVING
        EventHandler<ActionEvent> move =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ListView<String> fromView = null;
                        ObservableList<String> fromList = null,toList = null;
                        if (event.getSource().equals(moveL)) {
                            fromView = view2;
                            fromList = inactiveList;
                            toList = activeList;
                        } else if (event.getSource().equals(moveR)) {
                            fromView = view1;
                            fromList = activeList;
                            toList = inactiveList;
                        } else
                            return;
                        word = fromView.getSelectionModel().getSelectedItem();
                        if (word != null) {
                            fromView.getSelectionModel().clearSelection();
                            fromList.remove(word);
                            toList.add(word);
                        }
                    }};
        moveR.setOnAction(move);
        moveL.setOnAction(move);

        done.setOnAction(event -> {

//RANDOM SELECTION
            vocab = view1.getItems().get((int)Math.random()* activeList.size());
            System.out.println(vocab);

            Stage stage1 = new Stage();
            stage.getScene().getWindow().hide();
            try {
                new NewGame().start(stage1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        g.add(labelA, 0, 0);
        g.add(labelI, 2, 0);
        g.add(view1, 0, 1);
        g.add(b, 1, 1);
        g.add(view2, 2, 1);


        root.setCenter(g);
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("SETTINGS");
        stage.setScene(scene);
        stage.show();










    }
}

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import java.util.ArrayList;


public class Wheel extends StackPane {

    static Integer[] scoreOfWheel = new Integer[]{100, 300, 500, 250, 400, 800, 650, 150, 420, 900, 620, 230};
    private static final double Angle = 360d / (double) 12;
    private static double StartAngle = 270;
    int radius = 220;
    double angle = (2 * Math.PI) / 12;
    static ArrayList<Segment> arrayList = new ArrayList<>();

    public Wheel() {

        SegmentsDrawing();
    }

    private void SegmentsDrawing() {
        Group Segments = new Group();
        Group texts = new Group();
        for (int s = 0; s < scoreOfWheel.length; s++) {
            Segment segment = new Segment();
            segment.setStartAngle(StartAngle);

            if(s%2==0){
                segment.setFill(Color.DEEPPINK);
            }else{
                segment.setFill(Color.HONEYDEW);
            }
            StartAngle+=Angle;
            Segments.getChildren().add(segment);
            double x = Math.sin(angle * s) * radius,
                    y = Math.cos(angle * s) * radius;

            Label scoreWhellLabel = new Label(scoreOfWheel[s].toString());
            scoreWhellLabel.relocate(x,y);
            scoreWhellLabel.setTextFill(Color.WHITE);
            scoreWhellLabel.setFont(new Font("Arial", 25));
            segment.setLabel(scoreWhellLabel);

            if(s%2==0){
                scoreWhellLabel.setStyle("-fx-background-color: PURPLE");
            }else{
                scoreWhellLabel.setStyle("-fx-background-color: PINK");
            }

            System.out.println(segment.getStartAngle() +
                    "  " + segment.getLabel().getText());
            arrayList.add(segment);

            texts.getChildren().add(scoreWhellLabel);


        }
        getChildren().addAll(texts,Segments);

    }

    class Segment extends Arc {
        private static final double Angle = 360d / (double) 12;
        int radius = 200;
        int XCenter = 200;
        int YCenter = 200;
        private Label label;

        public Segment() {
            setCenterX(XCenter);
            setCenterY(YCenter);
            setRadiusX(radius);
            setRadiusY(radius);
            setStartAngle(StartAngle);
            setLength(Angle);
            setType(ArcType.ROUND);
            setStroke(Color.BLACK);
        }

        public Label getLabel() {

            return label;
        }

        public void setLabel(Label label) {

            this.label = label;
        }


    }
}







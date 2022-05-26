import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Main  extends Application{

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            OpenCV.loadShared();
            final VideoCapture capture=  new VideoCapture(0); // The number is the ID of the camera
            final ImageView imageView = new ImageView();
            HBox hbox = new HBox(imageView);
            hbox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(hbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            new AnimationTimer(){
                @Override
                public void handle(long l) {
                    imageView.setImage(getCapture(capture));
                }
            }.start();
        }

    public Image getCapture(VideoCapture capture) {
        Mat mat = new Mat();
        capture.read(mat);
        return mat2Img(mat);
    }

    public Image mat2Img(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, bytes);
        InputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        return new Image(inputStream);
    }

}

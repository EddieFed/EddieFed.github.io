/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */

package shaper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class Shaper extends Application {

    @Override public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image("resources/TRIANGLE-RED.png"));
        stage.setTitle("Shaper - Build Chains!");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
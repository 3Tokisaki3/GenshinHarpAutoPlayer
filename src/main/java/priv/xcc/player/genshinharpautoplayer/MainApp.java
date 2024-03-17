package priv.xcc.player.genshinharpautoplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author TokisakiKurumi
 * @className MainApp
 * @date 2023/8/16
 * @description
 **/
public class MainApp extends Application {
    public static Stage primaryStage = new Stage();
    public static Scene homeScene;
    public static void main(String[] args) {
        launch(MainApp.class,args);
    }

    @Override
    public void start(Stage Stage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PlayerHomePage.fxml"));
        AnchorPane homePage = loader.load();
        homeScene = new Scene(homePage);
        primaryStage.setTitle("原神风物之诗琴自动演奏器");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(MainApp.class.getResource("image/favicon.png")).toExternalForm()));
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    public static Stage getStage() {
        return primaryStage;
    }
    public static Scene getHomeScene() {
        return homeScene;
    }
}

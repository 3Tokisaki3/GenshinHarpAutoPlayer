package priv.xcc.player.genshinharpautoplayer.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import priv.xcc.player.genshinharpautoplayer.MainApp;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author TokisakiKurumi
 * @className PlayerHomePageController
 * @date 2023/8/17
 * @description
 **/
public class PlayerHomePageController {

    public void initialize() {
        MainApp.getStage().setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });
    }

    @FXML
    public void openFileChooser() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("midi文件导入器");
            List<File> files = chooser.showOpenMultipleDialog(MainApp.getStage());
            if (Objects.nonNull(files)) {
                for (File file : files) {
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("../../../src/midi_files/" + file.getName()));
                    BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes,0,len);
                        outputStream.flush();
                    }
                    outputStream.close();
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void openMusicPlayerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MusicSearchPage.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            MainApp.getStage().setScene(new Scene(anchorPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

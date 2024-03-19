package priv.xcc.player.genshinharpautoplayer.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import priv.xcc.player.genshinharpautoplayer.MainApp;
import priv.xcc.player.genshinharpautoplayer.midi.MidiPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TokisakiKurumi
 * @className MusicSearchPageController
 * @date 2023/8/18
 * @description
 **/
public class MusicSearchPageController {

    @FXML
    private TextField textField;

    @FXML
    VBox vBox;

    @FXML
    Label label;

    private String fileName;

    @FXML
    public void searchMusic() {
        vBox.getChildren().clear();
        fileName = textField.getText();
        if ("".equals(fileName)) {
            return;
        }
        textField.clear();
        List<File> fileList = new ArrayList<>();
        searchFile(fileName, "../../../src/midi_files", fileList);
        if (fileList.size() == 0) {
            JFXButton button = new JFXButton();
            button.getStyleClass().add("fileButton");
            button.setText("没找到音乐文件哦，请先导入(ΩДΩ)");
            vBox.getChildren().add(button);
            return;
        }
        for (File file : fileList) {
            JFXButton button = new JFXButton();
            button.getStyleClass().add("fileButton");
            button.setText(file.getName());
            button.setOnAction(actionEvent -> {
                File selectedFile = fileList.stream().filter(f -> button.getText().equals(f.getName())).findFirst().get();
                MusicPlayerPageController.music = selectedFile;
                openMusicPlayer();
            });
            vBox.getChildren().add(button);
        }
    }

    public void searchFile(String fileName, String dirName, List<File> fileList) {
        File dir = new File("../../../src/midi_files");
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                searchFile(fileName, file.getName(), fileList);
            }
            if (file.isFile() && file.getName().matches(".*" + fileName + ".*")) {
                fileList.add(file);
            }
        }
    }

    public void returnHomePage() {
        MainApp.getStage().setScene(MainApp.getHomeScene());
    }

    public void openFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("midi文件选择器");
//        System.out.println(new File(Objects.requireNonNull(MainApp.class.getResource("midi_files/").getFile().split("file:\\\\")[0])).exists());
        chooser.setInitialDirectory(new File("../../../src/midi_files"));
        File selectedFile = chooser.showOpenDialog(MainApp.getStage());
        if (Objects.isNull(selectedFile)) {
            return;
        }
        MusicPlayerPageController.music = selectedFile;
        openMusicPlayer();
    }

    public void openMusicPlayer() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(MainApp.class.getResource("view/MusicPlayerPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("原神风物之诗琴自动演奏器");
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(MainApp.class.getResource("image/favicon.png")).toExternalForm()));
            stage.setScene(new Scene(loader.load()));
            MainApp.getStage().setOpacity(0);
            stage.setOnCloseRequest(windowEvent -> {
                MainApp.getStage().setOpacity(1);
                MidiPlayer.threads.forEach(Thread::stop);
                GlobalScreen.removeNativeKeyListener(MusicPlayerPageController.listener);
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package priv.xcc.player.genshinharpautoplayer.controller;

import com.jfoenix.controls.JFXButton;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import priv.xcc.player.genshinharpautoplayer.midi.MidiPlayer;
import priv.xcc.player.genshinharpautoplayer.midi.thread.MusicThread;

import java.io.File;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author TokisakiKurumi
 * @className MusicPlayerPageController
 * @date 2023/8/19
 * @description
 **/
public class MusicPlayerPageController {

    private Long time = 0L;
    private long minuteTime = 0L;
    private long secondTime = 0L;

    public void initialize() {
        button.disableProperty().set(true);
        title.setText(music.getName().split("\\.")[0]);
        new Thread(() -> {
            MidiPlayer midiPlayer = new MidiPlayer();
            midiPlayer.play(music);
            button.disableProperty().set(false);
            for (List<Long> intervals : MidiPlayer.intervalList) {
                Long timeMedian = 0L;
                if (intervals.size() != 0) {
                    for (Long interval : intervals) {
                        if (interval != null) {
                            timeMedian += interval;
                        }
                    }
                    if ((time == 0 || time > timeMedian)) {
                        time = timeMedian;
                    }

                }
            }
            time /= 1000;
            minuteTime = time / 60;
            secondTime = time % 60;
            if (secondTime > 9) {
                endTime.setText(minuteTime + ":" + secondTime);
            } else {
                endTime.setText(minuteTime + ":0" + secondTime);
            }
            ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
            service.scheduleAtFixedRate(() -> {
                MusicThread musicThread = MidiPlayer.threads.get(0);
                minuteTime = musicThread.getInterval() / 1000 / 60;
                secondTime = musicThread.getInterval() / 1000 % 60;
                if (secondTime > 9) {
                    startTime.setText(minuteTime + ":" + secondTime);
                } else {
                    startTime.setText(minuteTime + ":0" + secondTime);
                }
                progressBar.progressProperty().set(((double) musicThread.getInterval() / 1000 / time));
            }, 0, 500, TimeUnit.MILLISECONDS);
        }).start();
    }

    @FXML
    Text title;

    @FXML
    Text startTime;

    @FXML
    Text endTime;

    @FXML
    JFXButton button;

    @FXML
    ProgressBar progressBar;

    public static File music;

    public void play() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "原神");
        if (hwnd != null) {
            User32.INSTANCE.ShowWindow(hwnd, User32.SW_RESTORE);
            User32.INSTANCE.SetForegroundWindow(hwnd);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("原神风物之诗琴自动演奏器");
            alert.setHeaderText("原神未启动");
            alert.setContentText("请先启动原神");
            alert.showAndWait();
            return;
        }
        button.getStyleClass().remove("startButton");
        button.getStyleClass().add("endButton");
        MidiPlayer.threads.forEach(MusicThread::resumeThread);
        button.setOnAction(actionEvent -> {
            pause();
        });
    }

    public void pause() {
        button.getStyleClass().remove("endButton");
        button.getStyleClass().add("startButton");
        MidiPlayer.threads.forEach(MusicThread::pauseThread);
        button.setOnAction(actionEvent -> {
            play();
        });
    }
}

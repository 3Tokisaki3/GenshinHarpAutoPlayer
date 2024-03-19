package priv.xcc.player.genshinharpautoplayer.listener;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import priv.xcc.player.genshinharpautoplayer.controller.MusicPlayerPageController;

/**
 * @author TokisakiKurumi
 * @className GlobalKeyListener
 * @date 2024/3/16
 * @description
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GlobalKeyListener implements NativeKeyListener {
    private boolean ctrlPressed = false;

    private MusicPlayerPageController controller;
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = true;
        }

        // 检测组合键是否按下（例如Ctrl + K）
        if (ctrlPressed && e.getKeyCode() == NativeKeyEvent.VC_K) {
            if (controller.isRun()) {
                controller.pause();
            } else {
                controller.play();
            }
        }
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = false;
        }
    }
}

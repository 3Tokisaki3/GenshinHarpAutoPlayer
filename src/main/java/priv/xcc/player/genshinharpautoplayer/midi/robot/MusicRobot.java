package priv.xcc.player.genshinharpautoplayer.midi.robot;

import priv.xcc.player.genshinharpautoplayer.midi.thread.MusicThread;

import java.awt.Robot;
import java.awt.AWTException;


public class MusicRobot {
    public Robot robot;
    public static long delayEnd;
    public static boolean flag = false;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public void music(Integer delay, char... keyWords) throws InterruptedException {
        flag = false;
        if (keyWords != null) {
            for (char s : keyWords) {
                robot.keyPress(s);
                robot.keyRelease(s);
            }
            if (delay != null) {
                while (delay > 59999) {
                    robot.delay(59999);
                    delay -= 59999;
                }
                robot.delay(delay);
                if (MusicThread.pause) {
                    delayEnd = System.currentTimeMillis();
                    flag = true;
                }
            }
        } else {
            if (delay != null) {
                while (delay > 59999) {
                    robot.delay(59999);
                    delay -= 59999;
                }
                robot.delay(delay);
                if (MusicThread.pause) {
                    delayEnd = System.currentTimeMillis();
                    flag = true;
                }
            }
        }
    }
}
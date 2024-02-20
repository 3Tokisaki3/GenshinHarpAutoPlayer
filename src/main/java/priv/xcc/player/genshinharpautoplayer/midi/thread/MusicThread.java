package priv.xcc.player.genshinharpautoplayer.midi.thread;

import priv.xcc.player.genshinharpautoplayer.midi.MidiPlayer;
import priv.xcc.player.genshinharpautoplayer.midi.robot.MusicRobot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author TokisakiKurumi
 * @className MusicThread
 * @date 2023/8/3
 * @description
 **/
public class MusicThread extends Thread {
    private final Object lock = new Object();
    private Queue<Integer> queue = new LinkedList<>();
    private int k1 = -1;
    private int i1 = -1;
    private MusicRobot robot = new MusicRobot();
    public static boolean pause = false;
    private Long pauseStartTime = 0L;
    private Long pauseTime = 0L;
    public long interval = 0L;
    private long difference = 0;

    public long getInterval() {
        return interval;
    }

    @Override
    public void run() {
        boolean flag = true;
        difference = 0;
        pause = false;
        interval = 0L;
        long startTime = System.currentTimeMillis();
        this.pauseThread();
        while (flag) {
            try {
                difference = 0;
                while (pause) {
                    onPause();
                }
                if (!queue.isEmpty()) {
                    i1 = queue.poll();
                    /*if (MidiPlayer.intervalList.get(k1).get(i1) == 0) {
                        if (i1 == MidiPlayer.keyList.get(k1).size() - 1) {
                            robot.music(null, MidiPlayer.keyList.get(k1).get(i1).toCharArray());
                            flag = false;
                        } else if (i1 == 0) {
                            robot.robot.delay(0);
                        } else if (MidiPlayer.keyList.get(k1).get(i1) != "") {
                            robot.music(0, MidiPlayer.keyList.get(k1).get(i1 - 1).toCharArray());
                        } else {
                            robot.music(0, null);
                        }
                        continue;
                    }*/
                    if (System.currentTimeMillis() - startTime > interval) {
                        difference = System.currentTimeMillis() - startTime - interval + difference;
                    }
                    System.out.println(MidiPlayer.intervalList.get(k1).get(i1) - difference + pauseTime);
                    if (i1 == MidiPlayer.keyList.get(k1).size() - 1) {
                        robot.music(null, MidiPlayer.keyList.get(k1).get(i1).toCharArray());
                        flag = false;
                    } else if (i1 == 0) {
                        robot.music(Integer.parseInt(String.valueOf(MidiPlayer.intervalList.get(k1).get(i1))), null);
                    } else if (MidiPlayer.keyList.get(k1).get(i1) != "") {
                        robot.music(Integer.parseInt(String.valueOf(MidiPlayer.intervalList.get(k1).get(i1) - difference + pauseTime < 0 ? 0 : MidiPlayer.intervalList.get(k1).get(i1) - difference + pauseTime)), MidiPlayer.keyList.get(k1).get(i1 - 1).toCharArray());
                    } else {
                        robot.music(Integer.parseInt(String.valueOf(MidiPlayer.intervalList.get(k1).get(i1) - difference + pauseTime < 0 ? 0 : MidiPlayer.intervalList.get(k1).get(i1) - difference + pauseTime)), null);
                    }
                    interval += MidiPlayer.intervalList.get(k1).get(i1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void onPause() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }

    public void pauseThread() {
        pause = true;
        pauseStartTime = System.currentTimeMillis();
    }

    public void resumeThread() {
        synchronized (lock) {
            pauseTime += System.currentTimeMillis() - pauseStartTime;
            /*if (MusicRobot.flag) {
                new Thread(() -> {
                    System.err.println(MusicRobot.delayEnd - pauseStartTime);
                    robot.robot.delay(Integer.parseInt(String.valueOf(MusicRobot.delayEnd - pauseStartTime)));
                }).start();
            }*/
            lock.notify();
        }
        pause = false;
    }

    public MusicThread() {
    }

    public void setK1I1(int k, int i) {
        this.k1 = k;
        queue.offer(i);
    }

    public MusicRobot getRobot() {
        return robot;
    }
}

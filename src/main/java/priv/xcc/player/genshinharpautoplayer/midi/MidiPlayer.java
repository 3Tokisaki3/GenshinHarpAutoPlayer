package priv.xcc.player.genshinharpautoplayer.midi;

import priv.xcc.player.genshinharpautoplayer.midi.map.MusicMap;
import priv.xcc.player.genshinharpautoplayer.midi.thread.MusicThread;
import priv.xcc.player.genshinharpautoplayer.midi.utils.MidiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TokisakiKurumi
 * @className MidiPlayer
 * @date 2023/8/19
 * @description
 **/
public class MidiPlayer {
    public static List<List<Long>> intervalList;
    public static List<List<String>> keyList;
    public static ArrayList<MusicThread> threads;
    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public void play(File midiFile) {
        List<List<String>> musicScore = getMusicScore(midiFile);
        intervalList = new ArrayList<>();
        keyList = new ArrayList<>();
        threads = new ArrayList<>();
        for (int j = 0; j < musicScore.size(); j++) {
            if (musicScore.get(j).size() == 0) {
                continue;
            }
            keyList.add(MusicMap.convert(getNoteList(musicScore.get(j))));
            intervalList.add(getIntervalList(musicScore.get(j)));
        }
        for (int j = 0; j < keyList.size(); j++) {
            List<String> musicKeyList = new ArrayList<>();
            List<Long> musicIntervalList = new ArrayList<>();
            StringBuilder sb = null;
            for (int i = 0; i < keyList.get(j).size(); i++) {
                if (i == 0) {
                    musicIntervalList.add(intervalList.get(j).get(0));
                    continue;
                }
                if (i == keyList.get(j).size() - 1) {
                    musicKeyList.add(keyList.get(j).get(i - 1));
                    musicIntervalList.add(null);
                } else if (intervalList.get(j).get(i) <= 30) {
                    Long interval = 0L;
                    sb = new StringBuilder(keyList.get(j).get(i - 1));
                    sb.append(keyList.get(j).get(i));
                    interval += intervalList.get(j).get(i);
                    i++;
                    while (i < intervalList.get(j).size() && intervalList.get(j).get(i) <= 30) {
                        if (i == keyList.get(j).size()) {
                            break;
                        }
                        sb.append(keyList.get(j).get(i));
                        interval += intervalList.get(j).get(i);
                        i++;
                    }
                    if (i < keyList.get(j).size()) {
                        interval += intervalList.get(j).get(i);
                    }
                    musicKeyList.add(sb.toString());
                    musicIntervalList.add(interval);
                } else {
                    musicKeyList.add(keyList.get(j).get(i - 1));
                    musicIntervalList.add(intervalList.get(j).get(i));
                }
            }
            intervalList.set(j, musicIntervalList);
            keyList.set(j, musicKeyList);
        }
        int count = 0;
        Iterator<List<String>> iterator = keyList.iterator();

        while (iterator.hasNext()) {
            int size = iterator.next().size();
            count = Math.max(size, count);
            MusicThread musicThread = new MusicThread();
            threads.add(musicThread);
            musicThread.start();
        }
        for (int i = 0; i < count; i++) {
            for (int k = 0; k < keyList.size(); k++) {
                MusicThread musicThread = threads.get(k);
                musicThread.setK1I1(k, i);
            }
        }
    }

    private static List<List<String>> getMusicScore(File midiFile) {
        return MidiUtil.getNodeAndTick(midiFile);
    }

    private static List<String> getNoteList(List<String> list) {
        List<String> noteList = new ArrayList<>();
        for (String s : list) {
            String note = s.split(" ")[0];
            noteList.add(note);
        }
        return noteList;
    }

    private static List<Long> getIntervalList(List<String> list) {
        List<Long> intervalList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).split(" ")[1]);
        }
        for (int i = -1; i < list.size() - 1; i++) {
            if (i == -1) {
                intervalList.add(Long.parseLong(list.get(i + 1)));
                continue;
            }
            intervalList.add(Long.parseLong(list.get(i + 1)) - Long.parseLong(list.get(i)));
        }
        return intervalList;
    }
}

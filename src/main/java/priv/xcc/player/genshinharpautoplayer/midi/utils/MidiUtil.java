package priv.xcc.player.genshinharpautoplayer.midi.utils;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author TokisakiKurumi
 * @className MidiUtil
 * @date 2023/8/19
 * @description
 **/
public class MidiUtil {
    public static String tone = null;
    public static float bpm;

    public static List<List<String>> getNodeAndTick(File midiFile) {
        Sequencer sequencer = null;
        int count = 0;
        List<List<String>> list = new ArrayList<>();
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = MidiSystem.getSequence(new FileInputStream(midiFile));
            tone = analyzeKey(sequence);
            sequencer.setSequence(sequence);
            int resolution = sequence.getResolution();
            Track[] tracks = sequence.getTracks();
            boolean flag = false;
            bpm = sequencer.getTempoInBPM();
            List<String> noteList = null;
            for (Track track : tracks) {
                count = 0;
                noteList = new ArrayList<>();
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent midiEvent = track.get(i);
                    MidiMessage message = midiEvent.getMessage();
                    if (message instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) message;
                        if (metaMessage.getType() == 0x51) {
                            byte[] data = metaMessage.getData();
                            int tempo = ((data[0] & 0xFF) << 16) |
                                    ((data[1] & 0xFF) << 8) |
                                    (data[2] & 0xFF);
                            bpm = 60000000f / tempo;
                            if (i == 0) {
                                flag = true;
                            }
                        }
                    }
                    if (!flag && i == 0) {
                        bpm = sequencer.getTempoInBPM();
                    }
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        if (sm.getCommand() == ShortMessage.NOTE_ON) {
                            int note = sm.getData1();
                            String noteName = PitchToNoteConverter.convertPitchToNoteName(note);
                            noteList.add(noteName + " " + tickToMilliseconds(midiEvent.getTick(), bpm, resolution));
                        }
                    }
                }
                list.add(noteList);
            }
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static long tickToMilliseconds(long tick, float bpm, int ticksPerBeat) {
        float beatsPerMinute = bpm;
        return (long) (tick * 60000.0 / (ticksPerBeat * beatsPerMinute));
    }

    public static String analyzeKey(Sequence sequence) {
        Map<Integer, Integer> pitchOccurrences = new HashMap<>();
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    ShortMessage shortMessage = (ShortMessage) message;
                    int command = shortMessage.getCommand();
                    int data1 = shortMessage.getData1();

                    if (command == ShortMessage.NOTE_ON) {
                        // 统计音符的出现频率
                        pitchOccurrences.put(data1, pitchOccurrences.getOrDefault(data1, 0) + 1);
                    }
                }
            }
        }
        int key = getKeyByKrumhanslSchmuckler(pitchOccurrences);
        return getKeyName(key);
    }

    private static int getKeyByKrumhanslSchmuckler(Map<Integer, Integer> pitchOccurrences) {
        // Krumhansl-Schmuckler 权重表（长度为 12）
        double[] krumhanslSchmucklerWeights = {6.35, 2.23, 3.48, 2.33, 4.38, 4.09, 2.52, 5.19, 2.39, 3.66, 2.29, 2.88};

        // 计算音符出现频率的总和
        int totalOccurrences = 0;
        for (int occurrences : pitchOccurrences.values()) {
            totalOccurrences += occurrences;
        }

        // 计算归一化后的权重表
        double[] normalizedWeights = new double[12];
        for (int i = 0; i < 12; i++) {
            double normalizedWeight = krumhanslSchmucklerWeights[i] * totalOccurrences / pitchOccurrences.size();
            normalizedWeights[i] = normalizedWeight;
        }

        // 计算每个音符对应的总权重
        double[] pitchWeights = new double[12];
        for (Map.Entry<Integer, Integer> entry : pitchOccurrences.entrySet()) {
            int pitchClass = entry.getKey() % 12;
            int occurrences = entry.getValue();
            pitchWeights[pitchClass] += occurrences;
        }

        // 找出最大权重对应的音调
        int bestKey = 0;
        double bestCorrelation = 0;
        for (int key = 0; key < 12; key++) {
            double correlation = 0;
            for (int i = 0; i < 12; i++) {
                correlation += pitchWeights[i] * normalizedWeights[(12 + i - key) % 12];
            }
            if (correlation > bestCorrelation) {
                bestCorrelation = correlation;
                bestKey = key;
            }
        }

        return bestKey;
    }

    // 根据音高值获取调式名称
    private static String getKeyName(int key) {
        String[] keyNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        return keyNames[key];
    }
}
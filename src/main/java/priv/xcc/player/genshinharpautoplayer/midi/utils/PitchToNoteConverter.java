package priv.xcc.player.genshinharpautoplayer.midi.utils;

import java.util.HashMap;

public class PitchToNoteConverter {
    public static HashMap<String, Integer> halfNoteMap = null;

    static {
        halfNoteMap = new HashMap<>();
        halfNoteMap.put("C", 0);
        halfNoteMap.put("C#", -1);
        halfNoteMap.put("D", -2);
        halfNoteMap.put("D#", -3);
        halfNoteMap.put("E", -4);
        halfNoteMap.put("F", -5);
        halfNoteMap.put("F#", -6);
        halfNoteMap.put("G", -7);
        halfNoteMap.put("G#", -8);
        halfNoteMap.put("A", -9);
        halfNoteMap.put("A#", -10);
        halfNoteMap.put("B", -11);
    }

    private static Integer count = halfNoteMap.get(MidiUtil.tone);
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static String convertPitchToNoteName(int pitch) {
        count = halfNoteMap.get(MidiUtil.tone);
        int octave = (pitch / 12) - 1;
        pitch = pitch + count;
        if (pitch < 12 || pitch > 96) {
            return "";
        }
        pitch = Math.max(0, Math.min(127, pitch));
        int noteIndex = pitch % 12;

        octave = Math.min((pitch / 12) - 1, octave);

        String noteName = NOTE_NAMES[noteIndex];
        return noteName + octave;
    }
}
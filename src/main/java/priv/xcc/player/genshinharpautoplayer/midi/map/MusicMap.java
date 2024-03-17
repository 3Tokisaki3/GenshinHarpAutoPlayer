package priv.xcc.player.genshinharpautoplayer.midi.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TokisakiKurumi
 * @className MusicMap
 * @date 2023/7/14
 * @description
 **/
public class MusicMap {
    private static Map<String,Map<String,String>> map = new HashMap<>();
    private static Map<String,String> cMap = null;
    static {
        cMap = new HashMap<>();
        map.put("C",cMap);
        init();
    }
    private static void init() {
        cMap.put("C1","Z");
//      CEG
        cMap.put("C#1","Z");
        cMap.put("D1","X");
//        DFA
        cMap.put("D#1","X");
        cMap.put("E1","C");
        cMap.put("F1","V");
//        FAC
        cMap.put("F#1","V");
        cMap.put("G1","B");
//        GBD
        cMap.put("G#1","B");
        cMap.put("A1","N");
//        ACE
        cMap.put("A#1","N");
        cMap.put("B1","M");

        cMap.put("C4","A");
        cMap.put("C#4","A");
        cMap.put("D4","S");
        cMap.put("D#4","S");
        cMap.put("E4","D");
        cMap.put("F4","F");
        cMap.put("F#4","F");
        cMap.put("G4","G");
        cMap.put("G#4","G");
        cMap.put("A4","H");
        cMap.put("A#4","H");
        cMap.put("B4","J");

        cMap.put("C5","Q");
        cMap.put("C#5","Q");
        cMap.put("D5","W");
        cMap.put("D#5","W");
        cMap.put("E5","E");
        cMap.put("F5","R");
        cMap.put("F#5","R");
        cMap.put("G5","T");
        cMap.put("G#5","T");
        cMap.put("A5","Y");
        cMap.put("A#5","Y");
        cMap.put("B5","U");
    }
    public static List<String> convert(List<String> list) {
        Map<String,String> toneMap = map.get("C");
        List<String> keyList = new ArrayList<>();
        for (String s : list) {
            switch (s.contains("#")?s.split("\\d")[0]:s.split("")[0]) {
                case "C":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("C5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("C4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("C1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "D":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("D5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("D4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("D1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "E":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("E5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("E4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("E1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "F":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("F5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("F4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("F1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "G":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("G5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("G4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("G1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "A":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("A5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("A4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("A1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "B":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("B5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("B4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("B1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "C#":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("C#5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("C#4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("C#1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "D#":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("D#5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("D#4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("D#1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "F#":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("F#5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("F#4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("F#1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "G#":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("G#5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("G#4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("G#1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                case "A#":
                    if (Integer.parseInt(s.split("")[s.length() - 1]) >= 5 && Integer.parseInt(s.split("")[s.length() - 1]) <= 7) {
                        keyList.add(toneMap.get("A#5"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 4) {
                        keyList.add(toneMap.get("A#4"));
                    } else if (Integer.parseInt(s.split("")[s.length() - 1]) >= 0 && Integer.parseInt(s.split("")[s.length() - 1]) <= 3){
                        keyList.add(toneMap.get("A#1"));
                    } else {
                        keyList.add("");
                    }
                    break;
                default:
                    keyList.add("");
            }
        }
        return keyList;
    }
}

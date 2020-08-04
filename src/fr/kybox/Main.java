package fr.kybox;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AudioDeviceSelector lineSelector = new AudioDeviceSelector();
        lineSelector.displayDeviceList();

        AudioRecorder audioRecorder = new AudioRecorder();
        audioRecorder.captureAudio(lineSelector.selectDevice().getMixerInfo());

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        audioRecorder.saveAudio(new File("Record.wav"));
    }
}

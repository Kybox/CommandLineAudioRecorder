package fr.kybox;

import fr.kybox.audio.AudioRecorder;
import fr.kybox.audio.AudioSaver;
import fr.kybox.config.AudioDeviceSelector;
import fr.kybox.utils.UserInput;

/**
 * Author : yann@kybox.fr
 **/
public class Main {

    public static void main(String[] args) {

        // Checking n' selecting the recording device
        AudioDeviceSelector lineSelector = new AudioDeviceSelector();
        lineSelector.displayDeviceList();

        // Start recording
        AudioRecorder audioRecorder = new AudioRecorder();
        audioRecorder.captureAudio(lineSelector.selectDevice().getMixerInfo());

        // Stop n' save
        UserInput.getLine();
        audioRecorder.stopRecording();
        AudioSaver.saveFile(audioRecorder.getRecordData());
    }
}

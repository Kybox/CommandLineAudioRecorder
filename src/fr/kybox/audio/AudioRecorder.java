package fr.kybox.audio;

import fr.kybox.config.AudioDataFormat;
import fr.kybox.thread.ThreadRecorder;

import javax.sound.sampled.*;

/**
 * Author : yann@kybox.fr
 **/
public class AudioRecorder {

    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    ThreadRecorder threadRecorder;

    public AudioRecorder() {
    }

    public void captureAudio(Mixer.Info mixerInfo) {

        try {
            audioFormat = AudioDataFormat.getAudioFormat();

            targetDataLine = AudioSystem.getTargetDataLine(audioFormat, mixerInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            System.out.println("\n-------------------");
            System.out.println("RECORDING PROCESS :");
            System.out.println("-------------------");
            System.out.println("Start recording from " + mixerInfo.getName());

            threadRecorder = new ThreadRecorder(targetDataLine);
            threadRecorder.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void stopRecording(){
        this.threadRecorder.setStopCapture(true);
        System.out.println("\rRecording completed");
    }

    public byte[] getRecordData() {
        return this.threadRecorder.getByteArrayOutputStream().toByteArray();
    }
}

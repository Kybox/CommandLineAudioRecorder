package fr.kybox;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class AudioRecorder {

    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    ThreadRecorder threadRecorder;
    byte[] audioData;

    public AudioRecorder() {
    }

    public void captureAudio(Mixer.Info mixerInfo) {

        try {
            audioFormat = getAudioFormat();

            targetDataLine = AudioSystem.getTargetDataLine(audioFormat, mixerInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            System.out.println("Start recording from " + mixerInfo.getName());

            threadRecorder = new ThreadRecorder(targetDataLine);
            threadRecorder.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void saveAudio(File file) {

        stopRecording();

        try {

            System.out.println("\nSaving process, please wait...");

            byte[] audioData = this.threadRecorder.getByteArrayOutputStream().toByteArray();

            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            AudioFormat audioFormat = getAudioFormat();

            this.audioInputStream = new AudioInputStream(
                    byteArrayInputStream,
                    audioFormat,
                    audioData.length / audioFormat.getFrameSize());

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            ThreadRecorder saveThread = new ThreadRecorder(file, this.audioInputStream);
            saveThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        // You can try also 8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public void stopRecording(){
        this.audioData = this.threadRecorder.getByteArrayOutputStream().toByteArray();
        this.threadRecorder.setStopCapture(true);
        System.out.println("\rRecording completed");
    }
}

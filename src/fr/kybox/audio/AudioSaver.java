package fr.kybox.audio;

import fr.kybox.config.AudioDataFormat;
import fr.kybox.thread.ThreadRecorder;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Author : yann@kybox.fr
 **/
public class AudioSaver {

    public static void saveFile(byte[] audioData) {

        try {

            System.out.println("\n----------------");
            System.out.println("SAVING PROCESS :");
            System.out.println("----------------");

            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            AudioFormat audioFormat = AudioDataFormat.getAudioFormat();

            AudioInputStream audioInputStream = new AudioInputStream(
                    byteArrayInputStream,
                    audioFormat,
                    audioData.length / audioFormat.getFrameSize());

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            ThreadRecorder saveThread = new ThreadRecorder(audioInputStream);
            saveThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}

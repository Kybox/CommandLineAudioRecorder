package fr.kybox.thread;

import fr.kybox.utils.UserInput;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Author : yann@kybox.fr
 **/
public class ThreadRecorder extends Thread {

    private final String CAPTURE = "c";
    private final String SAVE = "s";
    private final String threadType;

    private File file;
    private Boolean stopCapture;
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    public ThreadRecorder(AudioInputStream audioInputStream) {
        this.threadType = SAVE;
        this.audioInputStream = audioInputStream;
    }

    public ThreadRecorder(TargetDataLine targetDataLine) {
        this.targetDataLine = targetDataLine;
        this.threadType = CAPTURE;
    }

    public void run() {

        switch (this.threadType) {

            case CAPTURE:

                this.stopCapture = false;
                byte[] tempBuffer = new byte[10000];
                this.byteArrayOutputStream = new ByteArrayOutputStream();

                try {

                    System.out.println("Press 'Enter' to stop and save the file");

                    while (!this.stopCapture) {
                        int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
                        if (cnt > 0) this.byteArrayOutputStream.write(tempBuffer, 0, cnt);
                        System.out.print("\r" + this.targetDataLine.getMicrosecondPosition() + " ms");
                    }

                    this.byteArrayOutputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                break;

            case SAVE:

                System.out.println("\rPlease, enter a filename :");
                String filename = UserInput.getLine() + ".wav";

                System.out.println("\r  |_ Start saving");

                try {
                    if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE, this.audioInputStream))
                        AudioSystem.write(this.audioInputStream, AudioFileFormat.Type.WAVE, new File(filename));

                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                System.out.println("  |_ File saved : " + filename);

                break;
        }
    }

    public void setStopCapture(Boolean stopCapture) {
        this.stopCapture = stopCapture;
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return this.byteArrayOutputStream;
    }
}

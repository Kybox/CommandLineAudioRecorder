package fr.kybox;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ThreadRecorder extends Thread {

    private final String CAPTURE = "c";
    private final String SAVE = "s";
    private final String threadType;

    private File file;
    private Boolean stopCapture;
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    public ThreadRecorder(File file, AudioInputStream audioInputStream) {
        this.file = file;
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

                    System.out.println("Capture...");
                    System.out.println("Press 'Enter' to stop and save the file");

                    while (!stopCapture) {
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

                System.out.println("\r  |_ Start saving");

                try {
                    if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE, this.audioInputStream))
                        AudioSystem.write(this.audioInputStream, AudioFileFormat.Type.WAVE, file);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                System.out.println("  |_ File saved : " + file.getName());

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

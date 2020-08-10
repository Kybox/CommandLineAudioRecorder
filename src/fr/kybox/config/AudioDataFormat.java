package fr.kybox.config;

import javax.sound.sampled.AudioFormat;

/**
 * Author : yann@kybox.fr
 **/
public class AudioDataFormat {

    public static AudioFormat getAudioFormat() {
        int channels = 1;
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, true, false);
    }
}

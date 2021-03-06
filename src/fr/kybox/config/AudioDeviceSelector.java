package fr.kybox.config;

import fr.kybox.utils.UserInput;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : yann@kybox.fr
 **/
public class AudioDeviceSelector {

    private final List<Mixer> supportedMixerList = new ArrayList<>();

    public void displayDeviceList() {

        Line.Info targetDataLineInfo = new Line.Info(TargetDataLine.class);
        Mixer.Info[] mixerInfoList = AudioSystem.getMixerInfo();

        int deviceNb = 0;

        System.out.println("-----------------------------");
        System.out.println("AUDIO DEVICE SUPPORTED LIST :");
        System.out.println("-----------------------------");

        for (Mixer.Info mixerInfo : mixerInfoList) {

            Mixer mixer = AudioSystem.getMixer(mixerInfo);

            if (mixer.isLineSupported(targetDataLineInfo)) {

                deviceNb++;
                this.supportedMixerList.add(mixer);
                System.out.println("[" + deviceNb + "] -> " + mixer.getMixerInfo().getName());
                System.out.println("       Description : " + mixer.getMixerInfo().getDescription() + "\n");
            }
        }

        if (deviceNb == 0) {
            System.out.println("NO AUDIO DEVICE SUPPORTED");
            System.exit(0);
        }
    }

    public Mixer selectDevice() {

        System.out.println("Pick a number");
        Mixer selectedMixer = this.supportedMixerList.get(UserInput.getInt() - 1);

        System.out.println("\nSelected audio capture device : " + selectedMixer.getMixerInfo().getName());

        return selectedMixer;
    }
}

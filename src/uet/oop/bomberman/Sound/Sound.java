package uet.oop.bomberman.Sound;

import uet.oop.bomberman.Main;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class Sound {

    private static Clip myClip;

    public static void play(String sound) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    myClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(Main.class.getResourceAsStream("/sound/" + sound + ".wav")));
                    myClip.open(inputStream);
                    myClip.start();
                    if (sound.equals("Back")) {
                        loopInf();
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

    }

    public static void loopInf(){
        if (myClip.isActive()){
            return;
        }
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}

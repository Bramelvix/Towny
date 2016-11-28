package sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Clip woodChopping;
	public static Clip stoneMining;
	public static Clip drill;

	public static void speelGeluid(Clip clip) {
		clip.setFramePosition(0);
		clip.loop(0);
	}

	public static void initSound() {
		try {
			URL woodsoundurl = Sound.class.getResource("/res/sound/woodChopping.wav");
			woodChopping = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(woodsoundurl);
			woodChopping.open(inputStream);
			stoneMining = AudioSystem.getClip();
			URL stoneminingurl = Sound.class.getResource("/res/sound/stoneMining.wav");
			inputStream = AudioSystem.getAudioInputStream(stoneminingurl);
			stoneMining.open(inputStream);
			drill = AudioSystem.getClip();
			URL drillurl = Sound.class.getResource("/res/sound/drill.wav");
			inputStream = AudioSystem.getAudioInputStream(drillurl);
			drill.open(inputStream);
			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}

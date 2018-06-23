package Jarvis.Google;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Voice {
	
	private SynthesiserV2 voice;
	private Player player;

	public Voice(double speed, double pitch) {
		voice = new SynthesiserV2(
				"AIzaSyD8GsBCn5bQnm7WCFCdYAdm6SrvU0Y1QPA");
		voice.setSpeed(speed);
		voice.setPitch(pitch);
		voice.setLanguage("en-US");
	}
	
	public void say(String toSay) {
		try {player = new Player(voice.getMP3Data(toSay));
		player.play();
		}
		catch (JavaLayerException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
}

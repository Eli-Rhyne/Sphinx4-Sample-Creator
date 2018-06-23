package Jarvis.Google;

import java.io.File;

import javax.sound.sampled.LineUnavailableException;

import javaFlacEncoder.FLACFileWriter;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.recognizer.GoogleResponse;


public class GoogleRecognize {
	private static Microphone mic;
	private static File file;
	private static Recognizer recognizer;
	
	public GoogleRecognize() {
		mic = new Microphone(FLACFileWriter.FLAC);
		file = new File("tempaudio.flac");
		recognizer = new Recognizer (Recognizer.Languages.ENGLISH_US, false, System.getenv("GOOGLE_KEY"));
	}
	
	public void record(int timeInMilli) throws LineUnavailableException, InterruptedException {
		mic.captureAudioToFile(file);
		System.out.println ("Recording...");
		Thread.sleep(timeInMilli);
		mic.close ();
		System.out.println ("Recording stopped.");
	}
  

    public String recognize() {
    	//Although auto-detect is available, it is recommended you select your region for added accuracy.
    	try {
    		System.out.println("Sample rate is: " + (int) mic.getAudioFormat().getSampleRate());
			GoogleResponse response = recognizer.getRecognizedDataForFlac (file, 1, (int) mic.getAudioFormat().getSampleRate ());
			System.out.println ("Google Response: " + response.getResponse ());
			System.out.println ("Google is " + Double.parseDouble (response.getConfidence ()) * 100 + "% confident in" + " the reply");
			return response.getResponse();
		}
		catch (Exception ex) {
			System.err.println ("ERROR: Google cannot be contacted");
			ex.printStackTrace ();
			return "Something went wrong";
		}
    }
    
    public void close() {
    	file.deleteOnExit();
    	mic = null;
    	recognizer = null;
    }
}
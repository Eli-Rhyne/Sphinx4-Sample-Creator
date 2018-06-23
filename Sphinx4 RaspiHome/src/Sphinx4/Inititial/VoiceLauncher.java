
package Sphinx4.Inititial;

//Imports
import edu.cmu.sphinx.api.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import Jarvis.Google.GoogleRecognize;
import Jarvis.Google.Voice;


/**
*
* @author Eli Rhyne
*/
public class VoiceLauncher {
	private static Voice google = new Voice(1,.7);
	public static void main(String[] args) throws IOException, LineUnavailableException, InterruptedException {
		while(true) {
			// Configuration Object
			Configuration configuration = new Configuration();
			
			// Set path to the acoustic model.
			//configuration.setAcousticModelPath("Resources/en-us-eli");
			configuration.setAcousticModelPath("Resources/hub4opensrc.cd_continuous_8gau");
			// Set path to the dictionary.
			configuration.setDictionaryPath("Resources/Commands.dic");
			// Set path to the language model.
			configuration.setLanguageModelPath("Resources/Commands.lm");
		
			//Recognizer object, Pass the Configuration object
			LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
		
			//Start Recognition Process (The boolean parameter clears the previous cache if true)
			recognize.startRecognition(true);
			
			//Create SpeechResult Object
			SpeechResult result;
			
			//Checking if recognizer has recognized the speech
			while ((result = recognize.getResult()) != null) {
				System.out.println(result.getHypothesis());
				if(result.getHypothesis().toLowerCase().contains("hey")) {
					recognize.stopRecognition();
					google.say("What can i do for you?");
					GoogleRecognize recog = new GoogleRecognize();
					recog.record(4000);
					google.say(recog.recognize());
					recog.close();
					break;
				}
			}
		}
	}
}

package Configuration;

//Imports
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;

/**
*
* @author ex094
*/
public class voiceLauncher {
  public static void main(String[] args) throws IOException {
      // Configuration Object
      Configuration configuration = new Configuration();

      // Set path to the acoustic model.
      configuration.setAcousticModelPath("Resources/en-us-eli");
      // Set path to the dictionary.
      configuration.setDictionaryPath("Resources/cmudict-en-us.dict");
      // Set path to the language model.
      configuration.setLanguageModelPath("Resources/en-us.lm.bin");
      
      //Recognizer object, Pass the Configuration object
      LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);

      //Start Recognition Process (The boolean parameter clears the previous cache if true)
      recognize.startRecognition(true);

      //Create SpeechResult Object
      SpeechResult result;

      //Checking if recognizer has recognized the speech
      while ((result = recognize.getResult()) != null) {
          //Get the recognize speech
          String command = result.getHypothesis();
        //Match recognized speech with our commands
         System.out.println(command);
      }
      
    }

}

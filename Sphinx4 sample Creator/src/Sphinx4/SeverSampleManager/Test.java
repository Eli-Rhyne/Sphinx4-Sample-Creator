package Sphinx4.SeverSampleManager;

import java.io.File;
import java.util.ArrayList;

public class Test {
	
	private static String username = "earhyne";
	private static String password = "Rockstar125!";
	private static String ip = "192.168.0.15";
	private static SSHManager manager;
	private static String sampleDir = "/home/earhyne/speech_recognition/samples";
	private static String programDir = "/home/earhyne/speech_recognition/mfc_adapting/";
	private static ArrayList<File> files = new ArrayList<File>();
	private static String sampleName = "Test";
	
	public static void main(String[] args) {
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test.fileids"));
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test.transcription"));
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test_01.wav"));
		manager = new SSHManager(username, password, ip, "");
		String error = manager.connect();
		if(error != null) {System.out.println(error);}
		manager.sendCommand("cd /home/earhyne/speech_recognition/samples");
		String temp = manager.sendCommand("ls /home/earhyne/speech_recognition/samples/");
		String name = "sample_set_";
		String[] countCheck = temp.split(name);
		int max = 0;
		for(String s: countCheck) {
			s = s.trim();
			if(s.contains("models")) {
				s = "0";
			}
			if(max<Integer.parseInt(s)) {
				max = Integer.parseInt(s);
			}
		}
		
		sampleDir = sampleDir + "/" + (name + ++max);
		System.out.println(manager.sendCommand("mkdir " + sampleDir));

		for(File f: files) {
			manager.upload(f, sampleDir);
		}
		manager.sendCommand("sphinx_fe "
				+ "-argfile /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/feat.params "
				+ "-samprate 16000 "
				+ "-c " + sampleDir + "/" + sampleName + ".fileids "
				+ "-di " + sampleDir + " "
				+ "-do " + sampleDir + "/" + sampleName + "_adaption "
				+ "-ei wav "
				+ "-eo mfc "
				+ "-mswav yes");
		manager.sendCommand("mkdir " + sampleDir + "/" + sampleName + "_adaption");
		manager.sendCommand("mkdir " + sampleDir + "/" + sampleName + "_adaption/adaption_files");
		System.out.println(manager.sendCommand(programDir + "bw " + 
				"-hmmdir /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2 " + 
				"-moddeffn /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/mdef.txt " + 
				"-ts2cbfn .ptm. " + 
				"-feat 1s_c_d_dd " + 
				"-cepdir " +sampleDir + "/" + sampleName +"_adaption " +
				"-svspec 0-12/13-25/26-38 " + 
				"-cmn current " + 		
				"-agc none " + 
				"-dictfn /home/earhyne/speech_recognition/samples/models/cmudict-en-us.dict " + 
				"-ctlfn " + sampleDir +"/" + sampleName + ".fileids " + 
				"-lsnfn " + sampleDir +"/" + sampleName + ".transcription " +
				"-accumdir " + sampleDir + "/" + sampleName +"_adaption/adaption_files"));
		
		System.out.println(manager.sendCommand(programDir + "mllr_solve " + 
				"-meanfn /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/means " + 
				"-varfn /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/variances " + 
				"-outmllrfn "+  sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
				"-accumdir " + sampleDir + "/" + sampleName +"_adaption/adaption_files"));
		
		System.out.println(manager.sendCommand(programDir + "mllr_transform " + 
				"-inmeanfn /home/earhyne/speech_recognition/samples/models/old_acoustic_models/cmusphinx-en-us-ptm-5.2/means " + 
				"-varfn /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/variances " + 
				"-mllrmat "+  sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
				"-outmeanfn /home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/means"));
		
		System.out.println("Done");
	}

}

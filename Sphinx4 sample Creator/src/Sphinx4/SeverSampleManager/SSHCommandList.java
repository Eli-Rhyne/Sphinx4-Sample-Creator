package Sphinx4.SeverSampleManager;

import java.util.ArrayList;
import java.io.File;

public class SSHCommandList {
//	private int port = 22;
	private String username = "earhyne";
	private String password = "Rockstar125!";
	private String ip = "192.168.0.15";
	private SSHManager manager;
	private ArrayList<File> files;
	private String sampleName;
	private String sampleDir = "/home/earhyne/speech_recognition/samples";
	private String currentModelDir = "/home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/";
	private String programDir = "/home/earhyne/speech_recognition/mfc_adapting/";
	
	public SSHCommandList(ArrayList<File> files, String sampleName){
		manager = new SSHManager(username, password, ip, "");
		this.files = files;
		this.sampleName = sampleName;
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
	}
	
	public void MLLRAdaption() {
		
		manager.sendCommand("sphinx_fe "
				+ "-argfile " + currentModelDir + "feat.params "
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
				"-hmmdir " + currentModelDir + " " + 
				"-moddeffn " + currentModelDir + "mdef.txt " + 
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
				"-meanfn " + currentModelDir + "means " + 
				"-varfn " + currentModelDir + "variances " + 
				"-outmllrfn "+  sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
				"-accumdir " + sampleDir + "/" + sampleName +"_adaption/adaption_files"));
		
		System.out.println(manager.sendCommand(programDir + "mllr_transform " + 
				"-inmeanfn /home/earhyne/speech_recognition/samples/models/old_acoustic_models/cmusphinx-en-us-ptm-5.2/means " + 
				"-varfn " + currentModelDir + "variances " + 
				"-mllrmat "+  sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
				"-outmeanfn " + currentModelDir + "means"));
		
		System.out.println("Done");
	}
	
	public void MapAdaptation() {
		
	}
}

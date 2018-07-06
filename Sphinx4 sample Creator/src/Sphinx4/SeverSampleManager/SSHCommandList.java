package Sphinx4.SeverSampleManager;

import java.io.File;
import java.util.ArrayList;

public class SSHCommandList {
	private String username = "earhyne";
	private String password = "Rockstar125!";
	private String ip = "192.168.0.15";
	private SSHManager manager;
	private ArrayList<File> files;
	private String sampleName;
	private String sampleDir = "/home/earhyne/speech_recognition/samples";
	private String currentModelDir = "/home/earhyne/speech_recognition/samples/models/cmusphinx-en-us-ptm-5.2/";
	private String programDir = "/home/earhyne/speech_recognition/mfc_adapting/";
	private boolean mllrAdapt = false;
	private boolean mapAdapt = false;
	private String name = "sample_";
	
	/*
	 * Object to interface with the sever
	 */
	public SSHCommandList(String sampleName, ArrayList<File> files){
		manager = new SSHManager(username, password, ip, "");
		this.files = files;
		this.sampleName = sampleName;
		manager = new SSHManager(username, password, ip, "");
		String error = manager.connect();
		if(error != null) {System.out.println(error);}
	}
	
	/*
	 * Setup for the class
	 * @return <CODE> true </CODE if the setup is successful,
	 * <CODE>false</CODE> otherwise 
	 */
	public boolean setup() {
		String temp = manager.sendCommand("ls /home/earhyne/speech_recognition/samples/");
		if(!temp.contains(name + sampleName) && !mllrAdapt && !mapAdapt) {
			sampleDir = sampleDir + "/" + (name + sampleName);
			System.out.println(manager.sendCommand("mkdir " + sampleDir));
	
			for(File f: files) {
				manager.upload(f, sampleDir);
			}
			return true;
		}
		return false;
	}
	
	public boolean ifMapAdapt() {
		return mapAdapt;
	}
	
	public boolean ifMllrAdapt() {
		return mllrAdapt;
	}
	
	public void changeModel(String modelDir){
		String[] temp = manager.sendCommand("ls /home/earhyne/speech_recognition/samples/models/old_acoustic_models").split("\n");
		System.out.println(manager.sendCommand("cp -a " + currentModelDir.substring(0, currentModelDir.length()-1) + " " + currentModelDir.substring(0, currentModelDir.length()-24) + "old_acoustic_models/" + (temp.length+1)));
		System.out.println(manager.sendCommand("rm -r " + currentModelDir.substring(0, currentModelDir.length()-1)));
		System.out.println(manager.sendCommand("cp -a " + modelDir + " " + currentModelDir.substring(0, currentModelDir.length()-1)));
	}
	
	public String saveModel(){
		String[] temp = manager.sendCommand("ls /home/earhyne/speech_recognition/samples/models/old_acoustic_models").split("\n");
		System.out.println(manager.sendCommand("cp -a " + currentModelDir.substring(0, currentModelDir.length()-1) + " " + currentModelDir.substring(0, currentModelDir.length()-24) + "old_acoustic_models/" + (temp.length+1)));
		return currentModelDir.substring(0, currentModelDir.length()-23) + "old_acoustic_models/" + (temp.length+1);
	}
	
	public void MapAllSamples(){
		String tempSampleName = sampleName;
		String tempSampleDir = sampleDir;
		
		System.out.println(manager.sendCommand("mkdir /home/earhyne/speech_recognition/samples/generic_adaption_files/files"));
		sampleDir = "/home/earhyne/speech_recognition/samples/generic_adaption_files/files";
		
		System.out.println(manager.sendCommand("mkdir " + sampleDir));
		String[] temp = manager.sendCommand("ls /home/earhyne/speech_recognition/samples").split("\n");
		for(String s: temp) {
			s = s.trim();
		}
		
		sampleDir = tempSampleDir;
		sampleName = tempSampleName;
	}
	
	public void commonAdapt() {
		sampleDir = "/home/earhyne/speech_recognition/samples/"+ name + sampleName;
		System.out.println(manager.sendCommand("sphinx_fe "
			+ "-argfile " + currentModelDir + "feat.params "
			+ "-samprate 16000 "
			+ "-c " + sampleDir + "/" + sampleName + ".fileids "
			+ "-di " + sampleDir + " "
			+ "-do " + sampleDir + "/" + sampleName + "_adaption "
			+ "-ei wav "
			+ "-eo mfc "
			+ "-mswav yes"));
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
	}
	
	public void MapAdaptation() {
		if(!mllrAdapt) {
			commonAdapt();
		}
		String tempDir = saveModel();
		System.out.println(manager.sendCommand("mkdir /home/earhyne/speech_recognition/samples/generic_adaption_files/map_adapt"));
		System.out.println(manager.sendCommand(programDir + "/map_adapt " +
			"-moddeffn " + currentModelDir + "mdef.txt " + 
			"-ts2cbfn .ptm. " + 
			"-meanfn " + tempDir + "means " + 
			"-varfn " + tempDir + "variances " + 
			"-mixwfn " + tempDir + "mixture_weights " + 
			"-tmatfn " + tempDir + "transition_matrices " + 
			"-accumdir /home/earhyne/speech_recognition/samples/generic_adaption_files/map_adapt" + 
			"-mapmeanfn " + currentModelDir + "means " + 
			"-mapvarfn " + currentModelDir + "variances " + 
			"-mapmixwfn " + currentModelDir + "mixture_weights " + 
			"-maptmatfn " + currentModelDir + "transition_matrices"));
		mapAdapt = true;
	}
	
	public void MLLRAdaption() {
		if(!mapAdapt) {		
			commonAdapt();
		}
		System.out.println(manager.sendCommand(programDir + "mllr_solve " + 
				"-meanfn " + currentModelDir + "means " + 
				"-varfn " + currentModelDir + "variances " + 
				"-outmllrfn "+  sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
				"-accumdir " + sampleDir + "/" + sampleName + "_adaption/adaption_files"));
		
		System.out.println(manager.sendCommand(programDir + "mllr_transform " + 
			"-inmeanfn "+ currentModelDir + "means " + 
			"-varfn " + currentModelDir + "variances " + 
			"-mllrmat " + sampleDir + "/" + sampleName + "_adaption/adaption_files/mllr_matrix " + 
			"-outmeanfn " + currentModelDir + "means"));

		
		
		System.out.println("Done with MLLR");
		mllrAdapt = true;
	}
	public void end() {
		manager.close();
	}
	
}

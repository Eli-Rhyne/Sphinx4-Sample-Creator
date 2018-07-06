package Sphinx4.SeverSampleManager;

import java.io.File;
import java.util.ArrayList;

public class Test {
	

	private static ArrayList<File> files = new ArrayList<File>();
	private static String sampleName = "Name";	
	public static void main(String[] args) {
		

		SSHCommandList ssh = new SSHCommandList(sampleName, files);
		ssh.setup();
		ssh.MLLRAdaption();
			

		ssh.saveModel();
		ssh.end();
	}

}

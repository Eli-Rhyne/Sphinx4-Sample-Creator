package Sphinx4.SeverSampleManager;

import java.io.File;
import java.util.ArrayList;

public class Test {
	

	private static ArrayList<File> files = new ArrayList<File>();
	private static String sampleName = "Test";	
	public static void main(String[] args) {
		
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test.fileids"));
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test.transcription"));
		files.add(new File("/Users/earhy/OneDrive/Documents/Samples/Test_01.wav"));
		SSHCommandList ssh = new SSHCommandList(sampleName, files);
		if(ssh.setup()) {
			ssh.MLLRAdaption();
			
		}
		ssh.saveModel();
		ssh.end();
	}

}

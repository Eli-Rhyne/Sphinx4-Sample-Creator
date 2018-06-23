package Sphinx4.SampleCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SampleFormater {
	private static Writer transcription;
	private static Writer fileids;
	
	SampleFormater(String sampleName, String directory) {
		
		try {
			File fileFileids = new File(directory + File.separator + sampleName + ".fileids");
			File fileTranscription  = new File(directory + File.separator + sampleName + ".transcription");
			fileFileids.createNewFile();
			fileTranscription.createNewFile();
			
			transcription = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTranscription)));
			fileids = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFileids)));	
		}
		
		catch(IOException ex){ex.printStackTrace();}
	}

	public static void newSample(int count, String text, String name) {
		try {
			transcription.write("<s> " + text + " </s> " + "(" + name + intFormat(count) + ")\n");
			fileids.write(name + intFormat(count) + "\n");
			
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void closeWriters() {
		try {
			transcription.close();
			fileids.close();
		}
		catch (IOException e) {e.printStackTrace();}
		
	}
	
	public static File wavFormat(String name, String directory, int count) {
		File temp = new File(directory + File.separator + name + intFormat(count) + ".wav");
		try {
			temp.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	private static String intFormat(int i) {
		String out = new String("_");
		for(int j = 1000; j>i; j=j%10) {
			out = out + 0;
		}
		return (String)out+i;
	}
}


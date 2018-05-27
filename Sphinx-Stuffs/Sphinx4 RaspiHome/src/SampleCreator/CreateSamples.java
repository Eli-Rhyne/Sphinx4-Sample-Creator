package SampleCreator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class CreateSamples extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private boolean recording = false;
	private int sampleCount = 0;
	private JTextField txtStatusWaiting;
	private recordingThread record;
	private Thread rt;
	/**
	 * Create the frame.
	 */
	public CreateSamples(String name, String directory) {
		SampleFormater sample = new SampleFormater(name, directory);

		setTitle("Recording Samples");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1086, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblTextToBe = new JLabel("Text to be recorded:");
		
		JButton start = new JButton("Start/Stop");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recording) {
					recording = false;
					record.stop();
					txtStatusWaiting.setText("Status: Waiting");
					textField.setText("");
				}
				else {
					recording = true;
					SampleFormater.newSample(++sampleCount, textField.getText(), name);
					record = new recordingThread(name, directory, sampleCount);
					rt = new Thread(record);
					rt.setDaemon(true);
					rt.start();
					txtStatusWaiting.setText("Status: Recording");
				}
			}
		});
		start.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		JLabel lblEnterTheText = new JLabel("Enter the text to be recorded. When ready press the start stop button to begin or end recording.");
		
		txtStatusWaiting = new JTextField();
		txtStatusWaiting.setEditable(false);
		txtStatusWaiting.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtStatusWaiting.setText("Status: Waiting");
		txtStatusWaiting.setSelectedTextColor(Color.green);
		txtStatusWaiting.setColumns(10);
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sample.closeWriters();
				System.exit(0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(64)
							.addComponent(lblEnterTheText, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGap(385)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(start, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
										.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(txtStatusWaiting, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 257, Short.MAX_VALUE))))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblTextToBe)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 862, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(84, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterTheText, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(lblTextToBe))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtStatusWaiting, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(start, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	private class recordingThread implements Runnable {
		
		private String name;
		private String directory;
		private int sampleCount;
		final AudioUtil recorder = new AudioUtil();
		
		recordingThread(String nameString, String direc, int count){
			this.name = nameString;
			this.directory = direc;
			this.sampleCount = count;
		}
		@Override
		public void run() {
			try {recorder.start();} catch (LineUnavailableException e) {e.printStackTrace();}
		}
		public void stop() {
			try {
	            recorder.stop();
	            recorder.save(SampleFormater.wavFormat(name, directory, sampleCount));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}

		
	}
}
	
	

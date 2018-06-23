package Sphinx4.SampleCreator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;


import javax.swing.JFileChooser;

import java.awt.CardLayout;
import java.awt.Toolkit;

import javax.swing.JLayeredPane;
import javax.swing.JTextField;

public class SampleCreatorWindow {

	private JFrame frmSampleCreator;
	private JTextField sampleName;
	private String name = "Name", direct = "Directory";
	private JTextField txtDirectory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SampleCreatorWindow window = new SampleCreatorWindow();
					window.frmSampleCreator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SampleCreatorWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frmSampleCreator = new JFrame();
		frmSampleCreator.setTitle("Sample Creator");
		frmSampleCreator.setSize(new Dimension(700, 200));
		frmSampleCreator.setLocation(screenSize.width/2-300,screenSize.height/2-100);
		frmSampleCreator.getContentPane().setLayout(new CardLayout(0, 0));
		
		JLayeredPane selection = new JLayeredPane();
		selection.setBackground(Color.LIGHT_GRAY);
		frmSampleCreator.getContentPane().add(selection, "name_129430619478258");
		
		sampleName = new JTextField();
		sampleName.setText(name);
		sampleName.setBackground(Color.LIGHT_GRAY);
		sampleName.setBounds(113, 8, 392, 20);
		selection.add(sampleName);
		sampleName.setColumns(10);
		
		JButton btnBeginSampleCreation = new JButton("Begin Sample Creation");
		btnBeginSampleCreation.setBackground(Color.LIGHT_GRAY);
		btnBeginSampleCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSampleCreator.dispose();
				CreateSamples samples = new CreateSamples(sampleName.getText(),txtDirectory.getText());
				samples.setVisible(true);
			}
		});
		btnBeginSampleCreation.setBounds(225, 84, 214, 46);
		selection.add(btnBeginSampleCreation);
		
		JLabel lblNameOfSamples = new JLabel("Name of Samples:");
		lblNameOfSamples.setBounds(10, 11, 93, 14);
		selection.add(lblNameOfSamples);
		
		JLabel lblDirectory = new JLabel("Directory:");
		lblDirectory.setBounds(47, 36, 56, 14);
		selection.add(lblDirectory);
		
		JButton fileChooser = new JButton("...");
		fileChooser.setBounds(469, 39, 36, 23);
		selection.add(fileChooser);
		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				 
		        // For Directory
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 
		        // For File
		        //fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 
		        fileChooser.setAcceptAllFileFilterUsed(false);
		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          direct = fileChooser.getSelectedFile().toString();
		          txtDirectory.setText(fileChooser.getSelectedFile().toString());
		        }
			}
		});
		
		txtDirectory = new JTextField();
		txtDirectory.setText(direct);
		txtDirectory.setBackground(Color.LIGHT_GRAY);
		txtDirectory.setBounds(113, 39, 346, 20);
		selection.add(txtDirectory);
		txtDirectory.setColumns(10);
	}
	
}

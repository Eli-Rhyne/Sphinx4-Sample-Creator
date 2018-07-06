package Sphinx4.SampleCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Sphinx4.SeverSampleManager.SSHCommandList;

public class SSHController extends JFrame {

	private JPanel contentPane;
	private SSHCommandList ssh = new SSHCommandList(SampleFormater.files.get(0).getName().replace(".transcription", ""), SampleFormater.files);

	/**
	 * Create the frame.
	 */
	public SSHController() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCheckBox chckbxSaveOldModel = new JCheckBox("Save Old Model");
		chckbxSaveOldModel.setSelected(true);
		chckbxSaveOldModel.setToolTipText("Remotely save old model");
		chckbxSaveOldModel.setBounds(265, 211, 126, 23);
		contentPane.add(chckbxSaveOldModel);
		
		JCheckBox chckbxDownloadModel = new JCheckBox("Download Model");
		chckbxDownloadModel.setToolTipText("Downloads updated model");
		chckbxDownloadModel.setBounds(265, 177, 126, 23);
		contentPane.add(chckbxDownloadModel);
		
		JCheckBox chckbxMapAdapt = new JCheckBox("MAP Adapt");
		chckbxMapAdapt.setToolTipText("Use for large sets of data");
		chckbxMapAdapt.setBounds(265, 109, 126, 23);
		contentPane.add(chckbxMapAdapt);
		
		JCheckBox chckbxMllrAdapt = new JCheckBox("MLLR Adapt");
		chckbxMllrAdapt.setToolTipText("Use for small amounts of data");
		chckbxMllrAdapt.setBounds(265, 143, 126, 23);
		contentPane.add(chckbxMllrAdapt);
		
		JButton btnUploadNewModel = new JButton("Upload new Model");
		btnUploadNewModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO upload new model
			}
		});
		btnUploadNewModel.setBounds(43, 109, 171, 23);
		contentPane.add(btnUploadNewModel);
		
		JButton btnDownloadCurrentModel = new JButton("Download Current Model");
		btnDownloadCurrentModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO download Current Model
			}
		});
		btnDownloadCurrentModel.setBounds(431, 109, 171, 23);
		contentPane.add(btnDownloadCurrentModel);
		
		JLabel lblActionsToBe = new JLabel("Actions to be preformed:");
		lblActionsToBe.setBounds(10, 23, 133, 14);
		contentPane.add(lblActionsToBe);
		
		JButton btnUploadDictionary = new JButton("Upload New Dictionary");
		btnUploadDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO upload new dictionary
			}
		});
		btnUploadDictionary.setToolTipText("Upload dictionary and language model");
		btnUploadDictionary.setBounds(43, 143, 171, 23);
		contentPane.add(btnUploadDictionary);
		
		JButton btnUploadNewLanguage = new JButton("Upload New Language Model");
		btnUploadNewLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO upload new Language Model
			}
		});
		btnUploadNewLanguage.setBounds(43, 177, 171, 23);
		contentPane.add(btnUploadNewLanguage);
		
		JButton btnDownloadDictionary = new JButton("Download Dictionary");
		btnDownloadDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Download Dictionary
			}
		});
		btnDownloadDictionary.setBounds(431, 143, 171, 23);
		contentPane.add(btnDownloadDictionary);
		
		JButton btnDownloadLanguageModel = new JButton("Download Language Model");
		btnDownloadLanguageModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Download current LM
			}
		});
		btnDownloadLanguageModel.setBounds(431, 177, 171, 23);
		contentPane.add(btnDownloadLanguageModel);
		
		JButton btnDownloadPackage = new JButton("Download Package");
		btnDownloadPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Download all
			}
		});
		btnDownloadPackage.setToolTipText("Downloads all");
		btnDownloadPackage.setBounds(431, 211, 171, 23);
		contentPane.add(btnDownloadPackage);
		
		JButton btnBeginAdaption = new JButton("Begin Adaption");
		btnBeginAdaption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Start adaptation according to parameters
			}
		});
		btnBeginAdaption.setBounds(265, 242, 105, 23);
		contentPane.add(btnBeginAdaption);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(253, 297, 133, 80);
		contentPane.add(btnExit);
	}
}

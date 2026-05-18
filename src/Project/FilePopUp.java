package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FilePopUp extends JFrame{
	
	public FilePopUp() throws HeadlessException {
		while (true) {
			JFileChooser filechooser = new JFileChooser();
			
			
			int response = filechooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File selectedFile = filechooser.getSelectedFile();
			    String path = selectedFile.getAbsolutePath();
			    
			    AudioPlayer.playlist.clear();
			    AudioPlayer.playlist.add(path);
			    AudioPlayer.currentIndex = 0;
				
					try {
						new AudioPlayer(path, null);
						break;
					} catch (UnsupportedAudioFileException e1) {
						JOptionPane.showMessageDialog(null, "Please choose a .wav file");
						continue;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}	
			else if(response == JFileChooser.CANCEL_OPTION) {
				System.exit(0);
			}
		}
	}

}

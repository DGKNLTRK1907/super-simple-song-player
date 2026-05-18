package Project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class SettingsPopUp extends JFrame implements ActionListener {
	
	JButton setpath;
	JButton setcolor;
	JButton setSliderColor;
	AudioPlayer update;
	Color color;
	
	public SettingsPopUp(AudioPlayer current) throws HeadlessException {
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		update = current;
		this.setLayout(new FlowLayout());
		
		setpath = new JButton("Set file path");
		setpath.addActionListener(this);
		
		setcolor = new JButton("Set background color");
		setcolor.addActionListener(this);
		
		setSliderColor = new JButton("Set volume slider color");
		setSliderColor.addActionListener(this);
		
		this.add(setpath);
		this.add(setcolor);
		this.add(setSliderColor);
		
		this.setTitle("Settings");
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == setpath) {
			while(true) {
				JFileChooser filechooser = new JFileChooser();
				
				int response = filechooser.showOpenDialog(null);
				
				if(response == JFileChooser.APPROVE_OPTION) {
					
					try {
						String path = filechooser.getSelectedFile().getAbsolutePath();
			            
						if (path.endsWith(".wav")) {
							 // 1. Listeye ekle
				            AudioPlayer.playlist.add(path);
				            
				            AudioPlayer.currentIndex = AudioPlayer.playlist.size() - 1;
				            update.loadMusic(path);
				            AudioPlayer.music.start();
				            
				            AudioPlayer.playButton.setText("Stop");
				            AudioPlayer.playButton.setBackground(Color.RED);
						}
						else {
							JOptionPane.showMessageDialog(null, "Please choose a .wav file");
							continue;
						}
						
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
					break;
				}
			}
		}
		else if(e.getSource() == setcolor) {
			update.changeColor(JColorChooser.showDialog(null, "Pick a color", Color.black));
		}
		else if(e.getSource() == setSliderColor) {
			update.changeSliderColor(JColorChooser.showDialog(null, "Pick a color", Color.black));
		}
	}

}

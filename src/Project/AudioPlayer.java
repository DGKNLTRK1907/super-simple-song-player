package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class AudioPlayer {
	static String filepath;
	static boolean looped = false;
	static Clip music;
	
	JFrame a;
	
	JPanel panel;
	
	static JButton playButton;
	JButton resetButton;
	JButton loopButton;
	JButton settings;
	JButton backward10Sec;
	JButton forward10Sec;
	
	JLabel h;
	
	static Timer progressTimer;
	
	static JProgressBar bar = new JProgressBar();
	
	static JSlider volumeSlider = new JSlider(0, 100, 50);
	
	static FloatControl gainControl;
	
	public static ArrayList<String> playlist = new ArrayList<>();
	static int currentIndex = 0;
	
	int tmins;
	int tsecs;
	
	public AudioPlayer(String filepath, Color backg) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File s = new File(filepath);
		
		System.out.println(filepath);
		AudioInputStream c = AudioSystem.getAudioInputStream(s);
		music = AudioSystem.getClip();
		music.open(c);
		
		
		
		a = new JFrame();
		
		panel = new JPanel();
		
		playButton = new JButton();
		resetButton = new JButton();
		loopButton = new JButton();
		settings = new JButton();
		backward10Sec = new JButton();
		forward10Sec = new JButton();
		
		h = new JLabel();

		h.setVisible(true);
		h.setText("Super Simple Song Player(SSSP)");
		h.setBounds(100, 20, 200, 100);
		h.setForeground(Color.black);

		playButton.setBounds(130, 300, 75, 75);
		playButton.setText("Play");
		playButton.setBackground(Color.GREEN);
		playButton.setOpaque(true);
		playButton.setFocusPainted(false);
		
		//Action for play button
		playButton.addActionListener(e -> {
			if (music.isRunning()) {
				music.stop();
				progressTimer.stop();
				playButton.setText("Play");
				playButton.setBackground(Color.GREEN);
			} else {
				music.start();
				progressTimer.start();
				playButton.setText("Stop");
				playButton.setBackground(Color.RED);
			}
		});

		resetButton.setBounds(300, 300, 75, 75);
		resetButton.setText("Reset");
		resetButton.setBackground(Color.blue);
		resetButton.setOpaque(true);
		resetButton.setFocusPainted(false);
		
		//Action for reset button
		resetButton.addActionListener(e -> {
			music.setMicrosecondPosition(0);
			bar.setValue(0);
			bar.setString(String.format("%02d:%02d / %02d:%02d", tmins, tsecs, 0, 0));
		});
		
		loopButton.setBounds(204, 400, 100, 35);
		loopButton.setText("Loop: OFF");
		loopButton.setBackground(Color.red);
		loopButton.setOpaque(true);
		loopButton.setFocusPainted(false);
		
		//Action for loop button
		loopButton.addActionListener(e -> {
			if(looped == false) {
				loopButton.setText("Loop: ON");
				loopButton.setBackground(Color.green);
				looped = true;
			}
			else {
				loopButton.setText("Loop: OFF");
				loopButton.setBackground(Color.red);
				looped = false;
			}
			
		});
		
		settings.setBounds(0, 465, 100, 35);
		settings.setText("Settings");
		settings.setBackground(Color.cyan);
		settings.setOpaque(true);
		settings.setFocusPainted(false);
		
		//Action for settings
		settings.addActionListener(e -> {
			new SettingsPopUp(this);
		});
		
		backward10Sec.setBounds(30, 345, 60, 40);
		backward10Sec.setText("<10");
		backward10Sec.setBackground(Color.GRAY);
		backward10Sec.setOpaque(true);
		backward10Sec.setFocusPainted(false);
		
		//Action for Backwards 10 seconds
		backward10Sec.addActionListener(e -> {
			long newVal = music.getMicrosecondPosition() - 10000000;
			
			if (newVal < 0) {
				newVal = 0;
			}
			
			music.setMicrosecondPosition(newVal);
		});
		
		forward10Sec.setBounds(400, 345, 60, 40);
		forward10Sec.setText("10>");
		forward10Sec.setBackground(Color.GRAY);
		forward10Sec.setOpaque(true);
		forward10Sec.setFocusPainted(false);
		
		//Action for Forward 10 seconds
		forward10Sec.addActionListener(e -> {
			long newVal = music.getMicrosecondPosition() + 10000000;
			
			if (newVal > music.getMicrosecondLength()) {
				newVal = music.getMicrosecondLength();
			}
			
			music.setMicrosecondPosition(newVal);
		});
		
		bar.setValue(0);
		bar.setBounds(40, 200, 420, 50);
		bar.setStringPainted(true);
		bar.setFont(new Font("MV Boli", Font.BOLD, 25));
		bar.setForeground(Color.green);
		bar.setBackground(Color.black);
		bar.setString(String.format("%02d:%02d / %02d:%02d", ((music.getMicrosecondLength() / 1000000) / 60), ((music.getMicrosecondLength() / 1000000) % 60), 0, 0));
		
		volumeSlider.setOrientation(SwingConstants.HORIZONTAL);
		volumeSlider.setValue(100);
		volumeSlider.setBounds(375, 450, 125, 50);
		volumeSlider.setPaintTicks(true);
//		volumeSlider.setMinorTickSpacing(10);
		volumeSlider.setPaintTrack(true);
		volumeSlider.setMajorTickSpacing(25);
		volumeSlider.setPaintLabels(true);
		volumeSlider.setFont(new Font("MV Boli", Font.PLAIN, 15));
		
		a.setSize(500, 500);
		a.setTitle("Super Simple Song Player(SSSP)");
		a.getContentPane().setBackground(backg);
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setBackground(Color.black);
		panel.setLayout(null);
		panel.add(playButton);
		panel.add(resetButton);
		panel.add(h);
		panel.add(loopButton);
		panel.add(settings);
		panel.add(bar);
		panel.add(volumeSlider);
		panel.add(backward10Sec);
		panel.add(forward10Sec);
		a.add(panel);
		a.pack();
		a.setResizable(false);
//		a.setLayout(null);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setVisible(true);
		
		
		// Audio progress bar handler
		progressTimer = new Timer(100, e -> {
			if (music != null && music.isOpen()) {
		        long current = music.getMicrosecondPosition();
		        long total = music.getMicrosecondLength();
		        
		        int Totalsecs = (int) (total / 1000000);
		        tmins = Totalsecs / 60;
		        tsecs = Totalsecs % 60;
		        
		        int Currentsecs = (int) (current / 1000000);
		        int mins = Currentsecs / 60;
		        int secs = Currentsecs % 60;
		        
		        String TimeInfo = String.format("%02d:%02d / %02d:%02d", tmins, tsecs, mins, secs);
		        
		        if (total > 0) {
		            double percentage = (current * 100.0) / total;
		            bar.setValue((int) percentage);
		        }
		        
		        bar.setString(TimeInfo);
		    }
		});
		
		// Audio loop handler
		music.addLineListener(e -> {
			if(e.getType() == LineEvent.Type.STOP) {
				if(music.getMicrosecondPosition() >= music.getMicrosecondLength()) {
					if (looped /* && music.isOpen() */) {
		                music.setMicrosecondPosition(0);
		                music.start();
		            } 
					else {
						playNext();
//		                music.setMicrosecondPosition(0);
//		                playButton.setText("Play");
//		                playButton.setBackground(Color.GREEN);
		            }
				}
			}
			
		});
		
		// Volume slider listener
		volumeSlider.addChangeListener(e -> {
		    if (music != null) {
		        float volume = volumeSlider.getValue() / 100f;
		        
		        gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
	            
	            if (volume == 0) {
	                gainControl.setValue(gainControl.getMinimum());
	            }
	            else {
	                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
	                
	                if (dB < gainControl.getMinimum()) {
	                	 dB = gainControl.getMinimum();
	                }
	                
	                if (dB > gainControl.getMaximum()) {
	                	 dB = gainControl.getMaximum();
	                }
	                
	                gainControl.setValue(dB);
	            }
		    }
		});
	}
	
	public static void playNext() {
	    System.out.println("playNext tetiklendi. Mevcut sira: " + currentIndex + " Liste boyutu: " + playlist.size());
	    
	    if (currentIndex + 1 < playlist.size()) {
	        currentIndex++;
	        try {
	            loadMusic(playlist.get(currentIndex)); 
	            music.start();
	            playButton.setText("Stop");
	            playButton.setBackground(Color.RED);
	        } catch (Exception ex) { 
	            ex.printStackTrace(); 
	        }
	    } else {
	        System.out.println("Liste bitti, basa donuluyor...");
	        currentIndex = 0;
	        try {
	            loadMusic(playlist.get(currentIndex));
	            music.start();
	        } catch (Exception ex) { ex.printStackTrace(); }
	    }
	}

	public static void loadMusic(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	    System.out.println("Yukleniyor: " + path);
	    
	    if (music != null) {
	        music.stop();
	        music.flush(); 
	        music.close(); 
	    }

	    File s = new File(path);
	    AudioInputStream c = AudioSystem.getAudioInputStream(s);
	    music = AudioSystem.getClip();
	    music.open(c);
	    
	    filepath = path;
	    
	    try {
	        FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
	        float volume = volumeSlider.getValue() / 100f;
	        float dB = (float) (Math.log(Math.max(volume, 0.0001)) / Math.log(10.0) * 20.0);
	        gainControl.setValue(dB);
	    } catch (Exception e) { }

	    music.addLineListener(e -> {
	        if(e.getType() == LineEvent.Type.STOP) {
	            if(music.getMicrosecondPosition() >= music.getMicrosecondLength()) {
	                if (looped) {
	                    music.setMicrosecondPosition(0);
	                    music.start();
	                } 
	                else {
	                	playNext();
	                }
	            }
	        }
	    });
	}

    public void changeColor(Color c) {
        panel.setBackground(c);
        a.repaint();
    }
	
    public void changeSliderColor(Color c) {
    	volumeSlider.setBackground(c);
    	volumeSlider.repaint();
    }
}

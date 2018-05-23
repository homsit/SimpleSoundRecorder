import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class SoundProgram extends JFrame {
	SimpleAudioRecorder	recorder;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SoundProgram frame = new SoundProgram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SoundProgram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File	outputFile = new File("aufioFile.wav");
				AudioFormat	audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100.0F, 16, 2, 4, 44100.0F, false);
				DataLine.Info	info = new DataLine.Info(TargetDataLine.class, audioFormat);
				AudioFileFormat.Type	targetType = AudioFileFormat.Type.WAVE;
				TargetDataLine	targetDataLine = null;
				try{
					targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
					targetDataLine.open(audioFormat);
				}catch (LineUnavailableException ex){
					System.out.println("unable to get a recording line");
				}

				recorder = SimpleAudioRecorder.getInstance(targetDataLine,targetType,outputFile);
				recorder.start();
								
			}
		});
		btnStart.setBounds(60, 46, 117, 29);
		contentPane.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recorder.stopRecording();
			}
		});
		btnStop.setBounds(60, 87, 117, 29);
		contentPane.add(btnStop);
	}
}

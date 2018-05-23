import java.io.IOException;
import java.io.File;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;
public class SimpleAudioRecorder extends Thread{
	private static SimpleAudioRecorder instance=null;
	private TargetDataLine		m_line;
	private AudioFileFormat.Type	m_targetType;
	private AudioInputStream	m_audioInputStream;
	private File			m_outputFile;
	private SimpleAudioRecorder(TargetDataLine line, AudioFileFormat.Type targetType, File file)
	{
		m_line = line;
		m_audioInputStream = new AudioInputStream(line);
		m_targetType = targetType;
		m_outputFile = file;
	}
	public void start()
	{
		m_line.start();
		super.start();
	}
	public void stopRecording()
	{
		m_line.stop();
		m_line.close();
	}
	public void run()
	{
			try
			{
				AudioSystem.write(
					m_audioInputStream,
					m_targetType,
					m_outputFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	public static SimpleAudioRecorder getInstance(TargetDataLine line, AudioFileFormat.Type targetType, File file) {
		if(instance == null) {
	         instance = new SimpleAudioRecorder( line, targetType, file);
	      }
	      return instance;
	}
	
}

package apoSoccer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ApoSoccerProperties {

	private final String FILE_NAME = "properties.txt";
	
	private String homeAIPath;
	private String visitorAIPath;
	
	private boolean bBirdEyeView;
	private boolean bDebug;
	
	public ApoSoccerProperties() {
		this.homeAIPath = null;
		this.visitorAIPath = null;
		this.bBirdEyeView = false;
		this.bDebug = false;
	}
	
	public boolean loadProperties() {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(this.FILE_NAME));
			try {
				while (true) {
					String s = buffer.readLine();
					if (s.indexOf("HomeAI: ") != -1) {
						this.setHomeAIPath(s.replace("HomeAI: ", ""));
					} else if (s.indexOf("VisitorAI: ") != -1) {
						this.setVisitorAIPath(s.replace("VisitorAI: ", ""));
					} else if (s.indexOf("BirdEyeView: ") != -1) {
						String bool = s.replace("BirdEyeView: ", "");
						this.setBBirdEyeView(Boolean.parseBoolean(bool));
					} else if (s.indexOf("Debug: ") != -1) {
						String bool = s.replace("Debug: ", "");
						this.setBDebug(Boolean.parseBoolean(bool));
					}
				}
			} catch (Exception e) {
			}
			buffer.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean writeProperties() {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(this.FILE_NAME));
			try {
				buffer.write("//Properties for ApoSoccer//");
				buffer.newLine();
				if (this.homeAIPath != null) {
					buffer.write("HomeAI: "+this.getHomeAIPath());
					buffer.newLine();
				}
				if (this.visitorAIPath != null) {
					buffer.write("VisitorAI: "+this.getVisitorAIPath());
					buffer.newLine();
				}
				buffer.write("BirdEyeView: "+String.valueOf(this.isBBirdEyeView()));
				buffer.newLine();
				buffer.write("Debug: "+String.valueOf(this.isBDebug()));
				buffer.newLine();
				buffer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public String getHomeAIPath() {
		return homeAIPath;
	}

	public void setHomeAIPath(String homeAIPath) {
		this.homeAIPath = homeAIPath;
	}

	public String getVisitorAIPath() {
		return this.visitorAIPath;
	}

	public void setVisitorAIPath(String visitorAIPath) {
		this.visitorAIPath = visitorAIPath;
	}

	public boolean isBBirdEyeView() {
		return this.bBirdEyeView;
	}

	public void setBBirdEyeView(boolean birdEyeView) {
		this.bBirdEyeView = birdEyeView;
	}

	public boolean isBDebug() {
		return this.bDebug;
	}

	public void setBDebug(boolean debug) {
		this.bDebug = debug;
	}
	
}

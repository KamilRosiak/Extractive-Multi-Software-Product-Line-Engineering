package org.apogames.help;

import java.io.File;
import java.io.FilenameFilter;

public class ApoFileNameFilter implements FilenameFilter {

	private String extension;
	
	public ApoFileNameFilter( String extension ) {
		super();
		
		this.setExtension( extension );
	}

	public boolean accept(File f, String s) {
		return s.toLowerCase().endsWith( this.getExtension() );
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}

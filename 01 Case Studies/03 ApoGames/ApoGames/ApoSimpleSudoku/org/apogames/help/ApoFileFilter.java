package org.apogames.help;

import java.io.File;

/**
 * Klasse, um im OpenDialog nur bestimmte Dateien zu sehen und
 * nicht alle anderen Dateiformate
 * @author Dirk Aporius
 *
 */
public class ApoFileFilter extends javax.swing.filechooser.FileFilter 
{
	String s	= "";
	
	String[] aString;
	
	public ApoFileFilter( String s )
	{
		this.s		= s;
	}
	
	public ApoFileFilter( String[] aString ) {
		this.s = null;
		this.aString	= aString;
	}
	
	public String getLevelName(){
		if (this.s != null) {
			return "."+this.s;	
		} else if (this.aString != null) {
			String s = "";
			for (int i = 0; i < this.aString.length; i++) {
				s += this.aString[i];
			}
			return s;
		}
		return "";
	}
	
    public String getExtension(File file) 
    {
        String s = "";
        String s1 = file.getName();
        int i = s1.lastIndexOf(46);
        if(i > 0 && i < s1.length() - 1)
            s = s1.substring(i + 1).toLowerCase();
        return s;
    }

    /**
     * gibt zurück, welches Dateien angezeigt werden sollen
     * @return gibt zurück, welches Dateien angezeigt werden sollen
     */
    public String getDescription() 
    {
    	if ( this.s != null ) {
    		return "Nur "+this.s+"-Dateien";
    	} else if ( this.aString != null ) {
    		String s = "Nur ";
    		for ( int i = 0; i < this.aString.length; i++ ) {
    			s += this.aString[i];
    			if (i < this.aString.length - 1) {
    				s += ", ";
    			}
    		}
    		s += " Dateien";
    		return s;
    	}
    	return "";
    }

    /**
     * akzeptiert nur die Dateien, die er annehmen soll, alle anderen nimmt er nicht an
     */
    public boolean accept(File file) 
    {
        if(file.isDirectory())
            return true;
        String s = getExtension(file);
        if(s != null) {
        	if ( this.aString != null ) {
        		for ( int i = 0; i < this.aString.length; i++ ) {
        			if ( s.equals( this.aString[i] ) )
        				return true;
        		}
        	}
            return ((s.equals(this.s)));
        } else {
            return false;
        }
    }
        
    public ApoFileFilter() 
    {
    }
}

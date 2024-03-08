
/*
 * Copyright 2013 Marc Wiedenhoeft - GliblyBits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gliblybits.bitsengine.utils;

import java.io.IOException;
import java.io.PrintStream;

/**
 * BitsLog is a simple yet powerful static logger class that is used to write short log strings to a console or a file.
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsLog {
	
	/**
	 * The logger will log DEBUG, WARNING, INFO and ERROR messages.
	 */
	public static final int TYPE_DEBUG 		= 3;
	
	/**
	 * The logger will log WARNING, INFO and ERROR messages.
	 */
	public static final int TYPE_WARNING 	= 2;
	
	/**
	 * The logger will log INFO and ERROR messages.
	 */
	public static final int TYPE_INFO 		= 1;
	
	/**
	 * The logger will log ERROR messages.
	 */
	public static final int TYPE_NONE 		= 0;

	/**
	 * The current max. log type.
	 */
	private static int 		sLogType 		= TYPE_INFO;
	
	/**
	 * The current file that is used to store the log messages.<br>
	 * If this String is set to NULL, the logger won't store any log messages to a file.<br>
	 */
	public static PrintStream sLogWriter = null;
	
	private 
	BitsLog() {
		
	}
	
	/**
	 * This method sets the max. output type of log messages.<br>
	 * Type order:<br>
	 *  - TYPE_NONE<br>
	 *  - TYPE_INFO<br>
	 *  - TYPE_WARNING<br>
	 *  - TYPE_DEBUG<br>
	 *  <br>
	 *  Every type contains the types above itself.<br>
	 *  So, if the type is set to TYPE_WARNING the logger will also log TYPE_INFO messages.<br>
	 *  <br>
	 *  Keep in mind that error messages will always be logged!<br>  
	 * @param type - The max. message type.
	 */
	public static final void 
	setLogType( 
			final int type ) {
		
		if( type < TYPE_NONE || type > TYPE_DEBUG ) {
			throw new IllegalArgumentException("The given logging type must be TYPE_NONE/TYPE_INFO/TYPE_WARNING/TYPE_DEBUG!");
		}
		
		sLogType = type;
	}
	
	/**
	 * This method sets the file name for the logger.<br>
	 * @param file - The name of the internal private file that the logger will use to write the log messages to. If NULL the logger won't store any log messages into a file.
	 */
	public static final void 
	setLogFileName( 
			final String fileName ) {
		
		if( fileName == null ) {
			throw new NullPointerException("The given logging file name is Null!");
		}
		
		if( fileName.equals("") == true ) {
			throw new IllegalArgumentException("The given logging file name is empty!");
		}
		
		try {
			sLogWriter = new PrintStream( fileName );
		} catch (IOException e) {
			sLogWriter = null;
			e.printStackTrace();
		}
	}
	
	public static final void 
	closeLogFile( ) {
		
		if( sLogWriter != null ) {
			sLogWriter.close();
		}
	}

	/**
	 * This method logs an information message.<br>
	 * 
	 * @param location - The class and/or method name the message is coming from. 
	 * @param message - The log message.
	 */
	public static final void 
	i(
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if(sLogType >= TYPE_INFO) {
			if( sLogWriter != null ) {
				sLogWriter.println( "INFO: " + location + " -> " + message );
				sLogWriter.flush();
			} else {
				android.util.Log.i(location, message);				
			}
		}
	}
	
	/**
	 * This method logs a debug message.<br>
	 * 
	 * @param location - The class and/or method name the message is coming from. 
	 * @param message - The log message.
	 */	
	public static final void 
	d(
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if(sLogType == TYPE_DEBUG) {
			if( sLogWriter != null ) {
				sLogWriter.println( "DEBUG: " + location + " -> " + message );
				sLogWriter.flush();
			} else {
				android.util.Log.d(location, message);				
			}
		}
	}

	/**
	 * This method logs a warning message.<br>
	 * 
	 * @param location - The class and/or method name the message is coming from. 
	 * @param message - The log message.
	 */
	public static final void 
	w(
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if(sLogType >= TYPE_WARNING) {
			if( sLogWriter != null ) {
				sLogWriter.println( "WARNING: " + location + " -> " + message );
				sLogWriter.flush();
			} else {
				android.util.Log.w(location, message);
			}
		}
	}
	
	public static final void
	w(
			final Exception e,
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if(sLogType >= TYPE_WARNING) {
			if( sLogWriter != null ) {
				e.printStackTrace(sLogWriter);
			} else {
				android.util.Log.e(location, message, e);
			}
		}
	}

	/**
	 * This method logs an error message.<br>
	 * 
	 * @param location - The class and/or method name the message is coming from. 
	 * @param message - The log message.
	 */
	public static final void 
	e(
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if( sLogWriter != null ) {
			sLogWriter.println( "ERROR: " + location + " -> " + message);
			sLogWriter.flush();
		} else {
			android.util.Log.e(location, message);
		}
	}
	
	public static final void
	e(
			final Exception e,
			final String location, 
			final String message) {
		
		if( location == null ) {
			throw new NullPointerException("The given location String is Null!");
		}
		
		if( message == null ) {
			throw new NullPointerException("The given message String is Null!");
		}
		
		if( sLogWriter != null ) {
			e.printStackTrace(sLogWriter);
		} else {
			android.util.Log.e(location, message, e);
		}
	}
}

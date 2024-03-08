
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

package net.gliblybits.bitsengine.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsZip {
	
	private static final int  BUFFER_SIZE = 4096;

  	private static final void 
  	extractFile(
  			final ZipInputStream in, 
  			final String outDir, 
  			final String name) 
  	throws IOException {
    
  		final byte[] buffer = new byte[BUFFER_SIZE];
  		final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outDir,name)));
    	int count = -1;
    	while ((count = in.read(buffer)) != -1) {
    		out.write(buffer, 0, count);
    	}
    	out.close();
  	}

  	private static final String 
  	dirPart(
  			final String name) {
  		
  		final int s = name.lastIndexOf( File.separatorChar );
    	return s == -1 ? null : name.substring( 0, s );
  	}

   /**
   	* Extract zipfile to outdir with complete directory structure
   	* @param zipfile Input .zip file
   	* @param outdir Output directory
   	*/
  	public static final boolean 
  	extract(
		  final String zipFile, final String outDir ) {
  		
	  try {
		  final ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
		  ZipEntry entry;
		  String name, dir;
		  
		  while( (entry = zin.getNextEntry()) != null ) {
			  name = entry.getName();
			  if( entry.isDirectory() == true ) {
				  final File f = new File( outDir, name );
				  if( f.exists() == false ) {
					  f.mkdirs();
				  }
				  continue;
			  }
			  
			  dir = dirPart( name );
			  if( dir != null ) {
				  final File f = new File( outDir, name );
				  if( f.exists() == false ) {
					  f.mkdirs();
				  }
			  }

			  extractFile(zin, outDir, name);
		  }
		  zin.close();
	  } catch (IOException e) {
		  BitsLog.e( e, "BitsZip - extract", "Unable to extract the zip file: " + zipFile + " to " + outDir);
		  return false;
	  }
	  return true;
  }
  
  	public static boolean 
  	exists( 
  			final String zipFile, 
  			final String file ) {
  		
  		try {
            //open the source zip file
            final ZipFile sourceZipFile = new ZipFile( zipFile );
            if( sourceZipFile.getEntry(file) == null ) {
            	sourceZipFile.close();
            	return false;
            } else {
            	sourceZipFile.close();
            	return true;
            }
        }
        catch( Exception e ) {
        	BitsLog.e( e, "BitsZip - fileExists", "Unable to open the zip file: " + zipFile );
        	return false;
        }
  	}
}

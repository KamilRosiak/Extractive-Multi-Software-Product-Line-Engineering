
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Locale;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.utils.BitsLog;

import android.content.SharedPreferences;
import android.os.Environment;

public final class 
BitsIO {

	private static BitsIO instance = new BitsIO();

	private File mPublicDir 	= null;
	private File mPrivateDir	= null;
	
	private 
	BitsIO( ) {
		
	}

	public static final BitsIO 
	getInstance( ) {
		
		return instance;
	}

	public static final void 
	initInstance( ) {

		BitsLog.d("BitsIO - initInstance", "Initializing singleton instance...");
		
		//create the public storage dir
		instance.mPublicDir = new File( Environment.getExternalStorageDirectory(), BitsApp.sPublicStorageFolder);
		if( instance.isPublicStorageWritable() == true ) {
			if( instance.mPublicDir.exists() == false ) {
				if( instance.mPublicDir.mkdirs() == true ) {
					//create .nomedia file to prevent media scanning
					final File noMedia = new File( instance.mPublicDir, ".nomedia");
					try {
						noMedia.createNewFile();
					} catch(Exception e) {
						BitsLog.w( e, "BitsIO - createNewInstance", "Unable to create .nomedia file in the Apps public storage dir. The content may be scanned by Androids media scanner.");
					}
				} else {
					BitsLog.e("BitsIO - createNewInstance", "Unable to create the Apps public storage dir: " + instance.mPublicDir.getAbsolutePath() );
				}
			}
		} else {
			BitsLog.e("BitsIO - createNewInstance", "Unable to create the Apps public storage dir. The public storage is not writable: " + instance.mPublicDir.getAbsolutePath() );
		}

		instance.mPrivateDir = BitsApp.sAppContext.getFilesDir();
	}

	public static final void 
	resetInstance( ) {
		
		BitsLog.d( "BitsIO - resetInstance", "Resetting the singleton instance..." );
	}
	
	//******GENERAL FILES***********//
	
	public final boolean
	dirExists(
			final String dir ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - dirExists", "The given dir is null.");
			return false;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - dirExists", "The given dir is empty.");
			return false;
		}
		
		final File f = new File( dir );
		if( f.isDirectory() == true ) {
			return f.exists();
		}
		return false;
	}
	
	public final boolean
	mkDir( 
			final String dir ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - mkDir", "The given dir is null.");
			return false;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - mkDir", "The given dir is empty.");
			return false;
		}
		
		final File f = new File( dir );
		if( f.exists() == false ) {
			return f.mkdirs();
		}
		return false;
	}
	
	public final String
	findFile( 
			final String path,
			final String file,
			final boolean isPrefix ) {
		
		if( file == null || path == null ) {
			BitsLog.e("BitsIO - findFile", "The given path or file name is null.");
			return null;
		}
		if( file.equals("") == true || path.equals("") == true ) {
			BitsLog.e("BitsIO - findFile", "The given path or file name is empty.");
			return null;
		}
		
		final File f = new File(path);
		
		if( f.exists() == true ) {
			final String[] files = f.list();
			for( int i = 0; i < files.length; i++ ) {
				if( isPrefix == true ) {
					if( files[i].matches("(?i)^" + file + ".*$") == true ) { //will ignore the case; looks for 
						return path + "/" + files[i];
					}					
				} else {
					if( files[i].equalsIgnoreCase(file) == true ) {
						return path + "/" + files[i];
					}
				}
			}
		}
		
		return null;
	}
	
	public final boolean
	fileExists(
			final String file,
			final boolean ignoreCase ) {
		
		if( file == null ) {
			BitsLog.e("BitsIO - fileExists", "The given file name is null.");
			return false;
		}
		if( file.equals("") == true ) {
			BitsLog.e("BitsIO - fileExists", "The given file name is empty.");
			return false;
		}

		if( ignoreCase == true ) {
			final int idx = file.lastIndexOf("/");
			final String path = ( (idx == -1) ? System.getProperty("user.dir") : file.substring(0, (idx == -1 ? file.length()-1 : idx)) );
			final String name = file.substring( (idx == -1 ? 0 : idx + 1));
			final File f = new File(path);
			
			if( f.exists() == true ) {
				final String[] files = f.list();
				for( int i = 0; i < files.length; i++ ) {
					if( files[i].equalsIgnoreCase(name) == true ) {
						return true;
					}
				}
			}
		} else {
			final File f = new File(file);
			return f.exists();
		}
		
		return false;
	}
	
	public final void 
	writeFile( 
			final String file, 
			final byte[] buffer,
			final boolean doAppend ) {
		
		if( file == null && buffer == null ) {
			BitsLog.e("BitsIO - writeFile", "The given file name or buffer is null.");
			return;
		}
		if( file.equals("") == true ) {
			BitsLog.e("BitsIO - writeFile", "The given file name is empty.");
			return;
		}
		
		try {
			BitsLog.d( "BitsIO - writeFile", "Writing file: " + file );
			final FileOutputStream out = new FileOutputStream( new File( file ), doAppend );
			out.write( buffer );
			out.flush( );
			out.close( );
			BitsLog.d( "BitsIO - writeFile", "...done writing file: " + file );
		} catch (Exception se) {
			BitsLog.e( se, "BitsIO - writeFile", "Unable to write file: " + file );
		}
	}
	
	public final byte[] 
	readFile( 
			final String file ) {
		
		if( file == null ) {
			BitsLog.e("BitsIO - readFile", "The given file is null.");
			return null;
		}
		if( file.equals("") == true ) {
			BitsLog.e("BitsIO - readFile", "The given file name is empty.");
			return null;
		}
		
		try {
			BitsLog.d( "BitsIO - readFile", "Reading file: " + file );
			final FileInputStream out = new FileInputStream( new File( file)  );
			final byte[] buffer = new byte[(int)out.getChannel().size()];
			out.read( buffer );
			out.close( );
			BitsLog.d( "BitsIO - readFile", "...done reading file: " + file );
			return buffer;
		} catch( Exception e ) {
			BitsLog.e( e, "BitsIO - readFile", "Unable to read file: " + file);
		}
		return null;
	}
	
	public final boolean
	deleteFile(
			final String file ) {
		
		if( file == null ) {
			BitsLog.e("BitsIO - deleteFile", "The given file is null.");
			return false;
		}
		if( file.equals("") == true ) {
			BitsLog.e("BitsIO - deleteFile", "The given file name is empty.");
			return false;
		}
		
		final File f = new File(file);
		if( f.exists() == false ) {
			BitsLog.e("BitsIO - deleteFile", "The given file does not exist: " + file);
		}
		return f.delete();
	}
	
	public final boolean
	deleteDir(
			final String dir,
			final boolean recursive ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - deleteDir", "The given dir is null.");
			return false;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - deleteDir", "The given dir is empty.");
			return false;
		}
		
		final File f = new File( dir );
	    if ( f.exists() == false ) {
	    	return true;
	    }

	    if( recursive == false || f.isDirectory() == false ) { //if it's a file or not recursive -> just delete
	    	return f.delete();
	    }

	    final String[] list = f.list();
	    for (int i = 0; i < list.length; i++) {
	    	if( this.deleteDir(dir + "/" + list[i], true) == false ) {
	    		return false;
	    	}
	    }

	    return f.delete();
	}
	
	public final String[]
	getDirs(
			final String dir ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is null.");
			return null;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is empty.");
			return null;
		}
		
		final File f = new File( dir );
		if( f.isDirectory() == true ) {
			return f.list(
					new FilenameFilter() {
						@Override
						public boolean accept(File current, String name) {
							return new File(current, name).isDirectory();
						}
					});
		}
		return null;		
	}

	public final String[] 
	getFiles(
			final String dir ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is null.");
			return null;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is empty.");
			return null;
		}
		
		final File f = new File( dir );
		if( f.isDirectory() == true ) {
			return f.list(
					new FilenameFilter() {
						@Override
						public boolean accept(File current, String name) {
							return new File(current, name).isFile();
						}
					});
		}
		return null;
	}

	public final String[] 
	getFiles(
			final String fileType,
			final String dir ) {
		
		if( dir == null ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is null.");
			return null;
		}
		if( dir.equals("") == true ) {
			BitsLog.e("BitsIO - getFiles", "The given dir is empty.");
			return null;
		}
		
		final File f = new File( dir );
		if( f.isDirectory() == true ) {
			return f.list(
					new FilenameFilter() {
						@Override
						public final boolean accept( final File current, final String name) {
							final File f = new File( current, name );
							if( f.isFile() && f.getName().toLowerCase(Locale.US).endsWith( fileType.toLowerCase(Locale.US) ) ) {
								return true;
							}
							return false;
						}
					});
		}
		return null;
	}
	
	//******PUBLIC FILES********//
	public final String 
	getPublicStorageDir( ) {
		
		return this.mPublicDir.getAbsolutePath( );
	}
	
	public final boolean
	publicDirExists(
			final String dir ) {

		return this.dirExists( this.mPublicDir.getAbsolutePath() + "/" + dir );
	}

	public final boolean
	mkPublicDir( 
			final String dir ) {

		return this.mkDir( this.mPublicDir.getAbsolutePath() + "/" + dir );
	}
	
	public final boolean
	publicFileExists(
			final String file,
			final boolean ignoreCase ) {

		return this.fileExists( this.mPublicDir.getAbsolutePath() + "/" + file, ignoreCase );
	}
	
	public final void 
	writePublicFile( 
			final String file, 
			final byte[] buffer,
			final boolean doAppend ) {
		
		this.writeFile( this.mPublicDir.getAbsolutePath() + "/" + file, buffer, doAppend );
	}
	
	public final byte[] 
	readPublicFile( 
			final String file ) {
		
		return this.readFile( this.mPublicDir.getAbsolutePath() + "/" + file );
	}
	
	public final boolean
	deletePublicFile(
			final String file ) {
		
		return this.deleteFile( this.mPublicDir.getAbsolutePath() + "/" + file );
	}
	
	public final boolean
	deletePublicDir(
			final String dir,
			final boolean recursive ) {
		
		return this.deleteDir( this.mPublicDir.getAbsolutePath() + "/" + dir, recursive );
	}
	
	public final String[]
	getPublicDirs( 
			final String dir ) {
		
		return this.getDirs( this.mPublicDir.getAbsolutePath() + "/" + dir );
	}

	public final String[] 
	getPublicFiles( ) {
		
		return this.getFiles( this.mPublicDir.getAbsolutePath() );
	}
	
	public final String[] 
	getPublicFiles(
			final String dir ) {
		
		return this.getFiles( this.mPublicDir.getAbsolutePath() + "/" + dir );
	}
	
	public final String[] 
	getPublicFiles(
			final String fileType,
			final String dir ) {
		
		return this.getFiles( fileType, this.mPublicDir.getAbsolutePath() + "/" + dir );
	}	
	
	//******PRIVATE FILES*******//
	public final String 
	getPrivateStorageDir( ) {
		
		return this.mPrivateDir.getAbsolutePath();
	}
	
	public final boolean
	privateDirExists(
			final String dir ) {
		
		return this.dirExists( this.mPrivateDir.getAbsolutePath() + "/" + dir );
	}

	public final boolean
	mkPrivateDir( final String dir ) {
		
		return this.mkDir( this.mPrivateDir.getAbsolutePath() + "/" + dir );
	}
	
	public final boolean
	privateFileExists(
			final String file,
			final boolean ignoreCase ) {
		
		return this.fileExists( this.mPrivateDir.getAbsolutePath() + "/" + file, ignoreCase );
	}
	
	public final void 
	writePrivateFile( 
			final String file, 
			final byte[] buffer,
			final boolean doAppend ) {
		
		this.writeFile( this.mPrivateDir.getAbsolutePath() + "/" + file, buffer, doAppend);
	}
	
	public final byte[] 
	readPrivateFile( 
			final String file ) {
		
		return this.readFile( this.mPrivateDir.getAbsolutePath() + "/" + file);
	}
	
	public final boolean
	deletePrivateFile(
			final String file ) {
		
		return this.deleteFile(this.mPrivateDir.getAbsolutePath() + "/" + file);
	}
	
	public final boolean
	deletePrivateDir(
			final String dir,
			final boolean recursive ) {
		
		return this.deleteDir( this.mPrivateDir.getAbsolutePath() + "/" + dir, recursive);
	}
	
	public final String[]
	getPrivateDirs( 
			final String dir ) {
		
		return this.getDirs( this.mPrivateDir.getAbsolutePath() + "/" + dir );
	}

	public final String[] 
	getPrivateFiles( ) {
		
		return this.getFiles( this.mPrivateDir.getAbsolutePath() );
	}
	
	public final String[] 
	getPrivateFiles(
			final String dir ) {
		
		return this.getFiles( this.mPrivateDir.getAbsolutePath() + "/" + dir );
	}
	
	public final String[] 
	getPrivateFiles(
			final String fileType,
			final String dir ) {
		
		return this.getFiles( fileType, this.mPrivateDir.getAbsolutePath() + "/" + dir );
	}		
	
	//********ANDROID********//
	public final boolean
	isPublicStorageReadable() {
		boolean externalStorageReadable = false;
		final String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			externalStorageReadable = true;
		} else { 
			if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				externalStorageReadable = true;
			} else {
				externalStorageReadable = false;				
			}
		}
		return externalStorageReadable;
	}
	
	public final boolean
	isPublicStorageWritable() {
		boolean externalStorageWritable = false;
		final String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			externalStorageWritable = true;
		} else { 
			if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				externalStorageWritable = false;
			} else {
				externalStorageWritable = false;				
			}
		}
		return externalStorageWritable;
	}
	
	//********SHARED PREFERENCES********//
	public final boolean
	removeData(			
			final String section,
			final String key ) {
		
		if( key == null || section == null ) {
			BitsLog.e("BitsIO - removeData", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - removeData", "Key or Section value is empty.");
			return false;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.remove(key);
		return sharedPreferencesEditor.commit();
	}
	
	public final boolean 
	storeInt(
			final String section,
			final String key, 
			final int value ) {
		
		if( key == null || section == null ) {
			BitsLog.e("BitsIO - storeInt", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - storeInt", "Key or Section value is empty.");
			return false;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putInt( key, value );
		return sharedPreferencesEditor.commit();
	}

	public final int 
	readInt( 
			final String section,
			final String key ) {
		
		if( key == null || section == null ) {
			BitsLog.e("BitsIO - readInt", "Key or Section value is null.");
			return -1;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - readInt", "Key or Section value is empty.");
			return -1;
		}

		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		return sharedPreferences.getInt(key, 0);
	}

	public final boolean 
	storeBoolean( 
			final String section,
			final String key, 
			final boolean value ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - storeBoolean", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - storeBoolean", "Key or Section value is empty.");
			return false;
		}

		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putBoolean( key, value );
		return sharedPreferencesEditor.commit();
	}

	public final boolean 
	readBoolean( 
			final String section,
			final String key ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - readBoolean", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - readBoolean", "Key or Section value is empty.");
			return false;
		}

		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		return sharedPreferences.getBoolean(key, false);
	}
	
	public final boolean 
	storeFloat( 
			final String section,
			final String key, 
			final float value ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - storeFloat", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - storeFloat", "Key or Section value is empty.");
			return false;
		}

		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putFloat( key, value );
		return sharedPreferencesEditor.commit();
	}

	public final float 
	readFloat( 
			final String section,
			final String key ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - readFloat", "Key or Section value is null.");
			return -1f;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - readFloat", "Key or Section value is empty.");
			return -1f;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		return sharedPreferences.getFloat(key, 0.0f);
	}
	
	public final boolean 
	storeLong( 
			final String section,
			final String key, 
			final Long value ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - storeLong", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - storeLong", "Key or Section value is empty.");
			return false;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putLong( key, value );
		return sharedPreferencesEditor.commit();
	}

	public final Long 
	readLong( 
			final String section,
			final String key ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - readLong", "Key or Section value is null.");
			return -1L;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - readLong", "Key or Section value is empty.");
			return -1L;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		return sharedPreferences.getLong(key, 0L);
	}

	public final boolean 
	storeString( 
			final String section,
			final String key, 
			final String value ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - storeString", "Key or Section value is null.");
			return false;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - storeString", "Key or Section value is empty.");
			return false;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putString( key, value );
		return sharedPreferencesEditor.commit();
	}

	public final String 
	readString( 
			final String section,
			final String key ) {

		if( key == null || section == null ) {
			BitsLog.e("BitsIO - readString", "Key or Section value is null.");
			return null;
		}
		if( key.equals("") == true || section.equals("") == true ) {
			BitsLog.e("BitsIO - readString", "Key or Section value is empty.");
			return null;
		}
		
		final SharedPreferences sharedPreferences = BitsApp.sAppContext.getSharedPreferences(section, 0);
		return sharedPreferences.getString(key, null);
	}
}

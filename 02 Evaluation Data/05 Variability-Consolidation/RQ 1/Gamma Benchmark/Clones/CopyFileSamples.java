import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


public class CopyFileSamples {
	
	public static void copyFile1(File srcFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}
		
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		
		FileChannel source = fis.getChannel();
		FileChannel destination = fos.getChannel();
		
		destination.transferFrom(source, 0, source.size());
		
		source.close();
		destination.close();
		
		fis.close();
		fos.close();
	}
	
	public static void copyFile2(File srcFile, File destFile) throws IOException {
		InputStream in = new FileInputStream(srcFile);
		OutputStream out = new FileOutputStream(destFile);
		IOUtils.copy(in, out);
		in.close();
		out.close();
	}
	
	public static void copyFile3(File srcFile, File destFile) throws IOException {
		InputStream in = new FileInputStream(srcFile);
		OutputStream out = new FileOutputStream(destFile);
		IOUtils.copyLarge(in, out);
		in.close();
		out.close();
	}

	
}

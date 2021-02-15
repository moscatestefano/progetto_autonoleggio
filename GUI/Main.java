package Autonoleggio.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

	public static void main(String[] args) {
		File[] files = new File("wiki").listFiles();
		if (file.isfolder)
		ArrayList<String> files_analisi = new ArrayList<String>();
		showFiles(files, files_analisi);
		System.out.println(files_analisi);
		//unzip(files_analisi);
	}
	
	public static void showFiles(File[] files, ArrayList<String> percorsi) {
		for (File file : files) {
			if (file.isDirectory()) {
				showFiles(file.listFiles(), percorsi);
			} else if (file.getName().toLowerCase().endsWith((".zip"))) {
					percorsi.add(file.getPath());
				}
			}
		}
	
	public static void unzip(ArrayList<String> files_analisi) {
		for (String percorso : files_analisi) {
			File dir = new File(percorso);
			if (!dir.exists())
				dir.mkdirs();
			FileInputStream fis;
			byte[] buffer = new byte[1024];
			try {
				fis = new FileInputStream(percorso);
				ZipInputStream zis = new ZipInputStream(fis);
				ZipEntry ze = zis.getNextEntry();
				while (ze != null) {
					String fileName = ze.getName();
					File nuovo_file = new File(percorso + File.separator + fileName);
					System.out.println("Decomprino in " + nuovo_file.getAbsolutePath());
					FileOutputStream fos = new FileOutputStream(nuovo_file);
	                int len;
	                while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	                }
	                fos.close();
	                zis.closeEntry();
	                ze = zis.getNextEntry();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

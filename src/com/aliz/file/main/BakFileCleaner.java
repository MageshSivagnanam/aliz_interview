package com.aliz.file.main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class BakFileCleaner {

	static ExecutorService executor = Executors.newFixedThreadPool(5);
	public static void main(String[] args) {
		
		cleanUp(args[0]);


	}
	
	public static void cleanUp(String inputFolderPath) {
		try {
			Stream<Path> dirPath = Files.walk(Paths.get(inputFolderPath)).filter(e -> e.toFile().isDirectory());
			Object [] path= dirPath.toArray();
			for(int i=0; i< path.length; i++) {
				
				Path path2 = (Path)path[i];
				
				File file = path2.toAbsolutePath().toFile();
			
				if(file.exists() &&  file !=null && file.listFiles().length>0) {
					System.out.println("Inside if :: " +file.getPath());
					for(File fileObj:file.listFiles()) {
							executor.submit(() -> {
								cleanbakFileAndEmptyDir(fileObj);
								
								throw new Exception();
								});
						 }
						
					}
			}
			
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			executor.shutdown();
		}
	
	}
	
	
	
	/**
	 * @param xx
	 * @throws Exception
	 */
	public static void cleanbakFileAndEmptyDir(File xx) throws Exception{
		
				if(xx.canRead() && xx.canExecute()) {
					System.out.println(xx.getPath() + "  " + Thread.currentThread().getId());
					
					 if(xx.getPath().contains(".bak")) {
						 xx.delete();
					 }else if(xx.length()==0) {
						 if(xx.exists()) {
							 xx.delete();
						 }
						 
					 }	 
				}
	}	
	
	
	
}


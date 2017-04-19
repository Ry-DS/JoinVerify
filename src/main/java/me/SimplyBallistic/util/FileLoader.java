package me.SimplyBallistic.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
/**
 * @deprecated
 * 
 */
public class FileLoader {
	public static Object[] loadFile(File dataFolder, Logger log, String fileName, String failMessage,Runnable onFail,ReturnObject loadFile){
		File file=new File(dataFolder, fileName);
		Object[] ret=new Object[2];
		try {
			ret[0]=loadFile.returnObject();
			log.info("Player file successfully loaded!");
			
		} catch (Exception e) {
			log.info("Player file not found/corrupted!\n Generating new file...");
			
			try {
				file.delete();
				file.createNewFile();
				ret[1]=loadFile.returnObject();
			} catch (IOException e1) {
				log.info("Failed generating file: "+e1.getMessage()+"! "+failMessage);
				onFail.run();
				
			}
			
			
		}
		return ret;
	}
	public interface ReturnObject{
		public Object returnObject();
	}

}

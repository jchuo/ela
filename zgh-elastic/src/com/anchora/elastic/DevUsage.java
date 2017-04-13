package com.anchora.elastic;

import java.io.File;
import java.text.NumberFormat;

public class DevUsage {
	
	public static String getDevUsage(String path){
		File f = new File(path);  
        long total = f.getTotalSpace();  
        long free = f.getFreeSpace();  
        long used = total - free;
        
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);  
        
        String result = numberFormat.format((float) used / (float) total * 100);
          
        return result;
	}

}

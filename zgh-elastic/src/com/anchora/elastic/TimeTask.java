package com.anchora.elastic;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTask {
	public static void main(String[] args) {
		
		System.out.println("==========>开始》》》》》》");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// task to run goes here
				System.out.println("=========>timer start-----");
				String path = PropertiesUtil.getProperty("path");
				File file = new File(path);
				File[] listFile = file.listFiles();
				if(listFile.length != 0){
				for (int i = 0; i < listFile.length; i++) {
					if(listFile[i].isDirectory()){
						System.out.println("=========>"+listFile[i].getName());
						Delete.delete(listFile[i].getName());
					}
				}
			}
			}
		};
		Timer timer = new Timer();
		long Period = 24 * 60 * 60 * 1000;

		Date d = new Date();
		Date newdate = new Date(d.getTime()+ 24 * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String strdate = df.format(newdate);
		Date firstTime;
		try {
			System.out.println(strdate);
			firstTime = df.parse(strdate);
			
			timer.schedule(task, firstTime, Period);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("===========>timer解析日期出错！");
			e.printStackTrace();
		}
	}
}

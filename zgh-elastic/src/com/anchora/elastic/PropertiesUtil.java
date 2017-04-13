package com.anchora.elastic;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
//    private final static String CONF_DIR = "/elasticsearchDelete.properties";
    private final static String CONF_DIR = "elasticsearchDelete.properties";
	private static Properties pro;

	private PropertiesUtil() {
	}

	public static String getProperty(String name) {

		if (pro != null)
			return pro.getProperty(name);
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl == null) {
				cl = PropertiesUtil.class.getClassLoader();
			}
			pro = new Properties();
			pro.load(cl.getResourceAsStream(CONF_DIR));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pro.getProperty(name);
	}

    
}

package br.ti365;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Property {
	public static void main(String[] args) {
		getProperties();
		
	}

	private static Properties prop;

	public static Properties getProperties() {
		
		try {
			Properties prop = new Properties();
			
			InputStream inputStream = new FileInputStream("src/main/resources/application.properties");
			prop.load(inputStream);
			
			
			String CMPUsername = prop.getProperty("cm.username");
			String CMPPassword = prop.getProperty("cm.password");
			String CMIp = prop.getProperty("cm.ip");
			String VCMPort = prop.getProperty("cm.port");
			int CMPort = Integer.parseInt(VCMPort);
			//Monitor.username = prop.getProperty("cm.username");
			Monitor.connectionCm1(CMPUsername, CMPPassword, CMIp, CMPort);
			Thread.sleep(7000);
			Monitor.connectionCm2(CMPUsername, CMPPassword, CMIp, CMPort);
			
			//System.out.println(CMPUsername);

		}catch(Exception ERROR) {
			System.out.println(ERROR.getMessage());
			System.out.println(ERROR.getCause());
			ERROR.printStackTrace();
			
		}
		
		return prop;
	}	
	
	
}

package br.ti365;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Monitor {
	private static Logger logger = LogManager.getLogger(Monitor.class);

	public static String username = null;

	public static void main(String[] args) {
		Property.getProperties();

	}

	public static List<String> connectionCm1(String username, String password, String ip, int port) {

//		logger.debug
		System.out.println("Iniciando coneção ao Servidor");
		try {

			JSch jsch = new JSch();
			Session session = jsch.getSession(username, ip, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);

			session.connect(241019);

			Channel channel = session.openChannel("shell");
//			String commands = "uptime;\n sat;\n" + "ossi\n" + "t\n" + "cmonitor traffic trunk-groups\n" + "\n" + "t\n";
//			0001ff01 - trunk
//			0001ff02 - size
//			0001ff03 - active calls
			String commands = "uptime;\n sat;\n" + "ossi\n" + "t\n" + "cmonitor traffic trunk-groups\n" + "t\n";

			InputStream inputStream = new ByteArrayInputStream(commands.getBytes());
			channel.setInputStream(inputStream);
			channel.connect();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			channel.setOutputStream(baos);

			int outputSize = baos.size();
			int currentSize = -1;

			// Verifica se o output stream est� em uso
			while (outputSize != currentSize) {
				outputSize = baos.size();
				Thread.sleep(10000);
				currentSize = baos.size();

			}
			channel.disconnect();
			session.disconnect();
			return MonTrarunk(baos);

			

		} catch (Exception ERROR) {
			logger.debug(ERROR.getMessage());
			logger.debug(ERROR.getCause());
			ERROR.printStackTrace();
		}
		return null;
	}
//#######################################################################################################################
	public static List<String> connectionCm2(String username, String password, String ip, int port) {
		try {
			System.out.println("Iniciando segundo Try");

			JSch jsch = new JSch();
			Session session = jsch.getSession(username, ip, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);

			session.connect(241019);

			Channel channel = session.openChannel("shell");
			String commands = "\n sat;\n" + "ossi\n" + "clist measurements trunk-group summary last-hour\n" + "t\n";

			InputStream inputStream = new ByteArrayInputStream(commands.getBytes());
			channel.setInputStream(inputStream);
			channel.connect();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			channel.setOutputStream(baos);

			int outputSize = baos.size();
			int currentSize = -1;

			while (outputSize != currentSize) {
				outputSize = baos.size();
				Thread.sleep(10000);
				currentSize = baos.size();
			}
			
			channel.disconnect();
			session.disconnect();

			return ListmeaTruSum(baos);

		} catch (Exception ERROR) {
			logger.debug(ERROR.getMessage());
			logger.debug(ERROR.getCause());
			ERROR.printStackTrace();
		}
		
		return null;
	}
	

	private static List<String> MonTrarunk(OutputStream output) {
		List<String> list2 = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		String line = null;
		
		System.out.println("iniciando scanner:");

		try (Scanner scanner = new Scanner(output.toString())) {
			while (scanner.hasNext()) {
				line = scanner.next();
				line = line.replace("d", "").replace("-", "");
				list2.add(line);
			}
			
			for (int i = 0; i < list2.size(); i++) {
//				debug dos campos para filtro = 124
//				System.out.println("i é = " + i + " " + list.get(i));
				
				if (i > 124) {
					list.add(list2.get(i));
				}
			}
			list2.clear();
			ReadString.ReadString(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<String> ListmeaTruSum(OutputStream output) {
		List<String> list2 = new ArrayList<String>();
		String line = null;
		
		System.out.println("iniciando scanner:");

		try (Scanner scanner = new Scanner(output.toString())) {
			while (scanner.hasNext()) {
				line = scanner.next();
				line = line.replace("d", "").replace("-", "");
				list2.add(line);
			}
			ReadString.ReadStringMonitorTrunk(list2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug(e.getCause());
		}

		return null;
	}
	
	
}


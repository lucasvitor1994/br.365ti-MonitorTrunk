package br.ti365;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadString {
	private static Logger logger = LogManager.getLogger(ReadString.class);

	public static int sunSize = 0;
	public static int sunActivecalls = 0;
	public static int sumOutOfService = 0;

	public static void ReadString(List<String> list) {
//		System.out.println("Conteudo do Array List" + list.toString());
//		System.out.println("tamanho " + list.size());

		int positionTrunkSize = 1;
		int positionCallsActive = 2;

		int i = 0;
		while (i < list.size() / 5) {
			i++;

			if (NumberUtils.isNumber(list.get(positionCallsActive))) {
				int tmp = Integer.parseInt(list.get(positionCallsActive));
				
				sunActivecalls = sunActivecalls + tmp;
		System.out.println("Active Calls: " + sunActivecalls);
			} else {
//				    System.out.println("String is not numeric." + list.get(positionCallsActive));
			}
			positionCallsActive = positionCallsActive + 5;

			if (NumberUtils.isNumber(list.get(positionTrunkSize))) {
				int tmp2 = Integer.parseInt(list.get(positionTrunkSize));
//				System.out.println("Size " +  sunSize);
//				System.out.println("tmp " +  tmp2);
				sunSize = sunSize + tmp2;
			} else {
//				    System.out.println("String is not numeric." + list.get(positionTrunkSize));
			}
			positionTrunkSize = positionTrunkSize + 5;

		}
//		System.out.println("Active Calls: " + sunActivecalls);
//		System.out.println("Trunk Size: " + sunSize);

	}

	public static void ReadStringMonitorTrunk(List<String> list2) {
		System.out.println(list2);
//		System.out.println("tamanho " + list2.size());
		
		if("Last".equals(list2.get(0))) {
			
			System.out.println("éeeeee " + list2.get(0));
			for (int i = 0; i < list2.size(); i++) {
				if (i < 86) {
					list2.remove(0);
				}
			}
			
		} else {
			System.out.println("Néeeeee " + list2.get(0));
			for (int i = 0; i < list2.size(); i++) {
				if (i < 74) {
					list2.remove(0);
				}
			}
		}

		System.out.println(list2);
		System.out.println("tamanho " + list2.size());

		int outOfService = 18;
		int trunk = 4;

		int i = 0;
		while (i < list2.size() / 25) {
			i++;
//
			  System.out.println("Position Out" + outOfService + " qtd" + list2.get(outOfService));
			  System.out.println("Position Trunk " + trunk + " trunk" + list2.get(trunk));

			int tmp1 = Integer.parseInt(list2.get(outOfService));
			sumOutOfService = sumOutOfService + tmp1;

			outOfService = outOfService + 26;
			trunk = trunk + 26;

		}
//		System.out.println("Channels out of Service: " + sumOutOfService);
		Writedata();
	}

	public static void Writedata() {
		try {
			FileWriter myWriter = new FileWriter("./logs/MonitorTrunk.txt");
			sunSize = sunSize - sunActivecalls - sumOutOfService;
			System.out.println("Active Calls " + sunActivecalls + " OutOfService " + sumOutOfService + " Size " + sunSize);
			myWriter.write("Active Calls " + sunActivecalls + "\n OutOfService " + sumOutOfService + "\n Size " + sunSize);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			logger.debug("An error occurred when try  write on file.");
			e.printStackTrace();
		}

	}

}

import java.io.*;
import java.util.*;

public class Main {

	ArrayList<Device> cell; //total devices
	ArrayList<ArrayList<Integer>> pairs;
	BaseStation bs;
	static PrintWriter out;
	
	public static void main(String[] args) throws Exception {
		/*Analysis an = new Analysis();
		an.process();*/
		
		out = new PrintWriter(new File("src/" + Parameters.D2Dpairs + "energy" 
				+ Parameters.wakeTime + ".csv"));
		for (int i = 0; i < 40000; i++) { //40000 simulations
			System.out.println(i);
			Main m1 = new Main();
			m1.init();
		}
		out.close();
	}
	
	public void init() {
		cell = new ArrayList<Device>();
		Random random_number_genrator = new Random();
		HashSet<String> taken = new HashSet<>();
		int i = 0;
		taken.add("0_0");
		while(i < Parameters.cell_size)  {
			int r = random_number_genrator.nextInt(Parameters.cell_radius+1); // 0 to cell_radius
			if (r == 0)
				r = 1;
			int theta = random_number_genrator.nextInt(360); //0 to 259
			if (taken.contains(r + "_" + theta)) {
				continue;
			}
			taken.add(r + "_" + theta); //adding a new device at (r, theta) location
			double x = r* Math.cos(Math.toRadians((double)theta));
			double y = r* Math.sin(Math.toRadians((double)theta));
			Device d1 = new Device(i, x, y);
			cell.add(d1);
			i++;
		}
		
		bs = new BaseStation(Parameters.D2Dpairs); //instantiating the base station
		pairs = generate_pairs();  //generate D2D pairs
		
		Algo1 algo1 = new Algo1(pairs, cell);
		Algo2 algo2 = new Algo2(pairs, cell);
		
		algo1.process_algo(out);
		out.print(",");
		 algo2.process_algo(out);
		out.print(",");
		out.print(algo1.transmit_power_consumed/(Parameters.subSlots * algo1.time_slots) + "," 
				+ algo2.transmit_power_consumed/(Parameters.subSlots * algo2.time_slots));
		//out.print(",");
		//out.print(algo1.through_put + "," + algo2.through_put);*/
		out.println();
	}
	
	public ArrayList<ArrayList<Integer>> generate_pairs() {
		//randomly select pairs
		
		Random random_number_generator = new Random();
		ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> taken = new HashSet<>();
		
		// Pattern frequencies.
		Set<Integer> takenFrequencies = new HashSet<Integer>();
		
		for (int i = 0;i < Parameters.D2Dpairs ; i++) {
			ArrayList<Integer> pair = new ArrayList<Integer>();
			int device1 = random_number_generator.nextInt(Parameters.cell_size);
			while (taken.contains(device1))
				device1 = random_number_generator.nextInt(Parameters.cell_size);
			taken.add(device1);
			int device2 = random_number_generator.nextInt(Parameters.cell_size);
			while (taken.contains(device2))
				device2 = random_number_generator.nextInt(Parameters.cell_size);
			taken.add(device2);
			
			// DONE: Assign a pattern.
			int freq;
			do {
				freq = random_number_generator.nextInt(Parameters.frequencyLimit) + 1;
			} while (takenFrequencies.contains(freq));
			
			cell.get(device1).assignPattern(freq);
			cell.get(device2).assignPattern(freq);

			pair.add(device1); //sender
			pair.add(device2); //receiver
			ans.add(pair);
		}
		return ans;
	}
}

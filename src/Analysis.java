import java.io.BufferedReader;
import java.io.FileReader;

public class Analysis  {
	public static void Main(String args[]) throws Exception {
		Analysis an = new Analysis();
		an.process();
	}
	public void process() throws Exception {
		BufferedReader bf = new BufferedReader(new FileReader("2.csv"));
		String line = "";
		double count1 = 0;
		double count2 = 0;
		while(true) {
			line = bf.readLine();
			if (line == null)
				break;	
			int algo1 = Integer.parseInt(line.split(",")[0].replaceAll("\\s", ""));
			int algo2 = Integer.parseInt(line.split(",")[1].replaceAll("\\s", ""));
			if (algo1 <= 18 && algo2 <= 8) {
				count1++;
				count2++;
			}
				
		}
		System.out.println(count1/40000);
		System.out.println(count2/40000);
		
	}
}

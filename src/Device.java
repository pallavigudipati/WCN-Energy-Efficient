import java.util.Arrays;

public class Device {
	int id;
	double x; //x coordinate in meter
	double y; //y coordinate in meter
	
	// 1-awake, 0-sleep
	int[] pattern;
	
	public Device(){
		
	}
	
	public Device(int device_id, double inputx, double inputy) {
		id = device_id;
		x = inputx;
		y = inputy;
	}
	
	public void assignPattern(int frequency) {
		pattern = new int[100];
		int wakeLength = Parameters.wakeTime / frequency;
		int wakeLeftover = Parameters.wakeTime % frequency;
		int sleepLength = Parameters.sleepTime / frequency;
		int sleepLeftover = Parameters.sleepTime % frequency;
		
		int state = 1;
		int subSlot = 0;
		for (int i = 0; i < 2 * frequency; ++i) {
			int length = state == 1 ? wakeLength : sleepLength;
			for (int j = 0; j < length; ++j) {
				pattern[subSlot] = state;
				subSlot += 1;
			}
			state = state == 1 ? 0 : 1;
		}
		
		// Take care of leftovers.
		for (int i = 0; i < wakeLeftover; ++i) {
			pattern[subSlot] = 1;
			subSlot += 1;
		}
		for (int i = 0; i < sleepLeftover; ++i) {
			pattern[subSlot] = 0;
			subSlot += 1;
		}
		// System.out.println(Arrays.toString(pattern));
	}

	/*
	// Code: 0 -> d1 is transmitting and d2 is listening
	//     : 1 -> d2 is transmitting and d1 is listening
	// (Not using boolean just in case we need to add more modes in future.
	// Return is zero-indexed
	public static int firstHit(Device d1, Device d2, int code) {
		int[] pattern1 = d1.pattern;
		int[] pattern2 = d2.pattern;
		
		int match1, match2;
		if (code == 0) {
			match1 = 1;
			match2 = -1;
		} else if (code == 1) {
			match1 = -1;
			match2 = 1;
		} else {
			System.out.println("Invalid code in findOverlap");
			return -1;
		}
		
		for (int i = 0; i < Parameters.subSlots; ++i) {
			if (pattern1[i] == match1 && pattern2[i] == match2) {
				return i;
			}
		}
		
		return -1; 
	}*/
	
	// Code: 0 -> d1 is transmitting and d2 is listening
	//     : 1 -> d2 is transmitting and d1 is listening
	// (Not using boolean just in case we need to add more modes in future.
	/*
	public static int findOverlap(Device d1, Device d2, int code) {
		int[] pattern1 = d1.pattern;
		int[] pattern2 = d2.pattern;
		
		int match1, match2;
		if (code == 0) {
			match1 = 1;
			match2 = -1;
		} else if (code == 1) {
			match1 = -1;
			match2 = 1;
		} else {
			System.out.println("Invalid code in findOverlap");
			return -1;
		}
		
		int overlap = 0;
		for (int i = 0; i < Parameters.subSlots; ++i) {
			if (pattern1[i] == match1 && pattern2[i] == match2) {
				overlap += 1;
			}
		}
		
		return overlap; 
	}*/
}

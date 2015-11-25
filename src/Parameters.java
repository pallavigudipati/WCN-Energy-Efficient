
public class Parameters {
	static int sinr_cell = 10; //in dB 
	static int sinr_d2d = 5; //in dB 
	static double noise_factor = Math.pow(10, -13.4); //Watt
	static int cell_size = 50;  //50 devices
	static int cell_radius = 400; //in meter
	static int D2Dpairs = 4;  //no. of D2D pairs
	static double eta = 0.95; //success probability
	static double transmission_probability = 1/(double)D2Dpairs; //equation 2
	static double p_success = transmission_probability * Math.pow(1 - transmission_probability, D2Dpairs-1); //equation 1
	static long frequency = 6;  //in MHz
	static double device_freq = 0.05;  //in MHz

	// Energy-efficient model
	static int subSlots = 100; // Number of sub-slots each slot has.
	static int wakeTime = 70; // Number of sub-slots the device is awake. Assuming transmit = listen
	static int sleepTime = 30;
	static int frequencyLimit = 7;
	
	static double transmit_power_bs = Math.pow(10, 1.1);  // Ignoring base station transmit power
	static double transmit_power_devices = Math.pow(10, -0.9);  //Watt
	static double receivePowerDevices = 0.5 * Math.pow(10, -0.9) * (wakeTime / subSlots);  //Watt
	
	static int retransmissions = (int) (Math.log(1- eta)/ Math.log(1 - p_success)) * Parameters.subSlots; //equation 5, retransmissions allowed on a discovery message
	static int retransmission_TM = 8 * Parameters.subSlots; //no. of retransmissions allowed on a transmission message
}

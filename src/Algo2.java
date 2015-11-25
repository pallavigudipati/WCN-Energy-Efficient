import java.util.ArrayList;

public class Algo2 extends Algo {

	public Algo2(ArrayList<ArrayList<Integer>> inputpairs, ArrayList<Device> input_cell) {
			time_slots = 4;
			pairs = inputpairs;
			cell = input_cell;
			discovery_message_time_slot = 0;
			time_slots_given = new ArrayList<Integer>();
			time_slots_needed = new ArrayList<Integer>();
			last_failed_transmissions = new ArrayList<Integer>();
			transmit_power_consumed = 0;
			through_put = 0;
			for (int i = 0;i < pairs.size(); i++)  {
				time_slots_needed.add(time_slots);
				time_slots_given.add(0);
				last_failed_transmissions.add(0);
				
				successful_time_quantum.add(0);
			}
			
			time_quantum = 0;
		}
	
	public boolean make_transmission(
			int selected_pair, 
			ArrayList<Integer> selected_pairs //used to calculate interference
		) {
		
		int step = time_slots - time_slots_needed.get(selected_pair);  //current step
		
		int ue1 = pairs.get(selected_pair).get(0); //sender
		int ue2 = pairs.get(selected_pair).get(1); //receiver
		
		if (step == 0) {
			// UE1 sends Discovery Message to UE2
			DiscoveryMessage dm1 = new DiscoveryMessage(ue1, ue2);
			dm1.feed_sinr(cell.get(ue1), cell.get(ue2), selected_pair, selected_pairs, pairs, cell);

			transmit_power_consumed += dm1.transmit_power_consumed;
			transmit_power_consumed += Parameters.receivePowerDevices;
			
			through_put += dm1.through_put;
			if (dm1.SINR < Parameters.sinr_d2d)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 1) {
			//UE2 replies to UE1 with the received SINR value, the path gain from BS and interference
			TransmissionMessage tm1 = new TransmissionMessage();
			tm1.feed_sinr(cell.get(ue1), cell.get(ue2), selected_pair, selected_pairs, pairs, cell);

			transmit_power_consumed += tm1.transmit_power_consumed;
			transmit_power_consumed += Parameters.receivePowerDevices;
			
			through_put += tm1.through_put;
			if (tm1.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 2) {
			//UE1 replies to BS with path gain, SINR and interference for both UE1 and UE2
			TransmissionMessage tm2 = new TransmissionMessage();
			tm2.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, true);
			
			transmit_power_consumed += tm2.transmit_power_consumed;
			transmit_power_consumed += Parameters.receivePowerDevices;
			
			through_put += tm2.through_put;
			if (tm2.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 3) {
			//BS instructs both to initiate D2D
			TransmissionMessage tm3 = new TransmissionMessage();
			tm3.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, false);
			
			TransmissionMessage tm4 = new TransmissionMessage();
			tm4.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, false);
			
			// transmit_power_consumed += tm3.transmit_power_consumed;
			// transmit_power_consumed += tm4.transmit_power_consumed;	
			transmit_power_consumed += 2 * Parameters.receivePowerDevices;
			
			through_put += tm3.through_put;
			through_put += tm4.through_put;
			
			if (tm3.SINR < Parameters.sinr_cell)
				return false;  //failure
			if (tm4.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		return false;  //failure
	}
}


public class DiscoveryMessage extends Message {
	//should have info about transmitter, receiver
	int transmitter;
	int receiver;
	// SINR should meet γD2D
	
	public DiscoveryMessage (int t, int r) {
		transmitter = t;
		receiver = r;
	}
}

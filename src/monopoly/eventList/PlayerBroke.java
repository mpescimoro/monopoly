package monopoly.eventList;

import monopoly.Player;
import monopoly.events.Event;
/**
 * 
 * 
 * @author Giovanni Caniato, Donatello Rovizzi, Mattia Pescimoro 
 *
 */
public class PlayerBroke extends Event {
	
	private Player player;
	
	/**
	 * Constructor of PlayerBroke class
	 * @param player
	 */
	public PlayerBroke(Player player) {
		this.player = player;
	}
	
	/**
	 * 
	 * @return the message of the event to broke
	 */
	public String getMessage() {
		return String.format("%s ESCE DALLA PARTITA!", player.getName());
	}
}

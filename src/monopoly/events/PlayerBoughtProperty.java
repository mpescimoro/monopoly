package monopoly.events;

import monopoly.Player;
import monopoly.event_handlers.Event;
import monopoly.slots.Property;

public class PlayerBoughtProperty implements Event {
	
	private Player player;
	private Property property;
	
	public PlayerBoughtProperty(Player player, Property property) {
		this.player = player;
		this.property = property;
	}

	public String getMessage() {
		return String.format("%s ha comprato %s", player.getName(), property.getName());
	}

	public Player getPlayer() {
		return player;
	}

	public Property getProperty() {
		return property;
	}
}
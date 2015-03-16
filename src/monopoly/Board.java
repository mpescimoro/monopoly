package monopoly;

import monopoly.event_handlers.EventGenerator;
import monopoly.events.OutOfPrison;
import monopoly.events.PlayerBroke;
import monopoly.events.PlayerToPrison;
import monopoly.events.PrintPlayerStatus;
import monopoly.slots.*;

/**
 * 
 * 
 * @author Giovanni Caniato, Donatello Rovizzi, Mattia Pescimoro 
 *
 */
public final class Board extends EventGenerator {
	
	private static final int TURNS_TO_PRISON = 2;
	private static final double PRISON_TAX = 50;

	final private Slot slots [];
	final private int prison;
	private byte consecutiveTurns;
	private int turnsPlayed;
	private double startBonus;
	
	/**
	 * Constructor of Board class
	 * @param dimension of the board
	 * @param prison the slot of the prison
	 * @param startBonus bonus if start slot is active 
	 */
	public Board(int dimension, int prison, double startBonus) {
		this.slots = new Slot [dimension];
		this.prison = prison;
		this.consecutiveTurns = 0;
		this.turnsPlayed = 0;
		this.startBonus = startBonus;
	}
	
	/**
	 * 
	 * @param index the index of the slot 
	 * @return the slot at the position of the index
	 */
	public Slot getSlot (int index) {
		return slots[index];
	}
	
	/**
	 * 
	 * @param s the slot to add
	 * @param position the position of the slot
	 */
	public void addSlot(Slot s, int position) {
		slots[position] = s;
	}
	
	/**
	 * 
	 * @param p Players involved in the action
	 */
	private void action(Players ps, int result) {
		slots[ps.current().getPosition()].action(ps, result);
	}

	/**
	 * 
	 * @param ps Players who have to play turn
	 * @param d the Dice
	 */
	public void playTurn(Players ps, Dice d) {
		Player p = ps.current();
		
		if (prisonCheck(p)) {
			getObserver().handleEvent(new PlayerBroke(ps.current()));
			ps.removeCurrent();
		}
		else
			if ((consecutiveTurns == TURNS_TO_PRISON) && d.same()) {
				getObserver().handleEvent(new PlayerToPrison(p));
				p.imprison();
				moveToPrison(p);
				consecutiveTurns = 0;
				ps.next();
			} else {
				p.move(d.result());
				action(ps, d.result());
	
				finalCheck(ps, d);
			}
	}
	
	private boolean prisonCheck(Player p) {
		if (p.imprisoned()) {
			p.withdrawMoney(PRISON_TAX);
			p.setFree();
			getObserver().handleEvent(new OutOfPrison(p, PRISON_TAX));
		}
		return p.broke();
	}
	
	private void finalCheck(Players ps, Dice d) {
		if (ps.current().broke()) {
			getObserver().handleEvent(new PlayerBroke(ps.current()));
			ps.removeCurrent();
		} else {
			getObserver().handleEvent(new PrintPlayerStatus(ps.current()));
			if (!d.same()) {	
				
				ps.next();
				consecutiveTurns = 0;
				turnsPlayed++;
			} else consecutiveTurns++;
		}
	}
        
	/**
	 * 
	 * @param p the player who must go to the prison
	 */
    public void moveToPrison(Player p) {
		p.setPosition(prison);
	}

    /**
     * 
     * @return the position of the prison
     */
	public int getPrisonPosition() {
		return prison;
	}
	
	/**
	 * 
	 * @return the number of the turns played
	 */
	public int getTurnsPlayed() {
		return turnsPlayed;
	}
	
	/**
	 *
	 * @param o the observer to add
	 */
	public void setObserver(Game o) {
		super.setObserver(o);
		for (Slot s:slots) {
			s.setObserver(o);
		}
	}
	
	/**
	 * 
	 * @param index the index of the property
	 * @return the property at the passed index
	 */
	public Property getProperty(int index) {
		if (slots[index] instanceof Property)
			return (Property) slots[index];
		return null;
			
	}
	
	public int size() {
		return slots.length;
	}
	
	public double getStartBonus() {
		return startBonus;
	}
}

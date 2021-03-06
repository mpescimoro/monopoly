package monopoly.cards;

import monopoly.Players;
/**
 * 
 * 
 * @author Giovanni Caniato, Donatello Rovizzi, Mattia Pescimoro 
 *
 */
public class WithdrawMoney extends Card {
	
	private double amount;
	
	/**
	 * Constructor of WithdrawMoney class
	 * @param amount the amount to be paid
	 * @param text the text
	 */
	public WithdrawMoney(double amount, String text) {
		super(text);
		this.amount = amount;
	}

	/**
	 * 
	 * @param ps the players who must paid
	 * @param result 
	 */
	@Override
	public void effect(Players ps, int result) {
		ps.current().withdrawMoney(amount);
	}
}
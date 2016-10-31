/**
 * @author 16ypande
 * @date 16 Nov 2014
 * @purpose The player class is used to record information about a player including their rating.
 */

package csIA_TeamManager;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author 000220-0086
 * @date 19 December, 2014
 * @purpose The player class contains information about a player, including their scores on criterion, their personal information and their game history. 
 */
public class Player implements Serializable
{
	private static final long serialVersionUID = 1100204078937501916L;
	Team team;
	public int quartile = -1;
	private int rating; //overall rating of player
	private int doublesrating; //doubles rating of player
	private int singlesrating; //singles rating of player
	private int gamesWon; //amount of games player has won
	private boolean isJVelgible; //use age to check if player is JV eligible
	private boolean isVeligible; //use grade to check if player is V eligible
	private String emailAddress; //stores email of player
	private String firstName; //stores first name of player
	private String lastName; //stores last name of player
	private int gamesplayed;
	private int gamestied;
	private boolean gender;
	ArrayList<ArrayList<Integer>> criterionhistory = new ArrayList<ArrayList<Integer>>();
	public ArrayList<Game> importantgames = new ArrayList<Game>();
	/**
	 * @purpose Uses criterion ratings to calculate overall, doubles and singles ratings of player. 
	 */
	public void calculateRating()
	{
		ArrayList<Integer> criterionratings = criterionhistory.get(criterionhistory.size()-1);
		singlesrating = 0;
		doublesrating = 0;
		rating = 0;
		int singleschange = 0;
		int doubleschange = 0;
		int ratingchange = 0;
		int curpos = 0;
		for (Criterion curcrit:team.criterionlist)
		{
			singlesrating+=criterionratings.get(curpos)*curcrit.getEffectonsingles();
			singleschange+=curcrit.getEffectonsingles();
			doublesrating+=criterionratings.get(curpos)*curcrit.getEffectondoubles();
			doubleschange+=curcrit.getEffectondoubles();
			rating+=criterionratings.get(curpos)*curcrit.getEffectoverall();
			ratingchange+=curcrit.getEffectoverall();
			curpos++;
		}
		if (singleschange!=0)
			singlesrating/=singleschange;
		if (singleschange!=0)
			doublesrating/=doubleschange;
		if (ratingchange!=0)
			rating/=ratingchange;
	}
	/**
	 * @return the number of games tied
	 */
	public int getGamestied() {
		return gamestied;
	}
	/**
	 * @param the number of games tied
	 */
	public void setGamestied(int gamestied) {
		this.gamestied = gamestied;
	}
	/**
	 * @return The gender of the player.
	 */
	public boolean getGender() {
		return gender;
	}
	/**
	 * @return The number of games the player has played.
	 */
	public int getGamesPlayed() {
		return gamesplayed;
	}
	/**
	 * @param The number of games the player has played.
	 */
	public void setGamesPlayed(int gamesplayed) {
		this.gamesplayed = gamesplayed;
	}

	/**
	 * @param The gender of the player.	 
	 * */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	/**
	 * @return The number of games won by the player.
	 */
	public int getGamesWon() {
		return gamesWon;
	}
	/**
	 * @param The number of games won by the player	 
	 * */
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	/**
	 * @return Whether or not the player is JVeligible
	 */
	public boolean isJVelgible() {
		return isJVelgible;
	}
	/**
	 * @param Whether or not the player is JVeligible
	 */
	public void setJVelgible(boolean isJVelgible) {
		this.isJVelgible = isJVelgible;
	}
	/**
	 * @return Whether or not the player is varsity eligible
	 */
	public boolean isVeligible() {
		return isVeligible;
	}
	/**
	 * @param isVeligible the isVeligible to set
	 */
	public void setVeligible(boolean isVeligible) {
		this.isVeligible = isVeligible;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @return the doublesrating
	 */
	public int getDoublesrating() {
		return doublesrating;
	}
	/**
	 * @return the singlesrating
	 */
	public int getSinglesrating() {
		return singlesrating;
	}
}

/**
 * @author 16ypande
 * @date 16 Nov 2014
 * @purpose The game class is a class used to record the results of a game. It is used by the player and GUI classes.
 * @hardware
 * @software
 */

package csIA_TeamManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 000220-0086
 * @date 19 December, 2014
 * @purpose The Game class stores the results of a game between two teams of players, team1 and team2. 
 */
public class Game implements Serializable
{
	private static final long serialVersionUID = 3019833035123139552L;
	private Team team;
	private char[] team1score;
	private char[] team2score;
	private ArrayList<Player> team1;
	private ArrayList<Player> team2;
	private Boolean important;
	private String comments;
	int courtplayedin;
	/**
	 * @param The full name of the player to search
	 * @return A boolean containing whether or not the game contains the player sent
	 */
	public Game(int whichcourt, ArrayList<Player> firstteam, ArrayList<Player> secondteam, char[] teamfirstscore, char[] teamsecscore)
	{
		courtplayedin = whichcourt;
		team1 = firstteam;
		team2 = secondteam;
		team1score = teamfirstscore;
		team2score = teamsecscore;
	}
	public String getname()
	{
		if (team1.size()==1)
			return team1.get(0).getFirstName() + " vs. " + team2.get(0).getFirstName();
		else
			return team1.get(0).getFirstName() + " and " + team1.get(1).getFirstName() + " vs. " + team2.get(0).getFirstName() + " and " + team2.get(1).getFirstName();
	}
	public boolean contains(Player tofind)
	{
		for (Player curplayer:team1)
			if ((curplayer.equals(tofind)))
				return true;
		for (Player curplayer:team2)
			if ((curplayer.equals(tofind)))
				return true;
		return false;
	}
	/**
	 * Sets whether or not a game result is unexpected using the game's rating to score ratio.
	 */
	public void findimportance()
	{
		int truerating1 = 0;
		int truerating2 = 0;
		if (team1.size()==1)
			truerating1+=(team1.get(0).getSinglesrating()+team1.get(0).getRating());
		else
			truerating1+=(team1.get(0).getDoublesrating()+team1.get(0).getRating()+team1.get(1).getDoublesrating()+team1.get(1).getRating());
		if (team2.size()==1)
			truerating2+=(team2.get(0).getSinglesrating()+team2.get(0).getRating());
		else
			truerating2+=(team2.get(0).getDoublesrating()+team2.get(0).getRating()+team2.get(1).getDoublesrating()+team2.get(1).getRating());
		int team1points = findteampoints(1);
		int team2points = findteampoints(2);
		if ((team1points/truerating1)/(team2points/truerating2)>2)
		{
			important = true;
			for (Player player:team1)
				player.importantgames.add(this);
			for (Player player:team2)
				player.importantgames.add(this);
			team.importantgames.add(this);
		}
		else
			important = false;
	}
	/**
	 * @purpose Calculates the number of points earned by a team.
	 * @param Which team to score
	 * @return Number of points the team earned
	 */
	public int findteampoints (int team)
	{
		int points = 0;
		char[] scores = team==1?team1score:team2score;
		int place = 0;
		while (place<scores.length)
		{
			int multiplier = 1;
			while(place==0?scores[place]!='-':false)
			{
				points+=multiplier*(int)scores[place];
				multiplier*=10;
				place++;
			}
			place++;
		}
		return points;
	}
	/**
	 * @return the team1score
	 */
	public char[] getTeam1score() {
		return team1score;
	}
	/**
	 * @param team1score the team1score to set
	 */
	public void setTeam1score(char[] team1score) {
		this.team1score = team1score;
	}
	/**
	 * @return the team2score
	 */
	public char[] getTeam2score() {
		return team2score;
	}
	/**
	 * @param team2score the team2score to set
	 */
	public void setTeam2score(char[] team2score) {
		this.team2score = team2score;
	}
	/**
	 * @return the team1
	 */
	public ArrayList<Player> getTeam1() {
		return team1;
	}
	/**
	 * @param team1 the team1 to set
	 */
	public void setTeam1(ArrayList<Player> team1) {
		this.team1 = team1;
	}
	/**
	 * @return the team2
	 */
	public ArrayList<Player> getTeam2() {
		return team2;
	}
	/**
	 * @param team2 the team2 to set
	 */
	public void setTeam2(ArrayList<Player> team2) {
		this.team2 = team2;
	}
	/**
	 * @return the important
	 */
	public Boolean getImportant() {
		return important;
	}
	/**
	 * @param important the important to set
	 */
	public void setImportant(Boolean important) {
		this.important = important;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the courtplayedin
	 */
	public int getCourtplayedin() {
		return courtplayedin;
	}
	/**
	 * @param courtplayedin the courtplayedin to set
	 */
	public void setCourtplayedin(int courtplayedin) {
		this.courtplayedin = courtplayedin;
	}
}

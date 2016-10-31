package csIA_TeamManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 000220-0086
 * @date 19 December, 2014
 * @purpose The Team class contains all information about a team, including the players, the game results and the criterion.
 */
public class Team implements Serializable
{
	private static final long serialVersionUID = -7803295880867110888L;
	public String name;
	public int type;
	public boolean forMesac;
	public int numCourts;
	public ArrayList<Player> playerlist,jvboys,vboys,jvgirls,vgirls;
	public ArrayList<Criterion> criterionlist = new ArrayList<Criterion>();
	public ArrayList<Game> gamelist = new ArrayList<Game>();
	public ArrayList<Game> importantgames = new ArrayList<Game>();
	public Team(String name, ArrayList<Player> playerlist, int numCourts, int type, boolean forMesac)
	{
		this.forMesac = forMesac;
		this.name = name;
		this.playerlist = playerlist;
		this.numCourts = numCourts;
		this.type = type;
		jvboys = new ArrayList<Player>();
		vboys = new ArrayList<Player>();
		jvgirls = new ArrayList<Player>();
		vgirls = new ArrayList<Player>();
	}
	/**
	 * @purpose Finds games with unexpected results as determined by the findimportance method in the game class.
	 * @return An arraylist containing the games that have been deemed important.
	 */
	ArrayList<Game> findImportantGames() 
	{
		ArrayList<Game> tosend = new ArrayList<Game>();
		for (Game curgame : gamelist) {
			curgame.findimportance();
			if (curgame.getImportant())
				tosend.add(curgame);
		}
		return tosend;
	}
	/**
	 * @purpose Finds games with unexpected results as determined by the findimportance method in the game class given a player.
	 * @param The player for whom the games must be found.
	 * @return An arraylist containing the games that have been deemed important for that player.
	 */
	ArrayList<Game> findImportantGames(Player tofind) 
	{
		return tofind.importantgames;
	}
}

/**
 * @author 16ypande
 * @date 16 Nov 2014
 * @purpose The criterion class is used to record the names of criterion and their effect on a player's rating.
 */

package csIA_TeamManager;

import java.io.Serializable;

/**
 * @author 000220-0086
 * @date 19 December, 2014
 * @purpose The Criterion class is instantiated to create a Criterion object, which stores information of a criterion as entered by the coach.
 */
public class Criterion implements Serializable
{
	private static final long serialVersionUID = -33626182587598572L;
	private String name;
	private String category;
	private int effectonsingles;
	private int effectondoubles;
	private int effectoverall;
	private int identifyingid;
	/**
	 * @return the name
	 */
	public int getid() {
		return identifyingid;
	}
	/**
	 * @return the name
	 */
	public void setid(int toset) {
		identifyingid = toset;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the effectonsingles
	 */
	public int getEffectonsingles() {
		return effectonsingles;
	}
	/**
	 * @param effectonsingles the effectonsingles to set
	 */
	public void setEffectonsingles(int effectonsingles) {
		this.effectonsingles = effectonsingles;
	}
	/**
	 * @return the effectondoubles
	 */
	public int getEffectondoubles() {
		return effectondoubles;
	}
	/**
	 * @param effectondoubles the effectondoubles to set
	 */
	public void setEffectondoubles(int effectondoubles) {
		this.effectondoubles = effectondoubles;
	}
	/**
	 * @return the effectoverall
	 */
	public int getEffectoverall() {
		return effectoverall;
	}
	/**
	 * @param effectoverall the effectoverall to set
	 */
	public void setEffectoverall(int effectoverall) {
		this.effectoverall = effectoverall;
	}
}

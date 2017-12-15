// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

public class Spell {

	// Each spell has a name, a description, and a level.
	private String spellName, spellDescription;
	private int spellLevel;
	
	public Spell(String name, String description, int level) {
		spellName = name;
		spellDescription = description;
		spellLevel = level;
	}
	
	public String getSpellName() {
		return spellName;
	}
	
	public String getSpellDescription() {
		return spellDescription;
	}
	
	public int getSpellLevel() {
		return spellLevel;
	}
}

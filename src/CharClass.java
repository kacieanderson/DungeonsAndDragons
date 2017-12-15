// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;

// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 11/19/2017

public enum CharClass {
	
	BARBARIAN("1d12", 12), 
	BARD("1d8", 8), 
	CLERIC("1d8", 8), 
	DRUID("1d8", 8), 
	FIGHTER("1d10", 10), 
	MONK("1d8", 8), 
	PALADIN("1d10", 10), 
	RANGER("1d10", 10), 
	ROGUE("1d8", 8),
	SORCERER("1d6", 6), 
	WARLOCK("1d8", 8), 
	WIZARD("1d6", 6);
	
	public String hitDice;
	int hitPoints;
	
	CharClass(String hitDice, int hitPoints) {
		this.hitDice = hitDice;
		this.hitPoints = hitPoints;
	}
	
	CharClass (CharClass c) {
		c.hitPoints = hitPoints;
		c.hitDice = hitDice;
	}
	
	// Return the Primary Abilities of the specific Class.
	public String getEquipment(ResourceBundle me) {
		if (this.equals(BARBARIAN)) return me.getString("DnDGUI.BarbarianEquipment");
		else if (this.equals(BARD)) return me.getString("DnDGUI.BardEquipment");
		else if (this.equals(CLERIC)) return me.getString("DnDGUI.ClericEquipment");
		else if (this.equals(DRUID)) return me.getString("DnDGUI.DruidEquipment");
		else if (this.equals(FIGHTER)) return me.getString("DnDGUI.FighterEquipment");
		else if (this.equals(MONK)) return me.getString("DnDGUI.MonkEquipment");
		else if (this.equals(PALADIN)) return me.getString("DnDGUI.PaladinEquipment");
		else if (this.equals(RANGER)) return me.getString("DnDGUI.RangerEquipment");
		else if (this.equals(ROGUE)) return me.getString("DnDGUI.RogueEquipment");
		else if (this.equals(SORCERER)) return me.getString("DnDGUI.SorcererEquipment");
		else if (this.equals(WARLOCK)) return me.getString("DnDGUI.WarlockEquipment");
		else if (this.equals(WIZARD)) return me.getString("DnDGUI.WizardEquipment");
		else return "None";
	}
	
	// Return the Throw Proficiencies of the specific Class.
	public String getHitDice() {
		
		if (this.equals(BARBARIAN)) return BARBARIAN.hitDice;
		else if (this.equals(BARD)) return BARD.hitDice;
		else if (this.equals(CLERIC)) return CLERIC.hitDice;
		else if (this.equals(DRUID)) return DRUID.hitDice;
		else if (this.equals(FIGHTER)) return FIGHTER.hitDice;
		else if (this.equals(MONK)) return MONK.hitDice;
		else if (this.equals(PALADIN)) return PALADIN.hitDice;
		else if (this.equals(RANGER)) return RANGER.hitDice;
		else if (this.equals(ROGUE)) return ROGUE.hitDice;
		else if (this.equals(SORCERER)) return SORCERER.hitDice;
		else if (this.equals(WARLOCK)) return WARLOCK.hitDice;
		else if (this.equals(WIZARD)) return WIZARD.hitDice;
		else return "None";
		
	}
	
	// Return the Armor/Weapon Proficiencies of the specific Class.
	public int getHitPoints() {
		switch(this) {
		case BARBARIAN:
			return BARBARIAN.hitPoints;
		case BARD:
			return BARD.hitPoints;
		case CLERIC:
			return CLERIC.hitPoints;
		case DRUID:
			return DRUID.hitPoints;
		case FIGHTER:
			return FIGHTER.hitPoints;
		case MONK:
			return MONK.hitPoints;
		case PALADIN:
			return PALADIN.hitPoints;
		case RANGER:
			return RANGER.hitPoints;
		case ROGUE:
			return ROGUE.hitPoints;
		case SORCERER:
			return SORCERER.hitPoints;
		case WARLOCK:
			return WARLOCK.hitPoints;
		case WIZARD:
			return WIZARD.hitPoints;
		default: 
			return 8;
		}
	}
	
	public String getClassString(ResourceBundle me) {
		if (this.equals(BARBARIAN)) return me.getString("DnDGUI.Barbarian");
		else if (this.equals(BARD)) return me.getString("DnDGUI.Bard");
		else if (this.equals(CLERIC)) return me.getString("DnDGUI.Cleric");
		else if (this.equals(DRUID)) return me.getString("DnDGUI.Druid");
		else if (this.equals(FIGHTER)) return me.getString("DnDGUI.Fighter");
		else if (this.equals(MONK)) return me.getString("DnDGUI.Monk");
		else if (this.equals(PALADIN)) return me.getString("DnDGUI.Paladin");
		else if (this.equals(RANGER)) return me.getString("DnDGUI.Ranger");
		else if (this.equals(ROGUE)) return me.getString("DnDGUI.Rogue");
		else if (this.equals(SORCERER)) return me.getString("DnDGUI.Sorcerer");
		else if (this.equals(WARLOCK)) return me.getString("DnDGUI.Warlock");
		else if (this.equals(WIZARD)) return me.getString("DnDGUI.Wizard");
		else return "None";
	}

}

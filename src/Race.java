// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.util.ResourceBundle;

public enum Race {
	DWARF(25), ELF(30), HALFLING(25), 
	HUMAN(30), DRAGONBORN(25), GNOME(25), 
	HALFELF(30), HALFORC(30), TIEFLING(30);

	public int speed;

	Race (int speed){
		this.speed = speed;
	}

	Race(Race r){

		r.speed = speed;

	}

	// Return the speed of the specific Race.
	public int getSpeed() {

		switch(this) {
		case DWARF:
			return DWARF.speed;
		case ELF:
			return ELF.speed;
		case HALFLING:
			return HALFLING.speed;
		case HUMAN:
			return HUMAN.speed;
		case DRAGONBORN:
			return DRAGONBORN.speed;
		case GNOME:
			return GNOME.speed;
		case HALFELF:
			return HALFELF.speed;
		case HALFORC:
			return HALFORC.speed;
		case TIEFLING:
			return TIEFLING.speed;
		default:
			return 0;
		}

	}

	public String getRaceString(ResourceBundle me) {
		if (this.equals(DWARF)) return me.getString("DnDGUI.Dwarf");
		else if (this.equals(ELF)) return me.getString("DnDGUI.Elf");
		else if (this.equals(HALFLING)) return me.getString("DnDGUI.Halfling");
		else if (this.equals(HUMAN)) return me.getString("DnDGUI.Human");
		else if (this.equals(DRAGONBORN)) return me.getString("DnDGUI.Dragonborn");
		else if (this.equals(GNOME)) return me.getString("DnDGUI.Gnome");
		else if (this.equals(HALFELF)) return me.getString("DnDGUI.Halfelf");
		else if (this.equals(HALFORC)) return me.getString("DnDGUI.Halforc");
		else if (this.equals(TIEFLING)) return me.getString("DnDGUI.Tiefling");
		else return "None";
	}
}

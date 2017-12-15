// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;

public class Character {
	
	private String name, alignment;
	private int strength, dexterity, constitution, intelligence, wisdom, charisma;
	public int athletics, acrobatics, sleightOfHand, stealth, arcana, history, investigation, nature, religion;
	public int animalHandling, insight, medicine, perception, survival, deception, intimidation, performance, persuasion;
	protected int level, hp, xp;
	private Race race;
	private CharClass characterClass;
	private LinkedList<Weapon> weapons;
	private LinkedList<Spell> spells;
	private CheckBox[] skills;
	public boolean alive = true;
	private Locale currentLocale;
	private ResourceBundle me, oldMe;
	
	// Create a new Character by first noting its Race
	public Character(String r, Locale currentLocale) {
		this.currentLocale = currentLocale;
		me = ResourceBundle.getBundle("tracker", currentLocale);
		oldMe = ResourceBundle.getBundle("messages", currentLocale);
		setRace(r);
		xp = 0;
		level = 1;
		weapons = new LinkedList<>();
		spells = new LinkedList<>();
	}
	
	// Get equipment string from CharClass enum
	public String getEquipment() {
		return characterClass.getEquipment(oldMe);
	}
	
	// In order to use proper case in the ComboBox during character creation.
	public void setRace(String r) {
		if (r.equals( oldMe.getString("DnDGUI.Dwarf")) ) race = Race.DWARF;
		else if (r.equals( oldMe.getString("DnDGUI.Elf")) ) race = Race.ELF;
		else if (r.equals( oldMe.getString("DnDGUI.Halfling")) ) race = Race.HALFLING;
		else if (r.equals( oldMe.getString("DnDGUI.Human")) ) race = Race.HUMAN;
		else if (r.equals( oldMe.getString("DnDGUI.Dragonborn")) ) race = Race.DRAGONBORN;
		else if (r.equals( oldMe.getString("DnDGUI.Gnome")) ) race = Race.GNOME;
		else if (r.equals( oldMe.getString("DnDGUI.Halfelf")) ) race = Race.HALFELF;
		else if (r.equals( oldMe.getString("DnDGUI.Halforc")) ) race = Race.HALFORC;
		else if (r.equals( oldMe.getString("DnDGUI.Tiefling")) ) race = Race.TIEFLING;
	}
	
	// Return the user's current level
	public int getLevel() {
		return level;
	}
	
	// Return the user's current XP.
	public int getXP() {
		return xp;
	}
	
	// Set the user's HP
	public void setHP() {
		hp = characterClass.hitPoints;
	}
	
	// Get the user's HP
	public int getHP() {
		return hp;
	}
	
	// Decrease HP if the character takes damage.
	// If HP is at 0 or below, the character has died.
	public void takeDamage(int hit) {
		hp -= hit;
		
		if (hp <= 0) {
			alive = false;
		}
	}
	
	// Increase HP if the character is healed or takes a rest.
	public void heal(int heal) {
		hp += heal;
	}
	
	// Add a new spell to the character's list of spells
	public void addSpell(String spellName, String description, int spellLevel) {
		Spell newSpell = new Spell(spellName, description, spellLevel);
		spells.add(newSpell);
	}
	
	// Add a new weapon to the character's list of weapons
	public void addWeapon(String weaponName, String weaponType, String damage) {
		Weapon newWeapon = new Weapon(weaponName, weaponType, damage);
		weapons.add(newWeapon);
	}
	
	// Add experience points to the character.
	// At certain intervals, increase the character's level.
	public void addExperience(int xpPoints) {
		xp = xpPoints;
		
		if ( xp <= 300 ) {
			level = 1;
		} else if ( xp <= 900 ) {
			level = 2;
		} else if ( xp <= 2700 ) {
			level = 3;
		} else if ( xp <= 6500 ) {
			level = 4;
		} else if ( xp <= 14000 ) {
			level = 5;
		} else if ( xp <= 23000 ) {
			level = 6;
		} else if ( xp <= 34000 ) {
			level = 7;
		} else if ( xp <= 48000 ) {
			level = 8;
		}  else if ( xp <= 64000 ) {
			level = 9;
		} else if ( xp > 64000 ) {
			level = 10;
		}
	}
	
	// When the User chooses to become proficient at a certain skill, add 2 to its Skill score.
	public void addSkillProficiencies(String skill) {
		if (skill.equals(me.getString("DnDGUI.athletics"))) athletics += 2;
		else if (skill.equals(me.getString("DnDGUI.acrobatics"))) acrobatics += 2;
		else if (skill.equals(me.getString("DnDGUI.sleightOfHand"))) sleightOfHand += 2;
		else if (skill.equals(me.getString("DnDGUI.stealth"))) stealth += 2;
		else if (skill.equals(me.getString("DnDGUI.arcana"))) arcana += 2;
		else if (skill.equals(me.getString("DnDGUI.history"))) history += 2;
		else if (skill.equals(me.getString("DnDGUI.investigation"))) investigation += 2;
		else if (skill.equals(me.getString("DnDGUI.nature"))) nature += 2;
		else if (skill.equals(me.getString("DnDGUI.religion"))) religion += 2;
		else if (skill.equals(me.getString("DnDGUI.animalHandling"))) animalHandling += 2;
		else if (skill.equals(me.getString("DnDGUI.insight"))) insight += 2;
		else if (skill.equals(me.getString("DnDGUI.medicine"))) medicine += 2;
		else if (skill.equals(me.getString("DnDGUI.perception"))) perception += 2;
		else if (skill.equals(me.getString("DnDGUI.survival"))) survival += 2;
		else if (skill.equals(me.getString("DnDGUI.deception"))) deception += 2;
		else if (skill.equals(me.getString("DnDGUI.intimidation"))) intimidation += 2;
		else if (skill.equals(me.getString("DnDGUI.performance"))) performance += 2;
		else if (skill.equals(me.getString("DnDGUI.persuasion"))) persuasion += 2;
	}
	
	/**
	 * Calculate the appropriate modifier for a given score.
	 * @param initialAbilityScore = the score the user has "rolled"
	 * @return an int value for the appropriate modifier
	 */
	public int getModifier(int initialAbilityScore) {
		if (initialAbilityScore <= 1) {
			return -5;
		} else if ((initialAbilityScore == 2) || (initialAbilityScore == 3)){
			return -4;
		} else if ((initialAbilityScore == 4) || (initialAbilityScore == 5)) {
			return -3;
		} else if ((initialAbilityScore == 6) || (initialAbilityScore == 7)) {
			return -2;
		} else if ((initialAbilityScore == 8) || (initialAbilityScore == 9)) {
			return -1;
		} else if ((initialAbilityScore == 10) || (initialAbilityScore == 11)) {
			return 0;
		} else if ((initialAbilityScore == 12) || (initialAbilityScore == 13)) {
			return 1;
		} else if ((initialAbilityScore == 14) || (initialAbilityScore == 15)) {
			return 2;
		} else if ((initialAbilityScore == 16) || (initialAbilityScore == 17)) {
			return 3;
		} else if ((initialAbilityScore == 18) || (initialAbilityScore == 19)) {
			return 4;
		} else if ((initialAbilityScore == 20) || (initialAbilityScore == 21)) {
			return 5;
		} else if ((initialAbilityScore == 22) || (initialAbilityScore == 23)) {
			return 6;
		} else if ((initialAbilityScore == 24) || (initialAbilityScore == 25)) {
			return 7;
		} else if ((initialAbilityScore == 26) || (initialAbilityScore == 27)) {
			return 8;
		} else if ((initialAbilityScore == 28) || (initialAbilityScore == 29)) {
			return 9;
		} else {
			return 10;
		}
	}
	
	// Get the character's Race.
	public Race getRace() {
		return race;
	}
	
	// Get the list of character's weapons.
	public LinkedList<Weapon> getWeapons(){
		return weapons;
	}
	
	// Get the list of character's weapons in neat String form.
	public LinkedList<String> getWeaponsString(){
		LinkedList<String> weaponsStrings = new LinkedList<String>();
		for (int i=0; i<weapons.size();i++) {
			weaponsStrings.add(weapons.get(i).getWeaponName() + " | "
					+ weapons.get(i).getWeaponType() + " Weapon | Damage: "
					+ weapons.get(i).getDamage());
		}
		return weaponsStrings;
	}
	
	// Get the list of character's spells.
	public LinkedList<Spell> getSpells(){
		return spells;
	}
	
	// Setter & Getter for the character's Class.
	public void setClass(String aClass) {
		if (aClass.equals( oldMe.getString("DnDGUI.Barbarian") )) characterClass = CharClass.BARBARIAN;
		else if (aClass.equals( oldMe.getString("DnDGUI.Bard") )) characterClass = CharClass.BARD;
		else if (aClass.equals( oldMe.getString("DnDGUI.Cleric") )) characterClass = CharClass.CLERIC;
		else if (aClass.equals( oldMe.getString("DnDGUI.Druid") )) characterClass = CharClass.DRUID;
		else if (aClass.equals( oldMe.getString("DnDGUI.Fighter") )) characterClass = CharClass.FIGHTER;
		else if (aClass.equals( oldMe.getString("DnDGUI.Monk") )) characterClass = CharClass.MONK;
		else if (aClass.equals( oldMe.getString("DnDGUI.Paladin") )) characterClass = CharClass.PALADIN;
		else if (aClass.equals( oldMe.getString("DnDGUI.Ranger") )) characterClass = CharClass.RANGER;
		else if (aClass.equals( oldMe.getString("DnDGUI.Rogue") )) characterClass = CharClass.ROGUE;
		else if (aClass.equals( oldMe.getString("DnDGUI.Sorcerer") )) characterClass = CharClass.SORCERER;
		else if (aClass.equals( oldMe.getString("DnDGUI.Warlock") )) characterClass = CharClass.WARLOCK;
		else if (aClass.equals( oldMe.getString("DnDGUI.Wizard") )) characterClass = CharClass.WIZARD;
	}
	public CharClass getCharClass() {
		return characterClass;
	}
	
	// Setter & Getter for the character's Name.
	public void setName(String aName) {
		name = aName;
	}
	public String getName(){
		return name;
	}
	
	// Setter & Getter for the character's Strength Ability Score, as well as it's related Skill Scores.
	public void setStrength(int aStrength) {
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Dragonborn")) || 
				race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Halforc"))) {
			aStrength += 2;
		}
		strength = aStrength;
		athletics = getModifier(aStrength);
	}
	public int getStrength() {
		return strength;
	}
	
	// Setter & Getter for the character's Dexterity Ability Score, as well as it's related Skill Scores.
	public void setDexterity(int aDexterity) {
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Elf")) || 
				race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Halfling"))) {
			aDexterity += 2;
		}
		dexterity = aDexterity;
		acrobatics = getModifier(aDexterity);
		sleightOfHand = getModifier(aDexterity);
		stealth = getModifier(aDexterity);
	}
	public int getDexterity() {
		return dexterity;
	}
	
	// Setter & Getter for the character's Constitution Ability Score.
	public void setConstitution(int aConstitution) {
		constitution = aConstitution;
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Dwarf"))) {
			constitution += 2;
		}
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Halforc"))) {
			constitution++;
		}
		
	}
	public int getConstitution() {
		return constitution;
	}
	
	// Setter & Getter for the character's Intelligence Ability Score, as well as it's related Skill Scores.
	public void setIntelligence(int aIntelligence) {
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Gnome"))) {
			aIntelligence += 2;
		} else if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Tiefling"))) {
			aIntelligence++;
		}
		intelligence = aIntelligence;
		arcana = getModifier(aIntelligence); 
		history = getModifier(aIntelligence);
		investigation = getModifier(aIntelligence);
		nature = getModifier(aIntelligence);
		religion = getModifier(aIntelligence);
	}
	public int getIntelligence() {
		return intelligence;
	}
	
	// Setter & Getter for the character's Wisdom Ability Score, as well as it's related Skill Scores.
	public void setWisdom(int aWisdom) {
		wisdom = aWisdom;
		animalHandling = getModifier(aWisdom);
		insight = getModifier(aWisdom);
		medicine = getModifier(aWisdom);
		perception = getModifier(aWisdom);
		survival = getModifier(aWisdom);
	}
	public int getWisdom() {
		return wisdom;
	}
	
	// Setter & Getter for the character's Charisma Ability Score, as well as it's related Skill Scores.
	public void setCharisma(int aCharisma) {
		if (race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Dragonborn")) || 
				race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Halfelf"))
				 || race.getRaceString(oldMe).equals(oldMe.getString("DnDGUI.Tiefling"))) {
			aCharisma += 2;
		}
		charisma = aCharisma;
		deception = getModifier(aCharisma);
		intimidation = getModifier(aCharisma);
		performance = getModifier(aCharisma);
		persuasion = getModifier(aCharisma);
	}
	public int getCharisma() {
		return charisma;
	}
	
	// Setter & Getter for the character's Alignment.
	public void setAlignment(String aAlignment) {
		alignment = aAlignment;
	}
	public String getAlignment() {
		return alignment;
	}
	
	public CheckBox[] getSkillProficiencyOptions() {
		return skills;
	}
	
	/**
	 * Each Class can choose from a certain set of skills to be proficient at. Unfortunately, there is no way 
	 * getting around a HUGE method for this.
	 * @param c = the character's Class
	 */
	public void setSkillProficiencyOptions(CharClass c) {
		if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Barbarian") ))  
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.animalHandling")),
				new CheckBox(me.getString("DnDGUI.athletics")),new CheckBox(me.getString("DnDGUI.intimidation")), new CheckBox(me.getString("DnDGUI.nature")), 
				new CheckBox(me.getString("DnDGUI.perception")),new CheckBox(me.getString("DnDGUI.survival"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Bard") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.animalHandling")),
				new CheckBox(me.getString("DnDGUI.athletics")),new CheckBox(me.getString("DnDGUI.intimidation")), new CheckBox(me.getString("DnDGUI.nature")), 
				new CheckBox(me.getString("DnDGUI.perception")),new CheckBox(me.getString("DnDGUI.survival"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Cleric") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.history")), new CheckBox(me.getString("DnDGUI.insight")),
				new CheckBox(me.getString("DnDGUI.medicine")), new CheckBox(me.getString("DnDGUI.persuasion")), new CheckBox(me.getString("DnDGUI.religion"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Druid") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.animalHandling")), new CheckBox(me.getString("DnDGUI.arcana")), 
				new CheckBox(me.getString("DnDGUI.insight")), new CheckBox(me.getString("DnDGUI.medicine")), new CheckBox(me.getString("DnDGUI.nature")),
				new CheckBox(me.getString("DnDGUI.perception")), new CheckBox(me.getString("DnDGUI.religion")),new CheckBox(me.getString("DnDGUI.survival"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Fighter") ))  
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.acrobatics")), new CheckBox(me.getString("DnDGUI.animalHandling")),
				new CheckBox(me.getString("DnDGUI.athletics")), new CheckBox(me.getString("DnDGUI.history")), new CheckBox(me.getString("DnDGUI.insight")),
				new CheckBox(me.getString("DnDGUI.intimidation")), new CheckBox(me.getString("DnDGUI.perception")),new CheckBox(me.getString("DnDGUI.survival"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Monk") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.acrobatics")), new CheckBox(me.getString("DnDGUI.athletics")),
				new CheckBox(me.getString("DnDGUI.history")),new CheckBox(me.getString("DnDGUI.insight")), new CheckBox(me.getString("DnDGUI.religion")),
				new CheckBox(me.getString("DnDGUI.stealth"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Paladin") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.athletics")), new CheckBox(me.getString("DnDGUI.insight")),
				new CheckBox(me.getString("DnDGUI.intimidation")),new CheckBox(me.getString("DnDGUI.medicine")), 
				new CheckBox(me.getString("DnDGUI.persuasion")),new CheckBox(me.getString("DnDGUI.religion"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Ranger") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.acrobatics")), new CheckBox(me.getString("DnDGUI.athletics")),
				new CheckBox(me.getString("DnDGUI.insight")), new CheckBox(me.getString("DnDGUI.investigation")), new CheckBox(me.getString("DnDGUI.nature")),
				new CheckBox(me.getString("DnDGUI.perception")), new CheckBox(me.getString("DnDGUI.stealth")), new CheckBox(me.getString("DnDGUI.survival"))}; 
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Rogue") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.animalHandling")),new CheckBox(me.getString("DnDGUI.athletics")),
				new CheckBox(me.getString("DnDGUI.deception")),new CheckBox(me.getString("DnDGUI.insight")), new CheckBox(me.getString("DnDGUI.intimidation")),
				new CheckBox(me.getString("DnDGUI.investigation")), new CheckBox(me.getString("DnDGUI.perception")), 
				new CheckBox(me.getString("DnDGUI.performance")), new CheckBox(me.getString("DnDGUI.persuasion")), 
				new CheckBox(me.getString("DnDGUI.sleightOfHand")), new CheckBox(me.getString("DnDGUI.stealth"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Sorcerer") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.arcana")), new CheckBox(me.getString("DnDGUI.deception")), 
				new CheckBox(me.getString("DnDGUI.insight")), new CheckBox(me.getString("DnDGUI.intimidation")), 
				new CheckBox(me.getString("DnDGUI.persuasion")), new CheckBox(me.getString("DnDGUI.religion"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Warlock") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.arcana")), new CheckBox(me.getString("DnDGUI.deception")), 
				new CheckBox(me.getString("DnDGUI.history")), new CheckBox(me.getString("DnDGUI.intimidation")), 
				new CheckBox(me.getString("DnDGUI.investigation")), new CheckBox(me.getString("DnDGUI.nature")), new CheckBox(me.getString("DnDGUI.religion"))};
		else if (c.getClassString(oldMe).equals( oldMe.getString("DnDGUI.Wizard") )) 
				skills = new CheckBox[] {new CheckBox(me.getString("DnDGUI.arcana")), new CheckBox(me.getString("DnDGUI.history")), 
				new CheckBox(me.getString("DnDGUI.insight")),new CheckBox(me.getString("DnDGUI.investigation")), new CheckBox(me.getString("DnDGUI.medicine")), 
				new CheckBox(me.getString("DnDGUI.religion"))};
	}
}

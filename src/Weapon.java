// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

public class Weapon {

	// Each weapon has a name, a type, and an attack damage.
	private String weaponName, weaponType, damage;
	
	public Weapon(String name, String type, String hit) {
		weaponName = name;
		weaponType = type;
		damage = hit;
	}
	
	public String getWeaponName() {
		return weaponName;
	}
	
	public String getWeaponType() {
		return weaponType;
	}
	
	public String getDamage() {
		return damage;
	}
}

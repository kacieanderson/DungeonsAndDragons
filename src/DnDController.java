// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DnDController extends Application {

	private static DnDGUI creationGUI;
	private static CharacterTrackerGUI tracker;
	private static Character character;
	private static Locale currentLocale;
	private static ResourceBundle me;
	private static HashMap<String, String> login = new HashMap<String, String>();
	public Stage stage;

	/**
	 * Start the application with the computer's specific Locale, then launch (run "start" method).
	 * @param args
	 */
	public static void main(String[] args) {
		String language;
		String country;

		File accounts = new File ("accounts.txt");
		Scanner scanner;
		try {
			scanner = new Scanner (accounts);
			String line;
			while (scanner.hasNextLine() && !isBlank(line = scanner.nextLine())) {

				String[] accountData = line.split("/");
				String user = accountData[0];
				String password = accountData[1];
				login.put(user, password);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (args.length != 2) {
			language = new String("en");
			country = new String("US");
		} else {
			language = new String(args[0]);
			country = new String(args[1]);
		}

		currentLocale = new Locale(language, country);
		me = ResourceBundle.getBundle("messages", currentLocale);

		creationGUI = new DnDGUI(currentLocale, me);
		launch(args);
	}

	/**
	 * Begin the applicaton with the Splash Screen of creationGUI, an instance of DnDGUI.java.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		creationGUI.start(stage);
		creationGUI.setUpVBoxes();
		creationGUI.createSplashScreen();
		addSplashScreenListeners();
	}

	/**
	 * There are two buttons on the Splash Screen: one to use the application in English,
	 * and one to use the application in Japanese. Clicking one or the other will update
	 * the Locale and ResourceBundles in creationGUI, then will cause the Log In and Sign Up buttons to appear.
	 */
	public void addSplashScreenListeners() {
		creationGUI.newCharacterBtnJa.setOnAction( e -> { // Use the GUI in Japanese
			creationGUI.currentLocale = new Locale("ja", "JP");
			creationGUI.me = ResourceBundle.getBundle("messages", creationGUI.currentLocale);
			creationGUI.splashVBox.getChildren().remove(3); creationGUI.splashVBox.getChildren().remove(3);
			creationGUI.titleLbl.setText(creationGUI.me.getString("DnDGUI.welcome")); 
			creationGUI.subTitleLbl.setText(creationGUI.me.getString("DnDGUI.loginsignup")); 
			creationGUI.splashLoginBtn.setText(creationGUI.me.getString("DnDGUI.login"));
			creationGUI.splashSignUpBtn.setText(creationGUI.me.getString("DnDGUI.signup"));
			creationGUI.splashVBox.getChildren().addAll(creationGUI.splashLoginBtn, creationGUI.splashSignUpBtn);
			addLoginSignupScreenListeners();
		});

		creationGUI.newCharacterBtnEn.setOnAction( e -> {
			creationGUI.currentLocale = new Locale("en", "US"); // Use the GUI in English
			creationGUI.me = ResourceBundle.getBundle("messages", creationGUI.currentLocale);
			creationGUI.splashVBox.getChildren().remove(3); creationGUI.splashVBox.getChildren().remove(3);
			creationGUI.splashVBox.getChildren().remove(1); creationGUI.splashVBox.getChildren().remove(1);
			creationGUI.titleLbl.setText(creationGUI.me.getString("DnDGUI.welcome")); 
			creationGUI.subTitleLbl.setText(creationGUI.me.getString("DnDGUI.loginsignup")); 
			creationGUI.splashVBox.getChildren().addAll(creationGUI.titleLbl, creationGUI.subTitleLbl, 
					creationGUI.splashLoginBtn, creationGUI.splashSignUpBtn);
			addLoginSignupScreenListeners();
		});
	}

	/**
	 * The user can either log in or sign up - each has different functionalities. This method 
	 * separates the two.
	 */
	public void addLoginSignupScreenListeners() {
		creationGUI.splashLoginBtn.setOnAction( e -> {
			creationGUI.createLoginScreen();
			addLoginScreenListeners();
		});

		creationGUI.splashSignUpBtn.setOnAction( e -> {
			creationGUI.createSignupScreen();
			addSignupScreenListeners() ;
		});
	}

	/**
	 * Makes signing up easier so the user can traverse fields via TAB key and move on by pressing ENTER
	 * instead of clicking "Sign Up" button.
	 */
	public void addSignupScreenListeners() {
		creationGUI.signupUserNameField.setOnKeyPressed( e -> {
			creationGUI.signupPasswordField.setFocusTraversable(true);
			if (e.getCode() == KeyCode.TAB) {
				creationGUI.signupPasswordField.requestFocus();
			}
		});

		creationGUI.signupPasswordField.setOnKeyPressed( e -> {
			if (e.getCode() == KeyCode.ENTER) {
				signupHelper();
			}
		});

		creationGUI.signupBtn.setOnAction( e -> { 
			signupHelper();
		});
	}

	/**
	 * Helper function to reuse signup code.
	 * The user inputs a new username and password. If the username is already in use,
	 * an error message appears. Otherwise, the new information is stored in the accounts.txt
	 * file and the user can continue creation. When the user opens the program again, he or she can
	 * log in.
	 */
	public void signupHelper() {
		if (login.containsKey(creationGUI.signupUserNameField.getText())) {
			creationGUI.signupErrorLbl.setText("That username is already registered.");
		} else if( creationGUI.signupUserNameField.getText().length() > 0 && creationGUI.signupPasswordField.getText().length() > 0
				&& !creationGUI.signupUserNameField.getText().toString().startsWith(" ")
				&& !creationGUI.signupPasswordField.getText().toString().startsWith(" ")) {

			try(FileWriter fWriter = new FileWriter("accounts.txt", true); BufferedWriter bWriter = new BufferedWriter(fWriter);
					PrintWriter print = new PrintWriter(bWriter)) {
				print.println(creationGUI.signupUserNameField.getText() + "/" + creationGUI.signupPasswordField.getText());
				creationGUI.createChooseRaceScreen();
				addRaceScreenListeners();
			} catch (IOException e) {
				//exception handling left as an exercise for the reader
			}

		}

	}

	/**
	 * Makes logging in easier so the user can traverse fields via TAB key and move on by pressing ENTER
	 * instead of clicking "Log In" button.
	 */
	public void addLoginScreenListeners() {

		creationGUI.userNameField.setOnKeyPressed( e -> {
			creationGUI.passwordField.setFocusTraversable(true);
			if (e.getCode() == KeyCode.TAB) {
				creationGUI.passwordField.requestFocus();
			}
		});

		creationGUI.passwordField.setOnKeyPressed( e -> {
			if (e.getCode() == KeyCode.ENTER) {
				loginHelper();
			}
		});

		creationGUI.loginBtn.setOnAction( e -> { 
			loginHelper();
		});
	}

	/**
	 * Helper function to reuse login code.
	 * Checks the user inputs in the username and password fields against the login map.
	 * Displays an error message if incorrect username or password is entered. Otherwise,
	 * "Choose a Race" screen appears.
	 */
	public void loginHelper() {	
		if (login.containsKey(creationGUI.userNameField.getText())) {
			//String storedPassword = login.get(creationGUI.userNameField.getText());
			if (( login.get(creationGUI.userNameField.getText()) ).equals(creationGUI.passwordField.getText())) {
				creationGUI.createChooseRaceScreen();
				addRaceScreenListeners();
			} else { // User entered incorrect password
				creationGUI.loginErrorLbl.setText(creationGUI.me.getString("DnDGUI.passwordError"));
			}
		} else {
			creationGUI.loginErrorLbl.setText(creationGUI.me.getString("DnDGUI.usernameError"));
		}

	}

	private static boolean isBlank(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * The "Next" button on the "Choose a Race" screen is disabled until a Race is chosen from
	 * the raceCb ComboBox. Clicking it causes a new instance of Character.java (stored
	 * as "character" in this class) to be made, passing in the chosen Race; it also causes the "Choose a
	 * Class" screen to appear.
	 * Also, choosing different Races from the raceCb ComboBox on the "Choose a Race" screen will cause
	 * an image and description of the selected race to appear.
	 */
	public void addRaceScreenListeners() {
		creationGUI.raceNextBtn.setOnAction( e -> {  // Create the new Character. Next button goes to the next screen
			character = new Character(creationGUI.raceCb.getValue(), creationGUI.currentLocale);
			creationGUI.createChooseClassScreen();
			addClassScreenListeners();
		});

		creationGUI.raceCb.setOnAction( e -> { // Display selection-specific Race image and description
			String r = creationGUI.raceCb.getValue();
			creationGUI.eachRaceHBox.setVisible(true);
			if (r.equals( creationGUI.me.getString("DnDGUI.Dwarf")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/dwarf.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeDwarves"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Elf")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/elf.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeElves"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Halfling")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/halfling.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeHalflings"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Human")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/human.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeHumans"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Dragonborn")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/dragonborn.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeDragonborns"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Gnome")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/gnome.jpg"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeGnomes"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Halfelf")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/halfelf.jpg"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeHalfelves"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Halforc")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/halforc.jpg"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeHalforcs"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Tiefling")) ) {
				creationGUI.eachRaceImg.setImage(new Image("images/tiefling.png"));
				creationGUI.eachRaceLbl.setText(creationGUI.me.getString("DnDGUI.DescribeTieflings"));
			}
			if (!r.equals(creationGUI.me.getString("DnDGUI.raceCb"))) { // Enable the Next button once a Race is chosen			
				creationGUI.raceNextBtn.setDisable(false);
				creationGUI.raceNextBtn.requestFocus();
			}
		});
	}

	/**
	 * The "Next" button on the "Choose a Class" screen is disabled until a Class is chosen from the
	 * classCb ComboBox. When clicked, the selected Class will be assigned to "character," and methods
	 * in Character.java are also called to set the character's HP and determine which Skill options
	 * the user will have at a later point in time. It also causes the "Set Ability Scores" screen to appear.
	 * 
	 * Also, choosing a class from the classCb ComboBox causes a description of that class to appear.
	 */
	public void addClassScreenListeners() {
		creationGUI.classCb.setOnAction( e -> { // Enable the Next button once a Class is chosen.
			String r = creationGUI.classCb.getValue();
			creationGUI.eachClassHBox.setVisible(true);
			if (r.equals( creationGUI.me.getString("DnDGUI.Barbarian")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/barbarian.png"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeBarbarians"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Bard")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/bard.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeBards"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Cleric")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/cleric.png"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeClerics"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Druid")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/druid.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeDruids"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Fighter")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/fighter.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeFighters"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Monk")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/monk.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeMonks"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Paladin")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/paladin.png"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribePaladins"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Ranger")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/ranger.png"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeRangers"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Rogue")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/rogue.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeRogues"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Sorcerer")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/sorcerer.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeSorcerers"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Warlock")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/warlock.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeWarlocks"));
			} else if (r.equals( creationGUI.me.getString("DnDGUI.Wizard")) ) {
				creationGUI.eachClassImg.setImage(new Image("images/wizard.jpg"));
				creationGUI.eachClassLbl.setText(creationGUI.me.getString("DnDGUI.DescribeWizards"));
			}
			if (!creationGUI.classCb.getValue().equals(creationGUI.me.getString("DnDGUI.classCb"))) {
				creationGUI.classNextBtn.setDisable(false);
				creationGUI.classNextBtn.requestFocus();
			}
		});

		creationGUI.classNextBtn.setOnAction( e -> {
			character.setClass(creationGUI.classCb.getValue());
			character.setHP();
			character.setSkillProficiencyOptions(character.getCharClass());
			creationGUI.skills = character.getSkillProficiencyOptions();
			creationGUI.createSetAbilitiesScreen();
			addSetAbilitiesScreenListeners();
		});
	}

	/**
	 * Add functionality to re-generate Ability scores when the "Randomize Scores" button is clicked.
	 * Set Ability scores and change the View to "Add Skills" screen
	 */
	public void addSetAbilitiesScreenListeners() {
		creationGUI.rerollBtn.setOnAction( e -> {
			creationGUI.addAbilityScores();
		});

		creationGUI.abilitiesNextBtn.setOnAction( e -> {
			character.setStrength(creationGUI.strength); character.setDexterity(creationGUI.dexterity);
			character.setConstitution(creationGUI.constitution); character.setIntelligence(creationGUI.intelligence);
			character.setWisdom(creationGUI.wisdom); character.setCharisma(creationGUI.charisma);
			creationGUI.createSetSkillsScreen();
			addSetSkillsScreenListeners();
		});
	}

	/**
	 * When Next button is clicked, notify Character.java which Skills to add +2 to, and then change the View
	 * to the "Choose Name and Alignment" screen.
	 */
	public void addSetSkillsScreenListeners() {
		creationGUI.skillsNextBtn.setOnAction( e -> {
			for (int i=0; i<creationGUI.skills.length; i++) {
				if (creationGUI.skills[i].isSelected()) {
					character.addSkillProficiencies(creationGUI.skills[i].getUserData().toString());
				}
			}
			creationGUI.createNameAlignmentSkillsScreen();
			addNameAlignmentSkillsScreenListeners();
		});
	}

	/**
	 * Allow the user to enter a Name for their character that does not exceed 25 characters.
	 * "Next" button is disabled until the user chooses an alignment and enters a name that does not start
	 * with a space.
	 * When the user clicks Next, set the Character.java Name and Alignment, and open the next stage - the 
	 * character tracker.
	 */
	public void addNameAlignmentSkillsScreenListeners() {
		creationGUI.nameField.setOnKeyPressed( e -> { // Users cannot have a name longer than 25 characters.
			if(creationGUI.nameField.getText().length() > 25) {
				String s = creationGUI.nameField.getText().substring(0, 24);
				creationGUI.nameField.setText(s);
				creationGUI.nameAlignmentNextBtn.requestFocus();
			} // Enable Next button when an alignment is chosen and a name is entered
			if (!creationGUI.alginmentCb.getValue().equals(creationGUI.me.getString("DnDGUI.setAlignment")) && 
					creationGUI.nameField.getText().length() > 0
					&& !creationGUI.nameField.getText().toString().startsWith(" ")) {
				if (e.getCode() == KeyCode.ENTER) {
					nextStageHelper();
				} else {
					creationGUI.nameAlignmentNextBtn.setDisable(false);
				}
			}
		});

		creationGUI.nameAlignmentNextBtn.setOnAction( e -> {
			nextStageHelper();
		});
	}

	/**
	 * Reusable code for switching between character creation and the character tracker.
	 */
	public void nextStageHelper() {
		if (!creationGUI.alginmentCb.getValue().equals(creationGUI.me.getString("DnDGUI.setAlignment"))) {
			character.setAlignment(creationGUI.alginmentCb.getValue());
			character.setName(creationGUI.nameField.getText());
			tracker = new CharacterTrackerGUI(character, creationGUI.currentLocale, creationGUI.me);
			tracker.createTabPane();
			tracker.initializePopupWindows();
			addHPUpdateListeners();
			addExperienceListener();
			addNewWeaponListeners();
			addNewWeaponErrorChecking();
			addNewSpellListeners();

			Stage oldStage = (Stage) creationGUI.nameAlignmentNextBtn.getScene().getWindow();
			oldStage.close();

			try {
				tracker.start(stage);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * User can add HP indefinitely. User can subtract HP until it reaches 0.
	 */
	public void addHPUpdateListeners() {
		tracker.addHP.setOnAction(e -> {
			character.hp = character.getHP() + 1;
			tracker.hpNumLbl.setText(Integer.toString(character.getHP()));
		});

		tracker.subtractHP.setOnAction(e -> {
			if (character.hp > 0) {
				character.hp = character.getHP() - 1;
				tracker.hpNumLbl.setText(Integer.toString(character.getHP()));
			} else {
				tracker.hpNumLbl.setText("0");
			}
		});
	}

	/**
	 * Make sure the user can only enter numeric values in the XP textbox.
	 * Automatically update user level when appropriate
	 */
	public void addExperienceListener() {
		tracker.xpTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("\\d*")) {
					tracker.xpTextField.setText(newValue.replaceAll("[^\\d]", ""));
				} else {
					character.addExperience(Integer.parseInt(tracker.xpTextField.getText()));
					tracker.alignmentLevelLbl.setText(tracker.me.getString("DnDGUI.level") + " " + character.getLevel() 
					+ " " + character.getRace().getRaceString(tracker.OldMe) + " " + character.getCharClass().getClassString(tracker.OldMe));
				}
			}
		});
	}

	public void addNewSpellListeners() {
		tracker.newSpellBtn.setOnAction( e -> {
			newSpellWindow();
		});

		tracker.viewSpellsBtn.setOnAction( e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://www.dandwiki.com/wiki/5e_Spells"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	/**
	 * User can enter a spell name and description.
	 */
	private void newSpellWindow() {
		Stage newSpellStage = new Stage();
		newSpellStage.setTitle(tracker.me.getString("DnDGUI.spellsExample"));
		VBox newNewSpellVBox = new VBox(20);
		Scene newSpellScene = new Scene(newNewSpellVBox, 450, 450);
		tracker.newSpellTitleLbl = new Label(tracker.me.getString("DnDGUI.addNewSpell"));
		newSpellStage.setTitle(tracker.me.getString("DnDGUI.addNewSpell"));
		GridPane newSpellGridPane = new GridPane();
		newSpellGridPane.setHgap(15); newSpellGridPane.setVgap(20); newSpellGridPane.setAlignment(Pos.TOP_LEFT);
		newSpellGridPane.setHgap(15); newSpellGridPane.setVgap(20);
		Label newSpellNameLbl = new Label(tracker.me.getString("DnDGUI.spellName"));
		Label newSpellDescriptionLbl = new Label(tracker.me.getString("DnDGUI.spellDescription"));
		tracker.newSpellDoneBtn = new Button(tracker.me.getString("DnDGUI.doneBtn"));
		tracker.newSpellDoneBtn.setMinHeight(45);
		tracker.newSpellDoneBtn.setMinWidth(190);
		tracker.newSpellDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: #747474;");

		tracker.newSpellDoneBtn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			tracker.newSpellDoneBtn.setEffect(d);
			tracker.newSpellDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #4E4E4E;");
		});

		tracker.newSpellDoneBtn.setOnMouseExited( e -> {
			tracker.newSpellDoneBtn.setEffect(null);
			tracker.newSpellDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #747474;");
		});

		tracker.newSpellNameField = new TextField();
		tracker.spellDescriptionTextArea = new TextArea();
		newSpellGridPane.add(newSpellNameLbl, 0, 0);
		newSpellGridPane.add(tracker.newSpellNameField, 1, 0);
		newSpellGridPane.add(newSpellDescriptionLbl, 0, 1);
		newSpellGridPane.add(tracker.spellDescriptionTextArea, 1, 1);

		tracker.addNewSpellDesign(newNewSpellVBox); // Alignment, spacing, etc.
		newNewSpellVBox.getChildren().addAll(tracker.newSpellTitleLbl,newSpellGridPane, 
				tracker.newSpellDoneBtn, tracker.errorLbl);
		newSpellStage.setScene(newSpellScene);
		newSpellStage.show();
		addNewSpellListeners();
		addNewSpell();
		addNewSpellErrorChecking();

	}

	/**
	 * Add a new spell to the character's spells list (with error handling)
	 */
	private void addNewSpell() {
		tracker.newSpellDoneBtn.setOnAction(e -> {
			if (tracker.newSpellNameField.getUserData()!=null 
					&& tracker.newSpellNameField.getText().length()>1
					&& tracker.spellDescriptionTextArea.getUserData()!=null
					&& tracker.spellDescriptionTextArea.getText().length()>1) {
				tracker.spells.add(tracker.newSpellNameField.getText() + ": " + 
						tracker.spellDescriptionTextArea.getText());
				Stage stage = (Stage) tracker.newSpellDoneBtn.getScene().getWindow();
				stage.close();
			} else {
				tracker.errorLbl.setText("Please fill in all fields!");
			}

		});

	}

	/**
	 * Error handling for "Add New Spell" window; user cannot click "Done" until all
	 * fields have content.
	 */
	private void addNewSpellErrorChecking() {
		tracker.newSpellNameField.setOnKeyPressed(e->{
			tracker.newSpellNameField.setUserData(tracker.newSpellNameField.getText());
			if (tracker.newSpellNameField.getUserData()!=null 
					&& tracker.newSpellNameField.getText().length()>=1) {
				tracker.spellDescriptionTextArea.setDisable(false);
			} 
		});

		tracker.spellDescriptionTextArea.setOnKeyPressed(e->{
			tracker.spellDescriptionTextArea.setUserData(tracker.spellDescriptionTextArea.getText());
			if (tracker.newSpellNameField.getUserData()!=null 
					&& tracker.newSpellNameField.getText().length()>1
					&& tracker.spellDescriptionTextArea.getUserData()!=null
					&& tracker.spellDescriptionTextArea.getText().length()>1) {
				tracker.newSpellDoneBtn.setDisable(false);
			} else {
				tracker.newSpellDoneBtn.setDisable(true);
			}
		});
	}

	/**
	 * Add listeners to "Add New Weapon" and "Weapons List" buttons;
	 * Add listener to "Done" button on add new weapon window
	 */
	public void addNewWeaponListeners() {
		tracker.newWeaponBtn.setOnAction( e -> {
			newWeaponWindow();
		});

		tracker.viewWeaponsBtn.setOnAction( e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://www.dandwiki.com/wiki/5e_Weapons"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Add a new weapon to the character's weapons list (with error handling)
		tracker.newWeaponDoneBtn.setOnAction(e -> {
			if (tracker.newWeaponNameField.getUserData()!=null && tracker.newWeaponNameField.getText().length()>1
					&& tracker.weaponTypeGroup.getSelectedToggle()!=null && tracker.damageTextField.getUserData()!=null
					&& tracker.damageTextField.getText().length()>1) {
				character.addWeapon(tracker.newWeaponNameField.getText(), tracker.weaponTypeGroup.getSelectedToggle().getUserData().toString(), 
						tracker.damageTextField.getText());
				tracker.weapons.add(tracker.newWeaponNameField.getText() + " | " + tracker.weaponTypeGroup.getSelectedToggle().getUserData() + " | " +
						tracker.damageTextField.getText());
				Stage stage = (Stage) tracker.newWeaponDoneBtn.getScene().getWindow();
				stage.close();
			} else {
				tracker.errorLbl.setText("Please fill in all fields!");
			}
		});
	}

	/**
	 * User can enter a weapon name, type, and hit damage for a new weapon.
	 */
	private void newWeaponWindow() {
		Stage newWeaponStage = new Stage();
		VBox newNewWeaponVBox = new VBox(20);
		Scene newWeaponScene = new Scene(newNewWeaponVBox, 450, 450);
		newWeaponStage.setTitle(tracker.me.getString("DnDGUI.addNewWeapon"));
		GridPane newWeaponGridPane = new GridPane();
		newWeaponGridPane.setHgap(15); newWeaponGridPane.setVgap(20);
		tracker.newWeaponNameField.setText(""); tracker.damageTextField.setText(" ");
		tracker.addNewWeaponDesign(newNewWeaponVBox); // Alignment, spacing, etc.
		newWeaponGridPane.add(tracker.newWeaponNameLbl, 0, 0); newWeaponGridPane.add(tracker.newWeaponNameField, 1, 0);
		newWeaponGridPane.add(tracker.newWeaponTypeLbl, 0, 1); newWeaponGridPane.add(tracker.meleeRb, 1, 1);
		newWeaponGridPane.add(tracker.rangedRb, 1,2);
		newWeaponGridPane.add(tracker.newWeaponDamageLbl, 0, 3); newWeaponGridPane.add(tracker.damageTextField, 1, 3);
		tracker.weaponTypeGroup = new ToggleGroup();
		tracker.weaponTypeGroup.getToggles().addAll(tracker.meleeRb,tracker.rangedRb);

		newNewWeaponVBox.getChildren().addAll(tracker.newWeaponTitleLbl, newWeaponGridPane, tracker.newWeaponDoneBtn, tracker.errorLbl);
		newWeaponStage.setScene(newWeaponScene); newWeaponStage.show();
	}

	/**
	 * Error handling for "Add New Weapon" window; user cannot click "Done" until all
	 * fields have content.
	 */
	public void addNewWeaponErrorChecking() {
		tracker.newWeaponNameField.setOnKeyPressed(e->{
			tracker.newWeaponNameField.setUserData(tracker.newWeaponNameField.getText());
			if (tracker.newWeaponNameField.getUserData()!=null 
					&& tracker.newWeaponNameField.getText().length()>=1) {
				tracker.meleeRb.setDisable(false);
				tracker.rangedRb.setDisable(false);
			}
		});

		tracker.meleeRb.setOnMouseClicked(e ->{
			if (tracker.newWeaponNameField.getUserData()!=null 
					&& tracker.newWeaponNameField.getText().length()>=1) {
				tracker.damageTextField.setDisable(false);
			}
		});

		tracker.rangedRb.setOnMouseClicked(e ->{
			if (tracker.newWeaponNameField.getUserData()!=null 
					&& tracker.newWeaponNameField.getText().length()>=1) {
				tracker.damageTextField.setDisable(false);
			}
		});

		tracker.damageTextField.setOnKeyPressed(e->{
			tracker.damageTextField.setUserData(tracker.damageTextField.getText());
			if (tracker.newWeaponNameField.getUserData()!=null 
					&& tracker.newWeaponNameField.getText().length()>1
					&& tracker.weaponTypeGroup.getSelectedToggle()!=null
					&& tracker.damageTextField.getUserData()!=null
					&& tracker.damageTextField.getText().length()>1) {
				tracker.newWeaponDoneBtn.setDisable(false);
			} else {
				tracker.newWeaponDoneBtn.setDisable(true);
			}
		});
	}

	public Locale getLocale() {
		return currentLocale;
	}

	public ResourceBundle getResourceBundle() {
		return me;
	}

}

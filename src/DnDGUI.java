// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DnDGUI extends Application{
	BorderPane bPane;
	protected ImageView diceImg, eachRaceImg, eachClassImg;
	protected VBox splashVBox, chooseRaceVBox, headerVBox, chooseClassVBox, setAbilitiesVBox,
	setSkillsVBox, checkVBox, setNameAlignVBox, randomNumbersVBox, loginVBox, signupVBox, loginSignupVBox;
	protected Label titleLbl, subTitleLbl, stepLbl, raceDescriptionsLbl, eachRaceLbl, eachClassLbl,
	classDescriptionsLbl, abilitiesDescriptionsLbl, skillDescriptionsLbl, nameLbl,
	alignmentLbl, strengthLbl, charismaLbl, dexterityLbl, intelligenceLbl,
	wisdomLbl, constitutionLbl, hintLbl, loginErrorLbl, signupErrorLbl;
	CheckBox[] skills;
	protected Button newCharacterBtnEn, newCharacterBtnJa, loginBtn, raceNextBtn, classNextBtn, rerollBtn, abilitiesNextBtn,
	skillsNextBtn, nameAlignmentNextBtn, splashLoginBtn, splashSignUpBtn, signupBtn;
	protected ComboBox<String> alginmentCb;
	protected ComboBox<String> raceCb, classCb;
	private Stream<CharClass> classes;
	private Stream<Race> races;
	private String[] raceNames, classNames;
	protected int numChecked, strength, charisma, dexterity, intelligence,
	wisdom, constitution, counter;
	protected TextField nameField, userNameField, passwordField, signupUserNameField, signupPasswordField;
	protected HBox eachRaceHBox, eachClassHBox;
	
	//private Character character;
	protected Locale currentLocale;
	protected ResourceBundle me;

	/** Constructor
	 * 
	 * @param currentLocale = Locale retrieved from args in main
	 * @param me = ResourceBundle for current locale
	 */
	public DnDGUI(Locale currentLocaleA, ResourceBundle meA) {
		currentLocale = currentLocaleA;
		me = meA;
	}

	@Override
	public void start(Stage firstStage) throws Exception {
		bPane = new BorderPane();
		Scene scene = new Scene(bPane, 815, 715);
		scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
		firstStage.setScene(scene);
		firstStage.setResizable(false);
		firstStage.setTitle(me.getString("DnDGUI.subTitle"));
		firstStage.show();
	}

	public void setUpVBoxes() {
		splashVBox = new VBox(10);
		chooseRaceVBox = new VBox(10);
		headerVBox = new VBox();
		chooseClassVBox = new VBox(10);
		setAbilitiesVBox = new VBox(15);
		randomNumbersVBox = new VBox(7);
		setSkillsVBox = new VBox(15);
		checkVBox = new VBox(10);
		setNameAlignVBox = new VBox(15);
		loginVBox = new VBox(15);
		signupVBox = new VBox(15);
	}

	/**
	 * Generate ability scores as if you are rolling a six-sided die.
	 * Each ability score should be unique
	 * @param currentAbility: Each subsequent ability should keep "rolling" until it is
	 * unique from all the ability scores that came before it.
	 * @return integer value for the ability score.
	 */
	public int generateAbilities(int currentAbility) {

		int total = 0;
		for (int i=0; i<=2; i++) total += (int) (Math.random() * 6) + 1;

		switch(currentAbility) {
		case 1: return total; // Strength
		case 2: if (total == strength) total = generateAbilities(2); // Dexterity
		case 3: if ((total == strength) || (total == dexterity)) total = generateAbilities(3); // Constitution
		case 4: if ((total == strength) || (total == dexterity) || (total == constitution)) // Intelligence
			total = generateAbilities(4);
		case 5: if ((total == strength) || (total == dexterity) || (total == constitution) // Wisdom
				|| (total == intelligence)) total = generateAbilities(5);
		case 6: if ((total == strength) || (total == dexterity) || (total == constitution) // Charisma
				|| (total == intelligence) || (total == wisdom)) total = generateAbilities(6);
		}
		return total;
	}

	/**
	 * A splash screen with an image of a 20-sided die, as well as a title, a subtitle,
	 * and two button options to use the GUI in either English or Japanese.
	 */
	public void createSplashScreen() {
		diceImg = new ImageView(); // 20-sided die image
		diceImg.setImage(new Image("images/dice.png"));
		diceImg.setAccessibleText("Twenty-sided die");
		diceImg.setFocusTraversable(true);
		// Title and subtitle
		titleLbl = new Label(me.getString("DnDGUI.title"));
		subTitleLbl = new Label (me.getString("DnDGUI.subTitle"));
		
		// Language option buttons
		newCharacterBtnEn = new Button(me.getString("DnDGUI.newCharButtonEn"));
		newCharacterBtnJa = new Button(me.getString("DnDGUI.newCharButtonJa"));
		// Login and Signup buttons
		splashLoginBtn = new Button(me.getString("DnDGUI.login")); 
		splashSignUpBtn = new Button(me.getString("DnDGUI.signup"));

		splashScreenDesign(splashLoginBtn, splashSignUpBtn); // Spacing and Sizing
		splashScreenDesign(newCharacterBtnEn, newCharacterBtnJa); // Spacing and Sizing
		splashVBox.getChildren().addAll(diceImg, titleLbl, subTitleLbl, newCharacterBtnEn, newCharacterBtnJa);
		bPane.setCenter(splashVBox);
	}

	/**
	 * Formatting, padding, alignment, sizing for the splash screen
	 */
	public void splashScreenDesign(Button btn1, Button btn2) {
		splashVBox.setAlignment(Pos.CENTER);
		titleLbl.setFont(Font.font("Helvetica", FontWeight.BOLD,30));
		titleLbl.setPadding(new Insets(20,0,0,0));
		subTitleLbl.setFont(Font.font(19));
		subTitleLbl.setPadding(new Insets(0,0,15,0));
		btn1.setMinWidth(195);
		btn1.setMinHeight(45);
		btn1.getStyleClass().add("button");
		btn1.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		btn2.setMinWidth(195);
		btn2.setMinHeight(45);
		btn2.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		btn2.getStyleClass().add("button");
		btn1.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			btn1.setEffect(d);
		});
		btn2.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			btn2.setEffect(d);
		});
		btn1.setOnMouseExited( e -> {
			btn1.setEffect(null);
		});
		btn2.setOnMouseExited( e -> {
			btn2.setEffect(null);
			
		});
	}
	
	public void createSignupScreen() {
		stepLbl = new Label(me.getString("DnDGUI.signup"));
		stepLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
		hintLbl = new Label(me.getString("DnDGUI.signupHint"));
		signupErrorLbl = new Label("");
		signupErrorLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		signupErrorLbl.setPadding(new Insets(20,20,20,20));
		signupUserNameField = new TextField(); 
		signupUserNameField.setPromptText(me.getString("DnDGUI.uname"));
		signupUserNameField.setFocusTraversable(false);
		signupPasswordField = new TextField();
		signupPasswordField.setPromptText(me.getString("DnDGUI.pass"));
		signupPasswordField.setFocusTraversable(false);
		signupUserNameField.setMaxWidth(250); signupUserNameField.setMinHeight(45); signupUserNameField.setFont(Font.font(14));
		signupPasswordField.setMaxWidth(250); signupPasswordField.setMinHeight(45); signupPasswordField.setFont(Font.font(14));
		signupBtn = new Button(me.getString("DnDGUI.signup"));
		setScreenDesign(loginVBox, hintLbl, signupBtn); // Formatting
		signupBtn.setDisable(false);
		signupBtn.setMinWidth(250);
		headerVBox.setAlignment(Pos.CENTER);
		headerVBox.getChildren().add(stepLbl);
		signupVBox.getChildren().addAll(headerVBox, signupUserNameField, signupPasswordField, signupBtn, signupErrorLbl);
		signupVBox.setAlignment(Pos.CENTER);
		bPane.setCenter(signupVBox);
	}
	
	/**
	 * A log in screen with a title and instructions, two text fields for username and password,
	 * and a "Log in" button.
	 */
	public void createLoginScreen() {
		stepLbl = new Label(me.getString("DnDGUI.login"));
		stepLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
		hintLbl = new Label(me.getString("DnDGUI.hintLbl"));
		loginErrorLbl = new Label("");
		loginErrorLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		loginErrorLbl.setPadding(new Insets(20,20,20,20));
		userNameField = new TextField(); 
		userNameField.setPromptText(me.getString("DnDGUI.uname"));
		userNameField.setFocusTraversable(false);
		passwordField = new TextField();
		passwordField.setPromptText(me.getString("DnDGUI.pass"));
		passwordField.setFocusTraversable(false);
		userNameField.setMaxWidth(250); userNameField.setMinHeight(45); userNameField.setFont(Font.font(14));
		passwordField.setMaxWidth(250); passwordField.setMinHeight(45); passwordField.setFont(Font.font(14));
		loginBtn = new Button(me.getString("DnDGUI.login"));
		setScreenDesign(loginVBox, hintLbl, loginBtn); // Formatting
		loginBtn.setMinWidth(250);
		hintLbl.setFont(Font.font(12)); hintLbl.setMaxWidth(250);
		hintLbl.setTextAlignment(TextAlignment.LEFT);
		loginBtn.setDisable(false);
		headerVBox.setAlignment(Pos.CENTER);
		headerVBox.getChildren().add(stepLbl);
		loginVBox.getChildren().addAll(headerVBox, userNameField, passwordField, loginBtn, loginErrorLbl);
		loginVBox.setAlignment(Pos.CENTER);
		bPane.setCenter(loginVBox);
	}

	/**
	 * Screen for character to choose their Race. Has a title label, a description of
	 * what Races are, a combo box to choose a Race, and a Button to go to the next 
	 * window (only enabled when a race is selected in the combo box).
	 */
	public void createChooseRaceScreen() {
		eachRaceImg = new ImageView(); // Will be set to image of selected race
		eachRaceImg.maxHeight(50);
		stepLbl.setText(me.getString("DnDGUI.step1Lbl"));
		stepLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 30)); stepLbl.setPadding(new Insets(30,0,0,0));
		raceDescriptionsLbl = new Label(me.getString("DnDGUI.raceDescriptions"));
		eachRaceLbl = new Label("");
		eachRaceLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		eachRaceLbl.setWrapText(true);
		raceCb = new ComboBox<String>();
		raceCb.getStyleClass().add("combobox");
		races = Arrays.stream(Race.values());
		raceNames = new String[9];
		counter = 0;
		races.forEach(race -> {
			raceNames[counter] = race.getRaceString(me);
			counter++;
		});
		for (int i=0; i<raceNames.length; i++) {
			raceCb.getItems().add(raceNames[i]);
		}
		raceCb.setPromptText(me.getString("DnDGUI.raceCb"));
		raceCb.setMinWidth(195); raceCb.setMinHeight(35);
		raceNextBtn = new Button(me.getString("DnDGUI.nextBtn"));

		setScreenDesign(chooseRaceVBox, raceDescriptionsLbl, raceNextBtn); // Formatting
		eachRaceHBox = new HBox(30);
		eachRaceHBox.setMaxWidth(550);
		eachRaceHBox.setStyle("-fx-background-color: #FFFFFF;" + "-fx-padding: 20;" + 
				"-fx-border-insets: 20px;" + "-fx-background-insets: 20px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		eachRaceHBox.setVisible(false);
		eachRaceHBox.setAlignment(Pos.CENTER);
		eachRaceHBox.getChildren().addAll(eachRaceImg, eachRaceLbl);
		chooseRaceVBox.getChildren().addAll(raceDescriptionsLbl, raceCb, raceNextBtn, eachRaceHBox);
		bPane.setTop(headerVBox);
		bPane.setCenter(chooseRaceVBox);
	}

	/**
	 * Screen for character to choose their Class. Has a title label, a description of
	 * what Classes are, a combo box to choose a Class, and a Button to go to the next 
	 * window (only enabled when a Class is selected in the combo box).
	 */
	public void createChooseClassScreen() {
		eachClassImg = new ImageView(); // Will be set to image of selected race
		eachClassImg.maxHeight(50);
		stepLbl.setText(me.getString("DnDGUI.step2Lbl"));
		classDescriptionsLbl = new Label(me.getString("DnDGUI.classDescriptions"));
		eachClassLbl = new Label("");
		eachClassLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		eachClassLbl.setWrapText(true);
		classCb = new ComboBox<String>();
		classCb.getStyleClass().add("combobox");
		classes = Arrays.stream(CharClass.values());
		classNames = new String[12];
		counter = 0;
		classes.forEach(charClass -> {
			classNames[counter] = charClass.getClassString(me);
			counter++;
			//classCb.getItems().add(charClass);
		});
		for (int i=0; i< classNames.length;i++) {
			classCb.getItems().add(classNames[i]);
		}
		classCb.setPromptText(me.getString("DnDGUI.classCb"));
		classCb.setMinWidth(195); classCb.setMinHeight(35);
		classNextBtn = new Button(me.getString("DnDGUI.nextBtn"));

		setScreenDesign(chooseClassVBox, classDescriptionsLbl, classNextBtn);
		//classCb.setStyle("-fx-font-size: 18;");
		eachClassHBox = new HBox(30);
		eachClassHBox.setMaxWidth(550);
		eachClassHBox.setStyle("-fx-background-color: #FFFFFF;" + "-fx-padding: 20;" + 
				"-fx-border-insets: 20px;" + "-fx-background-insets: 20px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		eachClassHBox.setVisible(false);
		eachClassHBox.setAlignment(Pos.CENTER);
		eachClassHBox.getChildren().addAll(eachClassImg, eachClassLbl);
		chooseClassVBox.getChildren().addAll(classDescriptionsLbl, classCb, classNextBtn, eachClassHBox);
		bPane.setCenter(chooseClassVBox);
		skills = new CheckBox[] {};
	}

	/**
	 * Screen for character to choose their Ability scores. Has a title label, a description of
	 * what Abilities are, a list of randomly generated scores, a button to re-generate scores, 
	 * and a Button to go to the next window.
	 */
	public void createSetAbilitiesScreen() {
		stepLbl.setText(me.getString("DnDGUI.step3Lbl"));
		abilitiesDescriptionsLbl = new Label(me.getString("DnDGUI.AScoreDescription") + 
				"\n\n" + me.getString("DnDGUI.abilityDescriptions"));
		// All labels to be filled
		strengthLbl = new Label(); charismaLbl = new Label(); dexterityLbl = new Label(); 
		intelligenceLbl = new Label(); wisdomLbl = new Label(); constitutionLbl = new Label();

		addAbilityScores(); // Generate scores and add them to a VBox
		randomNumbersVBox.setAlignment(Pos.TOP_CENTER); randomNumbersVBox.setMaxWidth(200); 
		randomNumbersVBox.setPadding(new Insets(20, 20, 20, 20));
		randomNumbersVBox.setStyle("-fx-background-color: #FFFFFF;" +  
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		randomNumbersVBox.getChildren().addAll(strengthLbl, charismaLbl, dexterityLbl, 
				intelligenceLbl, wisdomLbl, constitutionLbl);
		rerollBtn = new Button(me.getString("DnDGUI.randomizeScores")); 
		rerollBtn.setMinWidth(195); rerollBtn.setMinHeight(45);
		rerollBtn.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		rerollBtn.getStyleClass().add("button");
		
		rerollBtn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			rerollBtn.setEffect(d);
		});
		
		rerollBtn.setOnMouseExited( e -> {
			rerollBtn.setEffect(null);
		});
		abilitiesNextBtn = new Button(me.getString("DnDGUI.nextBtn"));

		setScreenDesign(setAbilitiesVBox, abilitiesDescriptionsLbl, abilitiesNextBtn);
		abilitiesNextBtn.setDisable(false);
		setAbilitiesVBox.getChildren().addAll(abilitiesDescriptionsLbl, randomNumbersVBox,
				rerollBtn, abilitiesNextBtn);
		bPane.setCenter(setAbilitiesVBox);
	}

	/**
	 * Populate the ability score labels with text and randomly generated scores on
	 * the Set Ability Scores window.
	 */
	public void addAbilityScores() {
		strength = generateAbilities(1);
		strengthLbl.setText(me.getString("DnDGUI.strengthLbl") + " " + Integer.toString(strength));
		strengthLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		dexterity = generateAbilities(2);
		dexterityLbl.setText(me.getString("DnDGUI.dexterityLbl") + " " + Integer.toString(dexterity));
		dexterityLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		constitution = generateAbilities(3);
		constitutionLbl.setText(me.getString("DnDGUI.constitutionLbl") + " " + Integer.toString(constitution));
		constitutionLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		intelligence = generateAbilities(4);
		intelligenceLbl.setText(me.getString("DnDGUI.intelligenceLbl") + " " + Integer.toString(intelligence));
		intelligenceLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		wisdom = generateAbilities(5);
		wisdomLbl.setText(me.getString("DnDGUI.wisdomLbl") + " " + Integer.toString(wisdom));
		wisdomLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		charisma = generateAbilities(6);
		charismaLbl.setText(me.getString("DnDGUI.charismaLbl") + " " + Integer.toString(charisma));
		charismaLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
	}

	/**
	 * Screen for character to choose two skills to be proficient at. Has a title label, a description of
	 * what is going on, checkboxes for the available skills for the class/race already chosen, 
	 * and a Button to go to the next window.
	 */
	public void createSetSkillsScreen() {
		stepLbl.setText(me.getString("DnDGUI.step4Lbl"));
		skillDescriptionsLbl = new Label(me.getString("DnDGUI.skillsDescriptions"));
		skillsNextBtn = new Button(me.getString("DnDGUI.nextBtn"));

		numChecked = 0; // For use making sure only 2 checkboxes are checked at a time
		checkVBox.setAlignment(Pos.TOP_LEFT);
		checkVBox.setMaxWidth(195);
		// Add checkboxes for Skills user can choose from
		for (int i = 0; i < skills.length; i++) {
			checkVBox.getChildren().add(skills[i]);
			skills[i].setUserData(skills[i].getText());
			addCheckBoxListener(skills[i]);
		}
		checkVBox.setMaxWidth(200); 
		checkVBox.setPadding(new Insets(20, 20, 20, 20));
		checkVBox.setStyle("-fx-background-color: #FFFFFF;" +  
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		setScreenDesign(setSkillsVBox, skillDescriptionsLbl, skillsNextBtn);
		setSkillsVBox.getChildren().addAll(skillDescriptionsLbl, checkVBox, skillsNextBtn);
		bPane.setCenter(setSkillsVBox);
	}
	
	/**
	 * Screen for character to choose their alignment and name. Has a title label, a description of
	 * the alignments, a combobox to choose an alignment, a text field to enter a name, and a Button to go to the next window.
	 */
	public void createNameAlignmentSkillsScreen() {
		nameLbl = new Label(me.getString("DnDGUI.setName"));
		// Descriptions of each alignment
		alignmentLbl = new Label(me.getString("DnDGUI.AlignmentDescription") + "\n\n" + 
				me.getString("DnDGUI.lawfulGood") + "\n" + me.getString("DnDGUI.neutralGood") + "\n" +
				me.getString("DnDGUI.chaoticGood") + "\n\n" + me.getString("DnDGUI.lawfulNeutral") + "\n" + me.getString("DnDGUI.trueNeutral") 
				+ "\n" + me.getString("DnDGUI.chaoticNeutral") + "\n\n" + me.getString("DnDGUI.lawfulEvil") + "\n" + me.getString("DnDGUI.neutralEvil") 
				+ "\n" + me.getString("DnDGUI.chaoticEvil"));
		alignmentLbl.setMaxWidth(650);

		// Combo box to choose alignment
		alginmentCb = new ComboBox<String>();
		alginmentCb.getItems().addAll(me.getString("DnDGUI.lgCb"), me.getString("DnDGUI.ngCb"), me.getString("DnDGUI.cgCb"),
				me.getString("DnDGUI.lnCb"), me.getString("DnDGUI.tnCb"), me.getString("DnDGUI.cnCb"), me.getString("DnDGUI.leCb"), 
				me.getString("DnDGUI.neCb"), me.getString("DnDGUI.ceCb"));
		nameField = new TextField(); // Text field to enter character name.
		nameAlignmentNextBtn = new Button(me.getString("DnDGUI.nextBtn"));
	
		nameAlignmentSkillsDesign();
		nameAlignmentNextBtn.setMinWidth(250);
		//alginmentCb.setStyle("-fx-font-size: 18;");
		setScreenDesign(setNameAlignVBox, alignmentLbl, nameAlignmentNextBtn); // Alignment, spacing, etc.
		setNameAlignVBox.getChildren().addAll(alignmentLbl, alginmentCb, nameLbl, nameField, nameAlignmentNextBtn);
		bPane.setCenter(setNameAlignVBox);
	}
	
	/**
	 * Spacing, alignment, sizing specifically for the "Choose Name and Alignment" screen
	 */
	public void nameAlignmentSkillsDesign() {
		nameLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		nameLbl.setPadding(new Insets(10,0,0,0));
		alginmentCb.setMaxWidth(250);
		alginmentCb.getStyleClass().add("combobox");
		nameField.setMaxWidth(250);
		nameField.setMinHeight(45);
		nameField.setFont(Font.font(14));
		alginmentCb.setValue(me.getString("DnDGUI.setAlignment"));
		alignmentLbl.setTextAlignment(TextAlignment.LEFT);
		alignmentLbl.setMaxWidth(580);
		stepLbl.setText(me.getString("DnDGUI.step5Lbl"));
	}

	/**
	 * Design that is common across most screens during the character creation portion
	 * of the application.
	 * 
	 * @param vb = VBox that contains screen elements
	 * @param lbl = Label (not title label) that contains text contents
	 * @param btn = Next Button for the screen
	 */
	public void setScreenDesign(VBox vb, Label lbl, Button btn) {
		vb.setAlignment(Pos.TOP_CENTER);
		lbl.setWrapText(true);
		lbl.setFont(Font.font(15));
		lbl.setMaxWidth(550);
		lbl.setPadding(new Insets(20,0,15,0));
		lbl.setTextAlignment(TextAlignment.JUSTIFY);
		btn.setMinWidth(195);
		btn.setMinHeight(45);
		btn.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		btn.setDisable(true);
		btn.getStyleClass().add("button");
		
		btn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			btn.setEffect(d);
		});
		
		btn.setOnMouseExited( e -> {
			btn.setEffect(null);
		});
	}

	/**
	 * Error checking: when user chooses two skills to be proficient at,
	 * make sure they are only able to choose two check boxes at a time. Also, the 
	 * "Next" button is only enabled when there are two checkboxes chosen.
	 * @param cb
	 */
	public void addCheckBoxListener(CheckBox cb) {
		cb.setOnMouseClicked( c -> {
			if(cb.isSelected() && numChecked >=2) {
				cb.setSelected(false);
			} else {
				if(cb.isSelected()) {
					numChecked++;
				} else {
					numChecked--;
					skillsNextBtn.setDisable(true);
				}
			}

			if(numChecked >=2) {
				skillsNextBtn.setDisable(false);
			}

		});
	}
}

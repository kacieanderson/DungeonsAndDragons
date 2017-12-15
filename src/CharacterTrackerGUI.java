// Kacie Anderson
// ITP 368, Fall 2017
// Final Project GUI
// kqanders@usc.edu
// 12/7/2017

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CharacterTrackerGUI extends Application{
	private BorderPane bPane, weaponsBPane, spellsBPane;
	private ImageView dragon;
	private TabPane tabPane;
	private VBox generalPaneVBox, initiativeVBox, hpVBox, speedVBox, hitDiceVBox,
	armorClassVBox, proficiencyVBox, generalCenterVBox, weaponsVBox,
	spellsVBox, diceVBox, detailsVBox;
	protected VBox strengthVBox, dexterityVBox, intelligenceVBox, wisdomVBox, charismaVBox, newNewWeaponVBox,
				detailsHBox;
	private HBox generalCenterHBox, generalBottomHBox, diceButtonsHBox;
	protected GridPane abilityScoresGridPane, newWeaponGridPane, newSpellGridPane,
	detailsGridPane;
	private Tab generalTab, abilitiesTab, weaponsTab, spellsTab, detailsTab, diceTab;
	private Character character;
	private Label nameLbl;
	Label alignmentLevelLbl;
	private Label rollResult;
	private Label rollResultTitleLbl;
	protected Label initiativeLbl, initiativeNumLbl, hpLbl, hpNumLbl, speedLbl, speedNumLbl,
	hitDiceLbl, hitDiceNumLbl, armorClassLbl, armorClassNumLbl, proficiencyLbl,
	proficiencyNumLbl, newWeaponTitleLbl, newSpellTitleLbl,  newWeaponNameLbl, newWeaponTypeLbl, newWeaponDamageLbl;
	protected Label strengthLbl, athleticsLbl, intelligenceLbl, arcanaLbl, historyLbl,
	investigationLbl, natureLbl, religionLbl, wisdomLbl, animalLbl, insightLbl, 
	medicineLbl, perceptionLbl, survivalLbl, dexterityLbl, acrobaticsLbl, sleightLbl,
	stealthLbl, charismaLbl, deceptionLbl, intimidationLbl, performanceLbl, persuasionLbl,
	errorLbl;
	protected ObservableList<String> weapons, spells;
	private ListView<String> weaponsListView, spellsListView;
	protected Button newWeaponBtn, newSpellBtn, newWeaponDoneBtn, newSpellDoneBtn, viewWeaponsBtn, viewSpellsBtn,
				addHP, subtractHP;
	protected TextField newWeaponNameField, newSpellNameField, xpTextField, damageTextField;
	protected TextArea spellDescriptionTextArea;
	private TextArea backgroundTa;
	private TextArea proficienciesTa;
	private TextArea equipmentTa;
	private TextArea notesTa;
	protected ToggleGroup weaponTypeGroup;
	protected RadioButton meleeRb, rangedRb;
	//private Locale currentLocale;
	protected ResourceBundle me, OldMe;
//	protected Stage newWeaponStage;
//	protected Scene newWeaponScene;

	public CharacterTrackerGUI(Character c, Locale currentLocale, ResourceBundle me) {
		this.me = ResourceBundle.getBundle("tracker", currentLocale);
		OldMe = ResourceBundle.getBundle("messages", currentLocale);
		character = c;
		bPane = new BorderPane();
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene trackerScene = new Scene(tabPane, 800, 575);
		trackerScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
		stage.setScene(trackerScene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Create the tabs for the tab pane and call corresponding methods to fill them.
	 */
	public void createTabPane() {
		initializeVBoxes();
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Make sure users can't close tabs
		tabPane.getStyleClass().add("tab-pane");
		tabPane.getStyleClass().add("tab");
		tabPane.getStyleClass().add("tab-label");
		tabPane.getStyleClass().add("tab-header-area");
		tabPane.getStyleClass().add("tab-header-background");
		generalTab = new Tab(me.getString("DnDGUI.generalTab"));
		generalTab.getStyleClass().add("tab");
		//generalTab.getStyleClass().add("tab");
		generalTab.getStyleClass().add("tab-label");
		//generalTab.setStyle("-fx-background-colour: #FFFFFF;");
		fillGeneralPane();
		abilitiesTab = new Tab(me.getString("DnDGUI.abilitiesTab"));
		fillAbilitiesPane();
		weaponsTab = new Tab(me.getString("DnDGUI.weaponsTab"));
		fillWeaponsPane();
		spellsTab = new Tab(me.getString("DnDGUI.spellsTab"));
		fillSpellsPane();
		detailsTab = new Tab(me.getString("DnDGUI.detailsTab"));
		fillDetailsPane();
		diceTab = new Tab(me.getString("DnDGUI.diceTab"));
		fillDicePane();
		tabPane.getTabs().addAll(generalTab, abilitiesTab, weaponsTab, spellsTab, detailsTab, diceTab);
	}

	/**
	 * There are a lot of VBoxes in this GUI. This is where they are all initialized.
	 */
	public void initializeVBoxes() {
		diceVBox = new VBox(15); // Dice tab
		detailsVBox = new VBox(30); // Details tab
		weaponsVBox = new VBox(30); // Weapons tab
		spellsVBox = new VBox(30); // Spells tab
		strengthVBox = new VBox(); // Abilities tab
		dexterityVBox = new VBox(); // Abilities tab
		intelligenceVBox = new VBox(); // Abilities tab
		wisdomVBox = new VBox(); // Abilities tab
		charismaVBox = new VBox(); // Abilities tab
		generalPaneVBox = new VBox(); // General tab
		initiativeVBox = new VBox(); // General tab
		hpVBox = new VBox(); // General tab
		speedVBox = new VBox(); // General tab
		hitDiceVBox = new VBox(); // General tab
		armorClassVBox = new VBox(); // General tab
		proficiencyVBox = new VBox(); // General tab
		generalCenterVBox = new VBox(); // General tab
	}
	
	public void initializePopupWindows() {
		newWeaponTitleLbl = new Label(me.getString("DnDGUI.addNewWeapon"));
		newWeaponNameLbl = new Label(me.getString("DnDGUI.weaponName"));
		newWeaponTypeLbl = new Label(me.getString("DnDGUI.weaponType"));
		newWeaponDamageLbl = new Label(me.getString("DnDGUI.damage"));
		newWeaponDoneBtn = new Button(me.getString("DnDGUI.doneBtn")); // User clicks when finished
		newWeaponNameField = new TextField(); // User enters weapon name
		meleeRb = new RadioButton(me.getString("DnDGUI.melee") + " (" 
				+ character.getModifier(character.getStrength()) + ")");
		rangedRb = new RadioButton(me.getString("DnDGUI.ranged")+ " (" 
				+ character.getModifier(character.getDexterity()) + ")");
		damageTextField = new TextField(); // User can enter how much damage the weapon does
	}

	/**
	 * Fills the "Dice" tab with 7 buttons users can click to roll dice.
	 */
	public void fillDicePane() {
		diceButtonsHBox = new HBox(20); // HBox just holds all the dice buttons.

		// Create buttons to roll dice
		Button d4Btn = new Button(me.getString("DnDGUI.d4Btn")); 
		d4Btn.setStyle("-fx-background-color: #FDFFC7;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d6Btn = new Button(me.getString("DnDGUI.d6Btn"));
		d6Btn.setStyle("-fx-background-color: #E8D9C1;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d8Btn = new Button(me.getString("DnDGUI.d8Btn"));
		d8Btn.setStyle("-fx-background-color: #FFE2E1;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d10Btn = new Button(me.getString("DnDGUI.d10Btn"));
		d10Btn.setStyle("-fx-background-color: #D5C3E8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d12Btn = new Button(me.getString("DnDGUI.d12Btn"));
		d12Btn.setStyle("-fx-background-color: #D4EEFF;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d100Btn = new Button(me.getString("DnDGUI.d100Btn"));
		d100Btn.setStyle("-fx-background-color: #E1FFEB;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		Button d20Btn = new Button(me.getString("DnDGUI.d20Btn"));
		d20Btn.setStyle("-fx-background-color: #FFFBFA;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");

		// Make all buttons the same height and width, and make them work.
		setUpDiceButtons(d4Btn, 4); setUpDiceButtons(d6Btn, 6); setUpDiceButtons(d8Btn, 8);
		setUpDiceButtons(d10Btn, 10); setUpDiceButtons(d12Btn, 12); setUpDiceButtons(d100Btn, 100);
		setUpDiceButtons(d20Btn, 20);

		rollResultTitleLbl = new Label(me.getString("DnDGUI.resultLbl")); // Labels the roll result
		rollResult = new Label(); // Contains the actual result of the roll
		dicePaneDesign(); // Set up spacing, sizes, etc.

		diceButtonsHBox.setPadding(new Insets(100,0,0,0));
		diceButtonsHBox.getChildren().addAll(d4Btn,d6Btn,d8Btn,d10Btn,d12Btn,d100Btn,d20Btn);
		diceVBox.getChildren().addAll(diceButtonsHBox, rollResultTitleLbl, rollResult);
		//diceVBox.setAlignment(Pos.CENTER);
		diceTab.setContent(diceVBox);
	}

	/**
	 * Each button generates a random number based on the number of "sides" on the die.
	 * 
	 * @param btn = The button the user clicks
	 * @param i = The number of sides on the "die" the user is rolling
	 */
	public void setUpDiceButtons(Button btn, int i) {
		btn.setMinWidth(75);
		btn.setMinHeight(75);
		btn.setAlignment(Pos.CENTER);
		btn.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
		
		btn.setOnAction(e->{
			rollResult.setText(Integer.toString((int) (Math.random() * i) + 1));
		});
		
		btn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(5, Color.DARKGRAY);
			btn.setEffect(d);
		});
		
		btn.setOnMouseExited( e -> {
			btn.setEffect(null);
		});
	}

	/**
	 * Padding, sizing for elements on Dice Tab.
	 */
	public void dicePaneDesign() {
		diceVBox.setAlignment(Pos.TOP_CENTER);
		diceVBox.setPadding(new Insets(20,20,0,20));
		diceButtonsHBox.setAlignment(Pos.TOP_CENTER);
		rollResultTitleLbl.setPadding(new Insets(20,0,0,0));
		rollResultTitleLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		rollResult.setFont(Font.font("Helvetica", FontWeight.BOLD, 72));
	}

	/**
	 * Fills the "Details" tab with the character's alignment, their experience points (in an updatable text
	 * field), and four text areas for users to keep custom information about the character's background,
	 * proficiencies, equipment, and other notes.
	 */
	public void fillDetailsPane() {
		detailsHBox = new VBox(15); // HBox at top lists alignment and experience points
		HBox xpHBox = new HBox(15);
		xpHBox.setAlignment(Pos.TOP_CENTER);
		//detailsHBox.setPadding(new Insets(0,0,20,177));
		Label alignmentLabel = new Label(me.getString("DnDGUI.alignmentLbl") + " " + character.getAlignment());
		Label xpLabel = new Label(me.getString("DnDGUI.xpLbl"));
		alignmentLabel.setFont(Font.font(18)); xpLabel.setFont(Font.font(18));
		xpTextField = new TextField();
		xpTextField.setMaxWidth(100);
		xpTextField.setText(Integer.toString(character.getXP()));
		Tooltip xpTooltip = new Tooltip(me.getString("DnDGUI.xpTooltip"));
		Tooltip.install(xpTextField, xpTooltip);
		//addExperienceListener(); // Only allows numbers; updates character level as necessary
		xpHBox.getChildren().addAll(xpLabel,xpTextField);
		detailsHBox.getChildren().addAll(alignmentLabel, xpHBox);

		detailsGridPane = new GridPane(); // Gridpane has textareas for users to keep track of notes.
		Label backgroundLbl = new Label(me.getString("DnDGUI.backgroundLbl"));
		backgroundLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		backgroundLbl.setStyle("-fx-text-fill: #5B4970");
		Label proficienciesLbl = new Label(me.getString("DnDGUI.proficienciesLbl"));
		proficienciesLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		proficienciesLbl.setStyle("-fx-text-fill: #28588A");
		Label equipmentLbl = new Label(me.getString("DnDGUI.equipmentLbl"));
		equipmentLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		equipmentLbl.setStyle("-fx-text-fill: #715A34");
		Label notesLbl = new Label(me.getString("DnDGUI.notesLbl"));
		notesLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		notesLbl.setStyle("-fx-text-fill: #2B6A4A");
		backgroundTa = new TextArea();
		proficienciesTa = new TextArea();
		equipmentTa = new TextArea();
		notesTa = new TextArea();
		detailsPaneDesign(); // Alignment, spacing, prompt text
		Label buffer1 = new Label(" ");
		Label buffer2 = new Label(" ");

		detailsGridPane.add(backgroundLbl, 0, 0); detailsGridPane.add(backgroundTa, 0, 1);
		detailsGridPane.add(buffer1, 0, 2);
		detailsGridPane.add(proficienciesLbl, 0, 3); detailsGridPane.add(proficienciesTa, 0, 4);
		detailsGridPane.add(equipmentLbl, 1, 0); detailsGridPane.add(equipmentTa, 1, 1);
		detailsGridPane.add(buffer2, 1, 2);
		detailsGridPane.add(notesLbl, 1, 3); detailsGridPane.add(notesTa, 1, 4);

		detailsVBox.getChildren().addAll(detailsHBox, detailsGridPane);
		detailsVBox.setAlignment(Pos.TOP_CENTER);
		detailsTab.setContent(detailsVBox);
	}

	/**
	 * Alignment, spacing, prompt text for elements in the Details pane.
	 */
	public void detailsPaneDesign() {
		detailsHBox.setAlignment(Pos.TOP_CENTER);
		detailsHBox.setPadding(new Insets(35,0,0,25));
		detailsHBox.setStyle("-fx-background-color: #FFFFFF;" + "-fx-padding: 10;" + 
				"-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: black;");
		detailsHBox.setMaxWidth(590);
		detailsGridPane.setHgap(50);
		detailsGridPane.setVgap(5);
		detailsGridPane.setAlignment(Pos.TOP_CENTER);
		backgroundTa.setMaxWidth(270);
		backgroundTa.setMaxHeight(150);
		backgroundTa.setPromptText(me.getString("DnDGUI.backgroundTextArea"));
		backgroundTa.setWrapText(true);
		backgroundTa.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-radius: 3;" + "-fx-border-color: #5B4970;");
		proficienciesTa.setMaxWidth(270);
		proficienciesTa.setMaxHeight(150);
		proficienciesTa.setPromptText(me.getString("DnDGUI.proficienciesTextArea"));
		proficienciesTa.setWrapText(true);
		proficienciesTa.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-radius: 3;" + "-fx-border-color: #28588A;");
		equipmentTa.setMaxWidth(270);
		equipmentTa.setMaxHeight(150);
		equipmentTa.setText(character.getEquipment());
		equipmentTa.setWrapText(true);
		equipmentTa.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-radius: 3;" + "-fx-border-color: #715A34;");
		//equipmentTa.setPromptText(me.getString("DnDGUI.equipmentTextArea"));
		notesTa.setMaxWidth(270);
		notesTa.setMaxHeight(150);
		notesTa.setPromptText(me.getString("DnDGUI.notesTextArea"));
		notesTa.setWrapText(true);
		notesTa.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-radius: 3;" + "-fx-border-color: #2B6A4A;");
	}

	/**
	 * Fills the "Weapons" tab with a Listview that contains all weapons the character has,
	 * as well as a button user can click to add a new Weapon.
	 */
	public void fillWeaponsPane() {
		weaponsBPane = new BorderPane();
		weaponsVBox.setAlignment(Pos.TOP_CENTER);
		weaponsVBox.setPadding(new Insets(20,20,0,20));

		weapons = FXCollections.observableArrayList();
		weapons.add(me.getString("DnDGUI.weaponsExample"));
		weapons.addAll(character.getWeaponsString());
		weaponsListView = new ListView<String>(weapons); // List of weapons
		weaponsListView.setFocusTraversable(false);
		weaponsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		HBox weaponsHBox = new HBox(20);
		
		viewWeaponsBtn = new Button(me.getString("DnDGUI.viewWeaponsBtn"));

		newWeaponBtn = new Button(me.getString("DnDGUI.addNewWeapon"));
		newWeaponBtn.setFont(Font.font(19)); viewWeaponsBtn.setFont(Font.font(19));
		newWeaponBtn.setMinHeight(45); viewWeaponsBtn.setMinHeight(45);
		newWeaponBtn.setMinWidth(190); viewWeaponsBtn.setMinWidth(190);
		newWeaponBtn.getStyleClass().add("button"); viewWeaponsBtn.getStyleClass().add("button");
		
		newWeaponBtn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			newWeaponBtn.setEffect(d);
		});
		
		newWeaponBtn.setOnMouseExited( e -> {
			newWeaponBtn.setEffect(null);
		});

		weaponsHBox.getChildren().addAll(newWeaponBtn, viewWeaponsBtn);
		weaponsHBox.setAlignment(Pos.CENTER);
		weaponsVBox.getChildren().addAll(weaponsListView, weaponsHBox);
		weaponsBPane.setCenter(weaponsVBox);
		weaponsTab.setContent(weaponsBPane);
	}


	/**
	 * Alignment, spacing, etc. for "Add New Weapon" window
	 * @param box VBox all this content will be in.
	 */
	public void addNewWeaponDesign(VBox box) {
		box.setAlignment(Pos.TOP_CENTER);
		box.setPadding(new Insets(20,20,0,20));
		newWeaponTitleLbl.setFont(Font.font(18));
		newWeaponDoneBtn.setMinHeight(45);
		newWeaponDoneBtn.setMinWidth(190);
		newWeaponDoneBtn.setDisable(true);
		
		newWeaponNameField.setMinWidth(190);	damageTextField.setMinWidth(190);damageTextField.setPromptText("ex. 1d6");
		newWeaponNameField.setUserData(null);
		damageTextField.setUserData(null);
		meleeRb.setUserData(me.getString("DnDGUI.melee") + " (" 
				+ character.getModifier(character.getStrength()) + ")");
		rangedRb.setUserData(me.getString("DnDGUI.ranged")+ " (" 
				+ character.getModifier(character.getDexterity()) + ")");
		meleeRb.setDisable(true);
		rangedRb.setDisable(true);
		damageTextField.setDisable(true);
		errorLbl = new Label();
		errorLbl.setText("");
		newWeaponDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: #747474;");
		
		newWeaponDoneBtn.setOnMouseEntered( e -> {
			DropShadow d = new DropShadow(10, Color.DARKGRAY);
			newWeaponDoneBtn.setEffect(d);
			newWeaponDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #4E4E4E;");
		});
		
		newWeaponDoneBtn.setOnMouseExited( e -> {
			newWeaponDoneBtn.setEffect(null);
			newWeaponDoneBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #747474;");
		});
	}


	/**
	 * Fills the "Spells" tab with a Listview that contains all spells the character has,
	 * as well as a button user can click to add a new Spell.
	 */
	public void fillSpellsPane() {
		spellsBPane = new BorderPane();
		spellsVBox.setAlignment(Pos.TOP_CENTER);
		spellsVBox.setPadding(new Insets(20,20,0,20));

		spells = FXCollections.observableArrayList();
		spells.add(me.getString("DnDGUI.spellsExample"));
		spellsListView = new ListView<String>(spells); // List of weapons
		spellsListView.setFocusTraversable(false);
		spellsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		HBox spellsHBox = new HBox(20);
		
		viewSpellsBtn = new Button(me.getString("DnDGUI.viewSpellsBtn"));
		newSpellBtn = new Button(me.getString("DnDGUI.addNewSpell"));
		newSpellBtn.setFont(Font.font(19)); viewSpellsBtn.setFont(Font.font(19));
		newSpellBtn.setMinHeight(45); viewSpellsBtn.setMinHeight(45);
		newSpellBtn.setMinWidth(190); viewSpellsBtn.setMinWidth(190);
		spellsHBox.getChildren().addAll(newSpellBtn, viewSpellsBtn);
		spellsHBox.setAlignment(Pos.CENTER);
		spellsVBox.getChildren().addAll(spellsListView, spellsHBox);
		spellsBPane.setCenter(spellsVBox);
		spellsTab.setContent(spellsBPane);
	}

	/**
	 * Alignment, spacing, etc. for "Add New Spell" window
	 * @param box VBox all this content will be in.
	 */
	public void addNewSpellDesign(VBox box) {
		box.setAlignment(Pos.TOP_CENTER);
		box.setPadding(new Insets(20,20,0,20));
		newSpellTitleLbl.setFont(Font.font(18));
		newSpellNameField.setMaxWidth(170);
		spellDescriptionTextArea.setMaxWidth(170);
		spellDescriptionTextArea.setPromptText(me.getString("DnDGUI.spellPrompt"));
		newSpellDoneBtn.setDisable(true);
		spellDescriptionTextArea.setDisable(true);
		errorLbl = new Label();
		newSpellBtn.getStyleClass().add("button"); viewSpellsBtn.getStyleClass().add("button");
		newSpellBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
				"-fx-border-radius: 3;" + "-fx-border-color: #747474;");
		newSpellBtn.setOnMouseEntered( e -> { DropShadow d = new DropShadow(10, Color.DARKGRAY);newSpellBtn.setEffect(d);
			newSpellBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #4E4E4E;");
		});
		newSpellBtn.setOnMouseExited( e -> { newSpellBtn.setEffect(null);
			newSpellBtn.setStyle("-fx-background-color: #dce6d8;" + "-fx-border-style: solid outside;" + "-fx-border-width: 2.5;" + 
					"-fx-border-radius: 3;" + "-fx-border-color: #747474;");
		});

	}

	/**
	 * Fills the "Ability Scores" tab with the character's ability scores and
	 * skill modifiers (Strength, Intelligence, Wisdom, Dexterity, Charisma, and 
	 * their associated skills)
	 */
	public void fillAbilitiesPane() {
		abilityScoresGridPane = new GridPane(); // Content stored in a GridPane
		dragon = new ImageView();
		dragon.setImage(new Image("images/dragon.png"));

		// Individual functions for the long process of putting text into labels
		generateStrengthLabels();
		generateIntelligenceLabels();
		generateWisdomLabels();
		generateDexterityLabels();
		generateCharismaLabels();

		abilityScoresGridPane.add(strengthVBox, 0, 0);
		abilityScoresGridPane.add(intelligenceVBox, 0, 1);
		abilityScoresGridPane.add(wisdomVBox, 1, 0);
		abilityScoresGridPane.add(dragon, 1, 1);
		abilityScoresGridPane.add(dexterityVBox, 2, 0);
		abilityScoresGridPane.add(charismaVBox, 2, 1);
		abilityScoresGridPane.setAlignment(Pos.CENTER);
		abilityScoresGridPane.setHgap(10);

		abilitiesTab.setContent(abilityScoresGridPane);
	}

	/**
	 * Labels containing scores/modifiers for Strength & associated skills in the "Ability
	 * Scores" Tab. The if statements determine whether the score is negative or positive; if positive, adds a "+"
	 * to the label to distinguish.
	 */
	public void generateStrengthLabels() {
		strengthLbl = new Label(me.getString("DnDGUI.strengthLblTop") + "\n" 
				+ me.getString("DnDGUI.score") + " "+ character.getStrength() + "\n"
				+ me.getString("DnDGUI.modifier"));
		
		if (character.getModifier(character.getStrength())>0) strengthLbl.setText(strengthLbl.getText() + " +" 
				+ character.getModifier(character.getStrength()));
		else strengthLbl.setText(strengthLbl.getText() + " " + character.getModifier(character.getStrength()));

		if (character.athletics>0) athleticsLbl = new Label("\n" + me.getString("DnDGUI.athletics") + ": +"  + character.athletics);
		else athleticsLbl = new Label("\n" + me.getString("DnDGUI.athletics") + ": "  + character.athletics);
		
		strengthVBox.getChildren().addAll(strengthLbl, athleticsLbl);
		strengthVBox.setStyle("-fx-background-color: #FBFFD4;" + "-fx-padding: 20 40 20 40;" + "-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		abilityScoresDesign(strengthVBox, strengthLbl);
	}

	/**
	 * Labels containing scores/modifiers for Intelligence & associated skills in the "Ability
	 * Scores" Tab. The if statements determine whether the score is negative or positive; if positive, adds a "+"
	 * to the label to distinguish.
	 */
	public void generateIntelligenceLabels() {
		intelligenceLbl = new Label(me.getString("DnDGUI.intelligenceLblTop") + "\n" 
				+ me.getString("DnDGUI.score") + " " + character.getIntelligence() + "\n"
				+ me.getString("DnDGUI.modifier"));
		
		if (character.getModifier(character.getIntelligence())>0) intelligenceLbl.setText(intelligenceLbl.getText() + " +" 
				+ character.getModifier(character.getIntelligence()));
		else intelligenceLbl.setText(intelligenceLbl.getText() + " " + character.getModifier(character.getIntelligence()));
		
		if(character.arcana>0) arcanaLbl = new Label("\n" + me.getString("DnDGUI.arcana") + ": +"  + character.arcana);
		else arcanaLbl = new Label("\n" + me.getString("DnDGUI.arcana") + ": "  + character.arcana);
		
		if(character.history>0) historyLbl = new Label(me.getString("DnDGUI.history") + ": +"  + character.history);
		else historyLbl = new Label(me.getString("DnDGUI.history") + ": "  + character.history);
		
		if(character.investigation>0) investigationLbl = new Label(me.getString("DnDGUI.investigation") + ": +"  + character.investigation);
		else investigationLbl = new Label(me.getString("DnDGUI.investigation") + ": "  + character.investigation);
		
		if(character.nature>0) natureLbl = new Label(me.getString("DnDGUI.nature") + ": +"  + character.nature);
		else natureLbl = new Label(me.getString("DnDGUI.nature") + ": "  + character.nature);
		
		if(character.religion>0) religionLbl = new Label(me.getString("DnDGUI.religion") + ": +"  + character.religion);
		else religionLbl = new Label(me.getString("DnDGUI.religion") + ": "  + character.religion);
		
		intelligenceVBox.getChildren().addAll(intelligenceLbl, arcanaLbl, historyLbl,
				investigationLbl, natureLbl, religionLbl);
		intelligenceVBox.setStyle("-fx-background-color: #E8D9C1;" + "-fx-padding: 20 40 20 40;" + "-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		abilityScoresDesign(intelligenceVBox, intelligenceLbl);
	}

	/**
	 * Labels containing scores/modifiers for Wisdom & associated skills in the "Ability
	 * Scores" Tab. The if statements determine whether the score is negative or positive; if positive, adds a "+"
	 * to the label to distinguish.
	 */
	public void generateWisdomLabels() {
		wisdomLbl = new Label(me.getString("DnDGUI.wisdomLblTop") + "\n" 
				+ me.getString("DnDGUI.score") + " " + character.getWisdom() + "\n"
				+ me.getString("DnDGUI.modifier"));
		
		if (character.getModifier(character.getWisdom())>0) wisdomLbl.setText(wisdomLbl.getText() + " +" 
				+ character.getModifier(character.getWisdom()));
		else wisdomLbl.setText(wisdomLbl.getText() + " " + character.getModifier(character.getWisdom()));
		
		if(character.animalHandling>0) animalLbl = new Label("\n" + me.getString("DnDGUI.animalHandling") + ": +"  + character.animalHandling);
		else animalLbl = new Label("\n" + me.getString("DnDGUI.animalHandling") + ": "  + character.animalHandling);
		
		if(character.insight>0) insightLbl = new Label(me.getString("DnDGUI.insight") + ": +"  +character.insight);
		else insightLbl = new Label(me.getString("DnDGUI.insight") + ": "  +character.insight);
		
		if(character.medicine>0) medicineLbl = new Label(me.getString("DnDGUI.medicine") + ": +"  +character.medicine);
		else medicineLbl = new Label(me.getString("DnDGUI.medicine") + ": "  +character.medicine);
		
		if(character.perception>0) perceptionLbl = new Label(me.getString("DnDGUI.perception") + ": +"  + character.perception);
		else perceptionLbl = new Label(me.getString("DnDGUI.perception") + ": "  + character.perception);
		
		if(character.survival>0) survivalLbl = new Label(me.getString("DnDGUI.survival") + ": +"  + character.survival);
		else survivalLbl = new Label(me.getString("DnDGUI.survival") + ": "  + character.survival);
		
		wisdomVBox.getChildren().addAll(wisdomLbl, animalLbl, insightLbl, medicineLbl,
				perceptionLbl, survivalLbl);
		wisdomVBox.setStyle("-fx-background-color: #FFE2E1;" + "-fx-padding: 20 40 20 40;" + "-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		abilityScoresDesign(wisdomVBox, wisdomLbl);
	}

	/**
	 * Labels containing scores/modifiers for Dexterity & associated skills in the "Ability
	 * Scores" Tab. The if statements determine whether the score is negative or positive; if positive, adds a "+"
	 * to the label to distinguish.
	 */
	public void generateDexterityLabels() {
		dexterityLbl = new Label(me.getString("DnDGUI.dexterityLblTop") + "\n" 
				+ me.getString("DnDGUI.score") + " " + character.getDexterity() + "\n"
				+ me.getString("DnDGUI.modifier"));
		
		if (character.getModifier(character.getDexterity())>0) dexterityLbl.setText(dexterityLbl.getText() + " +" 
				+ character.getModifier(character.getDexterity()));
		else dexterityLbl.setText(dexterityLbl.getText() + " " + character.getModifier(character.getDexterity()));
		
		if(character.acrobatics>0) acrobaticsLbl = new Label("\n" + me.getString("DnDGUI.acrobatics") + ": +" + character.acrobatics);
		else acrobaticsLbl = new Label("\n" + me.getString("DnDGUI.acrobatics") + ": " + character.acrobatics);
		
		if(character.sleightOfHand>0)sleightLbl = new Label(me.getString("DnDGUI.sleightOfHand") + ": +" + character.sleightOfHand);
		else sleightLbl = new Label(me.getString("DnDGUI.sleightOfHand") + ": " + character.sleightOfHand);
		
		if(character.stealth>0)stealthLbl = new Label(me.getString("DnDGUI.stealth") + ": +" + character.stealth);
		else stealthLbl = new Label(me.getString("DnDGUI.stealth") + ": " + character.stealth);
		
		dexterityVBox.getChildren().addAll(dexterityLbl, acrobaticsLbl, sleightLbl, stealthLbl);
		dexterityVBox.setStyle("-fx-background-color: #D5C3E8;" + "-fx-padding: 20 40 20 40;" + "-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		abilityScoresDesign(dexterityVBox, dexterityLbl);
	}

	/**
	 * Labels containing scores/modifiers for Charisma & associated skills in the "Ability
	 * Scores" Tab. The if statements determine whether the score is negative or positive; if positive, adds a "+"
	 * to the label to distinguish.
	 */
	public void generateCharismaLabels() {
		charismaLbl = new Label(me.getString("DnDGUI.charismaLblTop") + "\n" 
				+  me.getString("DnDGUI.score") + " " + character.getCharisma() + "\n" + me.getString("DnDGUI.modifier"));
		
		if (character.getModifier(character.getCharisma())>0) charismaLbl.setText(charismaLbl.getText() + " +" 
				+ character.getModifier(character.getCharisma()));
		else charismaLbl.setText(charismaLbl.getText() + " " + character.getModifier(character.getCharisma()));
		
		if(character.deception>0) deceptionLbl = new Label("\n" + me.getString("DnDGUI.deception") + ": +" + character.deception);
		else deceptionLbl = new Label("\n" + me.getString("DnDGUI.deception") + ": " + character.deception);
		
		if(character.intimidation>0) intimidationLbl = new Label(me.getString("DnDGUI.intimidation") + ": +" + character.intimidation);
		else intimidationLbl = new Label(me.getString("DnDGUI.intimidation") + ": " + character.intimidation);
		
		if(character.performance>0) performanceLbl = new Label(me.getString("DnDGUI.performance") + ": +" + character.performance);
		else performanceLbl = new Label(me.getString("DnDGUI.performance") + ": " + character.performance);
		
		if(character.persuasion>0)persuasionLbl = new Label(me.getString("DnDGUI.persuasion") + ": +" + character.persuasion);
		else persuasionLbl = new Label(me.getString("DnDGUI.persuasion") + ": " + character.persuasion);
		
		charismaVBox.getChildren().addAll(charismaLbl, deceptionLbl, intimidationLbl, 
				performanceLbl, persuasionLbl);
		charismaVBox.setStyle("-fx-background-color: #D4EEFF;" + "-fx-padding: 20 40 20 40;" + "-fx-border-insets: 10px;" + "-fx-background-insets: 10px;" + 
				"-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		abilityScoresDesign(charismaVBox, charismaLbl);
	}

	/**
	 * Alignment, padding, sizes for the labels in the "Ability Scores" tab.
	 * @param vb = the individual VBox these labels will be in
	 * @param lbl = the main Ability label (larger than subsequent skill labels) 
	 */
	public void abilityScoresDesign(VBox vb, Label lbl) {
		vb.setAlignment(Pos.TOP_LEFT);
		vb.setPadding(new Insets(30,0,0,0));
		lbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
	}

	/**
	 * Fills the "General" tab with the character's name, level, race, class (in generalPaneVBox, which
	 * is in the TOP of bPane) as well as HP, inititive modifier, speed, hit dice, armor class, 
	 * proficiency modifier (all in generalCenterVBox, which is in the CENTER of bPane).
	 */
	public void fillGeneralPane() {
		generalCenterHBox = new HBox();
		generalBottomHBox = new HBox();

		nameLbl = new Label(character.getName());
		alignmentLevelLbl = new Label(me.getString("DnDGUI.level") + " " + character.getLevel() 
		+ " " + character.getRace().getRaceString(OldMe) + " " + character.getCharClass().getClassString(OldMe));

		generateGeneralLabels();	
		generalPaneVBox.getChildren().addAll(nameLbl, alignmentLevelLbl);
		
		generalCenterHBox.getChildren().addAll(hpVBox);
		generalBottomHBox.getChildren().addAll(initiativeVBox, speedVBox, hitDiceVBox,armorClassVBox, proficiencyVBox);
		generalCenterVBox.getChildren().addAll(generalCenterHBox, generalBottomHBox );

		generalPaneDesign();
		bPane.setTop(generalPaneVBox);
		bPane.setCenter(generalCenterVBox);
		generalTab.setContent(bPane);
	}

	/**
	 * There are a LOT of labels in the General Tab. This is a method to get them all initialized.
	 */
	public void generateGeneralLabels() {
		initiativeLbl = new Label(me.getString("DnDGUI.initiative"));
		int initiativeModifier = character.getModifier(character.getDexterity());
		if (initiativeModifier == 0) {
			initiativeNumLbl = new Label("+0");
		} else if (initiativeModifier < 0) {
			initiativeNumLbl = new Label(Integer.toString(initiativeModifier));
		} else if (initiativeModifier > 0) {
			initiativeNumLbl = new Label("+" + Integer.toString(initiativeModifier));
		}

		hitDiceNumLbl = new Label(character.getCharClass().hitDice);
		hitDiceLbl = new Label(me.getString("DnDGUI.hitDice"));
		hpLbl = new Label(me.getString("DnDGUI.hp"));
		hpNumLbl = new Label(Integer.toString(character.getHP()));
		speedLbl = new Label(me.getString("DnDGUI.speed"));
		speedNumLbl = new Label(Integer.toString(character.getRace().speed));
		armorClassLbl = new Label(me.getString("DnDGUI.armorClass"));
		armorClassNumLbl = new Label(Integer.toString( (10 + character.getDexterity()) ));
		proficiencyLbl = new Label(me.getString("DnDGUI.proficiency"));
		proficiencyNumLbl = new Label("+2");

		initiativeVBox.getChildren().addAll(initiativeLbl,initiativeNumLbl);
		addHP = new Button("+"); subtractHP = new Button("-");
		addHP.setMinWidth(40); subtractHP.setMinWidth(40);
		addHP.setMinHeight(40); subtractHP.setMinHeight(40);
		addHP.setFont(Font.font("Helvetica", FontWeight.BOLD, 22)); subtractHP.setFont(Font.font("Helvetica", FontWeight.BOLD, 22));
		HBox hpHBox = new HBox(20);
		hpHBox.getChildren().addAll(subtractHP, hpNumLbl,addHP );
		hpVBox.getChildren().addAll(hpLbl,hpHBox);
		speedVBox.getChildren().addAll(speedLbl,speedNumLbl);
		hitDiceVBox.getChildren().addAll(hitDiceLbl,hitDiceNumLbl);
		armorClassVBox.getChildren().addAll(armorClassLbl,armorClassNumLbl);
		proficiencyVBox.getChildren().addAll(proficiencyLbl,proficiencyNumLbl);

	}

	/**
	 * Alignment, spacing, sizing for elements in the General pane.
	 */
	public void generalPaneDesign() {
		generalPaneVBox.setAlignment(Pos.CENTER);
		generalPaneVBox.setPadding(new Insets(40,0,0,0));
		generalCenterVBox.setAlignment(Pos.TOP_CENTER);
		generalCenterVBox.setStyle("-fx-background-color: #FFFFFF;" + "-fx-padding:40 80 80 80;" + "-fx-border-insets: 70px;" + "-fx-background-insets: 70px;" 
				+ "-fx-border-style: solid inside;" + "-fx-border-width: 1.5;" + "-fx-border-radius: 3;" + "-fx-border-color: black;");

		generalCenterHBox.setAlignment(Pos.CENTER);
		generalBottomHBox.setAlignment(Pos.CENTER);
		initiativeVBox.setAlignment(Pos.CENTER);
		hpVBox.setAlignment(Pos.CENTER);
		speedVBox.setAlignment(Pos.CENTER);
		hitDiceVBox.setAlignment(Pos.CENTER);
		armorClassVBox.setAlignment(Pos.CENTER);
		proficiencyVBox.setAlignment(Pos.CENTER);
		generalCenterHBox.setSpacing(40);
		generalBottomHBox.setSpacing(40);
		generalBottomHBox.setPadding(new Insets(20,0,0,0));

		nameLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
		alignmentLevelLbl.setFont(Font.font(20));
		initiativeNumLbl.setFont(Font.font(30));
		speedNumLbl.setFont(Font.font(30));
		hitDiceNumLbl.setFont(Font.font(30));
		armorClassNumLbl.setFont(Font.font(30));
		proficiencyNumLbl.setFont(Font.font(30));
		hpNumLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
		hpLbl.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		generalPaneTooltips();
	}
	
	public void generalPaneTooltips() {
		Tooltip hitDiceTooltip = new Tooltip(me.getString("DnDGUI.hitDiceTooltip"));
		Tooltip hpTooltip = new Tooltip(me.getString("DnDGUI.hpTooltip"));
		Tooltip speedTooltip = new Tooltip(me.getString("DnDGUI.speedTooltip"));
		Tooltip acTooltip = new Tooltip(me.getString("DnDGUI.acTooltip"));
		Tooltip proficiencyTooltip = new Tooltip(me.getString("DnDGUI.proficiencyTooltip"));
		Tooltip initiativeTooltip = new Tooltip(me.getString("DnDGUI.initiativeTooltip"));
		hitDiceTooltip.setFont(Font.font(14));
		hpTooltip.setFont(Font.font(14));
		speedTooltip.setFont(Font.font(14));
		acTooltip.setFont(Font.font(14));
		proficiencyTooltip.setFont(Font.font(14));
		initiativeTooltip.setFont(Font.font(14));
		Tooltip.install(hitDiceNumLbl, hitDiceTooltip);
		Tooltip.install(hitDiceLbl, hitDiceTooltip);
		Tooltip.install(hpNumLbl, hpTooltip);
		Tooltip.install(hpLbl, hpTooltip);
		Tooltip.install(speedNumLbl, speedTooltip);
		Tooltip.install(speedLbl, speedTooltip);
		Tooltip.install(armorClassNumLbl, acTooltip);
		Tooltip.install(armorClassLbl, acTooltip);
		Tooltip.install(proficiencyNumLbl, proficiencyTooltip);
		Tooltip.install(proficiencyLbl, proficiencyTooltip);
		Tooltip.install(initiativeNumLbl, initiativeTooltip);
		Tooltip.install(initiativeLbl, initiativeTooltip);
	}

}

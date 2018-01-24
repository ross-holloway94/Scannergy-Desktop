/* 
 * Copyright 2017 Ross Holloway.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package allergyApp;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.atteo.evo.inflector.English;

/**
 * Controller class, which holds methods for functionality, and also holds
 * methods for controls linked to FXML document.
 *
 * @author Ross
 */
public final class Controller implements Initializable {

    private final JSONManager jsonmanager = JSONManager.getInstance();
    protected HashSet<Consumer> consumers = null;

    public Controller() {
        consumers = jsonmanager.setUpConsumers();
    }

    /**
     * Initialise the controller implicitly from FXMLLoader.
     *
     * @param url - Auto generated but not used.
     * @param rb - Auto generated but not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupPanes();
    }

    /**
     * Enter allergens for a consumer that already exists.
     *
     * @param consumer - The consumer to add allergens to.
     * @param consumerAllergies - The allergies of the consumer to store.
     * @return Consumer - The consumer with the allergy list updated.
     */
    public Consumer updateAllergens(Consumer consumer, HashSet<String> consumerAllergies) {
        consumer.setAllergies(consumerAllergies);
        return consumer;
    }

    /**
     * Create a new consumer, and save to file so it can be loaded on next boot.
     *
     * @param consumerName The name of the consumer.
     * @param consumerAllergies The allergies of the consumer.
     * @return The newly created consumer object.
     */
    public Consumer createNewConsumer(String consumerName, HashSet<String> consumerAllergies) {
        Consumer newConsumer = new Consumer(consumerName, consumerAllergies);
        return newConsumer;
    }

    /**
     * Compares the allergies of aConsumer with the ingredients of aProduct.
     * TODO Change to account for all consumers.
     *
     * @param aConsumer
     * @param aProduct
     * @return allergensFound - Any allergens which are found in the product.
     */
    Set<String> checkAllergens(Consumer aConsumer, FoodProduct aProduct) {
        //Get the consumer's allergens
        Collection<String> consumerAllergens = aConsumer.getAllergies();
        Set<String> allergensFound = new HashSet<>();

        String productIngredients = aProduct.getIngredientsString();

        for (String allergen : consumerAllergens) {
            boolean doesContainSingle = productIngredients.toLowerCase().contains(allergen.toLowerCase());
            String allergenPlural = English.plural(allergen);
            boolean doesContainPlural = productIngredients.toLowerCase().contains(allergenPlural.toLowerCase());
            if ((doesContainSingle == true) || doesContainPlural == true) {
                allergensFound.add(allergen);
            }
        }
        return allergensFound;
    }

    /**
     * Creates and stores a rating for a Food Product in the Rating database.
     * Allows the user to rate a Food Product in terms of reliability.
     *
     *
     * @param product The Food Product to be rated.
     * @param ratingValue The value of the rating (between 1 and 5).
     * @return The newly created rating.
     */
    public Rating rateItem(FoodProduct product, int ratingValue) {
        Rating newRating = new Rating(product, ratingValue);

        jsonmanager.saveRatingJSON(newRating);
        return newRating;
    }

    /**
     * Creates and stores a rating for a Brand in the Rating database. Allows
     * the user to rate a brand in terms of reliability.
     *
     * @param brand The brand to be rated
     * @param ratingValue The value of the rating (between 1 and 5).
     * @return The newly created rating.
     */
    public Rating rateItem(Brand brand, int ratingValue) {
        Rating newRating = null;
        if (jsonmanager.checkExists(brand)) {
            newRating = new Rating(brand, ratingValue);

        } else {
            System.err.println("Brand does not exist");
        }

        jsonmanager.saveRatingJSON(newRating);
        return newRating;
    }

    /**
     * Creates and stores a rating for a Shop in the rating database. Allows the
     * user to rate a shop in terms of reliability.
     *
     * @param shop The Shop to be rated.
     * @param ratingValue The value of the rating (between 1 and 5).
     * @return The new Rating if the Shop exists, null if the Shop does not
     * exist.
     */
    public Rating rateItem(Shop shop, int ratingValue) {
        Rating newRating = null;
        if (jsonmanager.checkExists(shop)) {
            newRating = new Rating(shop, ratingValue);

        } else {
            System.err.println("Shop does not exist");
        }

        jsonmanager.saveRatingJSON(newRating);
        return newRating;
    }

    /**
     * 'Interface.fxml' injections
     */
    @FXML
    private Button findProductButton;

    @FXML
    private Button myRatingsButton;

    @FXML
    private Label resultAllergens;

    @FXML
    private Button searchButton;

    @FXML
    private Label topLabel;

    @FXML
    private AnchorPane productResultPane;

    @FXML
    private Label resultMayContain;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane manageAllergiesPane;

    @FXML
    private TextField resultUpcTextField;

    @FXML
    private TextField enterUpcTextField;

    @FXML
    private AnchorPane enterManuallyPane;

    @FXML
    private TextField resultNameTextField;

    @FXML
    private Label resultIngredientsLabel;

    @FXML
    private AnchorPane findProductPane;

    @FXML
    private AnchorPane myRatingsPane;

    @FXML
    private AnchorPane mainMenuPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button manageAllergiesButton;

    @FXML
    private Button settingsButton;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private Region cameraRegion;

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private Label resultColourZone;

    @FXML
    private Button enterManuallyButton;

    /**
     * 'Interface.fxml' actions
     */
    Stage stage;
    Parent root = null;
    private AnchorPane lastVisible;
    private AnchorPane currentVisible = mainMenuPane;
    private String lastLabel;

    private void switchPane(AnchorPane hidePane, AnchorPane showPane, String header) {
        lastLabel = topLabel.getText();

        hidePane.setVisible(false);
        showPane.setVisible(true);
        topLabel.setText(header);

        lastVisible = hidePane;
        currentVisible = showPane;
    }

    @FXML
    void setupPanes() {
        mainMenuPane.setVisible(true);
        manageAllergiesPane.setVisible(false);
        myRatingsPane.setVisible(false);
        productResultPane.setVisible(false);
        settingsPane.setVisible(false);
        findProductPane.setVisible(false);
        enterManuallyPane.setVisible(false);
    }

    @FXML
    void goBack(ActionEvent event) {
        if ((currentVisible != mainMenuPane) && (lastVisible != null)) {
            switchPane(currentVisible, lastVisible, lastLabel);
        }
    }

    @FXML
    void openSettingsScene(ActionEvent event) {
        switchPane(mainMenuPane, settingsPane, "Settings");
    }

    @FXML
    void openZXingScene(ActionEvent event) {
        switchPane(mainMenuPane, findProductPane, "Scan");
    }

    @FXML
    void openEnterManuallyScene(ActionEvent event) {
        switchPane(findProductPane, enterManuallyPane, "Enter Manually");
    }

    @FXML
    void openManageAllergiesScene(ActionEvent event) {
        switchPane(mainMenuPane, manageAllergiesPane, "Manage Allergies");
    }

    @FXML
    void openMyRatingsScene(ActionEvent event) {
        switchPane(mainMenuPane, myRatingsPane, "My Ratings");
    }

    @FXML
    void findProductScanned(ActionEvent event) {
        //TODO
    }

    @FXML
    void findProductEntered(ActionEvent event) {
        //TODO Validate the text box to 8 or 13 digits

        FoodProduct currentProduct = jsonmanager.findProduct(enterUpcTextField.getText());

        if (currentProduct != null) {
            switchPane(enterManuallyPane, productResultPane, "Result");
            resultNameTextField.setText(currentProduct.getName());
            resultUpcTextField.setText(currentProduct.getUpc());
            resultIngredientsLabel.setText(currentProduct.getIngredientsString());
        } else if (currentProduct == null) {
            //Prompt barcode error, try again
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Barcode not found");
            alert.setHeaderText("Product code not found");
            alert.setContentText("The product code does not exist on the database.");

            //User either adds to database or sends me a report TODO
            alert.showAndWait();
        }

    }

    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

}

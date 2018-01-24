/*
 * Copyright 2017 Ross.
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

/**
 * Class for handling all JSON file conversions.
 *
 * @author Ross
 */
public final class JSONManager {

    private static final JSONManager managerInstance = new JSONManager();
    ObjectMapper mapper = new ObjectMapper(); //Used for json conversion.
    public String OpenFoodFactsURL = "https://uk.openfoodfacts.org/";
    JsonFactory factory = mapper.getFactory();

    private JSONManager() {
        //Exists only to defeat instantation. See https://www.javaworld.com/article/2073352/core-java/simply-singleton.html
    }

    /**
     * Checks if a JSONManager object exists, and if not creates one. This keeps
     * the JSONManager class singleton.
     *
     * @return The JSONManager object.
     */
    public static JSONManager getInstance() {
        return managerInstance;
    }

    /**
     * Saves a Rating object to the ratings database.
     *
     * @param rating the rating to be saved.
     */
    public void saveRatingJSON(Rating rating) {
        //TODO Serialize ratings to database
    }

    /**
     * Reads previously saved JSON files and converts to Consumer objects. This
     * way consumer objects do not need resetting.
     *
     * @return A set of the existing consumers.
     */
    public HashSet<Consumer> setUpConsumers() {
        HashSet<Consumer> consumers = new HashSet<>();

        //Point to folder
        File folder = new File("../ConsumerJSON");

        //For each file in the folder
        for (File consumerFile : folder.listFiles()) {
            Consumer newConsumer = new Consumer(null, null);
            try {
                JsonParser parser = factory.createParser(consumerFile);
                if (parser.nextToken() != JsonToken.START_OBJECT) {
                    System.err.println("Expected file to start with an object");
                }

                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    parser.nextToken();
                    String fieldName = parser.getCurrentName();
                    switch (fieldName) {
                        case "name":
                            newConsumer.setName(parser.getText());
                            break;
                        case "allergies":
                            newConsumer.setAllergies(parser.readValueAs(HashSet.class));
                            break;
                        default:
                            System.err.println("Name or allergies field not found whilst loading" + consumerFile);
                            break;
                    }
                }

            } catch (IOException ex) {
                System.err.println("Error reading the consumer file" + consumerFile.toString());
            }

            consumers.add(newConsumer);
        }
        return consumers;
    }

    /**
     * Saves the Consumer details to a JSON file.
     *
     * @param consumerObject The consumer object to be saved.
     */
    protected void saveConsumerJSON(Consumer consumerObject) {
        try {
            //Save to file
            File json = new File("../ConsumerJSON/" + consumerObject.getName());
            json.createNewFile(); //This line will not run if file already exists.
            mapper.writeValue(json, consumerObject);//Change
        } catch (IOException ex) {
            System.err.println("The file did not save.");
        }
    }

    /**
     * Converts the found product .json into a FoodProduct object with matching
     * details.
     *
     * @param upc The barcode of the FoodProduct to find.
     * @return the FoodProduct object created with those details found in the
     * database.
     */
    public FoodProduct findProduct(String upc) {

        //Build the URL
        URL url = URLBuilder("/api/v0/product/" + upc + ".json");

        FoodProduct product = null;
        try {
            //Convert to a FoodProduct object
            JsonNode root = mapper.readTree(url);
            String status = root.get("status").asText();
            if (!"0".equals(status)) {
                String productName = root.with("product").get("product_name_en").asText();
                String code = root.get("code").asText();
                String ingredients = root.with("product").get("ingredients_text_en").asText();
                if (productName == null) {
                    System.err.println("product name is null");
                }
                if (ingredients == null) {
                    System.err.println("ingredients are null");
                }
                product = new FoodProduct(productName, code, ingredients);
            }
            else
            {
                return product;
            }
        } catch (IOException ex) {
            System.err.println("The product cannot be found on the database.");
        }
        return product;
    }

    /**
     * Uses a subDomain at the end of the Open Food Facts API url to find the
     * product on the Open Food Facts database.
     *
     * @param subDomain The text to add at the end of the URL.
     * @return The newly created URL.
     */
    protected URL URLBuilder(String subDomain) {
        URL url = null;
        try {
            url = new URL(OpenFoodFactsURL + subDomain + ".json");
        } catch (MalformedURLException ex) {
            System.err.println("URL could not be found for product UPC " + subDomain);
        }
        return url;
    }

    /**
     * Checks the Open Food Facts database for the brand name to check that it
     * exists.
     *
     * @param brand The brand or manufacturer to be checked.
     * @return true if the brand exists, false otherwise.
     */
    Boolean checkExists(Brand brand) {
        //Build the URL
        URL url = null;
        try {
            url = new URL(OpenFoodFactsURL + "/brand/" + brand.getName() + ".json");
        } catch (MalformedURLException ex) {
            System.err.println("Brand could not be found on the database");
        }
        JsonNode root;
        int count = 0;
        try {
            root = mapper.readTree(url);
            count = root.get("count").asInt();
        } catch (IOException ex) {
            System.err.println("Error reading the json file.");
        }
        //Check if count is greater than 0. Count represents the amount of
        //products associated to this Brand.
        return count > 0; //Brand exists
    }

    /**
     * Checks the Open Food Facts database for the shop name to check that it
     * exists.
     *
     * @param shop The shop to be checked.
     * @return true if shop exists, false if shop does not exist.
     */
    Boolean checkExists(Shop shop) {
        //Build the URL
        URL url = null;
        try {
            url = new URL(OpenFoodFactsURL + "/brand/" + shop.getName() + ".json");
        } catch (MalformedURLException ex) {
            System.err.println("Brand could not be found on the database");
        }
        JsonNode root;
        int count = 0;
        try {
            root = mapper.readTree(url);
            count = root.get("count").asInt();
        } catch (IOException ex) {
            System.err.println("Error reading the json file.");
        }
        //Check if count is greater than 0. Count represents the amount of
        //products associated to this Brand.
        return count > 0; //Brand exists
    }
}

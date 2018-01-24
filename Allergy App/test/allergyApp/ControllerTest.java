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

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ross
 */
public class ControllerTest {

    public ControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of updateAllergens method, of class Controller.
     */
    @Test
    public void testUpdateAllergens() {
        System.out.println("updateAllergens");

        //setup
        Consumer ross = new Consumer("ross", null);
        HashSet<String> testAllergies = new HashSet<>();
        testAllergies.add("peanut");
        testAllergies.add("tree nut");
        testAllergies.add("egg");
        Controller instance = new Controller();

        //method
        Consumer expResult = ross;
        Consumer result = instance.updateAllergens(ross, testAllergies);

        //test that allergies match
        assertEquals("The collections do not match", testAllergies, ross.getAllergies());
    }

    /**
     * Test of createNewConsumer method, of class Controller.
     */
    @Test
    public void testCreateNewConsumer() {
        System.out.println("createNewConsumer");

        //setup
        String consumerName = "jane";
        HashSet<String> consumerAllergies = new HashSet<>();
        consumerAllergies.add("peanut");
        Controller instance = new Controller();

        //method
        Consumer result = instance.createNewConsumer(consumerName, consumerAllergies);

        //test that allergies match
        assertEquals("The collections do not match", consumerAllergies, result.getAllergies());
    }

    /**
     * Test of checkAllergens method, of class Controller.
     */
    @Test
    public void testCheckAllergens() {
        System.out.println("checkAllergens");

        //setup
        HashSet<String> testAllergens = new HashSet<>();
        testAllergens.add("peanut");
        testAllergens.add("hazelnut");
        testAllergens.add("egg");
        Consumer aConsumer = new Consumer("ross", testAllergens);
        JSONManager manager = JSONManager.getInstance();
        FoodProduct aProduct = manager.findProduct("01268584");
        Controller instance = new Controller();

        //method
        Set<String> result = instance.checkAllergens(aConsumer, aProduct);
        assertTrue("the egg allergen was not detected", result.contains("egg"));
    }

    /**
     * Test of rateItem method, of class Controller.
     */
    @Test
    public void testRateItem_FoodProduct_int() {
        //setup
        System.out.println("rateItem, FoodProduct arg");
        JSONManager manager = JSONManager.getInstance();
        FoodProduct product = manager.findProduct("01268584");
        int ratingValue = 4;
        Controller instance = new Controller();
        
        //method
        Rating expResult = new Rating(product, 4);
        Rating result = instance.rateItem(product, ratingValue);
        assertEquals(expResult.getProduct(), result.getProduct());
        assertEquals(expResult.getRatingValue(), result.getRatingValue(), 0);
    }

    /**
     * Test of rateItem method, of class Controller.
     */
    @Test
    public void testRateItem_Brand_int() {
        //setup
        System.out.println("rateItem, Brand arg");
        Brand brand = new Brand("Sainsbury-s");
        int ratingValue = 3;
        Controller instance = new Controller();
        
        //method
        Rating expResult = new Rating(brand, 3);
        Rating result = instance.rateItem(brand, ratingValue);
        assertEquals(expResult.getBrand(), result.getBrand());
        assertEquals(expResult.getRatingValue(), result.getRatingValue(), 0);
    }

    /**
     * Test of rateItem method, of class Controller.
     */
    @Test
    public void testRateItem_Shop_int() {
        System.out.println("rateItem, shop arg");
        Shop shop = new Shop("Sainsbury-s");
        int ratingValue = 2;
        Controller instance = new Controller();
        
        Rating expResult = new Rating(shop, 2);
        Rating result = instance.rateItem(shop, ratingValue);
        assertEquals(expResult.getShop(), result.getShop());
        assertEquals(expResult.getRatingValue(), expResult.getRatingValue(), 0);
    }

}

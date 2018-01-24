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

/**
 *
 * @author Ross
 */
public class FoodProduct {

    String name;
    String upc;
    String ingredients;

    /**
     * Constructor used to create a FoodProduct object from the retrieved
     * information. This should not be invoked outside of
     * controller.findProduct() except for testing.
     *
     * @param name The name of the product.
     * @param upc The Unique Product Code of the product.
     * @param ingredients The ingredients of the product.
     */
    public FoodProduct(String name, String upc, String ingredients) {
        this.name = name;
        this.upc = upc;
        this.ingredients = ingredients;
    }

    /**
     * Returns the name of the FoodProduct.
     *
     * @return name - The name of the FoodProduct.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the upc of the FoodProduct.
     *
     * @return upc - The upc of the FoodProduct.
     */
    public String getUpc() {
        return upc;
    }

    /**
     * Returns the ingredients of the FoodProduct as a String to be compatible
     * with OpenFoodFacts database.
     *
     * @return ingredients - The ingredients of the FoodProduct.
     */
    public String getIngredientsString() {
        return ingredients;
    }
}

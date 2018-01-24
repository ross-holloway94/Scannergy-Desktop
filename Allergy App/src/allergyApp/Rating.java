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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rating for a Food Product, Brand, or Shop with ratingValue between 1 to 5.
 * Planning comment ability in the future.
 *
 * @author Ross
 */
public class Rating {

    private final SearchType ratingFor; //String - product, brand or shop.
    private int ratingValue; //rating value 1-5
    private FoodProduct product;
    private Brand brand;
    private Shop shop;

    /**
     * Rating constructor for Food Products.
     *
     * @param product The Food Product to be rated.
     * @param ratingValue The setRatingValue of the rating, must be between 1
     * and 5.
     */
    public Rating(FoodProduct product, int ratingValue) {
        this.product = product;
        ratingFor = SearchType.PRODUCT;
        setRatingValue(ratingValue);
    }

    /**
     * Rating constructor for Brands.
     *
     * @param brand The Brand to be rated.
     * @param ratingValue The setRatingValue of the rating, must be between 1
     * and 5.
     */
    public Rating(Brand brand, int ratingValue) {
        this.brand = brand;
        ratingFor = SearchType.BRAND;
        setRatingValue(ratingValue);
    }

    /**
     * Rating constructor for Shops.
     * 
     * @param shop The Shop to be rated.
     * @param ratingValue The setRatingValue of the rating, must be between 1
     * and 5.
     */
    public Rating(Shop shop, int ratingValue) {
        this.shop = shop;
        ratingFor = SearchType.SHOP;
        setRatingValue(ratingValue);
    }

    /**
     * Sets the setRatingValue and validates between 1 - 5.
     *
     * @param ratingValue
     */
    private void setRatingValue(int ratingValue) {
        if (ratingValue > 5) {
            this.ratingValue = 5;
        }
        if (ratingValue < 1) {
            this.ratingValue = 1;
        }
        else this.ratingValue = ratingValue;
    }

    /**
     * @return the product
     */
    @JsonProperty("product")
    public FoodProduct getProduct() {
        return product;
    }

    /**
     * @return the brand
     */
    @JsonProperty("brand")
    public Brand getBrand() {
        return brand;
    }

    /**
     * @return the shop
     */
    @JsonProperty("shop")
    public Shop getShop() {
        return shop;
    }

    /**
     * @return the rating value
     */
    @JsonProperty("ratingValue")
    public int getRatingValue() {
        return ratingValue;
    }

    /**
     * @return The upc of the associated Food Product.
     */
    @JsonProperty("code")
    public String getCode() {
        return product.getUpc();
    }

    /**
     * @return Enum value of SearchType - For example, brand, product or shop.
     */
    @JsonProperty("ratingFor")
    public SearchType getRatingFor() {
        return ratingFor;
    }
    
    /**
     * @param brand the brand to set
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    
}

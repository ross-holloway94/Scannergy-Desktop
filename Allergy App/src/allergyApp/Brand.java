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
 * Represents a Brand or Manufacturer of a food product so that it can be rated
 * by a user.
 *
 */
public class Brand {

    private final String name;

    /**
     * Constructor with the name of the brand.
     *
     * @param brandName The name of the Brand.
     */
    public Brand(String brandName) {
        name = brandName;
    }

    /**
     * Returns the name of the brand.
     *
     * @return The name of the brand.
     */
    public String getName() {
        return name;
    }

//TODO Hold a collection of Ratings.
}

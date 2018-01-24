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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashSet;

/**
 * The person whom will be consuming the food. This may not be the the user.
 *
 * @author Ross Holloway
 */
public class Consumer {

    private String name; //Not final in case of user mistype.
    private HashSet<String> allergies = new HashSet();
    JSONManager jsonmanager = JSONManager.getInstance();

    /**
     * Constructor to set name and allergies upon creation, and saves to file.
     *
     * @param consumerName The name of the Consumer.
     * @param consumerAllergies Set of consumer's allergies.
     */
    public Consumer(String consumerName, HashSet consumerAllergies) {
        name = consumerName;
        allergies = consumerAllergies;
    }
    
    /**
     * Sets the name of the Consumer, and then saves to file.
     * 
     * @param name the name to set
     */
    protected void setName(String name) {
        this.name = name;
        jsonmanager.saveConsumerJSON(this);
    }
    
    /**
     * Returns the consumer's name.
     *
     * @return The name of the consumer.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Returns the Consumer's allergies.
     *
     * @return The consumer's allergies.
     */
    public HashSet<String> getAllergies() {
        return allergies;
    }

    /**
     * Stores the Consumer's allergies, so they can be compared against product
     * ingredients. Overwrites previously saved allergies, meaning they can be
     * edited by the user.
     *
     * @param consumerAllergies the consumer's allergies.
     */
    @JsonProperty("allergies")
    @JsonDeserialize(as=HashSet.class)
    public void setAllergies(HashSet<String> consumerAllergies) {
        allergies = consumerAllergies;
        jsonmanager.saveConsumerJSON(this);
    }
}

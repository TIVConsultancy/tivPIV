/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.interfaces;

import java.util.List;

/**
 *
 * @author Thomas
 * @param <T>
 */
public interface DistributionClass<T> extends Position{
    
    /**
     * Adds the content to the bin
     * @param loAdd content to be added.
     */
    public void addContent(T loAdd);
    
    /**
     * Returns the minimum allowed in the bin (e.g. min size)
     * @return minimum feature represented as a double.
     */
    public Double getMinBorder();
    
    /**
     * Returns the maximum allowed in the bin (e.g. max size)
     * @return maximum feature represented as a double.
     */
    public Double getMax();
    
    /**
     * This methods checks if the feature represented as double is
     * inside the bin.
     * @param dValue feature represented as double
     * @return true if inside, false if outside
     */
    public boolean isInside(Double dValue);
    
    /**
     * Returns the content inside the bin.
     * @return content of the bin as List.
     */
    public List<T> getContent();
    
}

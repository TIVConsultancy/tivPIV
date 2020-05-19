/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.physics.interfaces;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Trackable extends Serializable {
    public OrderedPair getPosition();
    
    public void setTracked(boolean bTracked);
    
    public Double getSize(); 
    
    public boolean isTracked();
    
    public Trackable shift(OrderedPair op);
    
    public Double getDistance(Trackable o);
    
    public Trackable getNearest(List<? extends Trackable> lo);
    
    public Trackable rotate(OrderedPair op, double dAlpha);
}

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.physics.interfaces;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.primitives.Vector;


/**
 *
 * @author Thomas Ziegenhein
 */
public interface Velocity {
    public void calcVelocityXY(Trackable o1, Trackable o2, double dT);
    
    public Double getVelocityX();
    
    public Double getVelocityY();
    
    public Double getVelocityZ();
    
    public OrderedPair getPositionXY();
    
    public Double getSize();
    
    public Vector getVec();
    
}

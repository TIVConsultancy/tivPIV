/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.physics.interfaces;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;


/**
 *
 * @author Thomas Ziegenhein
 */
public interface Accel {
    public void calcAccelerationXY(Trackable o1, Trackable o2, Trackable o3, double dT);
    
    public Double getAccelerationX();
    
    public Double getAccelerationY();
    
    public Double getAccelerationZ();
    
    public OrderedPair getPositionXY();
}

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.physics.interfaces;

import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface VelocityPair {
    public void addParticle(Trackable oParticle);
    
    public Trackable getLastParticle();
    
    public List<? extends Trackable> getContent();
    
    public void calculateVelocities();
    
    public void calculateAcceleration();
    
    public void closeTrack();
    
    public boolean isOpen();
}

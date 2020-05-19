/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.velocimetry.iterativemethods;

import com.tivconsultancy.opentiv.physics.interfaces.Trackable;
import com.tivconsultancy.opentiv.velocimetry.primitives.TrackAblePair;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class LostVelocity extends TrackAblePair {

    public LostVelocity(Trackable oVFrame1, Trackable oVFrame2) {
        super(oVFrame1, oVFrame2);
    }
    
    public LostVelocity(Trackable oVFrame1) {
        super(oVFrame1, null);
    }
    
    @Override
    public void updateCoeffBernahrdAndThompson(List<TrackAblePair> loLikelyPairs, double A, double B){
            
    }
    
    @Override
    public void normalizeCoeffBarnarddAndThompson(List<TrackAblePair> loAllPairs, LostVelocity oLostVelo) {
        Double dSummation = 0.0;
        for(TrackAblePair ovp : loAllPairs){
            if(ovp.oVFrame1.equals(super.oVFrame1)){
                dSummation = dSummation + ovp.dCoeffNew;
            }
        }        
        this.dCoeffNew = 1.0-dSummation;            
    }
    
}

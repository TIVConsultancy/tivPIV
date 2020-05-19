/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.velocimetry.iterativemethods;

import com.tivconsultancy.opentiv.velocimetry.primitives.TrackAblePair;
import java.util.Comparator;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SortTrackablePair implements Comparator<TrackAblePair> {

    @Override
    public int compare(TrackAblePair o1, TrackAblePair o2) {
        
        if(o1.dCoeffNew < o2.dCoeffNew){
            return -1;
        }
        
        if(o1.dCoeffNew > o2.dCoeffNew){
            return 1;
        }
        
        return 0;
    }
    
}

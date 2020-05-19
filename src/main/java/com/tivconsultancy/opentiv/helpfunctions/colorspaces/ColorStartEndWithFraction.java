/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.colorspaces;

import com.tivconsultancy.opentiv.math.sets.Set1D;
import java.awt.Color;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ColorStartEndWithFraction {

    public Set1D oStartEnd;
    public float[] fColorStart;
    public float[] fColorEnd;

    public ColorStartEndWithFraction(Double dStartFraction, Double dEndFraction, float[] fColorStart, float[] fColorEnd) {
        this.oStartEnd = new Set1D(dStartFraction, dEndFraction);
        this.fColorStart = fColorStart;
        this.fColorEnd = fColorEnd;
    }

    public float[] interpolate(Double dFraction) {
        float[] fColor = new float[fColorStart.length];
        for (int i = 0; i < fColorStart.length; i++) {
            fColor[i] = (float) ((dFraction - oStartEnd.dLeftBorder) / (oStartEnd.dRightBorder - oStartEnd.dLeftBorder) * (fColorEnd[i] - fColorStart[i])) + fColorStart[i];
        }
        return fColor;
    }

}

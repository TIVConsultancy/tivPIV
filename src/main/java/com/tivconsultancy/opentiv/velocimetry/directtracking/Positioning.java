/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.velocimetry.directtracking;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.List;
/**
 *
 * @author Thomas Ziegenhein
 */
public class Positioning {

    public static OrderedPair weightedPosition(List<? extends OrderedPair> loIn, Weight WeightOperation) {
        Double dIntegratorX = 0.0;
        Double dIntegratorY = 0.0;
        Double dSum = 0.0;
        for (OrderedPair op : loIn) {
            Double dWeight;
            if (WeightOperation != null) {
                dWeight = WeightOperation.getWeight(loIn, op);
            } else {
                dWeight = 1.0;
            }

            dIntegratorX = dIntegratorX + op.x * dWeight;
            dIntegratorY = dIntegratorY + op.y * dWeight;
            dSum = dSum + dWeight;
        }
        return new OrderedPair(dIntegratorX / dSum, dIntegratorY / dSum);
    }

    public static OrderedPair ellipticalGausFit(List<? extends OrderedPair> loIn) {
        /*
         Based on:
         Stephen M. Anthony and Steve Granick "Image Analysis with Rapid and Accurate Two-Dimensional Gaussian Fitting" Langmuir, 2009, 25 (14), pp 8152–8160 DOI: 10.1021/la900393v
         */
        double[][] daLeftSide = new double[loIn.size()][1];
        double[][] ddaMatrix = new double[loIn.size()][5];

        int i = 0;
        for (OrderedPair op : loIn) {
            daLeftSide[i][0] = Math.log(Math.abs(255-op.dValue));
            ddaMatrix[i][0] = op.x * op.x;
            ddaMatrix[i][1] = op.x;
            ddaMatrix[i][2] = op.y * op.y;
            ddaMatrix[i][3] = op.y;
            ddaMatrix[i][4] = 1.0;
            i++;
        }
        Jama.Matrix JMatrix = new Jama.Matrix(ddaMatrix);
        Jama.Matrix JInv;
        try {
            JInv = JMatrix.inverse();
        } catch (Exception e) {
            return null;
        }

        Jama.Matrix daVal = JInv.times(new Jama.Matrix(daLeftSide));

        Double dX = -0.5 * daVal.get(1, 0) / daVal.get(0, 0);
        Double dY = -0.5 * daVal.get(3, 0) / daVal.get(2, 0);

        return new OrderedPair(dX, dY);

    }

    public static interface Weight {

        public Double getWeight(List<? extends OrderedPair> loIn, OrderedPair op);
    }

}

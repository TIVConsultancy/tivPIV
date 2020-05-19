/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import java.util.List;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Splines {

    public static org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction getSpline(List<ImagePoint> loPoints) {

        boolean bX = true;
        boolean bY = true;

        for (int i = 1; i < loPoints.size(); i++) {
            if ((loPoints.get(i - 1).getPos().x - loPoints.get(i).getPos().x) >= 0) {
                bX = false;
            }
            if ((loPoints.get(i - 1).getPos().y - loPoints.get(i).getPos().y) <= 0) {
                bY = false;
            }
        }

        if (!bX && !bY) {
            return null;
        }

        double[] xPoints = new double[loPoints.size()];
        double[] yPoints = new double[loPoints.size()];
        if (bX) {
            for (int i = 0; i < loPoints.size(); i++) {
                xPoints[i] = loPoints.get(i).getPos().x;
                yPoints[i] = -1.0*loPoints.get(i).getPos().y;
            }
        } else {
            for (int i = 0; i < loPoints.size(); i++) {
                xPoints[i] = -1.0*loPoints.get(i).getPos().y;
                yPoints[i] = loPoints.get(i).getPos().x;
            }
        }

        SplineInterpolator o = new SplineInterpolator();
        PolynomialSplineFunction oSpline = o.interpolate(xPoints, yPoints);

        return oSpline;

    }
    
    public static Boolean checkRotation(List<ImagePoint> loPoints){
        boolean bX = true;
        boolean bY = true;

        for (int i = 1; i < loPoints.size(); i++) {
            if ((loPoints.get(i - 1).getPos().x - loPoints.get(i).getPos().x) >= 0) {
                bX = false;
            }
            if ((loPoints.get(i - 1).getPos().y - loPoints.get(i).getPos().y) <= 0) {
                bY = false;
            }
        }

        if (!bX && !bY) {
            return null;
        }
        
        return !bX;
        
    }

}

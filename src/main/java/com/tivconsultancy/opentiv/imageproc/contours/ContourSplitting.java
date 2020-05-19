/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.contours;

import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ContourSplitting {

    public static List<CPX> splitByCurvature(int iCurvOrder, int iTangOrder, double dCurvThresh, List<CPX> loInput) {
        List<ImagePoint> loBreakPointsCurv = new ArrayList<>();
        for (CPX o : loInput) {
            if (o.lo.size() < 2 * iCurvOrder) {
                continue;
            }
            for (int i = 0; i < o.lo.size(); i++) {
//                if(Math.abs(o.getCurv(i, iCuvrOrder, iTangOrder)) > 1 || o.islocallyconvex(iConvOrder, i, oHelp) ){
                if (Math.abs(o.getCurv(i, iCurvOrder, iTangOrder)) > dCurvThresh) {
                    loBreakPointsCurv.add(o.lo.get(i));
                }
            }
        }

        for (CPX o : loInput) {
            o.lo.removeAll(loBreakPointsCurv);
        }

        return loInput;
    }

}

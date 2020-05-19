/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Line2 {

    ImagePoint meStart;
    ImagePoint meEnd;
    public List<ImagePoint> lmeLine = new ArrayList<>();
    
    public static int maxPoints = 2000;

    public Line2(ImagePoint meStart, ImagePoint meEnd) {

        this.meStart = meStart;
        this.meEnd = meEnd;
        if (meStart.getNorm(meEnd) < 2) {
            this.lmeLine.add(meStart);
            this.lmeLine.add(meEnd);
        } else {
            this.lmeLine = BresenhamAlgorithm(meStart, meEnd);
        }
    }

    public static List<ImagePoint> BresenhamAlgorithm(ImagePoint meStart, ImagePoint meEnd) {

        int x1 = (int) meStart.getPos().x;
        int y1 = (int) meStart.getPos().y;

        int x2 = (int) meEnd.getPos().x;
        int y2 = (int) meEnd.getPos().y;

        if (!((x2 - x1) > 1 || (y2 - y1) > 1)) {
            x1 = (int) meEnd.getPos().x;
            y1 = (int) meEnd.getPos().y;

            x2 = (int) meStart.getPos().x;
            y2 = (int) meStart.getPos().y;
        }

        List<ImagePoint> lmeLineEntries = new ArrayList<>();

        if ((x2 - x1) > 1 || (y2 - y1) > 1) {

            boolean bSwitch = false;
            if (Math.abs(y2 - y1) > Math.abs(x2 - x1)) {
                bSwitch = true;
                int temp = x1;
                x1 = y1;
                y1 = temp;
                temp = x2;
                x2 = y2;
                y2 = temp;
            }
            // If line goes from right to left, swap the endpoints
            if (x2 - x1 < 0) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }

            int x, // Current x position
                    y = y1, // Current y position
                    e = 0, // Current error
                    m_num = y2 - y1, // Numerator of slope
                    m_denom = x2 - x1, // Denominator of slope
                    threshold = m_denom / 2;  // Threshold between E and NE increment 

            for (x = x1; x < Math.min(x2, maxPoints); x++) {
                if (bSwitch) {
                    lmeLineEntries.add(new ImagePoint(y, x, 255, meStart.getGrid()));
                } else {
                    lmeLineEntries.add(new ImagePoint(x, y, 255, meStart.getGrid()));
                }

                e += m_num;

                if (m_num < 0) {
                    if (e < -threshold) {
                        e += m_denom;
                        y--;
                    }
                } else if (e > threshold) {
                    e -= m_denom;
                    y++;
                }
            }

            if (bSwitch) {
                lmeLineEntries.add(new ImagePoint(y, x, 255, meStart.getGrid()));
            } else {
                lmeLineEntries.add(new ImagePoint(x, y, 255, meStart.getGrid()));
            }
        }

        return lmeLineEntries;
    }
    
    public ImagePoint getCrossPoint(Line2 oLine){
        for(ImagePoint oInner : lmeLine){
            for(ImagePoint oOuter: oLine.lmeLine){
                if(oInner.getNorm(oOuter) <=1){
                    return oInner;
                }
            }
        }
        return null;
    }

    public ImageGrid setLine(ImageGrid oGrid, int iValue) {
        oGrid.setPoint(lmeLine, iValue);
        return oGrid;
    }
    
}

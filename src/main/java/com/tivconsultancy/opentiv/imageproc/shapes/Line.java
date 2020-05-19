/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ziegen60
 */
public class Line {

    public MatrixEntry meStart;
    public MatrixEntry meEnd;
    public List<MatrixEntry> lmeLine = new ArrayList<MatrixEntry>();
    
    public static int maxPoints = 2000;

    public Line(MatrixEntry meStart, MatrixEntry meEnd) {

        this.meStart = meStart;
        this.meEnd = meEnd;
        if (MatrixEntry.SecondCartesian(meEnd, meStart) < 2) {
            this.lmeLine.add(meStart);
            this.lmeLine.add(meEnd);
        } else {
            this.lmeLine = BresenhamAlgorithm(meStart, meEnd);
        }
    }

    public static List<MatrixEntry> BresenhamAlgorithm(MatrixEntry meStart, MatrixEntry meEnd) {

        int x1 = meStart.j;
        int y1 = meStart.i;

        int x2 = meEnd.j;
        int y2 = meEnd.i;

        if (!((x2 - x1) > 1 || (y2 - y1) > 1)) {
            x1 = meEnd.j;
            y1 = meEnd.i;

            x2 = meStart.j;
            y2 = meStart.i;
        }

        List<MatrixEntry> lmeLineEntries = new ArrayList<MatrixEntry>();

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

            for (x = x1; x < Math.min(x2, Line.maxPoints); x++) {
                if (bSwitch) {
                    lmeLineEntries.add(new MatrixEntry(x, y));
                } else {
                    lmeLineEntries.add(new MatrixEntry(y, x));
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
                lmeLineEntries.add(new MatrixEntry(x, y));
            } else {
                lmeLineEntries.add(new MatrixEntry(y, x));
            }
        }

        return lmeLineEntries;
    }

    public ImageInt setLine(ImageInt iaBlackBord, int iValue) {
        for (MatrixEntry me : lmeLine) {
            iaBlackBord.setPoints(lmeLine, iValue);
        }
        return iaBlackBord;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Line) {
            Line oCompare = (Line) o;
            if (oCompare.meStart.equals(this.meStart) && oCompare.meEnd.equals(this.meEnd)) {
                return true;
            }
            if (oCompare.meStart.equals(this.meEnd) && oCompare.meEnd.equals(this.meStart)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.meStart);
        hash = 37 * hash + Objects.hashCode(this.meEnd);
        return hash;
    }

}

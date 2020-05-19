/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.algorithms.algorithms;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixGenerator;
import static com.tivconsultancy.opentiv.helpfunctions.operations.Convolution.Convolution;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class NoiseReduction {

    public static ImageGrid simple1(ImageGrid oPic, int iThreshold) {
        for (ImagePoint op : oPic.oa) {
            List<ImagePoint> lo = oPic.getNeighborsN8(op);
            int iSum = 0;
            int iCount = 0;
            for (ImagePoint opIntern : lo) {
                if (opIntern != null) {
                    iSum = iSum + opIntern.iValue;
                    iCount++;
                }
            }
            if (iCount != 0) {
                if (Math.abs(iSum / iCount - op.iValue) < iThreshold) {
                    op.iValue = iSum / iCount;
                }
            }
        }
        return oPic;
    }

    public static void simple1(ImageInt oInput, int iThreshold) {
        for (int i = 0; i < oInput.iaPixels.length; i++) {
            for (int j = 0; j < oInput.iaPixels[0].length; j++) {
                List<MatrixEntry> lo = oInput.getNeighborsN8(i, j);
                int iSum = 0;
                int iCount = 0;
                for (MatrixEntry p : lo) {
                    if (p != null) {
                        iSum = iSum + oInput.iaPixels[p.i][p.j];
                        iCount++;
                    }
                }
                if (iCount != 0) {
                    if (Math.abs(iSum / iCount - oInput.iaPixels[i][j]) < iThreshold) {
                        oInput.iaPixels[i][j] = iSum / iCount;
                    }
                }
            }
        }
    }

    public static ImageGrid Gauß(ImageGrid oInput) {

        return new ImageGrid(Convolution(oInput.getMatrix(), MatrixGenerator.get3x3NormalDistribution()));

    }
    
    public static ImageInt Gauß(ImageInt oInput) {
        return new ImageInt(Convolution(oInput.iaPixels, MatrixGenerator.get3x3NormalDistribution()));
    }
    
    public static ImageInt Gauß5x5(ImageInt oInput) {
        return new ImageInt(Convolution(oInput.iaPixels, MatrixGenerator.get5x5NormalDistribution()));
    }
    
    public static ImageInt Box3x3(ImageInt oInput) {
        return new ImageInt(Convolution(oInput.iaPixels, MatrixGenerator.get3x3Smear()));
    }

    public static int[][] Gau(int[][] iaInput) {

        return Convolution(iaInput, MatrixGenerator.get3x3NormalDistribution());

    }

}

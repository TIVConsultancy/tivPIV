/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import static com.tivconsultancy.opentiv.helpfunctions.statistics.Basics.Arithmetic;
import static com.tivconsultancy.opentiv.helpfunctions.statistics.Basics.getVariance;
import static com.tivconsultancy.opentiv.helpfunctions.statistics.Basics.median;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Writer;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.shapes.Rectangle;
import com.tivconsultancy.opentiv.math.grids.CellRec;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import com.tivconsultancy.opentiv.velocimetry.helpfunctions.FFT;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataBoolean;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataDouble;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataInt;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * InterrArea is by default a rectangular shape by extending the CellRec class
 *
 * @author Thomas Ziegenhein
 */
public class InterrArea extends CellRec implements Area, Serializable {

    private static final long serialVersionUID = -8921264415788907575L;

    FFT fftDisp;
    boolean bMasked = false;
    MatrixEntry mePosInGrid = null;
    public Double dVx = null;
    public Double dVy = null;
    public boolean bOutlier = false;
    public boolean bOnceValidated = false;
    public AreaInSecondFrame oAreaSecFrame = null;
    InterrArea[][] oRefinedAreas;

    public InterrArea(Set1D oIntervalX, Set1D oIntervalY) {
        super(oIntervalX, oIntervalY);
        this.oAreaSecFrame = new RecArea(oIntervalX, oIntervalY);
    }

    @Override
    public int[][] getValuesInAreaInt(getDataInt o) {
        int[][] iaData = o.getfield();
        int[][] iaSub = new int[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder; i < oIntervalY.dRightBorder; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder; j < oIntervalX.dRightBorder; j++) {
                if (i >= iaData.length || j >= iaData[0].length) {
                    iaSub[iSub][jSub] = 0;
                } else {
                    iaSub[iSub][jSub] = iaData[i][j];
                }
                jSub++;
            }
            iSub++;
        }
        return iaSub;
    }

    @Override
    public double[][] getValuesInAreaDouble(getDataDouble o) {
        double[][] iaData = o.getfield();
        double[][] iaSub = new double[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder; i < oIntervalY.dRightBorder; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder; j < oIntervalX.dRightBorder; j++) {
                if (i < 0 || i >= iaData.length || j < 0 || j >= iaData[0].length) {
                    iaSub[iSub][jSub] = 0.0;
                } else {
                    iaSub[iSub][jSub] = iaData[i][j];
                }
                jSub++;
            }
            iSub++;
        }
        return iaSub;
    }

    @Override
    public double[][] getValuesInAreaDouble(getDataDouble o, OrderedPair subPixelShift) {
        double[][] iaData = o.getfield();
        double[][] iaSub = new double[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iPixelShiftY = (int) Math.ceil(subPixelShift.y);
        double dSubPixelShiftY = subPixelShift.y - iPixelShiftY;
        int iPixelShiftX = (int) Math.ceil(subPixelShift.x);
        double dSubPixelShiftX = subPixelShift.x - iPixelShiftX;
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder + iPixelShiftY; i < oIntervalY.dRightBorder + iPixelShiftY; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder + iPixelShiftX; j < oIntervalX.dRightBorder + iPixelShiftX; j++) {
                if (i < 0 || i + 1 >= iaData.length || j < 0 || j + 1 >= iaData[0].length) {
                    iaSub[iSub][jSub] = 0.0;
                } else {
                    if (dSubPixelShiftX == 0 && dSubPixelShiftY == 0) {
                        iaSub[iSub][jSub] = iaData[i][j];
                    } else if (dSubPixelShiftX > 0 && dSubPixelShiftY == 0) {
                        double f_q11 = iaData[i][j];
                        double f_q21 = iaData[i][j + 1];
                        double wx1 = dSubPixelShiftX;
                        double wx2 = 1.0 - dSubPixelShiftX;
                        iaSub[iSub][jSub] = (f_q11 * wx2 + f_q21 * wx1);
                    } else if (dSubPixelShiftX < 0 && dSubPixelShiftY == 0) {
                        double f_q11 = iaData[i][j];
                        double f_q21 = iaData[i][j - 1];
                        double wx1 = Math.abs(dSubPixelShiftX);
                        double wx2 = 1.0 - Math.abs(dSubPixelShiftX);
                        iaSub[iSub][jSub] = (f_q11 * wx2 + f_q21 * wx1);
                    } else if (dSubPixelShiftX == 0 && dSubPixelShiftY > 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i + 1][j];
                        double wy1 = dSubPixelShiftY;
                        double wy2 = 1.0 - dSubPixelShiftY;
                        iaSub[iSub][jSub] = f_q11 * wy2 + f_q12 * wy1;
                    } else if (dSubPixelShiftX == 0 && dSubPixelShiftY < 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i + 1][j];
                        double wy1 = Math.abs(dSubPixelShiftY);
                        double wy2 = 1.0 - Math.abs(dSubPixelShiftY);
                        iaSub[iSub][jSub] = f_q11 * wy2 + f_q12 * wy1;
                    } else if (dSubPixelShiftX > 0 && dSubPixelShiftY > 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i + 1][j];
                        double f_q21 = iaData[i][j + 1];
                        double f_q22 = iaData[i + 1][j + 1];
                        double wy1 = dSubPixelShiftY;
                        double wy2 = 1.0 - dSubPixelShiftY;
                        double wx1 = dSubPixelShiftX;
                        double wx2 = 1.0 - dSubPixelShiftX;
                        iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
                    } else if (dSubPixelShiftX < 0 && dSubPixelShiftY > 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i + 1][j];
                        double f_q21 = iaData[i][j - 1];
                        double f_q22 = iaData[i + 1][j - 1];
                        double wy1 = dSubPixelShiftY;
                        double wy2 = 1.0 - dSubPixelShiftY;
                        double wx1 = Math.abs(dSubPixelShiftX);
                        double wx2 = 1.0 - Math.abs(dSubPixelShiftX);
                        iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
                    } else if (dSubPixelShiftX > 0 && dSubPixelShiftY < 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i - 1][j];
                        double f_q21 = iaData[i][j + 1];
                        double f_q22 = iaData[i - 1][j + 1];
                        double wy1 = Math.abs(dSubPixelShiftY);
                        double wy2 = 1.0 - Math.abs(dSubPixelShiftY);
                        double wx1 = dSubPixelShiftX;
                        double wx2 = 1.0 - dSubPixelShiftX;
                        iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
                    } else if (dSubPixelShiftX < 0 && dSubPixelShiftY < 0) {
                        double f_q11 = iaData[i][j];
                        double f_q12 = iaData[i - 1][j];
                        double f_q21 = iaData[i][j - 1];
                        double f_q22 = iaData[i - 1][j - 1];
                        double wy1 = Math.abs(dSubPixelShiftY);
                        double wy2 = 1.0 - Math.abs(dSubPixelShiftY);
                        double wx1 = Math.abs(dSubPixelShiftX);
                        double wx2 = 1.0 - Math.abs(dSubPixelShiftX);
                        iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
                    } else {
                        iaSub[iSub][jSub] = iaData[i][j];
                    }
                }
                jSub++;
            }
            iSub++;
        }
        return iaSub;
    }

    @Override
    public boolean[][] getValuesInAreaBoolean(getDataBoolean o) {
        boolean[][] iaData = o.getfield();
        boolean[][] iaSub = new boolean[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder; i < oIntervalY.dRightBorder; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder; j < oIntervalX.dRightBorder; j++) {
                if (i >= iaData.length || j >= iaData[0].length) {
                    iaSub[iSub][jSub] = false;
                } else {
                    iaSub[iSub][jSub] = iaData[i][j];
                }
                jSub++;
            }
            iSub++;
        }
        return iaSub;
    }

    public void getDisplacement(DataPIV Data) {

        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oRefinedAreas[i][j].getDisplacement(Data);
                }
            }
        } else {
            if (bMasked) {
                return;
            }
            getDataDouble dIntensity1 = () -> Data.iaGreyIntensity1;

            getDataDouble dIntensity2 = () -> Data.iaGreyIntensity2;

            int iDivider = (int) this.oIntervalX.getSize();

            InterrArea oOverlapRight = null;
            if (this.oIntervalX.dRightBorder + this.oIntervalX.getSize() / iDivider < Data.iaGreyIntensity1[0].length) {
                oOverlapRight = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder + this.oIntervalX.getSize() / iDivider, this.oIntervalX.dRightBorder + this.oIntervalX.getSize() / iDivider), new Set1D(this.oIntervalY.dLeftBorder, this.oIntervalY.dRightBorder));
                oOverlapRight.checkIfMasked(Data);
            }

            InterrArea oOverlapLeft = null;
            if (this.oIntervalX.dLeftBorder - this.oIntervalX.getSize() / iDivider > 0) {
                oOverlapLeft = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder - this.oIntervalX.getSize() / iDivider, this.oIntervalX.dRightBorder - this.oIntervalX.getSize() / iDivider), new Set1D(this.oIntervalY.dLeftBorder, this.oIntervalY.dRightBorder));
                oOverlapLeft.checkIfMasked(Data);
            }

            InterrArea oOverlappTop = null;
            if (this.oIntervalY.dLeftBorder - this.oIntervalX.getSize() / iDivider > 0) {
                oOverlappTop = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder - this.oIntervalY.getSize() / iDivider, this.oIntervalY.dRightBorder - this.oIntervalX.getSize() / iDivider));
                oOverlappTop.checkIfMasked(Data);
            }

            InterrArea oOverlappBottom = null;
            if (this.oIntervalY.dRightBorder + this.oIntervalX.getSize() / iDivider < Data.iaGreyIntensity1.length) {
                oOverlappBottom = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder + this.oIntervalY.getSize() / iDivider, this.oIntervalY.dRightBorder + this.oIntervalX.getSize() / iDivider));
                oOverlappBottom.checkIfMasked(Data);
            }

            List<InterrArea> oOverlapp = new ArrayList<>();
            if (Data.Hart1998) {
                if (oOverlapRight != null && !oOverlapRight.bMasked) {
                    oOverlapp.add(oOverlapRight);
                }
                if (oOverlapLeft != null && !oOverlapLeft.bMasked) {
                    oOverlapp.add(oOverlapLeft);
                }
                if (oOverlappTop != null && !oOverlappTop.bMasked) {
                    oOverlapp.add(oOverlappTop);
                }
                if (oOverlappBottom != null && !oOverlappBottom.bMasked) {
                    oOverlapp.add(oOverlappBottom);
                }
            }

            if (oOverlapp.isEmpty()) {
                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), (int) this.oIntervalX.getSize(), Data.sSubPixelType);
            } else {
                List<double[][]> ldIntensity1 = new ArrayList<>();
                List<double[][]> ldIntensity2 = new ArrayList<>();
                for (InterrArea oOverlappAreas : oOverlapp) {
                    ldIntensity1.add(oOverlappAreas.getValuesInAreaDouble(dIntensity1));
                    ldIntensity2.add(oOverlappAreas.oAreaSecFrame.getValuesInAreaDouble(dIntensity2));
                }

                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), ldIntensity1, ldIntensity2, (int) this.oIntervalX.getSize(), new MatrixEntry(0, 0));

            }
//            if (oOverlappRight == null || oOverlappRight.bMasked) {
//                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), (int) this.oIntervalX.getSize() + 1, new MatrixEntry(0, 0));
//            } else {
//                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), oOverlappRight.getValuesInAreaDouble(dIntensity1), oOverlappRight.getValuesInAreaDouble(dIntensity2), (int) this.oIntervalX.getSize() + 1, new MatrixEntry(0, 0));
//            }

            this.dVx = fftDisp.meDisplacment.x + this.oAreaSecFrame.getShift().j;
            this.dVy = fftDisp.meDisplacment.y + this.oAreaSecFrame.getShift().i;
        }

    }

    public void getDisplacement(OrderedPair opSubPixShift, DataPIV Data) {

        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oRefinedAreas[i][j].getDisplacement(Data);
                }
            }
        } else {
            if (bMasked) {
                return;
            }
            getDataDouble dIntensity1 = () -> Data.iaGreyIntensity1;

            getDataDouble dIntensity2 = () -> Data.iaGreyIntensity2;

            double iDivider = Data.Hart1998Divider;

            if (Data.Hart1998) {
                InterrArea oOverlappEast = null;
                if (this.oIntervalX.dRightBorder + this.oIntervalX.getSize() / iDivider < Data.iaGreyIntensity1[0].length) {
                    oOverlappEast = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder + this.oIntervalX.getSize() / iDivider, this.oIntervalX.dRightBorder + this.oIntervalX.getSize() / iDivider), new Set1D(this.oIntervalY.dLeftBorder, this.oIntervalY.dRightBorder));
                    oOverlappEast.checkIfMasked(Data);
                }
                InterrArea oOverlappWest = null;
                if (this.oIntervalX.dLeftBorder - this.oIntervalX.getSize() / iDivider > 0) {
                    oOverlappWest = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder - this.oIntervalX.getSize() / iDivider, this.oIntervalX.dRightBorder - this.oIntervalX.getSize() / iDivider), new Set1D(this.oIntervalY.dLeftBorder, this.oIntervalY.dRightBorder));
                    oOverlappWest.checkIfMasked(Data);
                }
                InterrArea oOverlappNorth = null;
                if (this.oIntervalY.dLeftBorder - this.oIntervalX.getSize() / iDivider > 0) {
                    oOverlappNorth = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder - this.oIntervalY.getSize() / iDivider, this.oIntervalY.dRightBorder - this.oIntervalX.getSize() / iDivider));
                    oOverlappNorth.checkIfMasked(Data);
                }
                InterrArea oOverlappSouth = null;
                if (this.oIntervalY.dRightBorder + this.oIntervalX.getSize() / iDivider < Data.iaGreyIntensity1.length) {
                    oOverlappSouth = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder + this.oIntervalY.getSize() / iDivider, this.oIntervalY.dRightBorder + this.oIntervalX.getSize() / iDivider));
                    oOverlappSouth.checkIfMasked(Data);
                }

                boolean bValidWest = oOverlappWest != null && !oOverlappWest.bMasked && oOverlappWest.oIntervalX.dLeftBorder >= 0;
                boolean bValidEast = oOverlappEast != null && !oOverlappEast.bMasked && oOverlappEast.oIntervalX.dRightBorder < Data.iaGreyIntensity1[0].length;
                boolean bValidNorth = oOverlappNorth != null && !oOverlappNorth.bMasked && oOverlappNorth.oIntervalY.dLeftBorder >= 0 && oOverlappNorth.oIntervalY.dRightBorder >= 0;
                boolean bValidSouth = oOverlappSouth != null && !oOverlappSouth.bMasked && oOverlappSouth.oIntervalY.dLeftBorder < Data.iaGreyIntensity1.length && oOverlappSouth.oIntervalY.dRightBorder < Data.iaGreyIntensity1.length;

                double[][] dIntensity1N = null;
                double[][] dIntensity2N = null;

                double[][] dIntensity1E = null;
                double[][] dIntensity2E = null;

                double[][] dIntensity1S = null;
                double[][] dIntensity2S = null;

                double[][] dIntensity1W = null;
                double[][] dIntensity2W = null;

                if (bValidNorth) {
                    dIntensity1N = (double[][]) oOverlappNorth.getValuesInAreaDouble(dIntensity1);
                    dIntensity2N = oOverlappNorth.oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift);
                }
                if (bValidEast) {
                    dIntensity1E = (double[][]) oOverlappEast.getValuesInAreaDouble(dIntensity1);
                    dIntensity2E = oOverlappEast.oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift);
                }
                if (bValidSouth) {
                    dIntensity1S = (double[][]) oOverlappSouth.getValuesInAreaDouble(dIntensity1);
                    dIntensity2S = oOverlappSouth.oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift);
                }
                if (bValidWest) {
                    dIntensity1W = (double[][]) oOverlappWest.getValuesInAreaDouble(dIntensity1);
                    dIntensity2W = oOverlappWest.oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift);
                }

                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift), dIntensity1N, dIntensity2N, dIntensity1E, dIntensity2E, dIntensity1S, dIntensity2S, dIntensity1W, dIntensity2W, (int) this.oIntervalX.getSize(), Data.sSubPixelType);

            } else {
                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2, opSubPixShift), (int) this.oIntervalX.getSize(), Data.sSubPixelType);
            }

//            if (oOverlappRight == null || oOverlappRight.bMasked) {
//                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), (int) this.oIntervalX.getSize() + 1, new MatrixEntry(0, 0));
//            } else {
//                fftDisp = new FFT(getValuesInAreaDouble(dIntensity1), oAreaSecFrame.getValuesInAreaDouble(dIntensity2), oOverlappRight.getValuesInAreaDouble(dIntensity1), oOverlappRight.getValuesInAreaDouble(dIntensity2), (int) this.oIntervalX.getSize() + 1, new MatrixEntry(0, 0));
//            }
            this.dVx = fftDisp.meDisplacment.x + opSubPixShift.x;
            this.dVy = fftDisp.meDisplacment.y + opSubPixShift.y;
        }
    }

    public void refine(boolean bOverlap) {
        if (!bOverlap) {
            oRefinedAreas = new InterrArea[2][2];
            oRefinedAreas[0][0] = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, (int) this.oIntervalX.getCenter()), new Set1D(this.oIntervalY.dLeftBorder, (int) this.oIntervalY.getCenter()));
            oRefinedAreas[0][1] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder, (int) this.oIntervalY.getCenter()));
            oRefinedAreas[1][0] = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, (int) this.oIntervalX.getCenter()), new Set1D((int) this.oIntervalY.getCenter(), this.oIntervalY.dRightBorder));
            oRefinedAreas[1][1] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), this.oIntervalX.dRightBorder), new Set1D((int) this.oIntervalY.getCenter(), this.oIntervalY.dRightBorder));

            if (!bMasked && this.dVx != null && this.dVy != null) {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        oRefinedAreas[i][j].dVx = this.getVeloX();
                        oRefinedAreas[i][j].dVy = this.getVeloY();
                    }
                }
            }
        } else {
            oRefinedAreas = new InterrArea[3][3];
            double dSize = this.oIntervalX.dRightBorder - (int) this.oIntervalX.getCenter();
            oRefinedAreas[0][0] = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, (int) this.oIntervalX.getCenter()), new Set1D(this.oIntervalY.dLeftBorder, (int) this.oIntervalY.getCenter()));
            oRefinedAreas[0][1] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), dSize / 2, dSize / 2), new Set1D(this.oIntervalY.dLeftBorder, (int) this.oIntervalY.getCenter()));
            oRefinedAreas[0][2] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), this.oIntervalX.dRightBorder), new Set1D(this.oIntervalY.dLeftBorder, (int) this.oIntervalY.getCenter()));
            oRefinedAreas[1][0] = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, (int) this.oIntervalX.getCenter()), new Set1D((int) this.oIntervalY.getCenter(), dSize / 2, dSize / 2));
            oRefinedAreas[1][1] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), dSize / 2, dSize / 2), new Set1D((int) this.oIntervalY.getCenter(), dSize / 2, dSize / 2));
            oRefinedAreas[1][2] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), this.oIntervalX.dRightBorder), new Set1D((int) this.oIntervalY.getCenter(), dSize / 2, dSize / 2));
            oRefinedAreas[2][0] = new InterrArea(new Set1D(this.oIntervalX.dLeftBorder, (int) this.oIntervalX.getCenter()), new Set1D((int) this.oIntervalY.getCenter(), this.oIntervalY.dRightBorder));
            oRefinedAreas[2][1] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), dSize / 2, dSize / 2), new Set1D((int) this.oIntervalY.getCenter(), this.oIntervalY.dRightBorder));
            oRefinedAreas[2][2] = new InterrArea(new Set1D((int) this.oIntervalX.getCenter(), this.oIntervalX.dRightBorder), new Set1D((int) this.oIntervalY.getCenter(), this.oIntervalY.dRightBorder));

            if (!bMasked && this.dVx != null && this.dVy != null) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        oRefinedAreas[i][j].dVx = this.getVeloX();
                        oRefinedAreas[i][j].dVy = this.getVeloY();
                    }
                }
            }
        }

        this.bRefined = true;

    }

    public Double getVeloX() {
        return this.dVx;
    }

    public Double getVeloY() {
        return this.dVy;
    }

    public boolean checkIfMasked(DataPIV Data) {
        int iFactor = Data.bOverlap ? 3 : 2;
        if (bRefined) {
            for (int i = 0; i < iFactor; i++) {
                for (int j = 0; j < iFactor; j++) {
                    oRefinedAreas[i][j].checkIfMasked(Data);
                }
            }
        }

        boolean[][] bsub = getValuesInAreaBoolean(() -> Data.baMask);

        for (boolean[] ba : bsub) {
            for (boolean b : ba) {
                if (b) {
                    this.bMasked = true;
                    return true;
                }
            }
        }
        this.bMasked = false;
        return false;
    }

    public void paintIntensityValues(String sFile1, String sFile2, DataPIV Data) throws IOException {

        int[][] ia1 = getValuesInAreaInt(new getDataInt() {

            @Override
            public int[][] getfield() {
                return Data.iaReadInFirst;
            }
        });

        int[][] ia2 = getValuesInAreaInt(new getDataInt() {

            @Override
            public int[][] getfield() {
                return Data.iaReadInSecond;
            }
        });

        IMG_Writer.PaintGreyPNG(new ImageGrid(ia1), new File(sFile1));
        IMG_Writer.PaintGreyPNG(new ImageGrid(ia2), new File(sFile2));
    }

    public boolean validateVectorDifference(int iStampSize, double dthreshold, InterrGrid oGrid) {
        /*
         Raffel Particle Image Velocimetry A Practical Guide Third Edition
         Section 7.1.1
         */

        if (!bMasked) {
            int iCountViolationTimes = 0;
            int iNeighbors = 0;

            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.bMasked) {
                    continue;
                }
                if (o.bOutlier) {
                    continue;
                }
                double dDeltaVX = this.getVeloX() - o.getVeloX();
                double dDeltaVY = this.getVeloY() - o.getVeloY();
                double dNorm = Math.sqrt(dDeltaVX * dDeltaVX + dDeltaVY * dDeltaVY);
                iNeighbors++;
                if (dNorm > dthreshold) {
                    iCountViolationTimes++;
                }
            }
            if (iCountViolationTimes < iNeighbors / 2) {
                bOutlier = false;
            } else {
                bOutlier = true;
            }
        }

        return bOutlier;

    }

    public boolean validateMedianComponent(int iStampSize, double dthreshold, InterrGrid oGrid) {
        /*
         Raffel Particle Image Velocimetry A Practical Guide Third Edition
         Section 7.1.2
         */

        if (!bMasked) {
            List<Double> lsBVx = new ArrayList<>();
            List<Double> lsBVy = new ArrayList<>();

            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.bMasked) {
                    continue;
                }
                if (o.bOutlier) {
                    continue;
                }
                lsBVx.add(o.getVeloX());
                lsBVy.add(o.getVeloY());
            }

            if (lsBVx.isEmpty()) {
                return bOutlier;
            }
            if (Math.abs(median(lsBVx) - this.getVeloX()) < dthreshold && Math.abs(median(lsBVy) - this.getVeloY()) < dthreshold) {
                bOutlier = false;
            } else {
                bOutlier = true;
            }
        }

        return bOutlier;

    }

    public boolean validateMedianLength(int iStampSize, double dthreshold, InterrGrid oGrid) {
        /*
         Raffel Particle Image Velocimetry A Practical Guide Third Edition
         Section 7.1.2
         */

        if (!bMasked) {
            List<Double> lsLength = new ArrayList<>();

            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.bMasked) {
                    continue;
                }
                if (o.bOutlier) {
                    continue;
                }
                lsLength.add(o.getVeloX() * o.getVeloX() + o.getVeloY() * o.getVeloY());
            }
            double dThislength = Math.sqrt(this.getVeloX() * this.getVeloX() + this.getVeloY() * this.getVeloY());
            if (lsLength.isEmpty()) {
                return bOutlier;
            }
            if (dThislength > 200) {
                System.out.println(dThislength);
            }
            if (Math.abs(median(lsLength) - dThislength) < dthreshold) {
                bOutlier = false;
            } else {
                bOutlier = true;
            }
        }

        return bOutlier;

    }

    public boolean validateNormMedian(int iStampSize, double dthreshold, InterrGrid oGrid) {
        /*
         Raffel Particle Image Velocimetry A Practical Guide Third Edition
         Section 7.1.2
         */

        double eps_0 = 0.1;

        if (!bMasked) {
            List<Double> lsBVx = new ArrayList<>();
            List<Double> lsBVy = new ArrayList<>();

            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.bMasked) {
                    continue;
                }
                if (o.bOutlier) {
                    continue;
                }
                lsBVx.add(o.getVeloX());
                lsBVy.add(o.getVeloY());
            }

            Double dMedVx = median(lsBVx);
            Double dMedVy = median(lsBVy);

            if (dMedVx == null || dMedVy == null) {
                return false;
            }

            List<Double> lsMedDiff = new ArrayList<>();

            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.bMasked) {
                    continue;
                }
                if (o.bOutlier) {
                    continue;
                }
                double dDeltaVx = dMedVx - o.getVeloX();
                double dDeltaVy = dMedVy - o.getVeloY();
                lsMedDiff.add(dDeltaVx * dDeltaVx + dDeltaVy * dDeltaVy);
            }

            double rmed = median(lsMedDiff);

            if (Math.abs(dMedVx - this.getVeloX()) / (rmed + eps_0) < dthreshold && Math.abs(dMedVy - this.getVeloY()) / (rmed + eps_0) < dthreshold) {
                bOutlier = false;
            } else {
                bOutlier = true;
            }

        }

        return bOutlier;

    }

    public void validateVectors_Ziegenhein2018(int iStampSize, double threshold, boolean interp, InterrGrid oGrid) {

        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oRefinedAreas[i][j].validateVectors_Ziegenhein2018(iStampSize, threshold, interp, oGrid);
                }
            }
        }

        if (!bMasked) {
            List<Double> lsBVx = new ArrayList<>();
            List<Double> lsBVy = new ArrayList<>();
            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.getVeloX() == null || o.getVeloY() == null || o.bMasked) {
                    continue;
                }
                if (Math.abs(o.getVeloX()) < threshold) {
                    lsBVx.add(o.getVeloX());
                }
                if (Math.abs(o.getVeloY()) < threshold) {
                    lsBVy.add(o.getVeloY());
                }
            }
            if (lsBVx.size() < 4 || lsBVy.size() < 4) {
                bOutlier = true;
                return;
            }

            Double dSTDDevX = Math.sqrt(getVariance(lsBVx, Arithmetic(lsBVx)));
            Double dSTDDevY = Math.sqrt(getVariance(lsBVy, Arithmetic(lsBVy)));

            if (Math.abs(this.dVx) > 2.5 * dSTDDevX || Math.abs(this.dVy) > 2.5 * dSTDDevY) {
                if (interp) {
//                            pParameter.oVelocity.y = y1;
//                            pParameter.oVelocity.x = x1;
//                            double y11 = y1 * dTime / Metric;
//                            double x11 = x1 * dTime / Metric;
//                            interpolfromNeigh(pParameter, oGrid, lsIA, dTime, Metric);
//                            double y11 = pParameter.oVelocity.y * dTime / Metric;
//                            double x11 = pParameter.oVelocity.x * dTime / Metric;
//                            pParameter.iDisplacement[0] = (int) Math.round(y11);
//                            pParameter.iDisplacement[1] = (int) Math.round(x11);
                    bOutlier = false;
//                            pParameter.xcorrcoeff = FFT.calccoeff(pParameter.iDisplacement, pParameter.dIntensityValues1, iSecondIm, pParameter.ostartPoint, pParameter.iWindowsize);
//                            pParameter.xcorrcoeff = pParameter.CompleteMatrix[pParameter.iDisplacement.i][pParameter.iDisplacement.j];

                } else {
                    this.dVx = 0.0;
                    this.dVy = 0.0;
                    bOutlier = true;
                }
            } else {
                bOutlier = true;
            }
        }

    }

    public void validateVectors_Hessenkemper2018(int iStampSize, double threshold, boolean interp, InterrGrid oGrid) {

        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oRefinedAreas[i][j].validateVectors_Hessenkemper2018(iStampSize, threshold, interp, oGrid);
                }
            }
        }

        if (!bMasked) {
            List<Double> lsBVx = new ArrayList<>();
            List<Double> lsBVy = new ArrayList<>();
//            lsBVx.add(this.getVeloX());
//            lsBVy.add(this.getVeloY());
            for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
                if (o.getVeloX() == null || o.getVeloY() == null || o.bMasked) {
                    continue;
                }
                lsBVx.add(o.getVeloX());
                lsBVy.add(o.getVeloY());
            }
            if (lsBVx.size() < 4 || lsBVy.size() < 4) {
                bOutlier = true;
                return;
            }
            Double[] a1 = lsBVx.toArray(new Double[]{});
            double x1 = median(a1);
            Double[] a3 = lsBVy.toArray(new Double[]{});
            double y1 = median(a3);
            double top1 = getVeloY() - y1;
            double top2 = getVeloX() - x1;
            double top = Math.sqrt((top1 * top1) + (top2 * top2));
            List<Double> lsBVx2 = new ArrayList<>();
            List<Double> lsBVy2 = new ArrayList<>();

            for (Double double1 : lsBVy) {
                lsBVy2.add(double1 - y1);
            }
            for (Double double1 : lsBVx) {
                lsBVx2.add(double1 - x1);
            }

            Double[] rx1 = lsBVx2.toArray(new Double[]{});
            Double[] ry2 = lsBVy2.toArray(new Double[]{});
            Double[] rmm = new Double[rx1.length];
            for (int k = 0; k < rx1.length; k++) {
                rmm[k] = Math.sqrt((rx1[k] * rx1[k]) + (ry2[k] * ry2[k]));
            }
            double rm = median(rmm);
            double rc = top / (rm + 0.1);
            if (rc > threshold && rm > 0) {
                if (interp) {
//                            pParameter.oVelocity.y = y1;
//                            pParameter.oVelocity.x = x1;
//                            double y11 = y1 * dTime / Metric;
//                            double x11 = x1 * dTime / Metric;
//                            interpolfromNeigh(pParameter, oGrid, lsIA, dTime, Metric);
//                            double y11 = pParameter.oVelocity.y * dTime / Metric;
//                            double x11 = pParameter.oVelocity.x * dTime / Metric;
//                            pParameter.iDisplacement[0] = (int) Math.round(y11);
//                            pParameter.iDisplacement[1] = (int) Math.round(x11);
                    bOutlier = false;
//                            pParameter.xcorrcoeff = FFT.calccoeff(pParameter.iDisplacement, pParameter.dIntensityValues1, iSecondIm, pParameter.ostartPoint, pParameter.iWindowsize);
//                            pParameter.xcorrcoeff = pParameter.CompleteMatrix[pParameter.iDisplacement.i][pParameter.iDisplacement.j];

                } else {
                    this.dVx = 0.0;
                    this.dVy = 0.0;
                    bOutlier = true;
                }
            } else {
                bOutlier = true;
            }
        }

    }

    public void Reconstruct_Hessenkemper2018(InterrGrid oGrid, int iStampSize) {
        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oRefinedAreas[i][j].Reconstruct_Hessenkemper2018(oGrid, iStampSize);
                }
            }
        }

        if (bMasked || !bOutlier) {
            return;
        }
        double xvel = 0.0;
        double yvel = 0.0;
        double distWeightx = 0.0;
        double distWeighty = 0.0;
        int counter = 0;
        for (InterrArea o : oGrid.getNeighbors(this, iStampSize)) {
            if (o != null && !o.bMasked && !o.bOutlier) {//&& !o.bManualMask) {                
                double dDistancex = Math.abs(o.getCenter().x - this.getCenter().x);
                double dDistancey = Math.abs(o.getCenter().y - this.getCenter().y);
                double dDistance = Math.sqrt(dDistancex * dDistancex + dDistancey * dDistancey);
                xvel += (o.getVeloX() / dDistance);
                yvel += (o.getVeloY() / dDistance);
                distWeightx += (1 / dDistance);
                distWeighty += (1 / dDistance);
                counter++;
            }
        }
        if (counter > 0) {
            this.dVx = xvel / distWeightx;
            this.dVy = yvel / distWeighty;
        }
        bOnceValidated = true;
    }

    @Override
    public ImageInt paintOngrid(ImageInt oGrid, int iValue) {
        if (bRefined) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    oGrid = oRefinedAreas[i][j].paintOngrid(oGrid, iValue);
                }
            }
            return oGrid;
        } else {
            //We have to substract here -1 since the interrgrid is defined as lef tborder <= x < right border; To paint the grid on an image, the right border has to be inside the area or it will be outside of the image
            if (!oGrid.checkIfInBound(this.oIntervalY.dLeftBorder, this.oIntervalX.dLeftBorder) || !oGrid.checkIfInBound(this.oIntervalY.dRightBorder - 1, this.oIntervalX.dRightBorder - 1)) {
                return oGrid;
            }
            Rectangle oRec = new Rectangle(new MatrixEntry((int) this.oIntervalY.dLeftBorder, (int) this.oIntervalX.dLeftBorder), new MatrixEntry((int) this.oIntervalY.dRightBorder - 1, (int) this.oIntervalX.dRightBorder - 1));
            return oRec.setRectangle(oGrid, iValue);
        }
    }

    public void shiftSecond(DataPIV Data) {
        if (!bMasked && this.dVx != null && this.dVy != null) {
            oAreaSecFrame.shift(new MatrixEntry((int) Math.round(this.dVy), (int) Math.round(this.dVx)), Data);
        }
    }

    public void resetshiftSecond(DataPIV Data) {
        if (!bMasked) {
            MatrixEntry meShift = oAreaSecFrame.getShift();
            oAreaSecFrame.shift(new MatrixEntry(-1.0 * meShift.i, -1.0 * meShift.j), Data);
        }
    }

    public void validateRefinedResult() {
        if (!bRefined || bMasked || this.dVx == null || this.dVy == null) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println(this.oRefinedAreas[i][j].dVy);
                if (Math.abs(this.oRefinedAreas[i][j].dVx - this.dVx) > 2 /* Math.abs(this.dVx)*/
                        || Math.abs(this.oRefinedAreas[i][j].dVy - this.dVy) > 2 /* Math.abs(this.dVy)*/) {
                    this.oRefinedAreas[i][j].dVx = this.dVx;
                    this.oRefinedAreas[i][j].dVy = this.dVy;
                }
            }
        }
    }

    @Override
    public double getSize() {
        return (this.oIntervalX.getSize() + this.oIntervalY.getSize()) / 2.0;
    }

    @Override
    public OrderedPair getPosition() {
        return new OrderedPair(oIntervalX.getCenter(), oIntervalY.getCenter());
    }

}

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.colorspaces.ColorSpaceCIEELab;
import com.tivconsultancy.opentiv.helpfunctions.colorspaces.Colorbar;
import com.tivconsultancy.opentiv.helpfunctions.hpc.Parallel;
import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.math.algorithms.Sorting;
import com.tivconsultancy.opentiv.math.exceptions.EmptySetException;
import com.tivconsultancy.opentiv.math.grids.CellRec;
import com.tivconsultancy.opentiv.math.interfaces.Grid;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import com.tivconsultancy.opentiv.math.specials.EnumObject;
import com.tivconsultancy.opentiv.physics.vectors.VelocityVec;
import com.tivconsultancy.opentiv.postproc.vector.PaintVectors;
import com.tivconsultancy.opentiv.velocimetry.helpfunctions.VelocityGrid;
import com.tivconsultancy.tivpiv.data.DataPIV;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas Ziegenhein
 */
public class InterrGrid implements Grid, Serializable {

    private static final long serialVersionUID = -1420517670312162142L;

    public InterrArea[][] oaContent;
    public boolean bSortedGrid = true;

    public InterrGrid(InterrArea[][] oaContent) {
        this.oaContent = oaContent;
    }

    public InterrGrid(InterrGrid oGrid, DataPIV Data) {
        this.oaContent = oGrid.oaContent;
        for (int i = 0; i < this.oaContent.length; i++) {
            for (int j = 0; j < this.oaContent[0].length; j++) {

//                if (oaContent[i][j].bRefined) {
//                    for (int k = 0; k < 2; k++) {
//                        for (int l = 0; l < 2; l++) {
//                            if (!oaContent[i][j].oRefinedAreas[k][l].bMasked) {
//                                oaContent[i][j].oRefinedAreas[k][l].reconstruct(oGrid, Data.iStampSize);
//                                oaContent[i][j].oRefinedAreas[k][l].dVx = (oaContent[i][j].oRefinedAreas[k][l].dVx * Data.SmoothFactor + oGrid.oaContent[i][j].oRefinedAreas[k][l].dVx) / (1.0 + Data.SmoothFactor);
//                                oaContent[i][j].oRefinedAreas[k][l].dVy = (oaContent[i][j].oRefinedAreas[k][l].dVy * Data.SmoothFactor + oGrid.oaContent[i][j].oRefinedAreas[k][l].dVy) / (1.0 + Data.SmoothFactor);
//                            }
//                        }
//                    }
//                } else {
                if (!oaContent[i][j].bMasked) {
                    oaContent[i][j].reconstruct(oGrid, Data.iStampSize);
                    oaContent[i][j].dVx = (oaContent[i][j].dVx * Data.SmoothFactor + oGrid.oaContent[i][j].dVx) / (1.0 + Data.SmoothFactor);
                    oaContent[i][j].dVy = (oaContent[i][j].dVy * Data.SmoothFactor + oGrid.oaContent[i][j].dVy) / (1.0 + Data.SmoothFactor);
                }
//                    double xvel = 0.0;
//                    double yvel = 0.0;
//                    double distWeightx = 0.0;
//                    double distWeighty = 0.0;
//                    int counter = 0;
//                    for (InterrArea o : oGrid.getNeighbors(oGrid.oaContent[i][j], 5)) {
//                        if (o != null && !o.bMasked && !o.bOutlier) {//&& !o.bManualMask) {                          
//                            double dDistancex = Math.abs(o.getCenter().x - oGrid.oaContent[i][j].getCenter().x);
//                            double dDistancey = Math.abs(o.getCenter().y - oGrid.oaContent[i][j].getCenter().y);
//                            double dDistance = Math.sqrt(dDistancex * dDistancex + dDistancey * dDistancey);
//                            xvel += (o.getVeloX() / dDistance);
//                            yvel += (o.getVeloY() / dDistance);
//                            distWeightx += (1 / dDistance);
//                            distWeighty += (1 / dDistance);
//                            counter++;
//                        }
//                    }
//                    if (counter > 0) {
//                        oaContent[i][j].dVx = xvel / distWeightx;
//                        oaContent[i][j].dVy = yvel / distWeighty;
//                    }
//                }
            }
            // System.out.println(oaContent[i][j].bMasked);
//                this.oaContent[i][j] = new InterrArea(oaContent[i][j].oIntervalX, oaContent[i][j].oIntervalY);
//                this.oaContent[i][j].bMasked = oaContent[i][j].bMasked;
//                this.oaContent[i][j].mePosInGrid = oaContent[i][j].mePosInGrid;
//                this.oaContent[i][j].dVx = oaContent[i][j].dVx;
//                this.oaContent[i][j].dVy = oaContent[i][j].dVy;
//                this.oaContent[i][j].bRefined=oaContent[i][j].bRefined;
//                this.oaContent[i][j].bOutlier=oaContent[i][j].bOutlier;
//                if (oaContent[i][j].bRefined) {                  
//                    for (int k = 0; k < 2; k++) {
//                        for (int l = 0; l < 2; l++) {
//                            this.oaContent[i][j].oRefinedAreas[k][l].bOutlier=oaContent[i][j].oRefinedAreas[k][l].bOutlier;
//                            this.oaContent[i][j].oRefinedAreas[k][l] = new InterrArea(oaContent[i][j].oRefinedAreas[k][l].oIntervalX, oaContent[i][j].oRefinedAreas[k][l].oIntervalY);
//                            this.oaContent[i][j].oRefinedAreas[k][l].bMasked = oaContent[i][j].oRefinedAreas[k][l].bMasked;
//                            this.oaContent[i][j].oRefinedAreas[k][l].mePosInGrid = oaContent[i][j].oRefinedAreas[k][l].mePosInGrid;
//                            this.oaContent[i][j].oRefinedAreas[k][l].dVx = oaContent[i][j].oRefinedAreas[k][l].dVx;
//                            this.oaContent[i][j].oRefinedAreas[k][l].dVy = oaContent[i][j].oRefinedAreas[k][l].dVy;
//                        }
//                    }
//                }
//            }
        }
    }

    public void getFFT(DataPIV Data) {
//        for (InterrArea[] oContent : oaContent) {
//            for (InterrArea Content : oContent) {
//                Content.getDisplacement(Data);
//            }
//        }
        for (InterrArea[] oContent : oaContent) {
            Parallel.For2(Arrays.asList(oContent), new Parallel.Operation<InterrArea>() {
                @Override
                public void perform(InterrArea pParameter
                ) {
                    pParameter.getDisplacement(Data);
                }

            });
        }

    }

    public VelocityGrid getVeloGrid(int iFactor) {
        VelocityGrid oGrid = new VelocityGrid(oaContent[0][0].oIntervalX.dLeftBorder,
                oaContent[0][oaContent[0].length - 1].oIntervalX.dRightBorder,
                oaContent[oaContent.length - 1][0].oIntervalY.dRightBorder,
                oaContent[0][0].oIntervalY.dLeftBorder,
                oaContent[0].length * iFactor,
                oaContent.length * iFactor);
        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (!o.bMasked) {
                    if (o.bRefined) {
                        CellRec oCellX = oGrid.GridVeloX
                                .getCell(new OrderedPair(o.oIntervalX.getCenter(), o.oIntervalY.getCenter()));
                        CellRec oCellY = oGrid.GridVeloY
                                .getCell(new OrderedPair(o.oIntervalX.getCenter(), o.oIntervalY.getCenter()));
                        if (oCellX != null && oCellY != null) {
                            oCellX.refine();
                            oCellY.refine();

                            for (int i = 0; i < iFactor; i++) {
                                for (int j = 0; j < iFactor; j++) {
                                    oGrid.GridVeloX
                                            .addContent(new OrderedPair(o.oRefinedAreas[i][j].oIntervalX.getCenter(),
                                                    o.oRefinedAreas[i][j].oIntervalY.getCenter(),
                                                    o.oRefinedAreas[i][j].getVeloX()));
                                    oGrid.GridVeloY
                                            .addContent(new OrderedPair(o.oRefinedAreas[i][j].oIntervalX.getCenter(),
                                                    o.oRefinedAreas[i][j].oIntervalY.getCenter(),
                                                    o.oRefinedAreas[i][j].getVeloY()));
                                }
                            }

                        }
                    }

                    oGrid.GridVeloX.addContent(
                            new OrderedPair(o.oIntervalX.getCenter(), o.oIntervalY.getCenter(), o.getVeloX()));
                    oGrid.GridVeloY.addContent(
                            new OrderedPair(o.oIntervalX.getCenter(), o.oIntervalY.getCenter(), o.getVeloY()));
                }
            }
        }

        oGrid.GridVeloX.calcAverage();
        oGrid.GridVeloY.calcAverage();

        return oGrid;

    }

    public VelocityGrid getVeloGrid(VelocityGrid oOutputGrid, DataPIV Data) {

        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (o.bRefined) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            InterrArea oR = o.oRefinedAreas[i][j];
                            if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null) {
                                oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
                            }
                        }
                    }
                } else {
                    if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                        oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                    }
                }

            }
        }

        oOutputGrid.addContent(oVeloVecs);
        oOutputGrid.GridVeloX.calcAverage();
        oOutputGrid.GridVeloY.calcAverage();

        return oOutputGrid;
    }

    public BufferedImage paintVecs(int[][] iaBlackBoard, List<Color> loColors, VelocityGrid oOutputGrid, DataPIV Data)
            throws IOException {
        if (loColors == null) {
            loColors = Colorbar.StartEndLinearColorBar.getCustom(0, 0, 0, 0, 0, 0);
        }
        Colorbar oColBar = new Colorbar.StartEndLinearColorBar(0.0, 5.0, loColors, new ColorSpaceCIEELab(),
                (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);
        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (o.bRefined) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            InterrArea oR = o.oRefinedAreas[i][j];
                            if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null) {
                                oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
                            }
                        }
                    }
                } else {
                    if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                        oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                    }
                }

            }
        }

        List<VelocityVec> oVecChecked;
        if (oOutputGrid != null) {
            oOutputGrid.addContent(oVeloVecs);
            oOutputGrid.GridVeloX.calcAverage();
            oOutputGrid.GridVeloY.calcAverage();
            oVecChecked = oOutputGrid.getVectors();
        } else {
            oVecChecked = oVeloVecs;
        }

        double dStretch = Data.dStretch;
        try {
            EnumObject o = Sorting.getMaxCharacteristic(oVecChecked, new Sorting.Characteristic() {

                @Override
                public Double getCharacteristicValue(Object pParameter) {
                    return ((VelocityVec) pParameter).opUnitTangent.dValue;
                }
            });
            if (Data.AutoStretch) {
                dStretch = (this.getCellSize() / o.dEnum * Data.AutoStretchFactor);
            }

            oColBar = new Colorbar.StartEndLinearColorBar(0.0, o.dEnum * 1.1, loColors, new ColorSpaceCIEELab(),
                    (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);

        } catch (EmptySetException ex) {
            System.out.println("Warning Empty Set, skipped");
        }
        return PaintVectors.paintOnImage(oVecChecked, oColBar, iaBlackBoard, null, dStretch);
    }

    public BufferedImage paintVecs(int[][] iaBlackBoard, String sFileOut, String sFileOutCSV, List<Color> loColors,
            VelocityGrid oOutputGrid, DataPIV Data) throws IOException {
        if (loColors == null) {
            loColors = Colorbar.StartEndLinearColorBar.getCustom(0, 0, 0, 0, 0, 0);
        }
        Colorbar oColBar = new Colorbar.StartEndLinearColorBar(0.0, 5.0, loColors, new ColorSpaceCIEELab(),
                (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);
        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (o.bRefined) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            InterrArea oR = o.oRefinedAreas[i][j];
                            if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null) {
                                oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
                            }
                        }
                    }
                } else {
                    if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                        oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                    }
                }

            }
        }

        List<VelocityVec> oVecChecked;
        if (oOutputGrid != null) {
            oOutputGrid.addContent(oVeloVecs);
            oOutputGrid.GridVeloX.calcAverage();
            oOutputGrid.GridVeloY.calcAverage();
            oVecChecked = oOutputGrid.getVectors();
        } else {
            oVecChecked = oVeloVecs;
        }

        double dStretch = Data.dStretch;
        try {
            EnumObject o = Sorting.getMaxCharacteristic(oVecChecked, new Sorting.Characteristic() {

                @Override
                public Double getCharacteristicValue(Object pParameter) {
                    return ((VelocityVec) pParameter).opUnitTangent.dValue;
                }
            });
            if (Data.AutoStretch) {
                dStretch = (this.getCellSize() / o.dEnum * Data.AutoStretchFactor);
            }

            oColBar = new Colorbar.StartEndLinearColorBar(0.0, o.dEnum * 1.1, loColors, new ColorSpaceCIEELab(),
                    (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);

        } catch (EmptySetException ex) {
            System.out.println("Warning Empty Set, skipped");
        }

        VelocityVec.writeToFile(sFileOutCSV, oVecChecked, null, null);
        return PaintVectors.paintOnImage(oVecChecked, oColBar, iaBlackBoard, sFileOut, dStretch);
    }

    public List<VelocityVec> getVectors(boolean bPaint) {
        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
//                if (bRefine) {
//                    if (o.bRefined) {
//                        for (int i = 0; i < 2; i++) {
//                            for (int j = 0; j < 2; j++) {
//                                InterrArea oR = o.oRefinedAreas[i][j];
//                                if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null && !oR.bMasked) {
//                                    oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
//                                }
//                            }
//                        }
////                    }
//                } else {
                if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                    oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                } else {
                    if (!bPaint) {
                        oVeloVecs.add(new VelocityVec(0.0, 0.0, o.getCenter()));
                    }
                }
//                }

            }
        }
        return oVeloVecs;
    }

    public BufferedImage paintVecs(int[][] iaBlackBoard, List<Color> loColors, DataPIV Data) throws IOException {
        if (loColors == null) {
            loColors = Colorbar.StartEndLinearColorBar.getCustom(0, 0, 0, 0, 0, 0);
        }
        Colorbar oColBar = new Colorbar.StartEndLinearColorBar(0.0, 5.0, loColors, new ColorSpaceCIEELab(),
                (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);
        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (o.bRefined) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            InterrArea oR = o.oRefinedAreas[i][j];
                            if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null) {
                                oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
                            }
                        }
                    }
                } else {
                    if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                        oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                    }
                }

            }
        }

        double dStretch = Data.dStretch;
        try {
            EnumObject o = Sorting.getMaxCharacteristic(oVeloVecs, new Sorting.Characteristic() {

                @Override
                public Double getCharacteristicValue(Object pParameter) {
                    return ((VelocityVec) pParameter).opUnitTangent.dValue;
                }
            });
            if (Data.AutoStretch) {
                dStretch = (this.getCellSize() / o.dEnum * Data.AutoStretchFactor);
            }

            oColBar = new Colorbar.StartEndLinearColorBar(0.0, o.dEnum * 1.1, loColors, new ColorSpaceCIEELab(),
                    (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);

        } catch (EmptySetException ex) {
            System.out.println("Warning Empty Set, skipped");
        }

        return PaintVectors.paintOnImage(oVeloVecs, oColBar, iaBlackBoard, null, dStretch);
    }

    public BufferedImage paintVecs(int[][] iaBlackBoard, String sFileOut, String sFileOutCSV, List<Color> loColors,
            DataPIV Data) throws IOException {
        if (loColors == null) {
            loColors = Colorbar.StartEndLinearColorBar.getCustom(0, 0, 0, 0, 0, 0);
        }
        Colorbar oColBar = new Colorbar.StartEndLinearColorBar(0.0, 5.0, loColors, new ColorSpaceCIEELab(),
                (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);
        List<VelocityVec> oVeloVecs = new ArrayList<>();

        for (InterrArea[] oa : oaContent) {
            for (InterrArea o : oa) {
                if (o.bRefined) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            InterrArea oR = o.oRefinedAreas[i][j];
                            if (oR != null && oR.getVeloX() != null && oR.getVeloY() != null) {
                                oVeloVecs.add(new VelocityVec(oR.getVeloX(), oR.getVeloY(), oR.getCenter()));
                            }
                        }
                    }
                } else {
                    if (o != null && o.getVeloX() != null && o.getVeloY() != null && !o.bOutlier) {
                        oVeloVecs.add(new VelocityVec(o.getVeloX(), o.getVeloY(), o.getCenter()));
                    }
                }

            }
        }

        double dStretch = Data.dStretch;
        try {
            EnumObject o = Sorting.getMaxCharacteristic(oVeloVecs, new Sorting.Characteristic() {

                @Override
                public Double getCharacteristicValue(Object pParameter) {
                    return ((VelocityVec) pParameter).opUnitTangent.dValue;
                }
            });
            if (Data.AutoStretch) {
                dStretch = (this.getCellSize() / o.dEnum * Data.AutoStretchFactor);
            }

            oColBar = new Colorbar.StartEndLinearColorBar(0.0, o.dEnum * 1.1, loColors, new ColorSpaceCIEELab(),
                    (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);

        } catch (EmptySetException ex) {
            System.out.println("Warning Empty Set, skipped");
        }

        VelocityVec.writeToFile(sFileOutCSV, oVeloVecs, null, null);
        return PaintVectors.paintOnImage(oVeloVecs, oColBar, iaBlackBoard, sFileOut, dStretch);
    }

    public void checkMask(DataPIV Data) {
        for (InterrArea[] oa : this.oaContent) {
            for (InterrArea o : oa) {
                o.checkIfMasked(Data);
            }
        }
    }

    public boolean checkMask(int i, int j) {
        return this.oaContent[i][j].bMasked;
    }

    public void validateVectors(int iStampSize, double threshold, String sOption) {
        for (InterrArea[] oa : this.oaContent) {
            for (InterrArea o : oa) {
                List<List<Double>> llsBV = o.get_Velocities(iStampSize, this);
                List<Double> lsBVx = llsBV.get(0);
                List<Double> lsBVy = llsBV.get(1);
                if (lsBVx.isEmpty()) {
                    
                    continue;
                }
                if (sOption == null || sOption.isEmpty() || sOption.contains("NormMedian")) {
                    o.bOutlier=o.validateNormMedian(lsBVx,lsBVy, threshold);
                } else if (sOption.contains("MedianComp")) {
                    o.validateMedianComponent(lsBVx,lsBVy, threshold);
                } else if (sOption.contains("MedianLength")) {
                    o.validateMedianLength(lsBVx,lsBVy, threshold);
                } else if (sOption.contains("VecDiff")) {
                    o.validateVectorDifference(iStampSize, threshold, this);
                } else {
                    o.validateNormMedian(lsBVx,lsBVy, threshold);
                }
            }
        }
    }

    public void reconstructInvalidVectors(int iStampSize) {
        for (InterrArea[] oa : this.oaContent) {
            for (InterrArea o : oa) {
                if (o.bOutlier) {
                    o.Reconstruct_Hessenkemper2018(this, iStampSize);
                    o.bOutlier = false;
                }

            }
        }
    }

    public void validateRefinedVectors() {
        for (InterrArea[] oa : this.oaContent) {
            for (InterrArea o : oa) {
                o.validateRefinedResult();
            }
        }
    }

    public List<InterrArea> getNeighbors(InterrArea oRef, int iStampSize) {
        int iStampSizeHalf = (int) (iStampSize / 2.0);
        List<InterrArea> loNeighbors = new ArrayList<>();
        if (bSortedGrid) {
            oRef.mePosInGrid = this.getPosition(oRef);
            for (int i = oRef.mePosInGrid.i - iStampSizeHalf; i <= oRef.mePosInGrid.i + iStampSizeHalf; i++) {
                for (int j = oRef.mePosInGrid.j; j <= oRef.mePosInGrid.j + iStampSizeHalf; j++) {
                    if (i == oRef.mePosInGrid.i && j == oRef.mePosInGrid.j) {
                        continue;
                    }
                    if (i < 0 || j < 0) {
                        continue;
                    }
                    if (i >= oaContent.length || j >= oaContent[0].length) {
                        continue;
                    }
                    if (oaContent[i][j].bMasked) {
                        continue;
                    }
                    loNeighbors.add(oaContent[i][j]);
                }
            }
        } else {
            for (InterrArea[] oa : this.oaContent) {
                for (InterrArea o : oa) {
                    double dDist = oRef.getCenter().getNorm(o.getCenter());
                    if (dDist <= iStampSize * oRef.oIntervalX.getSize()) {
                        if (o.bMasked) {
                            continue;
                        }
                        loNeighbors.add(o);
                    }
                }
            }
        }

        return loNeighbors;

    }

    public MatrixEntry getPosition(InterrArea oArea) {
        if (oArea.mePosInGrid != null) {
            return oArea.mePosInGrid;
        }
        for (int i = 0; i < oaContent.length; i++) {
            for (int j = 0; j < oaContent[0].length; j++) {
                if (oaContent[i][j] == oArea) {
                    oArea.mePosInGrid = new MatrixEntry(i, j);
                    return oArea.mePosInGrid;
                }
            }
        }
        return null;
    }

    public void shiftAndRecalc(DataPIV Data) {
//        for (int i = 0; i < oaContent.length; i++) {
//            for (int j = 0; j < oaContent[0].length; j++) {
//                if (!oaContent[i][j].bMasked && oaContent[i][j].dVx == null && oaContent[i][j].dVy == null) {
//                    oaContent[i][j].getDisplacement(Data);
//                }
//                oaContent[i][j].shiftSecond(Data);
//                oaContent[i][j].getDisplacement(Data);
//                oaContent[i][j].resetshiftSecond(Data);
//            }
//        }
        for (InterrArea[] oContent : oaContent) {
            Parallel.For2(Arrays.asList(oContent), new Parallel.Operation<InterrArea>() {
                @Override
                public void perform(InterrArea pParameter
                ) {
                    if (!pParameter.bMasked && pParameter.dVx == null && pParameter.dVy == null) {
                        pParameter.getDisplacement(Data);
                    }
                    pParameter.shiftSecond(Data);
                    pParameter.getDisplacement(Data);
                    pParameter.resetshiftSecond(Data);
                }

            });
        }

    }

    public void shiftAndRecalcSubPix(DataPIV Data) {

//        for (int i = 0; i < oaContent.length; i++) {
//            for (int j = 0; j < oaContent[0].length; j++) {
//                if (!oaContent[i][j].bMasked && oaContent[i][j].dVx == null && oaContent[i][j].dVy == null) {
//                    oaContent[i][j].getDisplacement(Data);
//                }
//                if (!oaContent[i][j].bMasked && oaContent[i][j].dVx != null && oaContent[i][j].dVy != null) {
//                    oaContent[i][j].getDisplacement(new OrderedPair(oaContent[i][j].dVx, oaContent[i][j].dVy), Data);
//                }
//
//            }
//        }
        for (InterrArea[] oContent : oaContent) {
            Parallel.For2(Arrays.asList(oContent), new Parallel.Operation<InterrArea>() {
                @Override
                public void perform(InterrArea pParameter
                ) {
                    if (!pParameter.bMasked && pParameter.dVx == null && pParameter.dVy == null) {
                        pParameter.getDisplacement(Data);
                    }
                    if (!pParameter.bMasked && pParameter.dVx != null && pParameter.dVy != null) {
                        pParameter.getDisplacement(new OrderedPair(pParameter.dVx, pParameter.dVy), Data);
                    }
                }

            });
        }
    }

    public ImageInt paintOnImageGrid(ImageInt oGrid, int iValue) {
        for (int i = 0; i < oaContent.length; i++) {
            for (int j = 0; j < oaContent[0].length; j++) {
                if (oaContent[i][j].bMasked) {
                    continue;
                }
                oGrid = oaContent[i][j].paintOngrid(oGrid, iValue);
            }
        }
        return oGrid;
    }

    public BufferedImage paintOnImage(ImageInt oGrid) {
        BufferedImage imgOrg = oGrid.getBuffImage();
        int height = oGrid.iaPixels.length;
        int width = oGrid.iaPixels[0].length;
        BufferedImage imgResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Result = imgResult.createGraphics();
        g2Result.drawImage(imgOrg, 0, 0, null);
        g2Result.setColor(Color.GREEN);
        for (int i = 0; i < oaContent.length; i++) {
            for (int j = 0; j < oaContent[0].length; j++) {
                if (oaContent[i][j].bMasked) {
                    continue;
                }
                int iWindowSize = (int) (double) this.getCellSize();
                g2Result.drawRect((int) (double) oaContent[i][j].oIntervalX.dLeftBorder, (int) (double) oaContent[i][j].oIntervalY.dLeftBorder, iWindowSize, iWindowSize);
            }
        }
        g2Result.dispose();
        return imgResult;
    }

    public ImageInt paintOnImageGridSecondGrid(ImageInt oGrid, int iValue) {
        for (int i = 0; i < oaContent.length; i++) {
            for (int j = 0; j < oaContent[0].length; j++) {
                oGrid = oaContent[i][j].oAreaSecFrame.paintOngrid(oGrid, iValue);
            }
        }
        return oGrid;
    }

    public InterrGrid getRefinedGrid(InterrGrid oGrid, DataPIV Data) {
        int iFactor = Data.bOverlap ? 3 : 2;
        int PIV_WindowSize = Data.PIV_WindowSize / 2;
        int PIV_columns = (int) ((Data.iaPreProcFirst[0].length - 1) / (PIV_WindowSize));
        int PIV_rows = (int) ((Data.iaPreProcFirst.length - 1) / (PIV_WindowSize));
        InterrArea[][] oaReturnGrid = new InterrArea[((iFactor - 1) * PIV_rows) - iFactor % 2 - 1][((iFactor - 1) * PIV_columns)
                - iFactor % 2 - 1];
        Double[][][] dVelo = new Double[((iFactor - 1) * PIV_rows) - iFactor % 2][((iFactor - 1) * PIV_columns)
                - iFactor % 2][2];
        Integer[][] iCounter = new Integer[((iFactor - 1) * PIV_rows) - iFactor % 2][((iFactor - 1) * PIV_columns)
                - iFactor % 2];
        for (int i = 0; i < oaReturnGrid.length; i++) {
            for (int j = 0; j < oaReturnGrid[0].length; j++) {
                if (i < oGrid.oaContent.length && j < oGrid.oaContent[0].length) {
                    oGrid.oaContent[i][j].refine(Data.bOverlap);
                }
                double dXL = (j + 1) * PIV_WindowSize / 2.0;
                double dXR = dXL + PIV_WindowSize;
                double dYL = (i + 1) * PIV_WindowSize / 2.0;
                double dYR = dYL + PIV_WindowSize;
                oaReturnGrid[i][j] = new InterrArea(new Set1D(dXL, dXR), new Set1D(dYL, dYR));
                dVelo[i][j][0] = 0.0;
                dVelo[i][j][1] = 0.0;
                iCounter[i][j] = 0;
            }
        }

        for (int i = 0; i < oaContent.length; i++) {
            for (int j = 0; j < oaContent[0].length; j++) {
                if (!oaContent[i][j].bMasked && oaContent[i][j].dVx != null && oaContent[i][j].dVy != null) {
                    for (int k = 0; k < iFactor; k++) {
                        for (int l = 0; l < iFactor; l++) {
                            double dVelox = oaContent[i][j].oRefinedAreas[k][l].dVx;
                            double dVeloy = oaContent[i][j].oRefinedAreas[k][l].dVy;
                            int iPos = (int) (oaContent[i][j].oRefinedAreas[k][l].oIntervalY.dLeftBorder / (PIV_WindowSize / 2.0));
                            int jPos = (int) (oaContent[i][j].oRefinedAreas[k][l].oIntervalX.dLeftBorder / (PIV_WindowSize / 2.0));
                            if (dVelo[iPos][jPos][0] != null) {
                                dVelo[iPos][jPos][0] += dVelox;
                                dVelo[iPos][jPos][1] += dVeloy;
                                iCounter[iPos][jPos] += 1;
                            }
                        }
                    }
                }
            }

        }
        for (int i = 0; i < oaReturnGrid.length; i++) {
            for (int j = 0; j < oaReturnGrid[0].length; j++) {
                if (iCounter[i][j] > 0) {
                    oaReturnGrid[i][j].dVx = dVelo[i][j][0] / iCounter[i][j];
                    oaReturnGrid[i][j].dVy = dVelo[i][j][1] / iCounter[i][j];
                }
            }
        }
        InterrGrid oMultipass = new InterrGrid(oaReturnGrid);
        return oMultipass;
    }

    public InterrGrid getRefinesGrid(DataPIV Data) {
        int iFactor = Data.bOverlap ? 3 : 2;

        int PIV_columns = (int) ((Data.iaPreProcFirst[0].length - 1) / (Data.PIV_WindowSize / 2));
        int PIV_rows = (int) ((Data.iaPreProcFirst.length - 1) / (Data.PIV_WindowSize / 2));
        InterrArea[][] oaRefinedGrid = new InterrArea[((iFactor) * PIV_rows) - iFactor][((iFactor) * PIV_columns)
                - iFactor];

        int iRefinedGrid = 0;
        int lastJref = 0;
        for (int i = 0; i < oaContent.length; i++) {
            int jRefinedGrid = 0;
            for (int j = 0; j < oaContent[0].length; j++) {
                for (int k = 0; k < iFactor; k++) {
                    for (int l = 0; l < iFactor; l++) {
                        oaRefinedGrid[iRefinedGrid + k][jRefinedGrid + l] = oaContent[i][j].oRefinedAreas[k][l];
                    }
                }
                jRefinedGrid = jRefinedGrid + iFactor;
            }
            lastJref = jRefinedGrid;
            iRefinedGrid = iRefinedGrid + iFactor;
        }

        double dYStartOrg = oaRefinedGrid[iRefinedGrid - 1][lastJref - 1].oIntervalY.getCenter();
        double dXStartOrg = oaRefinedGrid[iRefinedGrid - 1][lastJref - 1].oIntervalX.getCenter();
        if (oaContent.length * iFactor < oaRefinedGrid.length) {
            double dYStart = dYStartOrg;
            for (int i = iRefinedGrid; i < oaRefinedGrid.length; i++) {
                dYStart = dYStart + (Data.PIV_WindowSize / (iFactor + iFactor % 2));
                double dYR = dYStart + (Data.PIV_WindowSize / 2);
                for (int j = 0; j < lastJref; j++) {
                    double dXL = oaRefinedGrid[0][j].oIntervalX.dLeftBorder;
                    double dXR = dXL + (Data.PIV_WindowSize / 2);
                    oaRefinedGrid[i][j] = new InterrArea(new Set1D(dXL, dXR), new Set1D(dYStart, dYR));
                }
                double dXStart = dXStartOrg;
                for (int j = lastJref; j < oaRefinedGrid[0].length; j++) {
                    dXStart = dXStart + (Data.PIV_WindowSize / (iFactor + iFactor % 2));
                    double dXR = dXStart + (Data.PIV_WindowSize / 2);
                    oaRefinedGrid[i][j] = new InterrArea(new Set1D(dXStart, dXR), new Set1D(dYStart, dYR));
                }
            }

        }

        if (oaContent[0].length * iFactor < oaRefinedGrid[0].length) {
            double dXStart = dXStartOrg;
            for (int i = lastJref; i < oaRefinedGrid[0].length; i++) {
                dXStart = dXStart + (Data.PIV_WindowSize / (iFactor + iFactor % 2));
                double dXR = dXStart + (Data.PIV_WindowSize / 2);
                for (int j = 0; j < iRefinedGrid; j++) {
                    double dYL = oaRefinedGrid[j][0].oIntervalY.dLeftBorder;
                    double dYR = dYL + (Data.PIV_WindowSize / 2);
                    oaRefinedGrid[j][i] = new InterrArea(new Set1D(dXStart, dXR), new Set1D(dYL, dYR));
                }
                double dYStart = dYStartOrg;
                for (int j = iRefinedGrid; j < oaRefinedGrid.length; j++) {
                    dYStart = dYStart + (Data.PIV_WindowSize / (iFactor + iFactor % 2));
                    double dYR = dYStart + (Data.PIV_WindowSize / 2);
                    oaRefinedGrid[j][i] = new InterrArea(new Set1D(dXStart, dXR), new Set1D(dYStart, dYR));
                }
            }

        }
        //
        InterrGrid oMultipass = new InterrGrid(oaRefinedGrid);
        return oMultipass;
    }

    @Override
    public Double getCellSize() {
        return oaContent[0][0].oIntervalX.getSize();
    }

    @Override
    public Double getValue(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public Double setValue(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public Double getdfdx(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public Double getdfdy(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

}

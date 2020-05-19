/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.shapes.Rectangle;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataBoolean;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataDouble;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataInt;

/**
 *
 * @author Thomas Ziegenhein
 */
public class RecArea implements AreaInSecondFrame {

    Set1D oIntervalX;
    Set1D oIntervalY;

    MatrixEntry meShift = new MatrixEntry(0, 0);

    public RecArea(Set1D oIntervalX, Set1D oIntervalY) {
        this.oIntervalX = oIntervalX;
        this.oIntervalY = oIntervalY;
    }

    @Override
    public int[][] getValuesInAreaInt(getDataInt o) {
        int[][] iaData = o.getfield();
        int[][] iaSub = new int[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder; i < oIntervalY.dRightBorder; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder; j < oIntervalX.dRightBorder; j++) {
                if (i < 0 || i >= iaData.length || j < 0 || j >= iaData[0].length) {
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
    public boolean[][] getValuesInAreaBoolean(getDataBoolean o) {
        boolean[][] iaData = o.getfield();
        boolean[][] iaSub = new boolean[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
        int iSub = 0;
        for (int i = (int) oIntervalY.dLeftBorder; i < oIntervalY.dRightBorder; i++) {
            int jSub = 0;
            for (int j = (int) oIntervalX.dLeftBorder; j < oIntervalX.dRightBorder; j++) {
                if (i < 0 || i >= iaData.length || j < 0 || j >= iaData[0].length) {
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

    @Override
    public void shift(MatrixEntry meDisplacement, DataPIV Data) {
        if (this.oIntervalX.dLeftBorder + meDisplacement.j < 0 || this.oIntervalX.dLeftBorder + meDisplacement.j >= Data.iaGreyIntensity2[0].length) {
            return;
        }
        if (this.oIntervalX.dRightBorder + meDisplacement.j < 0 || this.oIntervalX.dRightBorder + meDisplacement.j >= Data.iaGreyIntensity2[0].length) {
            return;
        }
        if (this.oIntervalY.dLeftBorder + meDisplacement.i < 0 || this.oIntervalY.dLeftBorder + meDisplacement.i >= Data.iaGreyIntensity2.length) {
            return;
        }
        if (this.oIntervalY.dRightBorder + meDisplacement.i < 0 || this.oIntervalY.dRightBorder + meDisplacement.i >= Data.iaGreyIntensity2.length) {
            return;
        }
        this.oIntervalX = new Set1D(this.oIntervalX.dLeftBorder + meDisplacement.j, this.oIntervalX.dRightBorder + meDisplacement.j);
        this.oIntervalY = new Set1D(this.oIntervalY.dLeftBorder + meDisplacement.i, this.oIntervalY.dRightBorder + meDisplacement.i);
        this.meShift = meDisplacement;
    }

    @Override
    public ImageInt paintOngrid(ImageInt oGrid, int iValue) {
        Rectangle oRec = new Rectangle(new MatrixEntry((int) this.oIntervalY.dLeftBorder, (int) this.oIntervalX.dLeftBorder), new MatrixEntry((int) this.oIntervalY.dRightBorder, (int) this.oIntervalY.dRightBorder));
        return oRec.setRectangle(oGrid, iValue);
    }

    @Override
    public MatrixEntry getShift() {
        return meShift;
    }

    @Override
    public double getSize() {
        return (this.oIntervalX.getSize() + this.oIntervalY.getSize()) / 2.0;
    }

    @Override
    public OrderedPair getPosition() {
        return new OrderedPair(oIntervalX.getCenter(), oIntervalY.getCenter());
    }

//    @Override
//    public void shiftSubPix(OrderedPair meDisplacement) {
//        if(this.oIntervalX.dLeftBorder + meDisplacement.x < 0 || this.oIntervalX.dLeftBorder + meDisplacement.x >= DataPIV.iaGreyIntensity2[0].length ) return;
//        if(this.oIntervalX.dRightBorder + meDisplacement.x < 0 || this.oIntervalX.dRightBorder + meDisplacement.x >= DataPIV.iaGreyIntensity2[0].length ) return;
//        if(this.oIntervalY.dLeftBorder + meDisplacement.y < 0 || this.oIntervalY.dLeftBorder + meDisplacement.y >= DataPIV.iaGreyIntensity2.length ) return;
//        if(this.oIntervalY.dRightBorder + meDisplacement.y < 0 || this.oIntervalY.dRightBorder + meDisplacement.y >= DataPIV.iaGreyIntensity2.length ) return;        
//    }
//    @Override
//    public double[][] getValuesInAreaDouble(DataPIV.getDataDouble o, OrderedPair subPixelShift) {
//        double[][] iaData = o.getfield();
//        double[][] iaSub = new double[(int) oIntervalY.getSize()][(int) oIntervalX.getSize()];
//        int iPixelShiftY = 0;
//        double dSubPixelShiftY = 0.0;
//        if ((subPixelShift.y + oIntervalY.dLeftBorder) >= 1 && (subPixelShift.y + oIntervalY.dRightBorder) < iaData.length - 1) {
//            iPixelShiftY = (int) subPixelShift.y;
//            dSubPixelShiftY = subPixelShift.y - iPixelShiftY;
//        }
//        int iPixelShiftX = 0;
//        double dSubPixelShiftX = 0.0;
//        if ((subPixelShift.x + oIntervalX.dLeftBorder) >= 1 && (subPixelShift.x + oIntervalX.dRightBorder) < iaData[0].length - 1) {
//            iPixelShiftX = (int) subPixelShift.x;
//            dSubPixelShiftX = subPixelShift.x - iPixelShiftX;
//        }
//
//        int iSub = 0;
//        for (int i = (int) oIntervalY.dLeftBorder + iPixelShiftY; i < oIntervalY.dRightBorder + iPixelShiftY; i++) {
//            int jSub = 0;
//            for (int j = (int) oIntervalX.dLeftBorder + iPixelShiftX; j < oIntervalX.dRightBorder + iPixelShiftX; j++) {                
//                if (dSubPixelShiftX >= 0 && dSubPixelShiftY >= 0) {
//                    double f_q11 = iaData[i][j];
//                    double f_q12 = iaData[i + 1][j];
//                    double f_q21 = iaData[i][j + 1];
//                    double f_q22 = iaData[i + 1][j + 1];
//                    double wy1 = dSubPixelShiftY;
//                    double wy2 = 1.0 - dSubPixelShiftY;
//                    double wx1 = dSubPixelShiftX;
//                    double wx2 = 1.0 - dSubPixelShiftX;
//                    iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
//                } else if (dSubPixelShiftX <= 0 && dSubPixelShiftY >= 0) {
//                    double f_q11 = iaData[i][j];
//                    double f_q12 = iaData[i + 1][j];
//                    double f_q21 = iaData[i][j - 1];
//                    double f_q22 = iaData[i + 1][j - 1];
//                    double wy1 = dSubPixelShiftY;
//                    double wy2 = 1.0 - dSubPixelShiftY;
//                    double wx1 = Math.abs(dSubPixelShiftX);
//                    double wx2 = 1.0 - Math.abs(dSubPixelShiftX);
//                    iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
//                } else if (dSubPixelShiftX >= 0 && dSubPixelShiftY <= 0) {
//                    double f_q11 = iaData[i][j];
//                    double f_q12 = iaData[i - 1][j];
//                    double f_q21 = iaData[i][j + 1];
//                    double f_q22 = iaData[i - 1][j + 1];
//                    double wy1 = Math.abs(dSubPixelShiftY);
//                    double wy2 = 1.0 - Math.abs(dSubPixelShiftY);
//                    double wx1 = dSubPixelShiftX;
//                    double wx2 = 1.0 - dSubPixelShiftX;
//                    iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
//                } else {
//                    double f_q11 = iaData[i][j];
//                    double f_q12 = iaData[i - 1][j];
//                    double f_q21 = iaData[i][j - 1];
//                    double f_q22 = iaData[i - 1][j - 1];
//                    double wy1 = Math.abs(dSubPixelShiftY);
//                    double wy2 = 1.0 - Math.abs(dSubPixelShiftY);
//                    double wx1 = Math.abs(dSubPixelShiftX);
//                    double wx2 = 1.0 - Math.abs(dSubPixelShiftX);
//                    iaSub[iSub][jSub] = wy2 * (f_q11 * wx2 + f_q21 * wx1) + wy1 * (f_q22 * wx1 + f_q12 * wx2);
//                }
//
//                jSub++;
//            }
//            iSub++;
//        }
//        return iaSub;
//    }
    
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
                if (i < 1 || i+1 >= iaData.length || j < 1 || j+1 >= iaData[0].length) {
                    if (i < 0 || i+1 > iaData.length || j < 0 || j+1 > iaData[0].length) {
                        iaSub[iSub][jSub] = 0.0;
                    }else{
                        iaSub[iSub][jSub] = iaData[i][j];
                    }                    
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

}

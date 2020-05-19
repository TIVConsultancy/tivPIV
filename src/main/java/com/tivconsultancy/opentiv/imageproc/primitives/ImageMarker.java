/*
 *  
 *   TECHNOBIS CONFIDENTIAL
 *  __________________
 *  
 *   [2020] Technobis 
 *   All Rights Reserved.
 *  
 *  All information contained herein is, and remains the property of Technobis.
 *  The intellectual and technical concepts contained herein are proprietary to
 *  Technobis and may be covered by U.S. and Foreign Patents, patents in 
 *  process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material is 
 *  strictly forbidden unless prior written permission is obtained from 
 *  Technobis.
 *  
 *  __________________
 *  
 *  This code is developed in the "Project1" between TIVConsultancy and 
 *  Technobis. All information herein are the sole property of Technobis 
 *  and are subject to a pending exclusive rights agreement.
 *  
 *  @Project Management Thomas Ziegenhein   ThomasZiegenhein@TIVConsultancy.com 
 *                                          +1 480 494 7254
 *                                          TIVConsultancy
 *                                          Tempe, Arizona
 *  @Project Management Stephan van Banning stephan.vanbanning@technobis.com 
 *                                          +31 (0)6 3434 7095
 *                                          Technobis
 *                                          Alkmaar,  The Netherlands
 */


package com.tivconsultancy.opentiv.imageproc.primitives;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class ImageMarker {
    public boolean[][] ba;
    
    public ImageMarker(boolean[][] ba){
        this.ba = ba;
    }
    
    public boolean get(MatrixEntry me){
        return ba[me.i][me.j];
    }
    
    public List<MatrixEntry> getNeighborsN8(int i, int j) {
        /*
         Resturn array list with counter clockwise order:
         3--2--1
         4-- --0
         5--6--7
        
         Values are null when on boundary
         */

        List<MatrixEntry> lo = new ArrayList<>();
        if (j >= ba[i].length - 1) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i, j + 1));
        }

        if (j >= ba[i].length - 1 || i <= 0) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i - 1, j + 1));
        }

        if (i <= 0) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i - 1, j));
        }

        if (j <= 0 || i <= 0) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i - 1, j - 1));
        }

        if (j <= 0) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i, j - 1));
        }

        if (j <= 0 || i >= ba.length - 1) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i + 1, j - 1));
        }

        if (i >= ba.length - 1) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i + 1, j));
        }

        if (i >= ba.length - 1 || j >= ba[i].length - 1) {
            lo.add(null);
        } else {
            lo.add(new MatrixEntry(i + 1, j + 1));
        }

        return lo;

    }
    
}

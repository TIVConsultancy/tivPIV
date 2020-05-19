/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Rectangle {
    public MatrixEntry oTopLeft;
    public MatrixEntry oTopRight;
    public MatrixEntry oBottomLeft;
    public MatrixEntry oBottomRight;
    
    public Line LineLeft;
    public Line LineRight;
    public Line LineTop;
    public Line LineBottom;
    
    
    public Rectangle(MatrixEntry oTopLeft, MatrixEntry oBottomRight){
        this.oBottomLeft = new MatrixEntry(oBottomRight.i, oTopLeft.j);
        this.oTopLeft = oTopLeft;
        this.oTopRight = new MatrixEntry( oTopLeft.i, oBottomRight.j);
        this.oBottomRight = oBottomRight;
        
        this.LineLeft = new Line(this.oBottomLeft, this.oTopLeft);
        this.LineTop = new Line(this.oTopLeft, this.oTopRight);
        this.LineRight = new Line(this.oTopRight, this.oBottomRight);
        this.LineBottom = new Line(this.oBottomRight, this.oBottomLeft);
    }
    
    public ImageInt setRectangle(ImageInt oGrid, int iValue){        
        LineLeft.setLine(oGrid, iValue);
        LineTop.setLine(oGrid, iValue);
        LineRight.setLine(oGrid, iValue);
        LineBottom.setLine(oGrid, iValue);
        return oGrid;
    }
    
}

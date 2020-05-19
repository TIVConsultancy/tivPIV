/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.sets;

/**
 * Mathematical structure of a (closed) set
 * @author Thomas Ziegenhein
 */
public class Set1D {
    
    public double dLeftBorder;
    public double dRightBorder;

    public Set1D(double dLeftBorder, double dRightBorder) {
        this.dLeftBorder = dLeftBorder;
        this.dRightBorder = dRightBorder;
    }
    
    public Set1D(double dCenter, double dSizeLeft, double dSizeRight) {
        this.dLeftBorder = dCenter - dSizeLeft;
        this.dRightBorder = dCenter + dSizeRight;
    }
    
    public Set1D(){
        /*
        Definition is set so that the checkEmptySet() returns true and getSize() returns 0;
        */
        this.dLeftBorder = Double.MIN_VALUE;
        this.dRightBorder = Double.MIN_VALUE;
    }
    
    public boolean isInside(Set1D oProofSet){        
        if(isInside(oProofSet.dLeftBorder)&& isInside(oProofSet.dRightBorder) && !checkEmptySet()){
            return true;
        }else{
            return false;
        }        
    }
    
    public boolean isInside(double dValue){        
        if(dLeftBorder<=dValue && dValue<=dRightBorder && !checkEmptySet()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isInsideLR(double dValue){        
        if(dLeftBorder<=dValue && dValue<dRightBorder && !checkEmptySet()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isInsideWOBorder(double dValue){        
        if(dLeftBorder<dValue && dValue<dRightBorder && !checkEmptySet()){
            return true;
        }else{
            return false;
        }        
    }
    
    public Double getMaxCircleInSet(double dValue){
        /*
        Returns the largest distance from the boundary
        */
      if(this.isInside(dValue)){
          return (dValue - dLeftBorder) < (dRightBorder-dValue) ? (dValue - dLeftBorder) : (dRightBorder-dValue);
      }else{
          return null;
      }
    }
    
    public boolean overlap(Set1D oInterval){
        if(isInside(oInterval.dLeftBorder)){
            return true;
        }
        if(isInside(oInterval.dRightBorder)){
            return true;
        }
        return false;
    }
    
    public boolean checkEmptySet(){
        if(dLeftBorder >= dRightBorder){
            return true;
        }
        return false;        
    }
    
    public double getSize(){
        double dSize = dRightBorder - dLeftBorder;
        return dSize;
    }
    
    public Set1D getIntersection(Set1D oIntervall){
        if(this.overlap(oIntervall)){
            Set1D oInterSection = new Set1D(0, 0);
            if(isInside(oIntervall.dLeftBorder)){
                oInterSection.dLeftBorder = oIntervall.dLeftBorder;
            }else{
                oInterSection.dLeftBorder = this.dLeftBorder;
            }
            if(isInside(oIntervall.dRightBorder)){
                oInterSection.dRightBorder = oIntervall.dRightBorder;
            }else{
                oInterSection.dRightBorder = this.dRightBorder;
            }
            
            return oInterSection;
        }
        
        if(oIntervall.isInside(this)){
            return this;
        }
        
        return null;
    }
    
    public double getNormToNearestBorder(double d){
        if(Math.abs(dLeftBorder-d)<Math.abs(dRightBorder-d)){
            return dLeftBorder;
        }else{
            return dRightBorder;
        }
    }
    
    public double getCenter(){
        return ( dLeftBorder + (dRightBorder-dLeftBorder)/2.0);
    }
    
    @Override
    public String toString(){
        return "Left: " + dLeftBorder + " Right: " + dRightBorder;
    }
}

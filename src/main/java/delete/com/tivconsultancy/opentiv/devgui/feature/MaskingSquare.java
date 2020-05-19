/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.feature;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class MaskingSquare implements Feature{

    public int iPosX = 0;
    public int iPosY = 0;
    public Double dNorm = 0.0;
    public double dNormPot = 100;
    
    @Override
    public String getName() {
        return "Square";
    }

    @Override
    public String getDescription() {
        return "Masks an area in a shape of a square";
    }

    @Override
    public String getToolDescription() {
        return "Masks an area in a shape of a square";
    }

    @Override
    public String getSettingsText1() {
        return "Position";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Center of the circle";
    }

    @Override
    public String getValueDescriptions1() {
        return "in the format of i,j , for exaple 145,200";
    }

    @Override
    public String getSettings1() {
        return iPosY + "," + iPosX;
    }

    @Override
    public void setSettings1(Object o) {
        List<String> PosList = Arrays.asList(o.toString().split(","));
        if(PosList.isEmpty() || PosList.size() < 2)  return;
        iPosY = Integer.valueOf(PosList.get(0).trim());
        iPosX = Integer.valueOf(PosList.get(1).trim());
//        if(!checkIfInList()){
//            AllSettingsContainer.oSetMask.loShapes.add(new SimpleShapes(iPosX, iPosY, dNorm, dNormPot));            
//        }
    }

    @Override
    public String getSettingsText2() {
        return "Radius";
    }

    @Override
    public String getSettingsDescriptions2() {
        return "Radius of the desired circle";
    }

    @Override
    public String getValueDescriptions2() {
        return "Double, radius of the desired circle";
    }

    @Override
    public String getSettings2() {
        return dNorm.toString();
    }

    @Override
    public void setSettings2(Object o) {
        dNorm = Double.valueOf(o.toString().trim());
//        if(!checkIfInList()){
//            AllSettingsContainer.oSetMask.loShapes.add(new SimpleShapes(iPosX, iPosY, dNorm, dNormPot));            
//        }
    }

    @Override
    public void removeFeature() {
//        if(AllSettingsContainer.oSetMask.loShapes.isEmpty()) return;
//        int iCount = 0;
//        for(SimpleShapes o : AllSettingsContainer.oSetMask.loShapes){
//            if(o.iPosX == this.iPosX && o.iPosY == iPosY && o.dNorm == this.dNorm && o.dNormPot == this.dNormPot){
//                break;
//            }
//            iCount++;
//        }        
//        AllSettingsContainer.oSetMask.loShapes.remove(iCount);
    }

    @Override
    public Feature clone() {
        return new MaskingSquare();
    }
    
    public String toString(){
        return getName();
    }
    
//    public boolean checkIfInList(){
//        for(SimpleShapes o : AllSettingsContainer.oSetMask.loShapes){
//            if(o.iPosX == this.iPosX && o.iPosY == iPosY && o.dNorm == this.dNorm && o.dNormPot == this.dNormPot){
//                return true;
//            }
//        }
//        return false;
//    }
    
    public boolean equals(Object o) {
        if (o instanceof Feature){
            if(this.getName().equals( ((Feature) o).getName())){
                if (((MaskingSquare) o).iPosX == this.iPosX && ((MaskingSquare) o).iPosY == iPosY && ((MaskingSquare) o).dNorm == this.dNorm && ((MaskingSquare) o).dNormPot == this.dNormPot) {
                    return true;
                }
            }
        }
        return false;
    }
    
}

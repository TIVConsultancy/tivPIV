/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.feature;

import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;


/**
 *
 * @author Thomas Ziegenhein
 */
public class CutY implements Feature{

    Settings oSet;
    
    public CutY(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Cut Y";
    }

    @Override
    public String getDescription() {
        return "cuts the image in the y-cooridnate";
    }

    @Override
    public String getToolDescription() {
        return "cuts the image in the y-cooridnate";
    }

    @Override
    public String getSettingsText1() {
        return "Top Border";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "defines the cut at the top of the image (0,0) is at the top left corner";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer, >0 and smaller than the image height";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("cutyTop").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("BcutyTop", true);
        oSet.setSettingsValue("cutyTop", Integer.valueOf(o.toString().trim()));
    }

    @Override
    public String getSettingsText2() {
        return "Bottom Border";
    }

    @Override
    public String getSettingsDescriptions2() {
        return "defines the cut at the bottom of the image (0,0) is at the top left corner";
    }

    @Override
    public String getValueDescriptions2() {
        return "Integer, >0 and smaller than image height";
    }

    @Override
    public String getSettings2() {        
        return oSet.getSettingsValue("cutyBottom").toString();
    }

    @Override
    public void setSettings2(Object o) {
        oSet.setSettingsValue("BcutyBottom", true);
        oSet.setSettingsValue("cutyBottom", Integer.valueOf(o.toString().trim()));
    }
    
    public String toString(){
        return getName();
    }

    @Override
    public Feature clone() {
        return this;
    }

    @Override
    public void removeFeature() {
        oSet.setSettingsValue("BcutyTop", false);
        oSet.setSettingsValue("BcutyBottom", false);
    }
    
    public boolean equals(Object o) {
        if (o instanceof Feature){
            if(this.getName().equals( ((Feature) o).getName())){
                return true;
            }
        }
        return false;
    }
    
}

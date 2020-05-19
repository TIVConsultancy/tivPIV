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
public class CutX implements Feature{

    Settings oSet;
    
    public CutX(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Cut X";
    }

    @Override
    public String getDescription() {
        return "cuts the image in the x-cooridnate";
    }

    @Override
    public String getToolDescription() {
        return "cuts the image in the x-cooridnate";
    }

    @Override
    public String getSettingsText1() {
        return "Left Border";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "defines the cut at the left of the image, (0,0) is at the top left corner";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer, >0 and smaller than the image width";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("cutxLeft").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("BcutxLeft", true);
        oSet.setSettingsValue("cutxLeft", Integer.valueOf(o.toString().trim()));
    }

    @Override
    public String getSettingsText2() {
        return "Right Border";
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
        return oSet.getSettingsValue("cutxRight").toString();
    }

    @Override
    public void setSettings2(Object o) {
        oSet.setSettingsValue("BcutxRight", true);
        oSet.setSettingsValue("cutxRight", Integer.valueOf(o.toString().trim()));
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
        oSet.setSettingsValue("BcutxLeft", false);
        oSet.setSettingsValue("BcutxRight", false);
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

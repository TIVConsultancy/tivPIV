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
public class Contrast implements Feature {

    Settings oSet;
    
    public Contrast(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Contrast";
    }

    @Override
    public String getDescription() {
        return "stretches the Histogram in order to increase contrast ratio";
    }

    @Override
    public String getToolDescription() {
        return "stretches the Histogram in order to increase contrast ratio";
    }

    @Override
    public String getSettingsText1() {
        return "Black";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Defines the new black value";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer between 0 and 255";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("BlackMin").toString(); 
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("HGContrast", true);
        oSet.setSettingsValue("BlackMin", Integer.valueOf(o.toString().trim()));
    }

    @Override
    public String getSettingsText2() {
        return "White";
    }

    @Override
    public String getSettingsDescriptions2() {
        return "Defines the new white value";
    }

    @Override
    public String getValueDescriptions2() {
        return "Integer between 0 and 255";
    }

    @Override
    public String getSettings2() {
        return oSet.getSettingsValue("WhiteMax").toString(); 
    }

    @Override
    public void setSettings2(Object o) {
        oSet.setSettingsValue("HGContrast", true);
        oSet.setSettingsValue("WhiteMax", Integer.valueOf(o.toString().trim()));
    }
    
    public String toString(){
        return getName();
    }
    
    public Feature clone(){
        return this;
    }

    @Override
    public void removeFeature() {
        oSet.setSettingsValue("HGContrast", false);
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

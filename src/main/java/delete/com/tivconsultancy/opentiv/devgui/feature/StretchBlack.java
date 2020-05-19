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
public class StretchBlack implements Feature{

    Settings oSet;
    
    public StretchBlack(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "StretchBlack";
    }

    @Override
    public String getDescription() {
        return "Stretches the left side of the histogram by a Factor";
    }

    @Override
    public String getToolDescription() {
        return "Stretches the left side of the histogram by a Factor";
    }

    @Override
    public String getSettingsText1() {
        return "Factor";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Factor by which the left side of the histogram will be stretched";
    }

    @Override
    public String getValueDescriptions1() {
        return "Usually a value around 1 is common";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("BlackStretchFactor").toString(); 
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("HGBlackStretch", true);
        oSet.setSettingsValue("BlackStretchFactor", Double.valueOf(o.toString().trim()));
    }

    @Override
    public String getSettingsText2() {
        return null;
    }

    @Override
    public String getSettingsDescriptions2() {
        return null;
    }

    @Override
    public String getValueDescriptions2() {
        return null;
    }

    @Override
    public String getSettings2() {
        return null;
    }

    @Override
    public void setSettings2(Object o) {
    }
    
    @Override
    public String toString(){
        return getName();
    }
    
    public Feature clone(){
        return this;
    }

    @Override
    public void removeFeature() {
        oSet.setSettingsValue("HGBlackStretch", false);
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

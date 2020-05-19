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
public class Brightness implements Feature {
    
    Settings oSet;
    
    public Brightness(Settings oSet){
        this.oSet = oSet;
    }

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public String getDescription() {
        return "Positive or negative shift of the histogram";
    }

    @Override
    public String getToolDescription() {
        return "Positive or negative shift of the histogram";
    }

    @Override
    public String getSettingsText1() {
        return "Shift";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Integer, positive or negative value";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer, value between -255 and +255";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("Brightness").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("HGBrightness", true);
        oSet.setSettingsValue("Brightness", Integer.valueOf(o.toString().trim()));
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

    public String toString() {
        return getName();
    }

    public Feature clone() {
        return this;
    }

    @Override
    public void removeFeature() {
        oSet.setSettingsValue("HGBrightness", false);
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

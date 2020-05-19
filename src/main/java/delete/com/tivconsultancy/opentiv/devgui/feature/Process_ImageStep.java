/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.feature;

import delete.com.tivconsultancy.opentiv.devgui.feature.Feature;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;



/**
 *
 * @author Thomas Ziegenhein
 */
public class Process_ImageStep implements Feature {
    
    Settings oSet;
    
    public Process_ImageStep(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Image Leap";
    }

    @Override
    public String getDescription() {
        return "Leap between first and second image; e.g. standard value is 1, which meas first image is picture 1 and second image is picture 2. A value of 2 would be first image is picture 1 and second image is picture 3. ";
    }

    @Override
    public String getToolDescription() {
        return "Leap between first and second image";
    }

    @Override
    public String getSettingsText1() {
        return "Leap";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Leap from first to second image for PIV";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer > 0";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("Leap").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("Leap", Integer.valueOf(o.toString().trim()));
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
    
    public String toString(){
        return getName();
    }

    @Override
    public Feature clone() {
        return this;
    }

    @Override
    public void removeFeature() {
        oSet.setSettingsValue("Leap", 1);
    }
    
    public boolean equals(Object o) {
        if (o instanceof Process_ImageStep){
            return true;
        }
        return false;
    }
}
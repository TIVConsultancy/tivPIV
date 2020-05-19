/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.feature;

import delete.com.tivconsultancy.opentiv.devgui.mvc_model.SettingsProcess;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;



/**
 *
 * @author Thomas Ziegenhein
 */
public class Process_Sequence implements Feature {
    
    Settings oSet;
    
    public Process_Sequence(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Image Squence";
    }

    @Override
    public String getDescription() {
        return "Processes images that are taken in squences. For example: 3 Images recorded with 250 fps -> pause 1 second -> 3 Images recorded with 250 fps -> pause 1 second -> ... ";
    }

    @Override
    public String getToolDescription() {
        return "Processes images that are taken in squences. For example: 3 Images recorded with 250 fps -> pause 1 second -> 3 Images recorded with 250 fps -> pause 1 second -> ...";
    }

    @Override
    public String getSettingsText1() {
        return "Squence length";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Length of the image sequence taken consecutively";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("Sequence").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("Sequence", Integer.valueOf(o.toString().trim()));
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
        oSet.setSettingsValue("Sequence", 1);
    }
    
    public boolean equals(Object o) {
        if (o instanceof Process_Sequence){
            return true;
        }
        return false;
    }
}
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
public class PreProc_Box3x3 implements Feature{

    Settings oSet;
    
    public PreProc_Box3x3(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Box 3x3 Filter";
    }

    @Override
    public String getDescription() {
        return "Box smothing";
    }

    @Override
    public String getToolDescription() {
        return "Box smothing";
    }

   @Override
    public String getSettingsText1() {
        return null;
    }

    @Override
    public String getSettingsDescriptions1() {
        return null;
    }

    @Override
    public String getValueDescriptions1() {
        return null;
    }

    @Override
    public String getSettings1() {
        return null;
    }

    @Override
    public void setSettings1(Object o) {        
        oSet.setSettingsValue("SF3x3Box", true);        
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
        oSet.setSettingsValue("SF3x3Box", false);
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

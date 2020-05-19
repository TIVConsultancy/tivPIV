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
public class NRSimple1 implements Feature{

    Settings oSet;
    
    public NRSimple1(Settings oSet){
        this.oSet = oSet;
    }
    
    @Override
    public String getName() {
        return "Simpel1";
    }

    @Override
    public String getDescription() {
        return "Noise reduction simple1";
    }

    @Override
    public String getToolDescription() {
        return "Noise reduction simple1";
    }

   @Override
    public String getSettingsText1() {
        return "Noise Reduction 1";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Threshold for noise reduction simple1";
    }

    @Override
    public String getValueDescriptions1() {
        return "Threshold for noise reduction simple1";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("NRSimple1Threshold").toString();
    }

    @Override
    public void setSettings1(Object o) {
        oSet.setSettingsValue("NR", true);
        oSet.setSettingsValue("NRSimple1", true);
        oSet.setSettingsValue("NRSimple1Threshold", Integer.valueOf(o.toString().trim()));
        
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
        oSet.setSettingsValue("NRSimple1", false);
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

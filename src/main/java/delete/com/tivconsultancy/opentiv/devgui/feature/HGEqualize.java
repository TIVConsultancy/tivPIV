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
public class HGEqualize implements Feature {

    Settings oSet;
    
    public HGEqualize(Settings oSet){
        this.oSet = oSet;
    }
    
    public HGEqualize() {
    }

    @Override
    public String getName() {
        return "Equalize";
    }

    @Override
    public String getDescription() {
        return "Histogram equalization is a method in image processing of contrast adjustment using the image's histogram. ";
    }

    @Override
    public String getToolDescription() {
        return "Histogram equalization is a method in image processing of contrast adjustment using the image's histogram. ";
    }

    @Override
    public String getSettingsText1() {
        return "Max White";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "Set the maximal white value";
    }

    @Override
    public String getValueDescriptions1() {
        return "Integer < 256";
    }

    @Override
    public String getSettings1() {
        return oSet.getSettingsValue("Equalize").toString();
    }

    @Override
    public void setSettings1(Object o) {     
        oSet.setSettingsValue("HGEqualize", true);
        oSet.setSettingsValue("Equalize", Integer.valueOf(o.toString().trim()));
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
        oSet.setSettingsValue("HGEqualize", false);
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

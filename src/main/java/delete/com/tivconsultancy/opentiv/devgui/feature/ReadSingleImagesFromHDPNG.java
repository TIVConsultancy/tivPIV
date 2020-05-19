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
public class ReadSingleImagesFromHDPNG implements Feature{

    Settings oSet;
    
    public ReadSingleImagesFromHDPNG(Settings oSet){
        this.oSet = oSet;
    }
    
    public String sSettings1;
    
    @Override
    public String getName() {
        return "Single Images from HDD PNG";
    }

    @Override
    public String getDescription() {
        return "Reads all png  files in the specified folder";
    }

    @Override
    public String getToolDescription() {
        return "Reads all png  files in the specified folder";
    }

    @Override
    public String getSettingsText1() {
        return " File Folder";
    }

    @Override
    public String getSettingsDescriptions1() {
        return "The position where the files are located (for example C:\\myPictures or /home/myPictures";
    }

    @Override
    public String getValueDescriptions1() {
        return "C:\\myPictures or /home/myPictures";
    }

    @Override
    public String getSettings1() {
        if(sSettings1 == null){
            return "C:\\";
        }else{
            return sSettings1;
        }
    }

    @Override
    public void setSettings1(Object o) {
        this.sSettings1 = o.toString().trim();
        oSet.setSettingsValue("ReadInFolder", (o.toString().trim()));
        oSet.setSettingsValue("ReadInFileType", ".png");
        
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
        return new ReadSingleImagesFromHDPNG(oSet);
    }

    @Override
    public void removeFeature() {        
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

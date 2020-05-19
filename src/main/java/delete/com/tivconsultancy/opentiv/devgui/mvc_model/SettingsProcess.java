/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingsProcess extends Settings {

    public SettingsProcess() {
        this.loSettings.add(new SettingObject("Leap", 1,SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cores", 1,SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Sequence", 2,SettingObject.SettingsType.Integer));
        buildClusters();
    }

    @Override
    public String getType() {
        return "Processing";
    }
    
    @Override
    public void buildClusters() {
        SettingsCluster Leap = new SettingsCluster("Leap", new String[]{"Leap"} , this);
        Leap.setDescription("Leap between images. For example, Leap = 1, the images 1,2,3,4,... will be evaluated. Leap = 2, the images 1,3,5,7,... will be evaluated");
        lsClusters.add(Leap);
        
        SettingsCluster Cores = new SettingsCluster("CPU Cores", new String[]{"Cores"} , this);
        Cores.setDescription("The amount of CPU threads used");
        lsClusters.add(Cores);
        
        SettingsCluster Sequence = new SettingsCluster("Image Sequence", new String[]{"Sequence"} , this);
        Sequence.setDescription("Defines the burst length for methods that use the preceeding image");
        lsClusters.add(Sequence);
        
    }

}

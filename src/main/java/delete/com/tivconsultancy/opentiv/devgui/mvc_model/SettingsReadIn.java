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
public class SettingsReadIn extends Settings {

    public SettingsReadIn() {
        this.loSettings.add(new SettingObject("ReadInFolder", " ",SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("ReadInFileType", " ",SettingObject.SettingsType.String));
        buildClusters();
    }

    @Override
    public String getType() {
        return "Image Read In";
    }

    @Override
    public void buildClusters() {
        SettingsCluster ReadIn = new SettingsCluster("Read in folder", new String[]{"ReadInFolder"}, this);
        ReadIn.setDescription("Folder from which data will be read in");
        lsClusters.add(ReadIn);
        
        SettingsCluster ReadInFileType = new SettingsCluster("File Type", new String[]{"ReadInFileType"}, this);
        ReadInFileType.setDescription("The file type which is searched in the read in folder");
        lsClusters.add(ReadInFileType);
    }

}

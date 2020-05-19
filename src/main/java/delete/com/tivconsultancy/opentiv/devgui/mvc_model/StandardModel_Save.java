/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import com.tivconsultancy.opentiv.logging.TIVLog;
import delete.com.tivconsultancy.opentiv.devgui.mvc_model.StandardModel_FolderStructure.StandardFolders;
import delete.com.tivconsultancy.opentiv.devgui.datacontainer.GUI_Data;
import delete.com.tivconsultancy.opentiv.devgui.feature.Feature;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class StandardModel_Save implements Model_Save{
    
    @Override
    public void saveState(Model_Settings oModelSettings, Model_Features oModelFeatures, Model_FolderStructure oModelFolders) {
       
        String sOUT = oModelFolders.getFolder(StandardFolders.Out);
        String sSettingsFolder = oModelFolders.getFolder(StandardFolders.Settings);
        if(sOUT == null || sOUT.isEmpty()){
//            TIVLog.Log.error("Cannot save state because output file is not declared");
            return;
        }
                
        List<Feature> loAllFeatures = oModelFeatures.getAllFeatures();
        List<SettingObject> loAllSettings = new ArrayList<>();
        for(Settings oSet : oModelSettings.getAllSettings()){
            loAllSettings.addAll(oSet.getOutput());
        }
        
        
        Set<Feature> loAllFeaturesHash = new LinkedHashSet<>(loAllFeatures);
        loAllFeatures.clear();
        loAllFeatures.addAll(loAllFeaturesHash);        

        try {
            FileOutputStream fos = new FileOutputStream(sOUT + java.io.File.separator + sSettingsFolder + java.io.File.separator + "Features.gtt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(loAllFeatures);
            oos.close();
            fos.close();
        } catch (Exception ioe) {
//            TIVLog.Log.error((new Throwable()).getStackTrace()[0].getMethodName() + "Cannot write " + sOUT + java.io.File.separator + sSettingsFolder + java.io.File.separator + "Features.gtt", ioe);
        }                        
        
        try {
            FileOutputStream fos = new FileOutputStream(sOUT + java.io.File.separator + sSettingsFolder + java.io.File.separator + "Settings.gtt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(loAllSettings);
            oos.close();
            fos.close();
        } catch (Exception ioe) {
//            TIVLog.Log.error((new Throwable()).getStackTrace()[0].getMethodName() + "Cannot write " + sOUT + java.io.File.separator + sSettingsFolder + java.io.File.separator + "Settings.gtt", ioe);
        }

    }
}

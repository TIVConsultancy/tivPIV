/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject.SettingsType;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.tivpiv.data.DataPIV;


/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingsPIV extends Settings {
    
    public SettingsPIV(){
        //Grids
        loSettings.add(new SettingObject("GridType", "Overlap", SettingsType.String));
        loSettings.add(new SettingObject("PIV_WindowSize", 32, SettingsType.Integer));

        
        //Correlation improvement
        //Correlation Improvment
        loSettings.add(new SettingObject("Hart1998", false, SettingsType.Boolean));
        loSettings.add(new SettingObject("Hart1998Divider", 2, SettingsType.Integer));
        //SubPixel
        loSettings.add(new SettingObject("SubPixType", "Gaussian", SettingsType.String));
        
        //Processor
        //Refinement
        loSettings.add(new SettingObject("bRefine", false, SettingsType.Boolean));
        //Multipass
        loSettings.add(new SettingObject("bMultipass", false, SettingsType.Boolean));
        loSettings.add(new SettingObject("bMultipass_BiLin", true, SettingsType.Boolean));
        loSettings.add(new SettingObject("iMultipassCount", 3, SettingsType.Integer));
        

        //Post Processing
        //Validation
        loSettings.add(new SettingObject("bValidate", true, SettingsType.Boolean));
        loSettings.add(new SettingObject("sValidationtype", "MedianComp",SettingsType.String));        
        loSettings.add(new SettingObject("iStampSize", 5, SettingsType.Integer));
        loSettings.add(new SettingObject("dValidationThreshold", 1.5, SettingsType.Double));
        loSettings.add(new SettingObject("bInterpolation", true, SettingsType.Boolean));
        //Display
        loSettings.add(new SettingObject("dStretch", 5, SettingsType.Double));
        loSettings.add(new SettingObject("AutoStretch", false, SettingsType.Boolean));
        loSettings.add(new SettingObject("AutoStretchFactor", 1, SettingsType.Double));
        loSettings.add(new SettingObject("BlanckBackground", true, SettingsType.Boolean));        
        loSettings.add(new SettingObject("BlanckBackgroundGrayValue2", 255, SettingsType.Integer));
        loSettings.add(new SettingObject("ImageBackground", false, SettingsType.Boolean));
        
    }
    
    public DataPIV setSettingsToPIV(DataPIV Data){
        Data.cutyTop = Integer.valueOf(this.getSettingsValue("cutPIVyTop").toString());
        Data.cutyBottom = Integer.valueOf(this.getSettingsValue("cutPIVyBottom").toString());
        Data.cutxLeft = Integer.valueOf(this.getSettingsValue("cutPIVxLeft").toString());
        Data.cutxRight = Integer.valueOf(this.getSettingsValue("cutPIVxRight").toString());
        Data.bsharpen = Boolean.valueOf(this.getSettingsValue("bsharpen").toString());
        Data.iHistCut = Integer.valueOf(this.getSettingsValue("iHistCut").toString());
        Data.iThresholdSharp = Integer.valueOf(this.getSettingsValue("iThresholdSharp").toString());
        Data.bBinarize = Boolean.valueOf(this.getSettingsValue("bBinarize").toString());
        Data.imax = Integer.valueOf(this.getSettingsValue("imax").toString());
        Data.bhighcont = Boolean.valueOf(this.getSettingsValue("bhighcont").toString());
        Data.PIV_WindowSize = Integer.valueOf(this.getSettingsValue("PIV_WindowSize").toString());
        Data.bValidate = Boolean.valueOf(this.getSettingsValue("bValidate").toString());
        Data.sValidationType = this.getSettingsValue("sValidationtype").toString();
        Data.bMultipass = Boolean.valueOf(this.getSettingsValue("bMultipass").toString());
        Data.bMultipass_BiLin = Boolean.valueOf(this.getSettingsValue("bMultipass_BiLin").toString());
        Data.dValidationThreshold = Double.valueOf(this.getSettingsValue("dValidationThreshold").toString());
        Data.bInterpolation = Boolean.valueOf(this.getSettingsValue("bInterpolation").toString());
        Data.bRefine = Boolean.valueOf(this.getSettingsValue("bRefine").toString());
        Data.Hart1998 = Boolean.valueOf(this.getSettingsValue("Hart1998").toString());
        Data.Hart1998Divider = Double.valueOf(this.getSettingsValue("Hart1998Divider").toString());
        Data.iMultipassCount  = Integer.valueOf(this.getSettingsValue("iMultipassCount").toString());
        Data.dStretch = Double.valueOf(this.getSettingsValue("dStretch").toString());
        Data.AutoStretch = Boolean.valueOf(this.getSettingsValue("AutoStretch").toString());
        Data.AutoStretchFactor = Double.valueOf(this.getSettingsValue("dStretch").toString());
        Data.sSubPixelType = this.getSettingsValue("SubPixType").toString();
        Data.sGridType = this.getSettingsValue("GridType").toString();
        Data.bMask = Boolean.valueOf(this.getSettingsValue("Mask").toString());
        return Data;
    }

    @Override
    public String getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildClusters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

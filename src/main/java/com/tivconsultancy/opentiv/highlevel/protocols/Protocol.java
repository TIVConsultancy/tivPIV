/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.highlevel.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.Hints;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 * @param <T>
 */
public abstract class Protocol extends Settings implements Hints {
    
    protected LookUp<ImageInt> outPutImages;
    
    public Protocol(){
        outPutImages = new LookUp<>();
    }
    
    public List<SettingObject> getSettings(){
        return getAllSettings();
    }
    
    public abstract NameSpaceProtocolResults1D[] get1DResultsNames();
    public abstract List<String> getIdentForViews();    
    public abstract ImageInt getView(String identFromViewer);
    public abstract Double getOverTimesResult(NameSpaceProtocolResults1D ident);
    public abstract void run(Object... input) throws UnableToRunException;
    public abstract Object[] getResults();
    
    public List<SettingObject> getHints(){
        List<SettingObject> ls = new ArrayList<>();
        for (SettingsCluster c : getClusters()) {
                for (SettingObject o : c.getSettings()) {
                    if (o.ident.equals(SettingObject.SettingsType.Boolean)) {
                        ls.add(o);
                        ls.add(new SettingObject(o.getName(), !((Boolean) o.sValue), SettingObject.SettingsType.Boolean));
                    } else {
                        ls.add(o);
                    }
                }
            }
        return ls;
    }
    
    
}

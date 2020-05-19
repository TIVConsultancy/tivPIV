/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.edgedetector;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingsEdges extends Settings {

    public SettingsEdges() {

        this.loSettings.add(new SettingObject("ExecutionOrder", new ArrayList<>(), SettingObject.SettingsType.Object));

        //Edge Detectors
        this.loSettings.add(new SettingObject("OuterEdges", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("OuterEdgesThreshold", 127, SettingObject.SettingsType.Integer));

        //Simple Edge Detection
        this.loSettings.add(new SettingObject("SimpleEdges", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("SimpleEdgesThreshold", 127, SettingObject.SettingsType.Integer));

        //Edge Operations
        this.loSettings.add(new SettingObject("SortOutSmallEdges", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("MinSize", 30, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("SortOutLargeEdges", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("MaxSize", 1000, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("RemoveOpenContours", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("RemoveClosedContours", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("CloseOpenContours", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("DistanceCloseContours", 10, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("SplitByCurv", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("OrderCurvature", 10, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("ThresCurvSplitting", 10, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("RemoveWeakEdges", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("ThresWeakEdges", 180, SettingObject.SettingsType.Integer));

        //Shape Fitting
        this.loSettings.add(new SettingObject("EllipseFit_Ziegenhein2019", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("EllipseFit_Ziegenhein2019_Distance", 50, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("EllipseFit_Ziegenhein2019_LeadingSize", 30, SettingObject.SettingsType.Integer));
        //Shape Filter
        this.loSettings.add(new SettingObject("RatioFilter_Max", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("RatioFilter_Max_Value", 1, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("RatioFilter_Min", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("RatioFilter_Min_Value", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Size_Max", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Size_Max_Value", 10000, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Size_Min", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Size_Min_Value", 1, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Major_Max", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Major_Max_Value", 10000, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Major_Min", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Major_Min_Value", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Minor_Max", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Minor_Max_Value", 10000, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Minor_Min", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Minor_Min_Value", 1, SettingObject.SettingsType.Integer));

    }

    public void setOrder(List<?> loInput) {
        List<String> loOrder = new ArrayList<>();
        for (Object o : loInput) {
            String s = o.toString();
            switch (s) {
                case "Close Open Contours":
                    loOrder.add("CloseOpenContours");
                    continue;
                case "Filter Large Contours":
                    loOrder.add("SortOutLargeEdges");
                    continue;
                case "Filter Small Contours":
                    loOrder.add("SortOutSmallEdges");
                    continue;
                case "Remove Closed Contours":
                    loOrder.add("RemoveClosedContours");
                    continue;
                case "Remove Open Contours":
                    loOrder.add("RemoveOpenContours");
                    continue;
                case "Split By Curvature":
                    loOrder.add("SplitByCurv");
                    continue;
                case "Weak Edge Contours":
                    loOrder.add("RemoveWeakEdges");
                    continue;
            }
        }
        this.setSettingsValue("ExecutionOrder", loOrder);
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

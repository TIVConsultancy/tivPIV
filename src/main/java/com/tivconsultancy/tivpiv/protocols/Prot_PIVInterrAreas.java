/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.BasicIMGOper;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.Morphology;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import com.tivconsultancy.opentiv.math.sets.Set2D;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.InterrArea;
import com.tivconsultancy.tivpiv.helpfunctions.InterrGrid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVInterrAreas extends PIVProtocol {

    ImageInt InterrArea;

    private String name = "Areas";

    public Prot_PIVInterrAreas() {
        InterrArea = new ImageInt(50, 50, 0);
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, InterrArea.getBuffImage());
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return new NameSpaceProtocolResults1D[0];
    }

    @Override
    public List<String> getIdentForViews() {
        return Arrays.asList(new String[]{name});
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }
    
    @Override
    public void setImage(BufferedImage bi){
        InterrArea = new ImageInt(bi);
        buildLookUp();
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        
        data.PIV_WindowSize = Integer.valueOf(getSettingsValue("PIV_WindowSize").toString());
        data.sGridType = getSettingsValue("PIV_GridType").toString();
        
        InterrArea.setImage(new ImageInt(data.iaPreProcFirst).getBuffImage());
        data.oGrid = getGrid(InterrArea, data);
        InterrArea.setImage(checkPIVMasking(data.oGrid, new ImageInt(data.iaPreProcFirst), data).getBuffImage());
        buildLookUp();
    }

    @Override
    public Object[] getResults() {
        return new Object[]{InterrArea};
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
        this.loSettings.add(new SettingObject("Window Size", "PIV_WindowSize", 32, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Grid Type", "PIV_GridType", "50Overlap", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("PIV Interrogation", "PIV_Interrogation", true, SettingObject.SettingsType.Boolean));
    }

    @Override
    public void buildClusters() {
        SettingsCluster IMGFilter = new SettingsCluster("Interrogation Area",
                                                        new String[]{"PIV_WindowSize", "PIV_GridType","PIV_Interrogation"}, this);
        IMGFilter.setDescription("Masks objects in pictures based on edge detecting");
        lsClusters.add(IMGFilter);
    }

    public InterrGrid getGrid(ImageInt oSourceImage, DataPIV data) {
//        Stopwatch.addTimmer(new Throwable()
//                .getStackTrace()[0]
//                .getMethodName());

        InterrGrid oReturn;

        String sType = getSettingsValue("PIV_GridType").toString();
        if (sType.equals("Standard")) {
            oReturn = divideIntoAreas(oSourceImage.iaPixels, data);
//            Stopwatch.stop(new Throwable()
//                    .getStackTrace()[0]
//                    .getMethodName());
            return oReturn;
        } else if (sType.equals("50Overlap")) {
            oReturn = divideIntoAreas50Overlap(oSourceImage.iaPixels, data);
//            Stopwatch.stop(new Throwable()
//                    .getStackTrace()[0]
//                    .getMethodName());
            return oReturn;
        } else if (sType.equals("PIV_Ziegenhein2018")) {
            oReturn = divideIntoAreas2(oSourceImage.iaPixels, data);
//            Stopwatch.stop(new Throwable()
//                    .getStackTrace()[0]
//                    .getMethodName());
            return oReturn;
        } else {
//            Stopwatch.stop(new Throwable()
//                    .getStackTrace()[0]
//                    .getMethodName());
            return null;
        }
    }

    public InterrGrid divideIntoAreas(int[][] iaReadIn, DataPIV data) {

        Integer PIV_WindowSize = Integer.valueOf(getSettingsValue("PIV_WindowSize").toString());
        data.PIV_columns = (int) (iaReadIn[0].length / PIV_WindowSize);
        data.PIV_rows = (int) (iaReadIn.length / PIV_WindowSize);

        InterrGrid oGrid = new InterrGrid(new InterrArea[data.PIV_rows][data.PIV_columns]);

        for (int i = 0; i < data.PIV_rows; i++) {
            for (int j = 0; j < data.PIV_columns; j++) {
                double dXL = j * PIV_WindowSize;
                double dXR = dXL + PIV_WindowSize;
                double dYL = i * PIV_WindowSize;
                double dYR = dYL + PIV_WindowSize;
                oGrid.oaContent[i][j] = new InterrArea(new Set1D(dXL, dXR), new Set1D(dYL, dYR));
            }
        }
        return oGrid;
    }

    public InterrGrid divideIntoAreas50Overlap(int[][] iaReadIn, DataPIV data) {

        Integer PIV_WindowSize = Integer.valueOf(getSettingsValue("PIV_WindowSize").toString());
        data.PIV_columns = (int) ((iaReadIn[0].length - 1) / PIV_WindowSize);
        data.PIV_rows = (int) ((iaReadIn.length - 1) / PIV_WindowSize);

        InterrGrid oGrid = new InterrGrid(new InterrArea[(2 * data.PIV_rows) - 1][(2 * data.PIV_columns) - 1]);

        for (int i = 0; i < (2 * data.PIV_rows) - 1; i++) {
            for (int j = 0; j < (2 * data.PIV_columns) - 1; j++) {
                double dXL = j * PIV_WindowSize / 2.0;
                double dXR = dXL + PIV_WindowSize;
                double dYL = i * PIV_WindowSize / 2.0;
                double dYR = dYL + PIV_WindowSize;
                oGrid.oaContent[i][j] = new InterrArea(new Set1D(dXL, dXR), new Set1D(dYL, dYR));
            }
        }
        return oGrid;
    }

    public InterrGrid divideIntoAreas2(int[][] iaReadIn, DataPIV data) {

        Integer PIV_WindowSize = Integer.valueOf(getSettingsValue("PIV_WindowSize").toString());

        ImageGrid oGrid = new ImageGrid(iaReadIn);
        oGrid = BasicIMGOper.threshold(oGrid, 127);

        oGrid.resetMarkers();
        List<InterrArea> loAreas = new ArrayList<>();
        for (ImagePoint op : oGrid.oa) {
            if (op.iValue < 127 && !op.bMarker) {
                OrderedPair opPos = op.getPos();
                double dXL = opPos.x - PIV_WindowSize / 2.0;
                double dXR = opPos.x + PIV_WindowSize / 2.0;
                double dYL = opPos.y - PIV_WindowSize / 2.0;
                double dYR = opPos.y + PIV_WindowSize / 2.0;
                if (oGrid.isInside(new OrderedPair(dXL, dYL)) && oGrid.isInside(new OrderedPair(dXR, dYR))) {

                    loAreas.add(new InterrArea(new Set1D(dXL, dXR), new Set1D(dYL, dYR)));

                    ImageGrid.setPoint(oGrid, new Set2D(new Set1D(dXL, dXR), new Set1D(dYL, dYR)), new ImageGrid.setIMGPoint() {

                                   @Override
                                   public void setPoint(ImageGrid oGrid, ImagePoint op) {
                                       oGrid.oa[op.i].bMarker = true;
                                   }
                               }
                    );

                }
            }
        }

        InterrGrid oInterrGrid = new InterrGrid(new InterrArea[loAreas.size()][1]);
        for (int i = 0; i < loAreas.size(); i++) {
            oInterrGrid.oaContent[i][0] = loAreas.get(i);
        }

        oInterrGrid.bSortedGrid = false;
        return oInterrGrid;
    }

    public InterrGrid divideIntoVoronoiAreas(int[][] iaReadIn, DataPIV data) throws IOException {

        Integer PIV_WindowSize = Integer.valueOf(getSettingsValue("PIV_WindowSize").toString());

        ImageGrid oGrid = new ImageGrid(iaReadIn);
        oGrid = BasicIMGOper.threshold(oGrid, 127);

        oGrid.resetMarkers();
        List<InterrArea> loAreas = new ArrayList<>();
        for (ImagePoint op : oGrid.oa) {
            if (op.iValue < 127 && !op.bMarker) {
                OrderedPair opPos = op.getPos();
                double dXL = opPos.x - PIV_WindowSize / 2.0;
                double dXR = opPos.x + PIV_WindowSize / 2.0;
                double dYL = opPos.y - PIV_WindowSize / 2.0;
                double dYR = opPos.y + PIV_WindowSize / 2.0;
                if (oGrid.isInside(new OrderedPair(dXL, dYL)) && oGrid.isInside(new OrderedPair(dXR, dYR))) {

                    loAreas.add(new InterrArea(new Set1D(dXL, dXR), new Set1D(dYL, dYR)));

                    ImageGrid.setPoint(oGrid, new Set2D(new Set1D(dXL, dXR), new Set1D(dYL, dYR)), new ImageGrid.setIMGPoint() {

                                   @Override
                                   public void setPoint(ImageGrid oGrid, ImagePoint op) {
                                       oGrid.oa[op.i].bMarker = true;
                                   }
                               }
                    );

                }
            }
        }

        List<ImagePoint> loSeed = new ArrayList<>();
        for (int i = 0; i < loAreas.size(); i++) {
            InterrArea o = loAreas.get(i);
            OrderedPair op = o.getCenter();
            loSeed.add(new ImagePoint((int) op.x, (int) op.y, 0, oGrid));
        }
        oGrid.resetMarkers();

        List<List<ImagePoint>> llo = (new Morphology()).markFillN4(oGrid, loSeed);

        oGrid.setPoint(llo.get(10), 127);

        InterrGrid oInterrGrid = new InterrGrid(new InterrArea[loAreas.size()][1]);
        for (int i = 0; i < loAreas.size(); i++) {
            oInterrGrid.oaContent[i][0] = loAreas.get(i);
        }

        oInterrGrid.bSortedGrid = false;
        return oInterrGrid;
    }

    public ImageInt checkPIVMasking(InterrGrid oPIVGrid, ImageInt oSourceImage, DataPIV oDataPIV) {

        oPIVGrid.checkMask(oDataPIV);
        return oPIVGrid.paintOnImageGrid(oSourceImage.clone(), 255);

    }

    @Override
    public List<SettingObject> getHints() {
        List<SettingObject> ls = super.getHints();
        ls.add(new SettingObject("Grid Type", "PIV_GridType", "PIV_Ziegenhein2018", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Grid Type", "PIV_GridType", "Standard", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Window Size", "PIV_WindowSize", 16, SettingObject.SettingsType.Integer));
        ls.add(new SettingObject("Window Size", "PIV_WindowSize", 64, SettingObject.SettingsType.Integer));
        ls.add(new SettingObject("Window Size", "PIV_WindowSize", 128, SettingObject.SettingsType.Integer));
//        ls.add(new SettingObject("PIV Interrogation", "PIV_Interrogation", true, SettingObject.SettingsType.Boolean));
        return ls;
    }

}

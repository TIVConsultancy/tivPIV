/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.io.Writer;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.math.algorithms.Averaging;
import com.tivconsultancy.opentiv.math.interfaces.Value;
import com.tivconsultancy.opentiv.math.primitives.Vector;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.opentiv.physics.vectors.VelocityVec;
import com.tivconsultancy.opentiv.velocimetry.helpfunctions.VelocityGrid;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL.sqlEntryPIV;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVDataHandling extends PIVProtocol {

    private String name = "DataExport";
    LookUp<Double> results1D = new LookUp<>();

    public Prot_PIVDataHandling() {
        super();
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return NameSpaceProtocol1DResults.class.getEnumConstants();
    }

    @Override
    public List<String> getIdentForViews() {
        return Arrays.asList(new String[]{});
    }

    @Override
    public void setImage(BufferedImage bi) {
        buildLookUp();
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return results1D.get(ident.toString());
    }

    @Override
    public void run(Object... input) throws UnableToRunException {

        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();

        String expName = getSettingsValue("sql_experimentident").toString();
        String settingsPIVName = getSettingsValue("sql_evalsettingspiv").toString();
        int refPX_X = Integer.valueOf(getSettingsValue("data_refposPXX").toString());
        double refM_X = Double.valueOf(getSettingsValue("data_refposMX").toString());
        int refPX_Y = Integer.valueOf(getSettingsValue("data_refposPXY").toString());
        double refM_Y = Double.valueOf(getSettingsValue("data_refposMY").toString());
        double refM_Z = Double.valueOf(getSettingsValue("data_refposMZ").toString());
        double fps = Integer.valueOf(getSettingsValue("data_FPS").toString());
        boolean bUPSERT = Boolean.valueOf(getSettingsValue("sql_upsert").toString());
        double dResolution = Double.valueOf(getSettingsValue("data_Resolution").toString()) / 100000.0;
        boolean activateSQL = Boolean.valueOf(this.getSettingsValue("sql_activation").toString());

        try {
            if (activateSQL) {
                tivPIVSubControllerSQL sql_Control = (tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null);
                sql_Control.setTimeStamp(getTimeStamp());
                dResolution = sql_Control.getResolution(expName) / 100000.0;
                List<sqlEntryPIV> entries = new ArrayList<>();
                for (Vector v : data.oGrid.getVectors()) {
                    double dPosX = (v.getPosX() - refPX_X) * dResolution + refM_X;
                    double dPosY = (v.getPosY() - refPX_Y) * dResolution + refM_Y;
                    double dPosZ = refM_Z;
                    double dVX = v.getX() * dResolution * fps;
                    double dVY = v.getY() * dResolution * fps * -1.0;
                    entries.add(new sqlEntryPIV(expName, settingsPIVName, dPosX, dPosY, dPosZ, dVX, dVY));

                }
                if (bUPSERT) {
                    sql_Control.upsertEntry(entries);
                } else {
                    sql_Control.insertEntry(entries);
                }

//            for(Vector v : data.oGrid.getVectors()){
//                double dPosX = (v.getPosX() - refPX_X)*dResolution + refM_X;
//                double dPosY = (v.getPosY() - refPX_Y)*dResolution + refM_Y;
//                double dPosZ = refM_Z;
//                double dVX = v.getX() * dResolution;
//                double dVY = v.getY() * dResolution;
//                if(bUPSERT){
//                    sql_Control.upsertEntry(new sqlEntryPIV(expName, settingsPIVName, dPosX, dPosY, dPosZ, dVX, dVY));
//                }else{
//                    sql_Control.insertEntry(new sqlEntryPIV(expName, settingsPIVName, dPosX, dPosY, dPosZ, dVX, dVY));
//                }
//            }
            }
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.SEVERE, "Error writing to SQL database", e);
        }

        try {
            if (Boolean.valueOf(this.getSettingsValue("data_csvExport").toString())) {
//                mache export csv
                List<String[]> lsOut = new ArrayList<>();
                int time = (int) getTimeStamp();
                String sExportPath = this.getSettingsValue("data_csvExportPath").toString();
                if (sExportPath == "Directory") {
                    String sDir = ((PIVController) StaticReferences.controller).getCurrentFileSelected().getParent();
                    sExportPath = sDir + System.getProperty("file.separator") + data.sOutputFolder;
                    File oF = new File(sExportPath);
                    if (!oF.exists()) {
                        oF.mkdir();
                    }
                }

                for (Vector v : data.oGrid.getVectors()) {
                    double dPosX = (v.getPosX() - refPX_X) * dResolution + refM_X;
                    double dPosY = (v.getPosY() - refPX_Y) * dResolution + refM_Y;
//                    double dPosYPx = v.getPosY();
//                    double dPosXPx = v.getPosX();
                    double dPosZ = refM_Z;
                    double dVX = v.getX() * dResolution * fps;
                    double dVY = -1.0 * v.getY() * dResolution * fps;
                    String[] sOut = new String[5];
                    sOut[0] = String.valueOf(dPosX);
                    sOut[1] = String.valueOf(dPosY);
                    sOut[2] = String.valueOf(dPosZ);
                    sOut[3] = String.valueOf(dVX);
                    sOut[4] = String.valueOf(dVY);
//                    sOut[5] = String.valueOf(dPosXPx);
//                    sOut[6] = String.valueOf(dPosYPx);
                    lsOut.add(sOut);
                }
                lsOut.add(0, new String[]{"Position X [m]", "Position Y [m]", "Position Z [m]", "Velocity X [m/s]", "Velocity Y [m/s]" });
                Writer oWrite = new Writer(sExportPath + System.getProperty("file.separator") + "LiqVelo" + time + ".csv");
                oWrite.writels(lsOut, ",");
                lsOut.clear();
            }
        } catch (Exception e) {
        }

        run1DResults(dResolution, fps);
    }

    public void run1DResults(double resolution, double fps) {
        results1D = new LookUp<>();

        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();

        List<VelocityVec> loVec = data.oGrid.getVectors();

        double avgx = Averaging.getMeanAverage(loVec, new Value<Object>() {
            @Override
            public Double getValue(Object pParameter) {
                return ((VelocityVec) pParameter).getVelocityX();
            }
        });

        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol1DResults.avgx.toString(), avgx * resolution * fps));

        double avgy = Averaging.getMeanAverage(loVec, new Value<Object>() {
            @Override
            public Double getValue(Object pParameter) {
                return ((VelocityVec) pParameter).getVelocityY();
            }
        });

        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol1DResults.avgy.toString(), avgy * resolution * fps));

        List<Double> lvarX = new ArrayList<>();
        List<Double> lvarY = new ArrayList<>();
        for (VelocityVec v : loVec) {
            lvarX.add(Math.pow((v.getVelocityX() - avgx), 2));
            lvarY.add(Math.pow((v.getVelocityY() - avgy), 2));
        }

//        VelocityGrid ovelo = getVeloGrid();
//        List<Double> lvarX = new ArrayList<>();
//        for (OrderedPair[] lop : ovelo.GridVeloX.calcVariance()) {
//            for (OrderedPair op : lop) {
//                lvarX.add(op.dValue);
//            }
//        }
//        List<Double> lvarY = new ArrayList<>();
//        for (OrderedPair[] lop : ovelo.GridVeloY.calcVariance()) {
//            for (OrderedPair op : lop) {
//                lvarY.add(op.dValue);
//            }
//        }
        double varX = Averaging.getMeanAverage(lvarX, null);
        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol1DResults.tkeX.toString(), varX * resolution * resolution * fps * fps));

        double varY = Averaging.getMeanAverage(lvarY, null);
        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol1DResults.tkey.toString(), varY * resolution * resolution * fps * fps));

    }

    public VelocityGrid getVeloGrid() {
//        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
//        return data.oGrid.getVeloGrid();
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        ImageInt oSourceImage = new ImageInt(data.iaReadInFirst);
        double dSize = data.oGrid.getCellSize();
        int iOffSet = 0;
        if ("50Overlap".equals(data.sGridType)) {
            dSize = dSize / 2.0;
            iOffSet = (int) (dSize / 2.0);
        }
        VelocityGrid oOutputGrid = new VelocityGrid(iOffSet, oSourceImage.iaPixels[0].length, oSourceImage.iaPixels.length, iOffSet, (int) (oSourceImage.iaPixels[0].length / dSize), (int) (oSourceImage.iaPixels.length / dSize));

        oOutputGrid = data.oGrid.getVeloGrid(oOutputGrid, data);
        return oOutputGrid;

    }

    private int getBurstNumber() {
        int index = ((PIVController) StaticReferences.controller).getSelecedIndex();
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        if (data.iBurstLength > 1) {
            return (index / data.iBurstLength);
        } else {
            return 0;
        }
    }

    private double getTimeStamp() {
        int index = ((PIVController) StaticReferences.controller).getSelecedIndex();
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        int fps = Integer.valueOf(getSettingsValue("data_FPS").toString());
        if (data.iBurstLength > 1) {
            int burstFreq = Integer.valueOf(getSettingsValue("data_BurstFreq").toString());
            double dBurstTime = ((int) (index / data.iBurstLength)) * (1.0 / burstFreq);
            double dRestTime = (index - (((int) (index / data.iBurstLength)) * data.iBurstLength)) * (1.0 / fps);
            System.out.println("Burst Time" + dBurstTime);
            System.out.println("Rest Time" + dRestTime);
            System.out.println("Burst Number " + getBurstNumber());
            return dBurstTime + dRestTime;
        } else {
            return index * (1.0 / fps);
        }
    }

    @Override
    public Object[] getResults() {
        return new Object[0];
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
        this.loSettings.add(new SettingObject("Export->SQL", "sql_activation", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Experiment", "sql_experimentident", "TestHome2", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("UPSERT", "sql_upsert", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Settings PIV", "sql_evalsettingspiv", "bestpractice", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Reference Pos X [Px]", "data_refposPXX", 0, SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Reference Pos X [m]", "data_refposMX", 0.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("Reference Pos Y [Px]", "data_refposPXY", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Reference Pos Y [m]", "data_refposMY", 0.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("Resolution [micron/Px]", "data_Resolution", 10.0, SettingObject.SettingsType.Double));
//        this.loSettings.add(new SettingObject("Reference Pos Z [Px]", "data_refposPXZ", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Reference Pos Z [m]", "data_refposMZ", 0.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("FPS", "data_FPS", 500, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Burst Frequency [Hz]", "data_BurstFreq", 5, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("CSV", "data_csvExport", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Export Path", "data_csvExportPath", "Directory", SettingObject.SettingsType.String));
    }

    @Override
    public void buildClusters() {
        SettingsCluster sqlCluster = new SettingsCluster("SQL",
                new String[]{"sql_activation", "sql_experimentident", "sql_upsert", "sql_evalsettingspiv"}, this);
        sqlCluster.setDescription("Handles the export to the SQL database");
        lsClusters.add(sqlCluster);

        SettingsCluster csvExport = new SettingsCluster("CSV",
                new String[]{"data_csvExport", "data_csvExportPath"}, this);
        csvExport.setDescription("CSV export");
        lsClusters.add(csvExport);

        SettingsCluster refPos = new SettingsCluster("Referennce Position",
                new String[]{"data_refposPXX", "data_refposMX",
                    "data_refposPXY", "data_refposMY", "data_refposMZ", "data_Resolution"}, this);
        refPos.setDescription("Specifies the reference position in the image");
        lsClusters.add(refPos);

        SettingsCluster timeSettings = new SettingsCluster("Time",
                new String[]{"data_FPS", "data_BurstFreq"}, this);
        timeSettings.setDescription("Time settings for FPS and Burst Frequency");
        lsClusters.add(timeSettings);

    }

    private enum NameSpaceProtocol1DResults implements NameSpaceProtocolResults1D {
        avgx, avgy, tkeX, tkey
    }

}

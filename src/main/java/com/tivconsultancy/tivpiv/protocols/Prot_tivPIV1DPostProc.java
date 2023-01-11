/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.datamodels.overtime.DataBaseEntry;
import com.tivconsultancy.opentiv.datamodels.overtime.Database;
import com.tivconsultancy.opentiv.helpfunctions.io.Writer;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.math.algorithms.Averaging;
import com.tivconsultancy.opentiv.math.interfaces.Value;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.opentiv.physics.vectors.VelocityVec;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.InterrGrid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_tivPIV1DPostProc extends PIVProtocol {

    private String name = "Post Processor";
    LookUp<Double> results1D = new LookUp<>();

    public Prot_tivPIV1DPostProc() {
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
        return new ArrayList<>();
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return results1D.get(ident.toString());
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        System.out.println("Start post-proc in time");
        Database data = ((PIVController) StaticReferences.controller).getDataBase();
        Set<String> keys = data.getAllKeys();
        List<InterrGrid> lsGrids = new ArrayList<>();
        for (String s : keys) {
            DataPIV dataPIV = (DataPIV) data.getRes(s);
            lsGrids.add(dataPIV.oGrid);
        }
        // To save start and end point (in time) for each element according to masked or not masked
        int[][][] iiStart = new int[lsGrids.get(0).oaContent.length][lsGrids.get(0).oaContent[0].length][lsGrids.size()];
        int[][][] iiEnd = new int[lsGrids.get(0).oaContent.length][lsGrids.get(0).oaContent[0].length][lsGrids.size()];
        int stampsize = Integer.valueOf(getSettingsValue("tivPIVValTimeStampSize").toString());
        double dthreshold = Double.valueOf(getSettingsValue("tivPIVValThreshold").toString());
        for (int i = 0; i < lsGrids.get(0).oaContent.length; i++) {
            for (int j = 0; j < lsGrids.get(0).oaContent[0].length; j++) {
                Double[] dVx = new Double[lsGrids.size()];
                Double[] dVy = new Double[lsGrids.size()];
                List<Integer> lsIValid = new ArrayList<>();
                for (int k = 0; k < lsGrids.size(); k++) {
                    if (!lsGrids.get(k).checkMask(i, j)) {
                        dVx[k] = lsGrids.get(k).oaContent[i][j].dVx;
                        dVy[k] = lsGrids.get(k).oaContent[i][j].dVy;
                        lsIValid.add(k);
                    }
                }
                if (!lsIValid.isEmpty()) {
                    List<Integer> lsIOutlier = new ArrayList<>();
                    List<List<Double>> ldVxVy = new ArrayList<>();
                    ldVxVy.add(new ArrayList<>());
                    ldVxVy.add(new ArrayList<>());
                    for (Integer I : lsIValid) {
                        List<Double> lsBVx = new ArrayList<>();
                        List<Double> lsBVy = new ArrayList<>();
                        List<Integer> lITimeDiff = new ArrayList<>();
                        int iStart = I;
                        int iEnd = I;
                        for (int k = 1; k <= stampsize; k++) {
                            if (I - k >= 0 && dVx[I - k] != null) {
                                iStart = I - k;
                            } else {
                                break;
                            }
                        }
                        for (int k = 1; k <= stampsize; k++) {
                            if (I + k < dVx.length && dVx[I + k] != null) {
                                iEnd = I + k;
                            } else {
                                break;
                            }
                        }
                        iiStart[i][j][I] = iStart;
                        iiEnd[i][j][I] = iEnd;
                        for (int k = iStart; k <= iEnd; k++) {
                            if (k != I) {
                                lITimeDiff.add(Math.abs(I - k));
                                lsBVx.add(dVx[k]);
                                lsBVy.add(dVy[k]);
                            }
                        }
                        if (!lsBVx.isEmpty()) {
                            boolean bOutlier = lsGrids.get(I).oaContent[i][j].validateMedianComponent(lsBVx, lsBVy, dthreshold);
                            if (bOutlier) {
                                lsIOutlier.add(I);
                                double xvel = 0.0;
                                double yvel = 0.0;
                                double distWeight = 0.0;
                                for (int k = 0; k < lsBVx.size(); k++) {
                                    double dDistance = (double) lITimeDiff.get(k);
                                    xvel += (lsBVx.get(k) / dDistance);
                                    yvel += (lsBVy.get(k) / dDistance);
                                    distWeight += (1 / dDistance);
                                }
                                ldVxVy.get(0).add(xvel / distWeight);
                                ldVxVy.get(1).add(yvel / distWeight);
                            }
                        }
                    }
                    for (int k = 0; k < lsIOutlier.size(); k++) {
//                        System.out.println("Before "+lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVx+" "+lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVy);
                        lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVx = ldVxVy.get(0).get(k);
                        lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVy = ldVxVy.get(1).get(k);
//                        System.out.println("After "+lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVx+" "+lsGrids.get(lsIOutlier.get(k)).oaContent[i][j].dVy);
                    }
                }
            }
        }
        // Denoising 

        int ph = 0;
//        buildLookUp();
    }

    @Override
    public void setImage(BufferedImage bi) {
//        buildLookUp();
    }

    @Override
    public Object[] getResults() {
        return new Object[]{};
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
        this.loSettings.add(new SettingObject("Validate", "tivPIVValidateVectors", true, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Type", "tivPIVValidationType", "MedianComp", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Threshold", "tivPIVValThreshold", 1.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("StampSize", "tivPIVValTimeStampSize", 2, SettingObject.SettingsType.Integer));
    }

    @Override
    public void buildClusters() {
        SettingsCluster validation = new SettingsCluster("Validation",
                new String[]{"tivPIVValidateVectors", "tivPIVValThreshold", "tivPIVValTimeStampSize"}, this);
        validation.setDescription("Validation of output vectors");
        lsClusters.add(validation);

    }

    private enum NameSpaceProtocol1DResults implements NameSpaceProtocolResults1D {
        avgx, avgy, tkeX, tkey
    }

}

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.velocimetry.primitives;


import com.tivconsultancy.opentiv.math.primitives.Vector;
import com.tivconsultancy.opentiv.physics.interfaces.Trackable;
import com.tivconsultancy.opentiv.physics.interfaces.Velocity;
import com.tivconsultancy.opentiv.velocimetry.iterativemethods.LostVelocity;
import com.tivconsultancy.opentiv.velocimetry.iterativemethods.TwoTrackablePairCriteria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class TrackAblePair {

    public Trackable oVFrame1;
    public Trackable oVFrame2;
    public Vector vDisplacement;

    public Double dCoeffOld;
    public Double dCoeffNewNotNormalized;
    public Double dCoeffNew;

    public TrackAblePair(Trackable oVFrame1, Trackable oVFrame2) {
        this.oVFrame1 = oVFrame1;
        this.oVFrame2 = oVFrame2;
        if (oVFrame2 != null) {
            vDisplacement = new Vector(oVFrame1.getPosition(), oVFrame2.getPosition());
        } else {
            vDisplacement = null;
        }
    }

    public void initializeCoeffBernahrdAndThompson(double dM) {
        dCoeffOld = 1.0 / (dM + 1.0);
        dCoeffNewNotNormalized = 1.0 / (dM + 1.0);
        dCoeffNew = 1.0 / (dM + 1.0);
    }

    public void resetCoeff() {
        this.dCoeffOld = new Double(this.dCoeffNew.doubleValue());
        this.dCoeffNewNotNormalized = 0.0;
    }

    public void updateCoeffBernahrdAndThompson(List<TrackAblePair> loLikelyPairs, double A, double B) {
        if (loLikelyPairs.isEmpty()) {
            this.dCoeffNewNotNormalized = 0.0;
        } else {
            Double dSummation = 0.0;
            for (TrackAblePair ovp : loLikelyPairs) {
                dSummation = dSummation + ovp.dCoeffOld;
            }
            this.dCoeffNewNotNormalized = this.dCoeffOld * (A + (B * dSummation));
        }
    }

    public void normalizeCoeffBarnarddAndThompson(List<TrackAblePair> loAllPairs, LostVelocity oLostVelo) {
        Double dSummation = 0.0;
        for (TrackAblePair ovp : loAllPairs) {
            if (ovp.oVFrame1.equals(this.oVFrame1)) {
                dSummation = dSummation + ovp.dCoeffNewNotNormalized;
            }
        }
        this.dCoeffNew = this.dCoeffNewNotNormalized / (dSummation + oLostVelo.dCoeffOld);
    }

    public List<TrackAblePair> getPossiblePairsBarnardAndThompson(List<List<TrackAblePair>> lloPairs, Double dRs, Double dRc) {
        List<TrackAblePair> loPossibleVectors = new ArrayList<>();
        for (List<TrackAblePair> lovp : lloPairs) {
            for (TrackAblePair ovp : lovp) {
                if (this.oVFrame2 != null && ovp.oVFrame2 != null && this.oVFrame1!=this.oVFrame2 && ovp.oVFrame1!=ovp.oVFrame2 && this.oVFrame1!=ovp.oVFrame1 && this.oVFrame1.getPosition().getNorm(ovp.oVFrame1.getPosition()) < dRs && TwoTrackablePairCriteria.BernahrdAndThompson(this, ovp) < dRc) {
                    loPossibleVectors.add(ovp);
                }
            }
        }
//        loPossibleVectors.add(this);
        return loPossibleVectors;
    }

    public List<TrackAblePair> getPossiblePairsBarnardAndThompsonAdjusted(List<List<TrackAblePair>> lloPairs, Double dRs, Double dRc, double dMaxVelo) {
        List<TrackAblePair> loPossibleVectors = new ArrayList<>();
        for (List<TrackAblePair> lovp : lloPairs) {
            for (TrackAblePair ovp : lovp) {
                if (this.oVFrame2 != null && ovp.oVFrame2 != null && this.oVFrame1!=this.oVFrame2 && ovp.oVFrame1!=ovp.oVFrame2 && this.oVFrame1!=ovp.oVFrame1 && this.oVFrame1.getDistance(ovp.oVFrame1) < dRs && this.oVFrame1.getDistance(this.oVFrame2) < dMaxVelo && TwoTrackablePairCriteria.BernahrdAndThompson(this, ovp) < dRc) {                
                    loPossibleVectors.add(ovp);
                }
            }
        }
//        loPossibleVectors.add(this);
        return loPossibleVectors;
    }

    public List<TrackAblePair> getPossiblePairsZ(List<List<TrackAblePair>> lloPairs, Double dRsSearchRadius, Double dRc, Velocity oGuess, Double C1, Double C2) {
        List<TrackAblePair> loPossibleVectors = new ArrayList<>();
        for (List<TrackAblePair> lovp : lloPairs) {
            for (TrackAblePair ovp : lovp) {
                //if (this.oVFrame2 != null && ovp.oVFrame2 != null && this.oVFrame1!=this.oVFrame2 && ovp.oVFrame1!=ovp.oVFrame2 && this.oVFrame1!=ovp.oVFrame1 && this.oVFrame1.getPosition().SecondCartesian(ovp.oVFrame1.getPosition()) < dRsSearchRadius && TwoTrackablePairCriteria.Z0(this, ovp, oGuess, C1, C2) < dRc) {
                if (this.oVFrame2 != null && ovp.oVFrame2 != null && this.oVFrame1!=this.oVFrame2 && ovp.oVFrame1!=ovp.oVFrame2 && this.oVFrame1.getPosition().getNorm(ovp.oVFrame1.getPosition()) < dRsSearchRadius && TwoTrackablePairCriteria.Z0(this, ovp, oGuess, C1, C2) < dRc) {
                    loPossibleVectors.add(ovp);
                }
            }          
        }
//        loPossibleVectors.add(this);
        return loPossibleVectors;
    }
    
    public List<TrackAblePair> getPossiblePairsZAdjusted(List<List<TrackAblePair>> lloPairs, Double dRsSearchRadius, Double dRc, Velocity oGuess, Double C1, Double C2, double dMaxVelo) {
        List<TrackAblePair> loPossibleVectors = new ArrayList<>();
        for (List<TrackAblePair> lovp : lloPairs) {
            for (TrackAblePair ovp : lovp) {
                if (this.oVFrame2 != null && ovp.oVFrame2 != null && this.oVFrame1!=this.oVFrame2 && ovp.oVFrame1!=ovp.oVFrame2 && this.oVFrame1!=ovp.oVFrame1 && this.oVFrame1.getPosition().getNorm(ovp.oVFrame1.getPosition()) < dRsSearchRadius && this.oVFrame1.getPosition().getNorm(this.oVFrame2.getPosition()) < dMaxVelo && TwoTrackablePairCriteria.Z0(this, ovp, oGuess, C1, C2) < dRc) {
                    loPossibleVectors.add(ovp);
                }
            }
        }
        loPossibleVectors.add(this);
        return loPossibleVectors;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.datamodels;

import com.tivconsultancy.opentiv.highlevel.protocols.Result1D;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class IndexedResults {

//    Map<String, List<Double>> database = new HashMap<>();
    private LookUp<Result1D> resultsOverTime;
    private List<Refreshable> ObjectsToCallOnChange = new ArrayList<>();
//    protected Method coupledMethod;

    public IndexedResults() {
        initDatabase();
    }

    private void initDatabase() {
        resultsOverTime = new LookUp<>();
    }

    public void addObjectToRefresh(Refreshable ref) {
        ObjectsToCallOnChange.add(ref);
    }

    private void replaceOrAdd(String name, Result1D res) {
        if (resultsOverTime.get(name) == null) {
            resultsOverTime.add(new NameObject<>(name, res));
        } else {
            resultsOverTime.set(name, res);
        }
        refreshObjects();

    }

    public void replaceOrAdd(int i, Result1D res) {
        this.replaceOrAdd(i + "", res);
    }

    private void add(String name, Result1D res) {
        resultsOverTime.add(new NameObject<>(name, res));
        refreshObjects();
    }

    public void add(int i, Result1D res) {
        this.add(i + "", res);
    }

    public Result1D get(String s) {
        return resultsOverTime.get(s);
    }

    public Result1D get(int i) {
        return this.get(i + "");
    }
    
    public Result1D getIndexBased(int i) {
        return resultsOverTime.get(i).o;
    }
    
    public int getEntry(int i) {
        return Integer.valueOf(resultsOverTime.get(i).name);
    }
        
    public Double getIndexBased(int i, String name) {
        if(this.getIndexBased(i) == null || this.getIndexBased(i).getRes(name) == null){
            return 0.0;
        }
        return this.getIndexBased(i).getRes(name);
    }

    public Double get(int i, String name) {
        if(this.get(i) == null || this.get(i).getRes(name) == null){
            return 0.0;
        }
        return this.get(i).getRes(name);
    }

    public int getSize() {
        return resultsOverTime.getSize();
    }

    public void refreshObjects() {
        Platform.runLater(new Runnable() {

            public void run() {
                for (Refreshable r : ObjectsToCallOnChange) {
                    r.refresh();
                }
            }
        });

    }

}

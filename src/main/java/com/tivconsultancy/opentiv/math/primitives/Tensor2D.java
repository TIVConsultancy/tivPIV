/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.math.primitives;


/**
 *
 * @author Thomas
 */
public class Tensor2D {
    
    public double a11 = 0.0;
     public double a12 = 0.0;
     public double a21 = 0.0;
     public double a22 = 0.0;
    
     public Tensor2D(){
         
     }
     
     public OrderedPair product(OrderedPair op){
         double dX = op.x * a11 + op.y * a12;
         double dY = op.x * a21 + op.y * a22;
         
         return (new OrderedPair(dX, dY));
     }
     
     public static Tensor2D getRotTensor(double dAlpha){
         
         Tensor2D oRotX = new Tensor2D();
         
         oRotX.a11 = Math.cos(dAlpha);
         oRotX.a12 = (-1.0) * Math.sin(dAlpha);
         oRotX.a21 = Math.sin(dAlpha);
         oRotX.a22 = Math.cos(dAlpha);

         
         return oRotX;
         
     }
     
     
    
}

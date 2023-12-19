package Model;


import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.awt.image.BufferedImage;


public class Plate extends Shape {
    private boolean isHorizontalOnly;
    private GameObject carrier;
    private int carrierHorizontalPos;
    public Plate(int x,int y,BufferedImage image,ShapeType type){
        super(x,y,image,type);
        isHorizontalOnly=false;
        carrier=null;
    }
    @Override
    public void setY(int y){
        if(!isHorizontalOnly)
            super.setY(y);
    }
    @Override
    public void setX(int x){
        if(carrier==null)
            super.setX(x);
        else{
            int dX=carrier.getX()-carrierHorizontalPos;
            if(dX!=0) {
                super.setX(super.getX() + dX);
                carrierHorizontalPos=carrier.getX();
            }
        }

    }

    public void setCarrier(GameObject carrier) {
        this.carrier = carrier;
        carrierHorizontalPos=carrier.getX();
    }

    public void setHorizontalOnly(boolean horizontalOnly) {
        isHorizontalOnly = horizontalOnly;
    }
}

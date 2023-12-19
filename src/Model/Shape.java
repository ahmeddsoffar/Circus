package Model;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class Shape implements GameObject {

    private static final int MAX_MSTATE = 1;
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private int x;
    private int y;
    private boolean isVisible;
    private ShapeType type;

    public Shape(int x, int y,BufferedImage image,ShapeType type) {
        this.x = x;
        this.y = y;
        this.isVisible = true;
        spriteImages[0]=image;
        this.type=type;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x=x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y=y;
    }

    @Override
    public int getWidth() {
       return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean visiblity){
        this.isVisible=visiblity;
    }
    @Override
    public  BufferedImage[] getSpriteImages(){
        return spriteImages;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }
}

package Model;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackGround implements GameObject {

    private static final int MAX_MSTATE = 1;
    private int x;
    private int y;
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    public BackGround(int x,int y,String path){
        this.x=x;
        this.y=y;
        try {
            spriteImages[0]= ImageIO.read(BackGround.class.getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {

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
        return true;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }
}

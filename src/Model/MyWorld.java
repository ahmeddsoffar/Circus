package Model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;




public class MyWorld implements World {
    private static final int MAX_TIME = 1 * 60 * 1000; // 1 minute
    private int score = 0;
    private final long startTime = System.currentTimeMillis();
    private long creationTime;
    private final int width=1024;
    private final int height=950;
    private final List<GameObject> constant = new LinkedList<>();
    private final List<GameObject> moving = new LinkedList<>();
    private final List<GameObject> control = new LinkedList<>();
    private final List<GameObject> left = new ArrayList<>();
    private final List<GameObject> right = new ArrayList<>();
    private int timeDelay=2000;
    private static World myWorld=null;
    private Strategy speedStrategy;
    private MyWorld() {
        constant.add(new BackGround(0,0,"/PNGS/ff.jpg"));
        creationTime=System.currentTimeMillis()-timeDelay;
        createPlate(width,height);
        control.add(new ShapeFactory().createShape(width/3,(int)(height*0.7), "/PNGS/Clown.png", ShapeType.Clown));
    }
    public static World getInstance()
    {
        if(myWorld==null)
        {   myWorld=new MyWorld();
            return myWorld;
        }
        else
            return myWorld;
    }
    private void createPlate( int screenWidth, int screenHeight) {
        ShapeFactory factory=new ShapeFactory();
        int max = 4;
        int min = 1;
        int range = max - min + 1;
        int rand;
        rand = (int) (Math.random() * range) + min;
        creationTime+=timeDelay;

        switch (rand) {
            case 1:
                moving.add(factory.createShape((int) (Math.random() * (screenWidth - 100)), -50, "/PNGS/redplate2.png", ShapeType.REDPLATE));
                break;
            case 2:
                moving.add(factory.createShape((int) (Math.random() * (screenWidth - 100)),  -50, "/PNGS/blueplate.png", ShapeType.BLUEPLATE));
                break;
            case 3:
                moving.add(factory.createShape((int) (Math.random() * (screenWidth - 100)),  -50, "/PNGS/greenplate.png", ShapeType.GREENPLATE));
                break;
            case 4:
                moving.add(factory.createShape((int) (Math.random() * (screenWidth - 100)),  -50, "/PNGS/bomb.png", ShapeType.BOMB));
                break;
        }

    }
    
    private boolean intersectionWithClown(GameObject m){
        Clown clown=(Clown) control.get(0);
        return m.getX()>=clown.getX() && m.getX()<=clown.getX()+clown.getWidth()&& (m.getY() >= clown.getY() - 40 && m.getY() <= clown.getY() - 20);
    }
    
    private boolean intersectLeft(GameObject o1) {
        boolean intersect=false;
        Clown clown=(Clown)control.get(0);
        if(left.isEmpty())
            intersect = o1.getX()+o1.getWidth() <= clown.getX()+150 && o1.getX()+30 >= clown.getX()  && (o1.getY() >= clown.getY() - 40 && o1.getY() <= clown.getY() - 20);
        else
        {
            intersect = o1.getX()+o1.getWidth() <= clown.getX()+150 && o1.getX()+30 >= clown.getX()  && (o1.getY() >= left.get(left.size() - 1).getY() - 40 && o1.getY() <= left.get(left.size() - 1).getY() - 20);
        }

        return intersect;
    }
    
    
    
    private boolean intersectRight(GameObject o1) {
        boolean intersect=false;
        Clown clown=(Clown)control.get(0);
        if(right.isEmpty())
            intersect = o1.getX()+30 >= clown.getX()+clown.getWidth()/2+50 && o1.getX()+30<= clown.getX()+clown.getWidth()-30 && (o1.getY() >= clown.getY() - 40 && o1.getY() <= clown.getY() - 20);
        else
        {
            intersect = o1.getX()+30 >= clown.getX()+ clown.getWidth()/2+50 && o1.getX()+30<= clown.getX() + clown.getWidth()-30 && (o1.getY() >= right.get(right.size() - 1).getY() - 40 && o1.getY() <= right.get(right.size() - 1).getY() - 20);
        }
        return intersect;
    }
    
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;
        Clown clown=(Clown)control.get(0);
        if (!timeout) {
            GameObject temp = null;
            if (System.currentTimeMillis() - creationTime >= timeDelay) {
                createPlate(width, height);
            }
            Iterator<GameObject> iterator= moving.iterator();
            while(iterator.hasNext()) {
                GameObject m=iterator.next();
                m.setY(m.getY() + speedStrategy.adjustSpeed());
                if(!adjustForBomb(m)){
                    if( adjustPlateForLeft(m)|| adjustPlateForRight(m))
                        iterator.remove();
                }
                else{
                    iterator.remove();
                }
            }

        }

        return !timeout;

    }
    public void setFallingSpeed(Strategy strategy) {
        this.speedStrategy = strategy;
    }
    private boolean adjustForBomb(GameObject m){
        if (m instanceof Bomb &&(intersectionWithClown(m)||intersectLeft(m)||intersectRight(m)))
        {
            score--;
            return true;
        }
        return false;
    }
    private boolean adjustPlateForLeft(GameObject m) {
        Clown clown = (Clown) control.get(0);
        if (intersectLeft(m)) {
            if (left.isEmpty()) {
                m.setX(clown.getX() - 10);
                m.setY(clown.getY());
            } else {
                m.setX(left.get(left.size() - 1).getX());
                m.setY(left.get(left.size() - 1).getY() - left.get(left.size() - 1).getHeight() / 6);
            }

            left.add(m);
            control.add(m);
            Shape shape = (Shape) m;
            ((Plate) m).setHorizontalOnly(true);
            ((Plate) m).setCarrier(control.get(0));
            checkPlate(left);
            return true;
        }
        return false;
    }
    private boolean adjustPlateForRight(GameObject m){
        Clown clown = (Clown) control.get(0);
        if (intersectRight(m)) {
            if (right.isEmpty()) {
                m.setX(clown.getX() + 190);
                m.setY(clown.getY());
            } else {
                m.setX(right.get(right.size() - 1).getX());
                m.setY(right.get(right.size() - 1).getY() - right.get(right.size() - 1).getHeight() / 6);
            }
            right.add(m);
            control.add(m);
            Shape shape = (Shape) m;
            ((Plate) m).setHorizontalOnly(true);
            ((Plate) m).setCarrier(control.get(0));
            checkPlate(right);
            return true;
        }
        return false;
    }
    private void checkPlate(List<GameObject> side) {
        if (side.size() >= 3) {
            Plate plate1=(Plate)  side.get(side.size() - 1);
            Plate plate2=(Plate) side.get(side.size() - 2);
            Plate plate3 = (Plate) side.get(side.size() - 3);
            if(plate1.getType()==plate2.getType()&&plate2.getType()==plate3.getType())
            {
                side.remove(plate1);
                side.remove(plate2);
                side.remove(plate3);
                control.remove(plate1);
                control.remove(plate2);
                control.remove(plate3);
                score++;
            }
        }
    }
    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getStatus() {
        return "Please Use Arrows To Move   |   Score=" + score + "   |   Time="
                + Math.max(0, (MAX_TIME - (System.currentTimeMillis()-startTime))/1000);
    }


    public List<GameObject> getLeft() {
        return left;
    }

    public List<GameObject> getRight() {
        return right;
    }
}
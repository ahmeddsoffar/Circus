package Model;

public class ShapeFactory {

    public Shape createShape(int x,int y,String path,ShapeType type){
        switch(type){
            case REDPLATE : return new Plate(x,y,Flyweight.getAppropriateImage(path),type);
            case BLUEPLATE: return new Plate(x,y,Flyweight.getAppropriateImage(path),type);
            case GREENPLATE:return new Plate(x,y,Flyweight.getAppropriateImage(path),type);
            case Clown:return new Clown(x,y,Flyweight.getAppropriateImage(path),type);
            case BOMB:return new Bomb(x,y,Flyweight.getAppropriateImage(path),type);
            default:return null;
        }

    }
}

package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Flyweight {
    private static HashMap<String, BufferedImage> map=new HashMap<>();

    public static BufferedImage getAppropriateImage(String path){
        if(map.get(path)!=null)
            return map.get(path);
        else {
            BufferedImage image;
            try {
                 image= ImageIO.read(Flyweight.class.getResourceAsStream(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            map.put(path,image);
            return image;
        }
    }
}

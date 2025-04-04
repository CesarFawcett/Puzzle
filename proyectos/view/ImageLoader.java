package view;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public BufferedImage loadImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            throw new IOException("Archivo no encontrado: " + imageFile.getAbsolutePath());
        }
        return ImageIO.read(imageFile);
    }
    
    public BufferedImage scaleImage(BufferedImage original, int targetWidth, int targetHeight) {
        double ratio = Math.min(
            (double) targetWidth / original.getWidth(),
            (double) targetHeight / original.getHeight()
        );
        
        int newWidth = (int) (original.getWidth() * ratio);
        int newHeight = (int) (original.getHeight() * ratio);
        
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();
        
        return scaledImage;
    }
}
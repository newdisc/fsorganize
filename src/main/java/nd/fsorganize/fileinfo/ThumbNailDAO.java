package nd.fsorganize.fileinfo;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.util.FSOrganizeException;

public class ThumbNailDAO {
    private static final Logger log = LoggerFactory.getLogger(ThumbNailDAO.class);
    private static final int TWIDE = 150;
    private static final int THEIG = 150;
    public static byte[] getThumbNail(final String fname) {
        try {
            BufferedImage bi = ImageIO.read(new File(fname));
            if (null == bi) {
                return null;
            }
            bi = ThumbNailDAO.scale(bi);
            /*
            final String tfilename = fname.substring(0, fname.length() - 4) + ".thumb.jpg";
            ImageIO.write(bi, "jpg", new File(tfilename));
            */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            final FSOrganizeException a = FSOrganizeException.raiseAndLog("Could not read/write image : " + fname, e, log);
            throw a;
        }
    }
    private static BufferedImage scale(BufferedImage source) {
        double ratio = (double)TWIDE / (double)source.getWidth();
        double yratio = (double)TWIDE / (double)source.getHeight();
        BufferedImage bi = ThumbNailDAO.getCompatibleImage(TWIDE, THEIG);
        Graphics2D g2d = bi.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(ratio,yratio);
        g2d.drawRenderedImage(source, at);
        g2d.dispose();
        return bi;
      }

    private static BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        return image;
      }    
    private ThumbNailDAO() {}
}

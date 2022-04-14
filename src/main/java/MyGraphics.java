import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Dmitriy on 29.05.2018.
 */
public class MyGraphics {
    Image image;

    public final int GW = 340;
    public final int GH = 240;

    public MyGraphics( ){
        image = new BufferedImage(GW,GH,BufferedImage.TYPE_INT_ARGB);
    }

    public void drawGraphic(ArrayList<Integer> data){
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,GW,GH);

        g.setColor(Color.BLACK);
        g.drawString("0", 10, GH - 10);
        g.drawString("100", 0, GH - 220);
        g.drawString("t", 320, GH - 0);
        g.drawString("50", 0, GH - 120);
        //
        g.setStroke(new BasicStroke(2));
        g.drawLine(20, GH - 240, 20, GH - 20 );
        g.drawLine(20, GH - 20, GW, GH - 20 );
        g.setStroke(new BasicStroke(1));
        //
        g.drawLine(15, GH - 220, 25, GH - 220);
        g.drawLine(320, GH - 15, 320, GH - 25);
        //
        g.drawLine(15, GH-120, 25, GH - 120);

        for (int i = 1; i < 10; i++) {
            g.drawLine(
                    20 + (i-1)*30,      GH - data.get(i-1)*2 - 20,//point 1
                    20 + (i-1)*30 + 30, GH - data.get(i)*2 - 20   //point 2
            );
        }
    }
}

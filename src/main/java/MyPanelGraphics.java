import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.util.FormatUtil;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dmitriy on 29.05.2018.
 */
public class MyPanelGraphics extends JPanel {

    MyGraphics graphicsRAM;
    ArrayList<Integer> dataRAM;

    MyGraphics graphicsCPU;
    ArrayList<Integer> dataCPU;

    JButton gcButton;

    public MyPanelGraphics() {
        super(null);
        setPreferredSize(new Dimension(640,640));
        setVisible(true);

        gcButton = new JButton("Запустить сборщик мусора Java");
        gcButton.setBounds(0, 540, 640, 40);
        gcButton.addActionListener(e -> {
            gcButton.setText("Очистка...");
            System.gc();
            gcButton.setText("Запустить сборщик мусора Java");
        });

        add(gcButton);
        dataRAM = new ArrayList<>();
        dataCPU = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataCPU.add(i,0);
            dataRAM.add(i,0);
        }
        graphicsRAM = new MyGraphics();
        graphicsCPU = new MyGraphics();
    }

    @Override
    public void paint(Graphics g){
        SystemInfo si = new SystemInfo();
        CentralProcessor p = si.getHardware().getProcessor();
        GlobalMemory m = si.getHardware().getMemory();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,640,640);

        refresh(m, p);

        graphicsRAM.drawGraphic(dataRAM);
        graphicsCPU.drawGraphic(dataCPU);

        g.drawImage(graphicsRAM.image, 10,10, 340, 240, null);
        g.setColor(Color.BLACK);
        g.drawString(("Загрузка ОЗУ: " + FormatUtil.formatBytes((m.getTotal() - m.getAvailable())) + "/"
                + FormatUtil.formatBytes(m.getTotal())) , 370, 120);
        g.drawImage(graphicsCPU.image, 10,260, 340, 240, null );
        g.drawString("Загрузка CPU: " + String.format( "%.1f" ,p.getSystemCpuLoad() * 100) + "%/ 100%", 390, 360);

        paintComponents(g);
    }

    public void refresh(GlobalMemory m, CentralProcessor p){
        dataRAM.remove(0);
        dataCPU.remove(0);
        dataRAM.add(9,  100 - (int)((double)m.getAvailable()/ (double)m.getTotal()*100) );
        dataCPU.add(9, (int)(p.getSystemCpuLoad() * 100));
    }
}

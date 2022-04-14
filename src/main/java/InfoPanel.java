import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Dmitriy on 30.05.2018.
 */
public class InfoPanel extends JPanel {
    public InfoPanel() {
        super(null);
        setPreferredSize(new Dimension(640,640));
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cp = hal.getProcessor();
        ComputerSystem cs = hal.getComputerSystem();
        OperatingSystem os = si.getOperatingSystem();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,640,680);

        g.setColor(Color.BLACK);

        g.setFont(new Font("default",Font.BOLD,14));
        g.drawString("Данные ОС: ",20, 40);
        g.setFont(new Font("default",Font.PLAIN,14));

        g.drawString("Семейство ОС: " +
                os.getFamily(), 20, 70);
        g.drawString("Производитель ОС: " +
                os.getManufacturer(), 20, 100);
        g.drawString("Версия ОС: " +
                os.getVersion(), 20, 130);

        g.setFont(new Font("default",Font.BOLD,14));
        g.drawString("Данные ЦП: ",20, 160);
        g.setFont(new Font("default",Font.PLAIN,14));

        g.drawString("Имя ЦП: " +
                cp.getName() , 20, 190);
        g.drawString("Идентификатор ЦП: " +
                cp.getIdentifier(), 20, 220);
        g.drawString("Производитель: " +
                cp.getVendor(), 20, 250);
        g.drawString("Количество логических процессоров: " +
                cp.getLogicalProcessorCount(), 20, 280);
        g.drawString("Количество физических процессоров: " +
                cp.getPhysicalProcessorCount(), 20, 310);

        g.setFont(new Font("default",Font.BOLD,14));
        g.drawString("Общие данные компьютера: ",20, 340);
        g.setFont(new Font("default",Font.PLAIN,14));

        g.drawString("Модель компьютера: " +
                cs.getModel(), 20, 370);
        g.drawString("Производитель материнской платы: " +
                cs.getBaseboard().getManufacturer(), 20, 400);
        g.drawString("Версия материнской платы: " +
                cs.getBaseboard().getVersion(), 20, 430);
        g.drawString("Версия прошивки: " +
                cs.getFirmware().getVersion(), 20, 460);
        g.drawString("Имя прошивки: " +
                cs.getFirmware().getName(), 20, 490);
        g.drawString("Производитель компьютера: " +
                cs.getManufacturer(), 20, 520);
        g.drawString("Серийный номер компьютера: " +
                cs.getSerialNumber(), 20, 550);

        paintComponents(g);
    }
}

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dmitriy on 30.05.2018.
 */
class AboutDialog extends JDialog
{
    JPanel panel;
    ImageIcon image;
    JButton close;

    final int SIDE = 360;

    public AboutDialog(JFrame owner)
    {
        super(owner, "Информация", true);
        setSize(SIDE, SIDE);
        setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(SIDE, SIDE));
        panel.setBounds(0,0,SIDE,SIDE);

        try
        {
            image = new javax.swing.ImageIcon(getClass().getResource("pic.jpg"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        // При нажатии кнопки диалогове окно "закрывается".
        close = new JButton("Закрыть");
        panel.add(close);
        close.setBounds(20, 280, 300, 35);
        close.addActionListener( e -> {
            setVisible(false);
        });

        add(panel);
    }

    public void paint(Graphics g){
        paintComponents(g);

        g.setColor(Color.WHITE);
        g.fillRect(0,0,SIDE, SIDE);

        g.drawImage(image.getImage(), 20,20,144,192,null);

        g.setColor(Color.BLACK);
        g.drawString("Автор программы:", 175, 60);
        g.drawString("студент группы АЕ-155", 175, 90);
        g.drawString("Салагор Дмитрий Викторович",175,120);
        g.drawString("FactorialD@gmail.com",175,150);
        g.drawString("Данный диспетчер задач был разработан",20,240);
        g.drawString("для РГР по системному программированию",20,270);
        g.drawString("Преподаватель: Андриянов В. А.",20,300);
    }
}

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import static java.lang.Runtime.getRuntime;

public class MyFrame extends JFrame{

    public Timer t;

    public final SystemInfo si = new SystemInfo();
    public final OperatingSystem os =  si.getOperatingSystem();
    public final HardwareAbstractionLayer hal = si.getHardware();
    //
    public JTabbedPane tabbedPane;
    //
    public JDialog dialog;
    //Поля для вкладки процессов
    public JPanel processesTab;
    public JScrollPane scrollPane;
    public JTable processTable;
    public JPopupMenu popupMenu;
    public List<ProcessInfo> infos;
    public JButton refreshButton;
    public ArrayList<String> tabMap = new ArrayList<>();
    //
    public MyPanelGraphics panelGraphics;
    //
    public InfoPanel infoPanel;


    public MyFrame(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        setMyMenu();

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0,0,640,700);

        //long ml = System.currentTimeMillis();
        refreshProcessTab();
        //System.out.println("Время набора таблицы: " + (System.currentTimeMillis()-ml)/1000);

        tabMap.add(tabbedPane.getTabCount(), "Graphics");
        panelGraphics = new MyPanelGraphics();
        tabbedPane.addTab("Graphics", panelGraphics );
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == tabMap.indexOf("Graphics")) {
                t = new Timer(3000, e1 -> {
                    //System.out.println("tick");
                    panelGraphics.repaint();
                });
                t.start();
            }
        });

        infoPanel = new InfoPanel();
        tabMap.add( tabbedPane.getTabCount(), "Info");
        tabbedPane.addTab("Info", infoPanel);

        add(tabbedPane);
        setVisible(true);

    }

    public void paint(Graphics g) {paintComponents(g);}

    public void refreshProcessTab(){
        GlobalMemory m = hal.getMemory();

        infos = new ArrayList<>();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(os.getProcessCount(), OperatingSystem.ProcessSort.CPU));

        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);

            infos.add(new ProcessInfo(p, m));

            infos.get(i).processID = String.valueOf( p.getProcessID());
            infos.get(i).percentCPU = String.format("%5.1f", 100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime());
            infos.get(i).percentMem = String.format("%4.1f", 100d * p.getResidentSetSize() / m.getTotal());
            infos.get(i).sizeRMem = String.format("%9s", FormatUtil.formatBytes(p.getResidentSetSize()));
            infos.get(i).name = p.getName();
        }

        TableModel model = new MyTableModel(infos);
        processTable = new JTable(model);

        popupMenu = new JPopupMenu();
        JMenuItem closeItem = new JMenuItem("Завершить процесс");
        popupMenu.add(closeItem);
        processTable.add(popupMenu);
        processTable.addMouseListener(new MouseListener() {//обработка событий связанных с таблицей процессов
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
            public void mouseReleased(MouseEvent event) {}
            public void mousePressed(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON3) {
                    Point point = event.getPoint();
                    int column = processTable.columnAtPoint(point);
                    int row = processTable.rowAtPoint(point);
                    processTable.setColumnSelectionInterval(column,column);
                    processTable.setRowSelectionInterval(row, row);
                    popupMenu.show(processTable, (int)point.getX(), (int)point.getY());
                    closeItem.addActionListener(e -> {
                        try {
                            getRuntime().exec("taskkill /F /IM " + infos.get(row).name);
                            ((MyTableModel)processTable.getModel()).removeRow(row);
                            refreshProcessTab();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            }
        });
        processTable.setBounds(0,0,640,640);

        scrollPane = new JScrollPane(processTable);
        scrollPane.setBounds(0,0,630,580);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(0,580,630,30);
        add(refreshButton);
        refreshButton.addActionListener(
                e -> refreshProcessTab());

        processesTab = new JPanel();
        processesTab.setLayout(null);
        processesTab.add(scrollPane);
        processesTab.add(refreshButton);

        if(tabMap.contains("Processes")){
            tabbedPane.removeTabAt(tabMap.indexOf("Processes"));
            tabMap.remove("Processes");
        }
        tabMap.add(tabbedPane.getTabCount(), "Processes");

        tabbedPane.addTab("Processes", processesTab);
        tabbedPane.setSelectedIndex(tabMap.lastIndexOf("Processes"));
    }

    public void setMyMenu(){
        dialog = new AboutDialog(this);
        dialog.setVisible(false);
        JMenuBar menubar = new JMenuBar();
        JMenu menuMain = new JMenu("Меню");
        JMenu menuOther = new JMenu("Другое");
        JMenuItem exitItem = new JMenuItem("Выход");
        menuMain.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> {
            dialog.setVisible(true);
        });
        menuOther.add(aboutItem);
        menubar.add(menuMain);
        menubar.add(menuOther);
        setJMenuBar(menubar);
    }

    public static void main(String[] args) {
        MyFrame mf = new MyFrame("DisplayerPCInfo", 640, 700);
    }
}

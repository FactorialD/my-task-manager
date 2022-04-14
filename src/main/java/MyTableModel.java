import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitriy on 28.05.2018.
 */
public class MyTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<ProcessInfo> infos;

    public MyTableModel(List<ProcessInfo> infos) {
        this.infos = infos;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    public int getColumnCount() {
        return 5;
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
    public void setValueAt(Object value, int rowIndex, int columnIndex) {}
    public void removeRow(int row){infos.remove(row);}
    public int getRowCount() {
        return infos.size();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "PID";
            case 1:
                return "%CPU";
            case 2:
                return "%MEM";
            case 3:
                return "RSS";
            case 4:
                return "Name";
        }
        return "";
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessInfo info = infos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return info.processID;
            case 1:
                return info.percentCPU;
            case 2:
                return info.percentMem;
            case 3:
                return info.sizeRMem;
            case 4:
                return info.name;
        }
        return "";
    }
}

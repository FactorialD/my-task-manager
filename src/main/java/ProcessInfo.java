import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.util.FormatUtil;

/**
 * Created by Dmitriy on 28.05.2018.
 */
public class ProcessInfo {

    String processID;
    String percentCPU;
    String percentMem;
    String sizeRMem;
    String name;

    public ProcessInfo(OSProcess p, GlobalMemory memory) {
            processID = String.format("%5d", p.getProcessID());
            percentCPU = String.format("%5.1f", 100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime());
            percentMem = String.format("%4.1f", 100d * p.getResidentSetSize() / memory.getTotal());
            sizeRMem = String.format("%9s", FormatUtil.formatBytes(p.getResidentSetSize()));
            name = p.getName();
    }
}



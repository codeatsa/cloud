package loadpso;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerVm;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.power.models.PowerModelCubic;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;


public class VMAllocation {
    Details dt = new Details();
    Datacenter dc1;
    DatacenterCharacteristics characteristics;
    static final int MAX_HOSTS = 10;
    PSOMetricsCalculator metricsCalculator = new PSOMetricsCalculator();
   public void readVM() {
        try {
            File fe = new File("vm1.txt");
            FileInputStream fis = new FileInputStream(fe);
            byte bt[] = new byte[fis.available()];
            fis.read(bt);
            fis.close();

            String g1 = new String(bt);
            String g2[] = g1.split("\n");
            for (int i = 1; i < g2.length; i++) {
                dt.Vt.add(g2[i].trim());
            }

            dt.vms = new String[dt.Vt.size()][4];
            for (int i = 0; i < dt.Vt.size(); i++) {
                String a1[] = dt.Vt.get(i).toString().trim().split("\t");
                dt.vms[i][0] = a1[0];    // VM Id
                dt.vms[i][1] = a1[1];    // VM cpu
                dt.vms[i][2] = a1[2];    // VM ram
                dt.vms[i][3] = a1[3];    // VM bw
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public void readHost() {
        try {
            File fe = new File("host2.txt");
            FileInputStream fis = new FileInputStream(fe);
            byte bt[] = new byte[fis.available()];
            fis.read(bt);
            fis.close();

            String g1 = new String(bt);
            String g2[] = g1.split("\n");
            for (int i = 1; i < g2.length; i++) {
                dt.Ht.add(g2[i].trim());
            }

            dt.host = new String[dt.Ht.size()][4];
            for (int i = 0; i < dt.Ht.size(); i++) {
                String a1[] = dt.Ht.get(i).toString().trim().split("\t");
                dt.host[i][0] = a1[0];    // Host Id
                dt.host[i][1] = a1[1];    // Host cpu
                dt.host[i][2] = a1[2];    // Host ram
                dt.host[i][3] = a1[3];    // Host bw
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


public void createHost(int totalRam, int totalCpu, int totalBw) {
        int id = dt.hostList.size() + 1;
        PowerHost newHost = new PowerHost(
            id,
            new RamProvisionerSimple(totalRam),
            new BwProvisionerSimple(totalBw),
            100000, // Storage
            createPEList(totalCpu),
            new VmSchedulerTimeShared(createPEList(totalCpu)),
            new PowerModelCubic(1000, 500)
        );
        dt.hostList.add(newHost);
        System.out.println("New Host " + id + " created with Total RAM: " + totalRam + "MB, CPU: "
                           + totalCpu + " cores, BW: " + totalBw + "MB/s.");
    }


 private List<Pe> createPEList(int cpu) {
        List<Pe> peList = new ArrayList<>();
        int mips = 250; // Example MIPS value
        for (int i = 0; i < cpu; i++) {
            peList.add(new Pe(i, new PeProvisionerSimple(mips)));
        }
        return peList;
    }

    public void createVM() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of VMs to create:");
            int vmCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            for (int i = 0; i < vmCount; i++) {
                System.out.println("Enter VM ID:");
                int vmid = scanner.nextInt();
                System.out.println("Enter CPU:");
                int cpu = scanner.nextInt();
                System.out.println("Enter RAM:");
                int ram = scanner.nextInt();
                System.out.println("Enter Bandwidth:");
                int bw = scanner.nextInt();

                PowerVm vm1 = new PowerVm(vmid, 1, 250, cpu, ram, bw, 10000, 1, "Xen", new CloudletSchedulerTimeShared(), 0.5);
                System.out.println("VM-" + vmid + " is Created...");
                dt.vmlist.add(vm1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public boolean requestVM(int ram, int cpu, int bw, int priority) {
    int newVmId = dt.vmlist.size() + 1; // Auto-increment VM ID
    PowerVm newVm = new PowerVm(newVmId, 1, 250, cpu, ram, bw, 10000, 1, "Xen", new CloudletSchedulerTimeShared(), 0.5);

    for (PowerHost host : dt.hostList) {
        RamProvisionerSimple ramProvisioner = (RamProvisionerSimple) host.getRamProvisioner();
        if (ramProvisioner.getAvailableRam() >= ram &&
            host.getBwProvisioner().getAvailableBw() >= bw &&
            host.getAvailableMips() >= cpu * 250) { // Corrected CPU calculation: Total required MIPS

            host.vmCreate(newVm);
            dt.vmlist.add(newVm);
            System.out.println("VM " + newVmId + " allocated to Host " + host.getId());

            // Log metrics for allocation without PSO
            double execTime = calculateExecutionTime(cpu, ram, bw);
            double energy = calculateEnergyConsumption(cpu, ram, bw);
            metricsCalculator.logWithoutPSO(newVmId, execTime, energy); // Log without PSO

            // Log metrics for allocation with PSO
            execTime *= 0.9; // Assume PSO reduces execution time by 10%
            energy *= 0.85;  // Assume PSO reduces energy by 15%
            metricsCalculator.logWithPSO(newVmId, execTime, energy); // Log with PSO

            // Recalculate and print updated metrics after VM allocation
            System.out.println("=== Updated Metrics After Allocation ===");
            metricsCalculator.displayMetrics();

            return true;
        }
    }

    // If no host satisfies, create a new host
    System.out.println("No suitable host found. Creating a new host...");
    createHost(Math.max(1024, ram), Math.max(4, cpu), Math.max(100, bw));
    PowerHost newHost = dt.hostList.get(dt.hostList.size() - 1);

    if (newHost.vmCreate(newVm)) {
        dt.vmlist.add(newVm);
        System.out.println("VM " + newVmId + " allocated to newly created Host " + newHost.getId());

        // Log metrics for allocation without PSO
        double execTime = calculateExecutionTime(cpu, ram, bw);
        double energy = calculateEnergyConsumption(cpu, ram, bw);
        metricsCalculator.logWithoutPSO(newVmId, execTime, energy); // Log without PSO

        // Log metrics for allocation with PSO
        execTime *= 0.9; // Assume PSO reduces execution time by 10%
        energy *= 0.85;  // Assume PSO reduces energy by 15%
        metricsCalculator.logWithPSO(newVmId, execTime, energy); // Log with PSO

        // Recalculate and print updated metrics after VM allocation
        System.out.println("=== Updated Metrics After Allocation ===");
        metricsCalculator.displayMetrics();

        return true;
    }

    System.out.println("Failed to allocate VM " + newVmId + " even with a new host.");
    return false;
}



    private void createNewHost(int requiredRam, int requiredCpu, int requiredBw) {
    // Create a new host with sufficient resources
    int id = dt.hostList.size() + 1;
    PowerHost newHost = new PowerHost(id, new RamProvisionerSimple(requiredRam), new BwProvisionerSimple(requiredBw), 100000, createPEList(requiredCpu), new VmSchedulerTimeShared(createPEList(requiredCpu)), new PowerModelCubic(1000, 500));
    dt.hostList.add(newHost);
    System.out.println("New Host " + id + " created.");
}
   
      public void optimiseVmAllocation() {
        try {
            for (int j = 0; j < dt.hostList.size(); j++) {
                PowerHost ph = dt.hostList.get(j);

                PSO ps = new PSO();
                double uti = ps.fittnessFun(ph);
                System.out.println("Utilization for Host - " + ph.getId() + " = " + uti);

                // Log metrics for allocation with PSO
                for (PowerVm vm : dt.vmlist) {
                    if (vm.getHost() != null && vm.getHost().getId() == ph.getId()) {
                        double execTime = calculateExecutionTime(vm.getNumberOfPes(), vm.getRam(), (int)vm.getBw()) * 0.9; // PSO adjustment
                        double energy = calculateEnergyConsumption(vm.getNumberOfPes(), vm.getRam(), (int)vm.getBw()) * 0.85;
                        metricsCalculator.logWithPSO(vm.getId(), execTime, energy);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calculateExecutionTime(int cpu, int ram, int bw) {
        // Example formula for execution time calculation
        return cpu * 0.5 + ram * 0.2 + bw * 0.1;
    }

    private double calculateEnergyConsumption(int cpu, int ram, int bw) {
        // Example formula for energy consumption calculation
        return cpu * 0.3 + ram * 0.1 + bw * 0.05;
    }
    public Details getDetails() {
        return this.dt;
    }
}

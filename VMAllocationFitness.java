/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loadpso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;

/**
 *
 * @author sanja
 */
public class VMAllocationFitness implements FitnessFunction {
    private final List<Vm> vmList;
    private final List<Host> hostList;
    
    public VMAllocationFitness(List<Vm> vmList, List<Host> hostList) {
        this.vmList = vmList;
        this.hostList = hostList;
    }
    
    @Override
    public double evaluate(double[] solution) {
        double totalEnergy = 0;
        double resourceUtilization = 0;
        double loadBalance = 0;
        
        // Calculate fitness based on:
        // 1. Energy consumption
        // 2. Resource utilization
        // 3. Load balancing
        // 4. SLA violations
        
        Map<Integer, List<Vm>> allocation = decodeAllocation(solution);
        
        // Calculate metrics
        totalEnergy = calculateEnergyConsumption(allocation);
        resourceUtilization = calculateResourceUtilization(allocation);
        loadBalance = calculateLoadBalance(allocation);
        
        // Weighted sum of objectives
        return 0.4 * totalEnergy + 0.3 * (1 - resourceUtilization) + 0.3 * loadBalance;
    }
    
    private Map<Integer, List<Vm>> decodeAllocation(double[] solution) {
        Map<Integer, List<Vm>> allocation = new HashMap<>();
        
        for (int i = 0; i < vmList.size(); i++) {
            int hostIndex = (int)(solution[i] * hostList.size());
            allocation.computeIfAbsent(hostIndex, k -> new ArrayList<>())
                     .add(vmList.get(i));
        }
        
        return allocation;
    }
    
    // Implementation of metric calculations
    private double calculateEnergyConsumption(Map<Integer, List<Vm>> allocation) {
        // Energy model implementation
        return 0.0;
    }
    
    private double calculateResourceUtilization(Map<Integer, List<Vm>> allocation) {
        // Resource utilization calculation
        return 0.0;
    }
    
    private double calculateLoadBalance(Map<Integer, List<Vm>> allocation) {
        // Load balancing metric
        return 0.0;
    }
}
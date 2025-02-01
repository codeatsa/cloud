///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package loadpso;
//
//import static java.awt.AWTEventMulticaster.add;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.SwingUtilities;
//
///**
// *
// * @author sanja
// */public class QNPSOResultsFrame extends JFrame {
//    private static final Color HEADER_BG = new Color(51, 51, 51);
//    private static final Color CONTENT_BG = new Color(240, 240, 245);
//    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
//    private static final Font METRIC_FONT = new Font("Arial", Font.BOLD, 16);
//    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 14);
//
//    public QNPSOResultsFrame() {
//        setTitle("QNPSO Optimization Results");
//        setSize(1000, 800);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//        
//        // Add components
//        add(createHeaderPanel(), BorderLayout.NORTH);
//        add(createMainPanel(), BorderLayout.CENTER);
//        add(createControlPanel(), BorderLayout.SOUTH);
//        
//        setLocationRelativeTo(null);
//    }
//
//    private JPanel createHeaderPanel() {
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(HEADER_BG);
//        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
//
//        JLabel titleLabel = new JLabel("QNPSO Optimization Results");
//        titleLabel.setFont(TITLE_FONT);
//        titleLabel.setForeground(Color.WHITE);
//        headerPanel.add(titleLabel, BorderLayout.CENTER);
//
//        return headerPanel;
//    }
//
//    private JPanel createMainPanel() {
//        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
//        mainPanel.setBackground(CONTENT_BG);
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Add metrics panel
//        mainPanel.add(createMetricsPanel());
//        
//        // Add allocation map panel
//        mainPanel.add(createAllocationMapPanel());
//
//        return mainPanel;
//    }
//
//    private JPanel createMetricsPanel() {
//        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
//        metricsPanel.setBackground(Color.WHITE);
//        metricsPanel.setBorder(BorderFactory.createTitledBorder(
//            BorderFactory.createLineBorder(Color.GRAY),
//            "Performance Metrics"
//        ));
//
//        addMetricCard(metricsPanel, "Average Host Utilization", "31.25%");
//        addMetricCard(metricsPanel, "Load Distribution Variance", "3.0000");
//        addMetricCard(metricsPanel, "Estimated Energy Consumption", "1.98 kWh");
//        addMetricCard(metricsPanel, "SLA Compliance Rate", "100.00%");
//
//        return metricsPanel;
//    }
//
//    private void addMetricCard(JPanel panel, String label, String value) {
//        JPanel card = new JPanel();
//        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
//        card.setBackground(Color.WHITE);
//        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        JLabel titleLabel = new JLabel(label);
//        titleLabel.setFont(METRIC_FONT);
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JLabel valueLabel = new JLabel(value);
//        valueLabel.setFont(VALUE_FONT);
//        valueLabel.setForeground(new Color(41, 128, 185));
//        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        card.add(titleLabel);
//        card.add(Box.createVerticalStrut(10));
//        card.add(valueLabel);
//
//        panel.add(card);
//    }
//
//    private JPanel createAllocationMapPanel() {
//        JPanel mapPanel = new JPanel(new BorderLayout());
//        mapPanel.setBackground(Color.WHITE);
//        mapPanel.setBorder(BorderFactory.createTitledBorder(
//            BorderFactory.createLineBorder(Color.GRAY),
//            "VM Allocation Map"
//        ));
//
//        JTextArea allocationText = new JTextArea();
//        allocationText.setFont(new Font("Monospaced", Font.PLAIN, 14));
//        allocationText.setEditable(false);
//        allocationText.setText(createAllocationMapText());
//
//        mapPanel.add(new JScrollPane(allocationText), BorderLayout.CENTER);
//        return mapPanel;
//    }
//
//    private String createAllocationMapText() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Host 0 -> VMs: [0, 2, 4, 5, 8, 15]\n");
//        sb.append("Host 1 -> VMs: [1, 17]\n");
//        sb.append("Host 2 -> VMs: [9, 16]\n");
//        sb.append("Host 4 -> VMs: [13, 14]\n");
//        sb.append("Host 6 -> VMs: [6, 10, 11, 18]\n");
//        sb.append("Host 7 -> VMs: [7, 19]\n");
//        sb.append("Host 8 -> VMs: [12]\n");
//        sb.append("Host 9 -> VMs: [3]\n");
//        return sb.toString();
//    }
//
//    private JPanel createControlPanel() {
//        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        controlPanel.setBackground(CONTENT_BG);
//        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
//
//        JButton exportButton = new JButton("Export Results");
//        JButton closeButton = new JButton("Close");
//        
//        controlPanel.add(exportButton);
//        controlPanel.add(closeButton);
//
//        return controlPanel;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new QNPSOResultsFrame().setVisible(true);
//        });
//    }
//}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loadpso;

/**
 *
 * @author sanja
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class HolidayFeature {
    private String date;
    private String day;
    private String holidayName;
    private String type;

    public HolidayFeature(String date, String day, String holidayName, String type) {
        this.date = date;
        this.day = day;
        this.holidayName = holidayName;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

   public String getHolidayName() {
    if (holidayName.equals("None") && isWeekend()) {
        return "Weekend Holiday";
    }
    return holidayName;
}

public String getType() {
    if (type.equals("Regular") && isWeekend()) {
        return "Weekend";
    }
    return type;
}


    public double[] toFeatureArray() {
        double[] features = new double[7];  // Expanded features
        features[0] = isWeekend() ? 1 : 0;
        features[1] = isFestival() ? 1 : 0;
        features[2] = isPublicHoliday() ? 1 : 0;
        features[3] = getSeasonalEffect();
        features[4] = getDayOfWeekNumeric();  
        features[5] = getMonthFeature();      
        features[6] = isLongWeekend() ? 1 : 0;  
        return features;
    }

    

    private boolean isFestival() {
        return type.equalsIgnoreCase("Festival");
    }

    private boolean isPublicHoliday() {
        return type.equalsIgnoreCase("Public");
    }

    private double getSeasonalEffect() {
        int month = getMonthFromDate();
        switch (month) {
            case 2: // February
                return 1.2; // Higher multiplier for festival season
            case 12: // December
                return 1.3; // Year-end season
            case 6: case 7: // Summer months
                return 0.9; // Slightly lower in summer
            default:
                return 1.0;
        }
    }

    int getMonthFromDate() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsedDate = format.parse(date);
            return Integer.parseInt(new SimpleDateFormat("MM").format(parsedDate));
        } catch (Exception e) {
            return 0;
        }
    }

    private double getDayOfWeekNumeric() {
        switch(day.toLowerCase()) {
            case "monday": return 1;
            case "tuesday": return 2;
            case "wednesday": return 3;
            case "thursday": return 4;
            case "friday": return 5;
            case "saturday": return 6;
            case "sunday": return 7;
            default: return 0;
        }
    }

    private double getMonthFeature() {
        int month = getMonthFromDate();
        if (month == 12 || month == 1) return 1.5;  // New Year
        if (month == 10 || month == 11) return 1.4; // Diwali season
        if (month == 8 || month == 9) return 1.3;   // Festival season
        return 1.0;
    }

    private boolean isLongWeekend() {
        return isWeekend() && (type.equalsIgnoreCase("Holiday") || type.equalsIgnoreCase("Festival"));
    }

  public double estimatedLoad() {
    double baseLoad = 100.0;
    
    // First check for festival as it has highest priority
    if (type.equalsIgnoreCase("Festival")) {
        return baseLoad * 1.26; // 126% increase
    }
    // Then check for holiday
    else if (type.equalsIgnoreCase("Holiday")) {
        return baseLoad * 0.76; // 76% increase
    }
    // Finally check for weekends
    else if (isWeekend()) {
        return baseLoad * 0.25; // 25% increase for weekends
    }
    // Regular weekday
    return 0.0;
}

// Update isWeekend() to be public
public boolean isWeekend() {
    return day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday");
}
}
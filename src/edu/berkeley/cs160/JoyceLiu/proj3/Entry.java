package edu.berkeley.cs160.JoyceLiu.proj3;

public class Entry {
    public final String statName;
    public final String desti1;
    public final String desti2;
    public final String desti3;
    public final String desti4;
    public final String desti5;
    public final String etas1;
    public final String etas2;
    public final String etas3;
    public final String etas4;
    public final String etas5;

    Entry(String statName, String desti1, String etas1, String desti2, String etas2,
    		String desti3, String etas3, String desti4, String etas4, String desti5, String etas5) {
        this.statName = statName;
        this.desti1 = desti1;
        this.desti2 = desti2;
        this.desti3 = desti3;
        this.desti4 = desti4;
        this.desti5 = desti5;
        this.etas1 = etas1;
        this.etas2 = etas2;
        this.etas3 = etas3;
        this.etas4 = etas4;
        this.etas5 = etas5;

    }
    
    public String getStatName() {
    	return this.statName;
    }
    
    public String getDesti1() {
    	return this.desti1;
    }
    
    public String getDesti2() {
    	return this.desti1;
    }
    
    public String getDesti3() {
    	return this.desti1;
    }
    
    public String getDesti4() {
    	return this.desti1;
    }
    
    public String getDesti5() {
    	return this.desti1;
    }
    
    public String getETAS1() {
    	return this.etas1;
    }
    
    public String getETAS2() {
    	return this.etas2;
    }
    
    public String getETAS3() {
    	return this.etas3;
    }
    
    public String getETAS4() {
    	return this.etas4;
    }
    
    public String getETAS5() {
    	return this.etas5;
    }
    
}
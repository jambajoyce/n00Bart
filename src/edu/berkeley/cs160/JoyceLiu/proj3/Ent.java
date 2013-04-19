package edu.berkeley.cs160.JoyceLiu.proj3;

public class Ent {
    public final String desti;
    public final String eta;

    Ent(String desti, String eta) {
        this.desti = desti;
        this.eta = eta;

    }
    
    public String getDesti() {
    	return this.desti;
    }
    
    public String getETA() {
    	return this.eta;
    }
    
}
package org.example.model;

public enum FuelType {
    DIESEL("Diesel"),
    GASOLINE("Gasoline"),
    ELECTRIC("Electric"),
    HYBRID("Hybrid");

    private final String text;

    private FuelType(String text){
        this.text = text;
    }

    public static FuelType returnIDType(String text){
        for(FuelType fuelType : FuelType.values()){
            if(fuelType.text.equals(text)) {
                return fuelType;
            }
        }
        return null;
    }

    public static String[] getFuelTypes(){
        String[] fuelTypes = new String[FuelType.values().length];
        for(int n = 0; n < fuelTypes.length; n++){
            fuelTypes[n] = FuelType.values()[n].getText();
        }
        return fuelTypes;
    }

    public String getText(){
        return this.text;
    }

}

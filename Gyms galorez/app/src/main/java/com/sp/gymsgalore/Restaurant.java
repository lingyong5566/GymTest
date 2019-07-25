package com.sp.gymsgalore;

public class Restaurant {
    private String dayName= "";
    private String primaryActivites= "";
    private String secondaryActivities= "";
    private String primaryTimings = "";
    private String secondaryTimings = "";
    private String primaryReps = "";
    private String secondaryReps = "";
    private String repetitions= "";

    public String getDay(){
        return (dayName);
    }

    public void setDay(String restaurantName){
        this.dayName = restaurantName;
    }

    public String getPrimaryActivites(){
        return (primaryActivites);
    }

    public void setPrimaryActivites(String restaurantAddress){
        this.primaryActivites=restaurantAddress;
    }

    public String getSecondaryActivities(){
        return (secondaryActivities);
    }

    public void setSecondaryActivities(String restaurantTel){
        this.secondaryActivities=restaurantTel;
    }

    public String getPrimaryTimings(){
        return (primaryTimings);
    }

    public void setPrimaryTimings(String pTimings){
        this.primaryTimings = pTimings;
    }

    public String getSecondaryTimings(){
        return (secondaryTimings);
    }

    public void setSecondaryTimings(String sTimings){
        this.secondaryTimings = sTimings;
    }


    public String getPrimaryReps(){
        return (primaryReps);
    }

    public void setPrimaryReps(String primaryReps){
        this.primaryReps=primaryReps;
    }

    public String getSecondaryReps(){
        return (secondaryReps);
    }

    public void setSecondaryReps(String secondaryReps){
        this.secondaryReps=secondaryReps;
    }

    public String getRepetitions(){
        return(repetitions);
    }

    public void setRepetitions(String restaurantType){
        this.repetitions=restaurantType;
    }

    public String toString(){
        return (getDay());
    }
}

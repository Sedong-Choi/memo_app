package org.techtown.memo;

public class Health_Calendar {
    String health_icon;
    String health_weight;
    String health_set;
    String health_times;

    public Health_Calendar(String health_icon, String health_weight, String health_set, String health_times){
        this.health_icon =health_icon;
        this.health_weight=health_weight;
        this.health_set=health_set;
        this.health_times=health_times;
    }

    public String getHealth_icon() {
        return health_icon;
    }

    public void setHealth_icon(String health_icon) {
        this.health_icon = health_icon;
    }

    public String getHealth_weight() {
        return health_weight;
    }

    public void setHealth_weight(String health_weight) {
        this.health_weight = health_weight;
    }

    public String getHealth_set() {
        return health_set;
    }

    public void setHealth_set(String health_set) {
        this.health_set = health_set;
    }

    public String getHealth_times() {
        return health_times;
    }

    public void setHealth_times(String health_times) {
        this.health_times = health_times;
    }




}

public class Statistics {
    private int totalCarServed;
    private int totalCarsQueued;

    Statistics(){
        totalCarServed=0;
        totalCarsQueued=0;
    }

    public void carServed(){
        totalCarServed++;
    }

    public void carQueued(){
        totalCarsQueued++;
    }
    public int getTotalCarServed(){
        return totalCarServed;
    }

    public int getTotalCarsQueued(){
        return totalCarsQueued;
    }
}

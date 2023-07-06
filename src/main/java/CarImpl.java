public class CarImpl implements Car {

    private String carModel;
    private double chargingTime;

    public CarImpl(String carModel, double chargingTime) {
        this.carModel = carModel;
        this.chargingTime = chargingTime;
    }

    @Override
    public void charge() {
        try {
            Thread.sleep((long) (chargingTime * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

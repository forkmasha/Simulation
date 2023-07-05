import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public static void main(String[] args) {

        GasStation gasStation=new GasStationImpl(5,10);


        List<Car> cars=generateCars();
        for(Car car:cars){
            gasStation.serveCar(car);
        }

        Statistics statistics=((GasStationImpl)gasStation).getStatistics();
        System.out.println("Total cars served "+statistics.getTotalCarServed());
        System.out.println("Total cats queued "+statistics.getTotalCarsQueued());
    }

    private static List<Car>generateCars(){
        List<Car> cars=new ArrayList<>();
        cars.add(new CarImpl("CarModel1",1.0));
        cars.add(new CarImpl("CarModel2",2.0));
        cars.add(new CarImpl("CarModel3",3.0));
        cars.add(new CarImpl("CarModel4",4.0));
        cars.add(new CarImpl("CarModel5",5.0));
        cars.add(new CarImpl("CarModel6",6.0));
        cars.add(new CarImpl("CarModel7",7.0));
        cars.add(new CarImpl("CarModel8",8.0));
        cars.add(new CarImpl("CarModel9",9.0));
        cars.add(new CarImpl("CarModel10",10.0));
        return cars;
    }
}

import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Simulation {
    public static void main(String[] args) {
        GasStation gasStation = new GasStationImpl(5, 10);

        XYSeries meanSystemTimeSeries = new XYSeries("Mean System Time");
        XYSeries arrivalRateSeries = new XYSeries("Arrival Rate(Cars perSecond)");

        double meanArrivalInterval;
        for (meanArrivalInterval = 0.0; meanArrivalInterval <= 9.9; meanArrivalInterval += 0.1) {
            ((GasStationImpl) gasStation).setMeanArrivalInterval(meanArrivalInterval);
        }

        double meanServiceTime = 5.0;
        int maxCars = 100;

        SimulationResult result = gasStation.simulationResult(meanServiceTime, maxCars);

        meanSystemTimeSeries.add(meanArrivalInterval, result.getMeanSystemTime());
        arrivalRateSeries.add(meanArrivalInterval, 1 / meanArrivalInterval);


        XYSeriesCollection dataset=new XYSeriesCollection();

        System.out.println("Standard deviation of system time "+result.getStdSystemTime());
        System.out.println("Mean system time "+result.getStdSystemTime());
        System.out.println("Mean queue time "+result.getMeanQueueTime());
        System.out.println("Standard deviation of queue time "+result.getStdQueueTime());
        System.out.println();


        List<Car> cars = generateCars();
        for (Car car : cars) {
            gasStation.serveCar(car);
        }

        Statistics statistics = ((GasStationImpl) gasStation).getStatistics();
        System.out.println("Total cars served " + statistics.getTotalCarServed());
        System.out.println("Total cats queued " + statistics.getTotalCarsQueued());
    }

    private static List<Car> generateCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new CarImpl("CarModel1", 1.0));
        cars.add(new CarImpl("CarModel2", 2.0));
        cars.add(new CarImpl("CarModel3", 3.0));
        cars.add(new CarImpl("CarModel4", 4.0));
        cars.add(new CarImpl("CarModel5", 5.0));
        cars.add(new CarImpl("CarModel6", 6.0));
        cars.add(new CarImpl("CarModel7", 7.0));
        cars.add(new CarImpl("CarModel8", 8.0));
        cars.add(new CarImpl("CarModel9", 9.0));
        cars.add(new CarImpl("CarModel10", 10.0));
        return cars;
    }
}

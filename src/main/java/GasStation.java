public interface GasStation {
    void serveCar(Car car);
    int getQueueSize();

    SimulationResult simulationResult(double meanServiceTime, int maxCars);
}

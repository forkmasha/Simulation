import java.util.*;

public class GasStationImpl implements GasStation {

    private int numServers;
    private int queueLength;

    private double meanArrivalInterval;
    private Queue<Car> waitingQueue;
    private List<Car> servers;
    private Statistics statistics;

    public GasStationImpl(int numServers, int queueLength) {
        this.numServers = numServers;
        this.queueLength = queueLength;
        this.waitingQueue = new LinkedList<>();
        this.servers = new ArrayList<>();
        statistics = new Statistics();
    }

    public double getMeanArrivalInterval() {
        return meanArrivalInterval;
    }

    public void setMeanArrivalInterval(double meanArrivalInterval) {
        this.meanArrivalInterval = meanArrivalInterval;
    }

    @Override
    public void serveCar(Car car) {
        if (servers.size() < numServers) {
            servers.add(car);
            car.charge();
            statistics.carServed();
        } else {
            if (waitingQueue.size() < queueLength) {
                waitingQueue.add(car);
                statistics.carQueued();
            }
        }
    }

    public void serveNextCar() {
        if (!waitingQueue.isEmpty()) {
            Car car = waitingQueue.poll();
            servers.add(car);
            car.charge();
        }
    }

    public void removeCar(Car car) {
        servers.remove(car);
        serveNextCar();
    }

    @Override
    public int getQueueSize() {
        return waitingQueue.size();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public SimulationResult simulationResult(double meanServiceTime, int maxCars) {
        List<Double> arrivalTimes = new ArrayList<>();
        List<Double> serviceTimes = new ArrayList<>();
        List<Double> systemTimes = new ArrayList<>();
        List<Double> queueTimes = new ArrayList<>();

        int state = 0;
        double time = 0.0;
        int i = 0, j = 0, k = 0;
        double[] servers = new double[numServers];
        double[] waiting = new double[queueLength];

        while (i < maxCars) {
            double arrivalTime = generateExponentialRandom(meanArrivalInterval);
            double serviceTime = generateExponentialRandom(meanServiceTime);
            arrivalTimes.add(arrivalTime);
            serviceTimes.add(serviceTime);

            if (state < numServers) {
                double departureTime = time + serviceTime;
                systemTimes.add(departureTime);
                time = departureTime;
                state++;
            } else if (state < numServers + queueLength) {
                waiting[state - numServers] = time + serviceTime;
                time += arrivalTime;
                state++;
            } else {
                k++;
                time += arrivalTime;
            }
            i++;
        }
        while (state > 0) {
            if (state > numServers) {
                double departureTime = waiting[0];
                queueTimes.add(departureTime - arrivalTimes.get(j));
                time = departureTime;
                shift(waiting);
                state--;
            } else {
                double departureTime = time + serviceTimes.get(j);
                systemTimes.add(departureTime);
                time = departureTime;
                state--;
            }
            j++;
        }
        double meanSystemTime = calculateMean(systemTimes);
        double meanQueueTime = calculateMean(queueTimes);
        double stdSystemTime = calculateStd(systemTimes, meanSystemTime);
        double stdQueueTime = calculateStd(queueTimes, meanQueueTime);

        return new SimulationResult(meanSystemTime, stdSystemTime, meanQueueTime, stdQueueTime);
    }

    private double calculateMean(List<Double> values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private double calculateStd(List<Double> values, double mean) {
        double sum = 0.0;
        for (double value : values) {
            sum += Math.pow(value - mean, 2);
        }
        double variance = sum / values.size();
        return Math.sqrt(variance);
    }

    private void shift(double[] waiting) {
        for (int i = 0; i < waiting.length - 1; i++) {
            waiting[i] = waiting[i + 1];
        }
        waiting[waiting.length - 1] = 0.0;
    }

    public double generateExponentialRandom(double lambda) {
        Random random = new Random();
        double u = random.nextDouble();
        return -Math.log(1 - u) / lambda;
    }
}
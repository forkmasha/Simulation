import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GasStationImpl implements GasStation{

    private int numServers;
    private int queueLength;
    private Queue<Car> waitingQueue;
    private List<Car> servers;
    private  Statistics statistics;

    public GasStationImpl(int numServers, int queueLength) {
        this.numServers = numServers;
        this.queueLength = queueLength;
        this.waitingQueue=new LinkedList<>();
        this.servers=new ArrayList<>();
        statistics=new Statistics();
    }
@Override
    public void serveCar(Car car) {
        if (servers.size()<numServers){
            servers.add(car);
            car.charge();
            statistics.carServed();
        }
        else {
            if(waitingQueue.size()<queueLength){
                waitingQueue.add(car);
                statistics.carQueued();
            }
        }
    }
public void serveNextCar(){
        if(!waitingQueue.isEmpty()){
            Car car=waitingQueue.poll();
            servers.add(car);
            car.charge();
        }
}
public void removeCar(Car car){
        servers.remove(car);
        serveNextCar();
}
@Override
    public int getQueueSize() {
        return waitingQueue.size();
    }
    public Statistics getStatistics(){
        return statistics;
    }
}

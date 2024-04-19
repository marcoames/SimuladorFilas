import java.util.Arrays;

public class Fila {
    private String name;

    private int servers;
    private int capacity;

    private double minArrival;
    private double maxArrival;
    private double minService;
    private double maxService;

    private int customers;
    private int loss;

    private double[] times;

    public Fila(String name, int servers, int capacity, double minArrival, double maxArrival, double minService,
            double maxService) {
        this.name = name;
        this.servers = servers;
        this.capacity = capacity;

        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;

        this.customers = 0;
        this.loss = 0;

        this.times = new double[capacity + 1];

    }

    public String getName() {
        return this.name;
    }

    public int getServers() {
        return this.servers;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public double getMinArrival() {
        return this.minArrival;
    }

    public double getMaxArrival() {
        return this.maxArrival;
    }

    public double getMinService() {
        return this.minService;
    }

    public double getMaxService() {
        return this.maxService;
    }

    public int status() {
        return this.customers;
    }

    public void loss() {
        this.loss++;
    }

    public int getLoss() {
        return this.loss;
    }

    public void in() {
        this.customers++;
    }

    public void out() {
        this.customers--;
    }

    public double[] getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "fila: {" +
                "name: " + name +
                ", servers: " + servers +
                ", capacity: " + capacity +
                ", minArrival: " + minArrival +
                ", maxArrival: " + maxArrival +
                ", minService: " + minService +
                ", maxService: " + maxService +
                ", status: " + customers +
                ", perda: " + loss +
                ", times: " + Arrays.toString(times) +
                '}';
    }

}
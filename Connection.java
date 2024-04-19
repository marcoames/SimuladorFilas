public class Connection {
    private String source;
    private String target;
    private double probability;

    public Connection(String source, String target, double probability) {
        this.source = source;
        this.target = target;
        this.probability = probability;
    }

    // Getters
    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "Connection: {" +
                "source: " + source +
                ", target: " + target +
                ", probability: " + probability +
                '}';
    }

}
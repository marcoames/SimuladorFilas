public class Evento {
    // Tipos de Evento
    public static final int CHEGADA = 0;
    public static final int SAIDA = 1;
    public static final int PASSAGEM = 2;

    private int type;
    private double time;

    public Evento(int type, double time) {
        this.type = type;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", time=" + time +
                '}';
    }
}
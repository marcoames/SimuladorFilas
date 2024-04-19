public class Evento {
    // Tipos de Evento
    public static final int CHEGADA = 0;
    public static final int SAIDA = 1;
    public static final int PASSAGEM = 2;

    private int type;
    private double time;
    private Fila fila;

    public Evento(int type, double time, Fila fila) {
        this.type = type;
        this.time = time;
        this.fila = fila;
    }

    public int getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    public Fila getFila(){
        return fila;
    }

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", time=" + time +
                ", fila=" + fila.getName() +
                '}';
    }
}
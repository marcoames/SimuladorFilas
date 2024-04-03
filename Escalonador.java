import java.util.PriorityQueue;

public class Escalonador {
    private PriorityQueue<Evento> filaEventos;

    public Escalonador() {
        this.filaEventos = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
    }

    public void alocaEvento(Evento evento) {
        // System.out.println("Alocado Evento: " + event.toString());
        filaEventos.offer(evento);
    }

    public Evento proxEvento() {
        return filaEventos.poll();
    }
}

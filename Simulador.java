import java.util.Arrays;
import java.util.PriorityQueue;

public class Simulador {

    private static double previous = 0.5;

    private static double[] nums = {0.8, 0.2, 0.1, 0.9, 0.3, 0.4, 0.7};

    private static class Escalonador {
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

    private static class Fila {
        private int status;
        private int capacity;
        private int server;
        private int perda;
        private int minArrival;
        private int maxArrival;
        private int minService;
        private int maxService;

        private double[] tempos;

        public Fila(int capacity, int server, int minArrival, int maxArrival, int minService, int maxService) {
            this.status = 0;
            this.perda = 0;
            this.tempos = new double[capacity + 1];
            this.capacity = capacity;
            this.server = server;
            this.minArrival = minArrival;
            this.maxArrival = maxArrival;
            this.minService = minService;
            this.maxService = maxService;
        }

        @Override
        public String toString() {
            return "fila: {" +
                    "status=" + status +
                    ", perda=" + perda +
                    ", capacity=" + capacity +
                    ", server=" + server +
                    ", minArrival=" + minArrival +
                    ", maxArrival=" + maxArrival +
                    ", minService=" + minService +
                    ", maxService=" + maxService +
                    ", tempos=" + Arrays.toString(tempos) +
                    '}';
        }

    }

    private static class Evento {
        // Define Event class to represent different types of events
        public static final int CHEGADA = 0;
        public static final int SAIDA = 1;

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

    public static double Next_random() {
        // a = 81201, c = 28411, M = 1424215416
        previous = ((81201 * previous) + 28411) % 1424215416;
        return (double) previous / 1424215416;
    }

    public static double[] Chegada(Evento evento, Fila fila, Escalonador escalonador, double TG,int count){
        // acumulaTempo
        fila.tempos[fila.status] += evento.getTime() - TG;
        // Update TG
        TG = evento.getTime();

        if (fila.status < fila.capacity) {
            fila.status++;
            if (fila.status <= fila.server) {
                escalonador.alocaEvento(new Evento(1, TG + (fila.minService + (fila.maxService - fila.minService) * Next_random())));
                // escalonador.alocaEvento(new Evento(1, TG + (fila.minService + (fila.maxService - fila.minService) * nums[count])));
                count++;
            }
        } else {
            fila.perda++;
        }
            
        escalonador.alocaEvento(new Evento(0, TG + (fila.minArrival + (fila.maxArrival - fila.minArrival) * Next_random())));
        // escalonador.alocaEvento(new Evento(0, TG + (fila.minArrival + (fila.maxArrival - fila.minArrival) * nums[count])));
        count++;
        
        double[] resultado = {count,TG};
        return resultado;
    }

    public static double[] Saida(Evento evento, Fila fila, Escalonador escalonador, double TG,int count) {
        // acumulaTempo
        fila.tempos[fila.status] += evento.getTime() - TG;
        // Update TG
        TG = evento.getTime();

        fila.status--;
        if (fila.status >= fila.server) {
            escalonador.alocaEvento(new Evento(1, TG + (fila.minService + (fila.maxService - fila.minService) * Next_random())));
            // escalonador.alocaEvento(new Evento(1, TG + (fila.minService + (fila.maxService - fila.minService) * nums[count])));
            count++;
        }
        double[] resultado = {count,TG};
        return resultado;
    }

    public static void Simulacao(Fila fila){

        Escalonador escalonador = new Escalonador();

        // Tempo Global
        double TG = 0;

        Evento evento1 = new Evento(0, 2);
        escalonador.alocaEvento(evento1);

        // Loop Simulacao
        int count = 0;
        while (count < 100000) {
        // while (count < 7) {

            Evento nextEvent = escalonador.proxEvento();
            //System.out.println("Novo Evento: " + nextEvent.toString());

            if (nextEvent.getType() == Evento.CHEGADA) {
                double[] result = Chegada(nextEvent, fila, escalonador, TG, count);    
                count = (int)result[0];
                TG = result[1];

            } else if (nextEvent.getType() == Evento.SAIDA) {
                double[] result = Saida(nextEvent, fila, escalonador, TG, count);
                count = (int)result[0];
                TG = result[1];
            }
        }

        System.out.printf("\nFila G/G/%d/%d", fila.server, fila.capacity);

        System.out.println("\nEstado\t\tTempo\t\tProbabilidade");

        for (int i = 0; i < fila.tempos.length; i++) {
            double time = fila.tempos[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);

        }

        System.out.println("\nPerdas: " + fila.perda);
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");

    }

    public static void main(String[] args) {

        // FILA G/G/1/5
        Fila fila1 = new Fila(5, 1, 2, 5, 3, 5);

        // FILA G/G/2/5
        Fila fila2 = new Fila(5,2,2,5,3,5);

        // FILA G/G/1/2
        Fila fila3 = new Fila(2,1,1,4,1,3);

        // Faz a Simulacao passando a fila
        Simulacao(fila1);
        Simulacao(fila2);
        //Simulacao(fila3);
        
    }

}

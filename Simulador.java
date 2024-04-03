public class Simulador {

    // Para gerar numeros pseudoaleatorios
    private static double previous = 0.5;

    private static double TG = 0;
    private static int count = 0;

    // FILA G/G/1/5
    // Fila fila1 = new Fila(1, 5, 2, 5, 3, 5);

    // FILA G/G/2/5
    // Fila fila2 = new Fila(2, 5, 2, 5, 3, 5);

    // FILA G/G/2/4
    static Fila fila1 = new Fila(2, 4, 1, 3, 5, 6);

    // FILA G/G/3/5
    static Fila fila2 = new Fila(3, 5, 0, 0, 2, 4);

    // Para usar numeros pseudoaleatorios predefinidos
    // private static double[] nums = {0.8, 0.2, 0.1, 0.9, 0.3, 0.4, 0.7};

    public static double Next_random() {
        // a = 81201, c = 28411, M = 1424215416
        previous = ((81201 * previous) + 28411) % 1424215416;
        return (double) previous / 1424215416;
    }

    public static void Chegada(Evento evento, Escalonador escalonador) {
        // acumulaTempo
        fila1.getTimes()[fila1.status()] += evento.getTime() - TG;

        // Update TG
        TG = evento.getTime();

        if (fila1.status() < fila1.getCapacity()) {
            fila1.in();
            if (fila1.status() <= fila1.getServers()) {
                escalonador.alocaEvento(new Evento(2,
                        TG + (fila1.getMinService() + (fila1.getMaxService() - fila1.getMinService()) *
                                Next_random())));
                count++;
            }
        } else {
            fila1.loss();
        }

        escalonador.alocaEvento(new Evento(0,
                TG + (fila1.getMinArrival() + (fila1.getMaxArrival() - fila1.getMinArrival()) *
                        Next_random())));
        count++;

    }

    public static void Saida(Evento evento, Escalonador escalonador) {
        // acumulaTempo
        fila2.getTimes()[fila2.status()] += evento.getTime() - TG;
        // Update TG
        TG = evento.getTime();

        fila2.out();
        if (fila2.status() >= fila2.getServers()) {
            escalonador.alocaEvento(new Evento(1,
                    TG + (fila2.getMinService() + (fila2.getMaxService() - fila2.getMinService()) * Next_random())));
            count++;
        }
    }

    public static void Passagem(Evento evento, Escalonador escalonador) {
        // acumulaTempo
        fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
        fila2.getTimes()[fila2.status()] += evento.getTime() - TG;
        // Update TG
        TG = evento.getTime();

        fila1.out();
        if (fila1.status() >= fila1.getServers()) {
            escalonador.alocaEvento(new Evento(2,
                    TG + (fila1.getMinService() + (fila1.getMaxService() - fila1.getMinService()) * Next_random())));
        }

        if (fila2.status() < fila2.getCapacity()) {
            fila2.in();
            if (fila2.status() <= fila2.getServers()) {
                escalonador.alocaEvento(new Evento(1, TG
                        + (fila2.getMinService() + (fila2.getMaxService() - fila2.getMinService()) * Next_random())));
            }
        }

        else {
            fila2.loss();
        }

    }

    public static void Simulacao(Fila fila1, Fila fila2) {

        Escalonador escalonador = new Escalonador();

        Evento evento1 = new Evento(0, 1);

        escalonador.alocaEvento(evento1);

        // Loop Simulacao
        // int count = 0;
        while (count < 100000) {

            Evento nextEvent = escalonador.proxEvento();
            // System.out.println("Novo Evento: " + nextEvent.toString());

            if (nextEvent.getType() == Evento.CHEGADA) {
                Chegada(nextEvent, escalonador);

            } else if (nextEvent.getType() == Evento.SAIDA) {
                Saida(nextEvent, escalonador);

            } else if (nextEvent.getType() == Evento.PASSAGEM) {
                Passagem(nextEvent, escalonador);
            }
        }

        // PRINT FILA 1
        System.out.printf("\nFila G/G/%d/%d", fila1.getServers(), fila1.getCapacity());

        System.out.println("\nEstado\t\tTempo\t\tProbabilidade");

        for (int i = 0; i < fila1.getTimes().length; i++) {
            double time = fila1.getTimes()[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);

        }

        System.out.println("\nPerdas: " + fila1.getLoss());
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");

        // PRINT FILA 2
        System.out.printf("\nFila G/G/%d/%d", fila2.getServers(), fila2.getCapacity());

        System.out.println("\nEstado\t\tTempo\t\tProbabilidade");

        for (int i = 0; i < fila2.getTimes().length; i++) {
            double time = fila2.getTimes()[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);

        }

        System.out.println("\nPerdas: " + fila2.getLoss());
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");

    }

    public static void main(String[] args) {

        Simulacao(fila1, fila2);

    }

}

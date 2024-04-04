public class Simulador {

    // Para gerar numeros pseudoaleatorios
    private static double previous = 0.5;

    // Variaveis de Tempo global e count de pseudoaleatorios.
    private static double TG = 0;
    private static int count = 0;

    // Para usar numeros pseudoaleatorios predefinidos
    // private static double[] nums = {0.8, 0.2, 0.1, 0.9, 0.3, 0.4, 0.7};

    public static void main(String[] args) {

        //////// UNICA FILA ////////

        // FILA G/G/1/5
        Fila fila = new Fila(1, 5, 2, 5, 3, 5);

        // FILA G/G/2/5
        // Fila fila = new Fila(2, 5, 2, 5, 3, 5);

        //////// FILAS EM TANDEM ////////

        // FILA G/G/2/3
        Fila fila1 = new Fila(2, 3, 1, 4, 3, 4);

        // FILA G/G/1/5
        Fila fila2 = new Fila(1, 5, 0, 0, 2, 3);

        //////// SIMULACOES ////////

        // SimulacaoUnicaFila(fila);
        SimulacaoFilasEmTandem(fila1, fila2);

    }

    // GERAR NUMERO PSEUDOALEATORIO
    public static double Next_random() {
        // a = 81201, c = 28411, M = 1424215416
        previous = ((81201 * previous) + 28411) % 1424215416;
        return (double) previous / 1424215416;
    }

    /////////////////////// UNICA FILA ///////////////////////

    public static void Chegada(Evento evento, Escalonador escalonador, Fila fila) {
        // acumulaTempo
        fila.getTimes()[fila.status()] += evento.getTime() - TG;

        TG = evento.getTime();

        if (fila.status() < fila.getCapacity()) {
            fila.in();
            if (fila.status() <= fila.getServers()) {
                escalonador.alocaEvento(new Evento(1,
                        TG + (fila.getMinService() + (fila.getMaxService() - fila.getMinService()) * Next_random())));
                count++;
            }
        } else {
            fila.loss();
        }
        escalonador.alocaEvento(new Evento(0,
                TG + (fila.getMinArrival() + (fila.getMaxArrival() - fila.getMinArrival()) * Next_random())));
        count++;

    }

    public static void Saida(Evento evento, Escalonador escalonador, Fila fila) {
        // acumulaTempo
        fila.getTimes()[fila.status()] += evento.getTime() - TG;

        TG = evento.getTime();

        fila.out();
        if (fila.status() >= fila.getServers()) {
            escalonador.alocaEvento(new Evento(1,
                    TG + (fila.getMinService() + (fila.getMaxService() - fila.getMinService()) * Next_random())));
            count++;
        }

    }

    private static void SimulacaoUnicaFila(Fila fila) {
        Escalonador escalonador = new Escalonador();

        // PRIMEIRO EVENTO DE CHEGADA
        Evento evento1 = new Evento(0, 1);
        escalonador.alocaEvento(evento1);

        // Loop Simulacao
        while (count < 100000) {

            Evento nextEvent = escalonador.proxEvento();
            // System.out.println("Novo Evento: " + nextEvent.toString());

            if (nextEvent.getType() == Evento.CHEGADA) {
                Chegada(nextEvent, escalonador, fila);

            } else if (nextEvent.getType() == Evento.SAIDA) {
                Saida(nextEvent, escalonador, fila);
            }
        }

        // PRINT FILA
        System.out.printf("\nFila G/G/%d/%d", fila.getServers(), fila.getCapacity());

        System.out.println("\nEstado\t\tTempo\t\tProbabilidade");

        for (int i = 0; i < fila.getTimes().length; i++) {
            double time = fila.getTimes()[i];
            double probability = (time / TG) * 100;

            String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
            output = output.replace(',', '.');
            System.out.print(output);

        }

        System.out.println("\nPerdas: " + fila.getLoss());
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");

    }

    /////////////////////// FILAS EM TANDEM ///////////////////////

    public static void ChegadaTandem(Evento evento, Escalonador escalonador, Fila fila1, Fila fila2) {
        // acumulaTempo
        fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
        fila2.getTimes()[fila2.status()] += evento.getTime() - TG;

        TG = evento.getTime();

        if (fila1.status() < fila1.getCapacity()) {
            fila1.in();
            if (fila1.status() <= fila1.getServers()) {
                escalonador.alocaEvento(new Evento(2, TG
                        + (fila1.getMinService() + (fila1.getMaxService() - fila1.getMinService()) * Next_random())));
                count++;
            }
        } else {
            fila1.loss();
        }

        escalonador.alocaEvento(new Evento(0,
                TG + (fila1.getMinArrival() + (fila1.getMaxArrival() - fila1.getMinArrival()) * Next_random())));
        count++;

    }

    public static void SaidaTandem(Evento evento, Escalonador escalonador, Fila fila1, Fila fila2) {
        // acumulaTempo
        fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
        fila2.getTimes()[fila2.status()] += evento.getTime() - TG;

        TG = evento.getTime();

        fila2.out();
        if (fila2.status() >= fila2.getServers()) {
            escalonador.alocaEvento(new Evento(1,
                    TG + (fila2.getMinService() + (fila2.getMaxService() - fila2.getMinService()) * Next_random())));
            count++;
        }
    }

    public static void Passagem(Evento evento, Escalonador escalonador, Fila fila1, Fila fila2) {
        // acumulaTempo
        fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
        fila2.getTimes()[fila2.status()] += evento.getTime() - TG;

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

    public static void SimulacaoFilasEmTandem(Fila fila1, Fila fila2) {

        Escalonador escalonador = new Escalonador();

        // PRIMEIRO EVENTO DE CHEGADA
        Evento evento1 = new Evento(0, 1.5);
        escalonador.alocaEvento(evento1);

        // Loop Simulacao
        while (count < 100000) {

            Evento nextEvent = escalonador.proxEvento();
            // System.out.println("Novo Evento: " + nextEvent.toString());

            if (nextEvent.getType() == Evento.CHEGADA) {
                ChegadaTandem(nextEvent, escalonador, fila1, fila2);

            } else if (nextEvent.getType() == Evento.SAIDA) {
                SaidaTandem(nextEvent, escalonador, fila1, fila2);

            } else if (nextEvent.getType() == Evento.PASSAGEM) {
                Passagem(nextEvent, escalonador, fila1, fila2);
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

}

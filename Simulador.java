import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

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
        // Fila fila = new Fila(1, 5, 2.0, 5.0, 3.0, 5.0);

        // FILA G/G/2/5
        // Fila fila = new Fila(2, 5, 2, 5, 3, 5);

        //////// FILAS EM TANDEM ////////

        // FILA G/G/2/3
        // Fila fila1 = new Fila("F1",2, 3, 1.0, 4.0, 3.0, 4.0);

        // FILA G/G/1/5
        // Fila fila2 = new Fila("F2",1, 5, 0.0, 0.0, 2.0, 3.0);

        //////// LISTA DE FILAS ////////

        ArrayList<Fila> filas = new ArrayList<>();
        // G/G/1/10
        filas.add(new Fila("Q1", 1, 10, 2.0, 4.0, 1.0, 2.0));
        // G/G/2/5
        filas.add(new Fila("Q2", 2, 5, 0, 0, 4.0, 8.0));
        // G/G/2/10
        filas.add(new Fila("Q3", 2, 10, 0, 0, 5.0, 15.0));

        ArrayList<Connection> network = new ArrayList<>();
        network.add(new Connection("Q1", "Q2", 0.8));
        network.add(new Connection("Q1", "Q3", 0.2));
        network.add(new Connection("Q2", "Q1", 0.3));
        network.add(new Connection("Q2", "Q2", 0.5));
        network.add(new Connection("Q3", "Q3", 0.7));

        //////// SIMULACOES ////////

        // SimulacaoUnicaFila(fila);
        // SimulacaoFilasEmTandem(fila1, fila2);
        SimulacaoListaFilas(filas, network);

    }

    // GERAR NUMERO PSEUDOALEATORIO
    public static double Next_random() {
        // a = 81201, c = 28411, M = 1424215416
        previous = ((81201 * previous) + 28411) % 1424215416;
        return (double) previous / 1424215416;
    }

    /////////////////////// UNICA FILA ///////////////////////

    /*
     * 
     * public static void Chegada(Evento evento, Escalonador escalonador, Fila fila)
     * {
     * // acumulaTempo
     * fila.getTimes()[fila.status()] += evento.getTime() - TG;
     * 
     * TG = evento.getTime();
     * 
     * if (fila.status() < fila.getCapacity()) {
     * fila.in();
     * if (fila.status() <= fila.getServers()) {
     * escalonador.alocaEvento(new Evento(1,
     * TG + (fila.getMinService() + (fila.getMaxService() - fila.getMinService()) *
     * Next_random())));
     * count++;
     * }
     * } else {
     * fila.loss();
     * }
     * escalonador.alocaEvento(new Evento(0,
     * TG + (fila.getMinArrival() + (fila.getMaxArrival() - fila.getMinArrival()) *
     * Next_random())));
     * count++;
     * 
     * }
     * 
     * public static void Saida(Evento evento, Escalonador escalonador, Fila fila) {
     * // acumulaTempo
     * fila.getTimes()[fila.status()] += evento.getTime() - TG;
     * 
     * TG = evento.getTime();
     * 
     * fila.out();
     * if (fila.status() >= fila.getServers()) {
     * escalonador.alocaEvento(new Evento(1,
     * TG + (fila.getMinService() + (fila.getMaxService() - fila.getMinService()) *
     * Next_random())));
     * count++;
     * }
     * 
     * }
     * 
     * private static void SimulacaoUnicaFila(Fila fila) {
     * Escalonador escalonador = new Escalonador();
     * 
     * // PRIMEIRO EVENTO DE CHEGADA
     * Evento evento1 = new Evento(0, 1);
     * escalonador.alocaEvento(evento1);
     * 
     * // Loop Simulacao
     * while (count < 100000) {
     * 
     * Evento nextEvent = escalonador.proxEvento();
     * // System.out.println("Novo Evento: " + nextEvent.toString());
     * 
     * if (nextEvent.getType() == Evento.CHEGADA) {
     * Chegada(nextEvent, escalonador, fila);
     * 
     * } else if (nextEvent.getType() == Evento.SAIDA) {
     * Saida(nextEvent, escalonador, fila);
     * }
     * }
     * 
     * // PRINT FILA
     * System.out.printf("\nFila G/G/%d/%d", fila.getServers(), fila.getCapacity());
     * 
     * System.out.println("\nEstado\t\tTempo\t\tProbabilidade");
     * 
     * for (int i = 0; i < fila.getTimes().length; i++) {
     * double time = fila.getTimes()[i];
     * double probability = (time / TG) * 100;
     * 
     * String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time,
     * probability);
     * output = output.replace(',', '.');
     * System.out.print(output);
     * 
     * }
     * 
     * System.out.println("\nPerdas: " + fila.getLoss());
     * System.out.println("Tempo Global: " + TG);
     * System.out.println("\n");
     * 
     * }
     * 
     */

    /////////////////////// FILAS EM TANDEM ///////////////////////

    /*
     * 
     * public static void ChegadaTandem(Evento evento, Escalonador escalonador, Fila
     * fila1, Fila fila2) {
     * // acumulaTempo
     * fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
     * fila2.getTimes()[fila2.status()] += evento.getTime() - TG;
     * 
     * TG = evento.getTime();
     * 
     * if (fila1.status() < fila1.getCapacity()) {
     * fila1.in();
     * if (fila1.status() <= fila1.getServers()) {
     * escalonador.alocaEvento(new Evento(2, TG
     * + (fila1.getMinService() + (fila1.getMaxService() - fila1.getMinService()) *
     * Next_random()),fila1));
     * count++;
     * }
     * } else {
     * fila1.loss();
     * }
     * 
     * escalonador.alocaEvento(new Evento(0,
     * TG + (fila1.getMinArrival() + (fila1.getMaxArrival() - fila1.getMinArrival())
     * Next_random()),fila1));
     * count++;
     * 
     * }
     * 
     * public static void SaidaTandem(Evento evento, Escalonador escalonador, Fila
     * fila1, Fila fila2) {
     * // acumulaTempo
     * fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
     * fila2.getTimes()[fila2.status()] += evento.getTime() - TG;
     * 
     * TG = evento.getTime();
     * 
     * fila2.out();
     * if (fila2.status() >= fila2.getServers()) {
     * escalonador.alocaEvento(new Evento(1,
     * TG + (fila2.getMinService() + (fila2.getMaxService() - fila2.getMinService())
     * Next_random()),fila2));
     * count++;
     * }
     * }
     * 
     * public static void Passagem(Evento evento, Escalonador escalonador, Fila
     * fila1, Fila fila2) {
     * // acumulaTempo
     * fila1.getTimes()[fila1.status()] += evento.getTime() - TG;
     * fila2.getTimes()[fila2.status()] += evento.getTime() - TG;
     * 
     * TG = evento.getTime();
     * 
     * fila1.out();
     * if (fila1.status() >= fila1.getServers()) {
     * escalonador.alocaEvento(new Evento(2,
     * TG + (fila1.getMinService() + (fila1.getMaxService() - fila1.getMinService())
     * Next_random()),fila1));
     * }
     * 
     * if (fila2.status() < fila2.getCapacity()) {
     * fila2.in();
     * if (fila2.status() <= fila2.getServers()) {
     * escalonador.alocaEvento(new Evento(1, TG
     * + (fila2.getMinService() + (fila2.getMaxService() - fila2.getMinService()) *
     * Next_random()),fila2));
     * }
     * }
     * 
     * else {
     * fila2.loss();
     * }
     * 
     * }
     * 
     * public static void SimulacaoFilasEmTandem(Fila fila1, Fila fila2) {
     * 
     * Escalonador escalonador = new Escalonador();
     * 
     * // PRIMEIRO EVENTO DE CHEGADA
     * Evento evento1 = new Evento(0, 1.5,fila1);
     * escalonador.alocaEvento(evento1);
     * 
     * // Loop Simulacao
     * while (count < 100000) {
     * 
     * Evento nextEvent = escalonador.proxEvento();
     * // System.out.println("Novo Evento: " + nextEvent.toString());
     * 
     * if (nextEvent.getType() == Evento.CHEGADA) {
     * ChegadaTandem(nextEvent, escalonador, fila1, fila2);
     * 
     * } else if (nextEvent.getType() == Evento.SAIDA) {
     * SaidaTandem(nextEvent, escalonador, fila1, fila2);
     * 
     * } else if (nextEvent.getType() == Evento.PASSAGEM) {
     * Passagem(nextEvent, escalonador, fila1, fila2);
     * }
     * }
     * 
     * // PRINT FILA 1
     * System.out.printf("\nFila G/G/%d/%d", fila1.getServers(),
     * fila1.getCapacity());
     * 
     * System.out.println("\nEstado\t\tTempo\t\tProbabilidade");
     * 
     * for (int i = 0; i < fila1.getTimes().length; i++) {
     * double time = fila1.getTimes()[i];
     * double probability = (time / TG) * 100;
     * 
     * String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time,
     * probability);
     * output = output.replace(',', '.');
     * System.out.print(output);
     * 
     * }
     * 
     * System.out.println("\nPerdas: " + fila1.getLoss());
     * System.out.println("Tempo Global: " + TG);
     * System.out.println("\n");
     * 
     * // PRINT FILA 2
     * System.out.printf("\nFila G/G/%d/%d", fila2.getServers(),
     * fila2.getCapacity());
     * 
     * System.out.println("\nEstado\t\tTempo\t\tProbabilidade");
     * 
     * for (int i = 0; i < fila2.getTimes().length; i++) {
     * double time = fila2.getTimes()[i];
     * double probability = (time / TG) * 100;
     * 
     * String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time,
     * probability);
     * output = output.replace(',', '.');
     * System.out.print(output);
     * 
     * }
     * 
     * System.out.println("\nPerdas: " + fila2.getLoss());
     * System.out.println("Tempo Global: " + TG);
     * System.out.println("\n");
     * 
     * }
     * 
     */

    /////////////////////// LISTA DE FILAS ///////////////////////

    private static void ChegadaLista(Evento evento, Escalonador escalonador, Fila filaSource, Fila filaTarget,
            ArrayList<Fila> filas) {
        // acumulaTempo
        for (Fila fila : filas) {
            if (fila.status() >= 0) {
                fila.getTimes()[fila.status()] += evento.getTime() - TG;
            }
        }

        TG = evento.getTime();

        if (filaSource.status() < filaSource.getCapacity()) {
            filaSource.in();
            if (filaSource.status() <= filaSource.getServers()) {
                escalonador.alocaEvento(new Evento(2, TG
                        + (filaSource.getMinService()
                                + (filaSource.getMaxService() - filaSource.getMinService()) * Next_random()),
                        filaSource));
                count++;
            }
        } else {
            filaSource.loss();
        }

        // ALOCA CHEGADA NA FILA
        escalonador.alocaEvento(new Evento(0,
                TG + (filaSource.getMinArrival()
                        + (filaSource.getMaxArrival() - filaSource.getMinArrival()) * Next_random()),
                filaSource));
        count++;

    }

    private static void SaidaLista(Evento evento, Escalonador escalonador, Fila filaSource, Fila filaTarget,
            ArrayList<Fila> filas) {

        // acumulaTempo
        for (Fila fila : filas) {
            if (fila.status() >= 0) {
                fila.getTimes()[fila.status()] += evento.getTime() - TG;
            }
        }

        TG = evento.getTime();

        filaTarget.out();
        if (filaTarget.status() >= filaTarget.getServers()) {
            escalonador.alocaEvento(new Evento(1,
                    TG + (filaTarget.getMinService()
                            + (filaTarget.getMaxService() - filaTarget.getMinService()) * Next_random()),
                    filaSource));
            count++;
        }

    }

    private static void PassagemLista(Evento evento, Escalonador escalonador, Fila filaSource, Fila filaTarget,
            ArrayList<Fila> filas) {
        // acumulaTempo
        for (Fila fila : filas) {
            if (fila.status() >= 0) {
                fila.getTimes()[fila.status()] += evento.getTime() - TG;
            }
        }

        TG = evento.getTime();

        filaSource.out();
        if (filaSource.status() >= filaSource.getServers()) {
            escalonador.alocaEvento(new Evento(2,
                    TG + (filaSource.getMinService()
                            + (filaSource.getMaxService() - filaSource.getMinService()) * Next_random()),
                    filaTarget));
        }

        if (filaTarget.status() < filaTarget.getCapacity()) {
            filaTarget.in();
            if (filaTarget.status() <= filaTarget.getServers()) {
                escalonador.alocaEvento(new Evento(1, TG
                        + (filaTarget.getMinService()
                                + (filaTarget.getMaxService() - filaTarget.getMinService()) * Next_random()),
                        filaSource));
            }
        }

        else {
            filaTarget.loss();
        }
    }

    public static void SimulacaoListaFilas(ArrayList<Fila> filas, ArrayList<Connection> network) {
        Escalonador escalonador = new Escalonador();

        // Primeiro evento de chegada
        Evento evento1 = new Evento(0, 2.0, filas.get(0));
        escalonador.alocaEvento(evento1);
        // System.out.println("CHEGADA");

        // Loop de simulação
        while (count < 100000) {
            Evento nextEvent = escalonador.proxEvento();

            if (nextEvent.getType() == Evento.CHEGADA) {
                // System.out.println("CHEGADA");

                Fila filaSource = nextEvent.getFila();
                int filaTargetIndex = selectTarget(filas, filaSource, network);

                if (filaTargetIndex != -1 && filaTargetIndex < filas.size()) {
                    Fila filaTarget = filas.get(filaTargetIndex);
                    ChegadaLista(nextEvent, escalonador, filaSource, filaTarget, filas);
                } else {
                    System.out.println("No valid target found. CHEGADA.");
                }

            } else if (nextEvent.getType() == Evento.SAIDA) {
                // System.out.println("SAIDA");

                Fila filaSource = nextEvent.getFila();
                int filaTargetIndex = selectTarget(filas, filaSource, network);

                if (filaTargetIndex != -1 && filaTargetIndex < filas.size()) {
                    Fila filaTarget = filas.get(filaTargetIndex);
                    SaidaLista(nextEvent, escalonador, filaSource, filaTarget, filas);
                } else {
                    // SaidaLista(evento1, escalonador, filaSource, filaSource, filas);
                    System.out.println("No valid target found. SAIDA.");
                }

            } else if (nextEvent.getType() == Evento.PASSAGEM) {
                // System.out.println("PASSAGEM");

                Fila filaSource = nextEvent.getFila();
                int filaTargetIndex = selectTarget(filas, filaSource, network);

                if (filaTargetIndex != -1 && filaTargetIndex < filas.size()) {
                    Fila filaTarget = filas.get(filaTargetIndex);
                    PassagemLista(nextEvent, escalonador, filaSource, filaTarget, filas);
                } else {
                    System.out.println("No valid target found. PASSAGEM");
                }

            }

            // 1 VEZ LOOP
            // break;
        }

        // PRINT FILAS
        for (Fila fila : filas) {
            System.out.printf("\nFila G/G/%d/%d", fila.getServers(), fila.getCapacity());

            System.out.println("\nEstado\t\tTempo\t\tProbabilidade");

            for (int i = 0; i <= fila.getCapacity(); i++) {
                double time = fila.getTimes()[i];
                double probability = (time / TG) * 100;

                String output = String.format("%-8d\t%-10.2f\t%.2f%%\n", i, time, probability);
                output = output.replace(',', '.');
                System.out.print(output);

            }
            System.out.println("\nPerdas: " + fila.getLoss());
        }
        System.out.println("Tempo Global: " + TG);
        System.out.println("\n");
    }

    public static Fila getFilaByName(String name, ArrayList<Fila> filas) {
        for (Fila fila : filas) {
            if (fila.getName().equals(name)) {
                return fila;
            }
        }
        return null; // Fila not found
    }

    public static int selectTarget(ArrayList<Fila> filas, Fila filaSource, ArrayList<Connection> network) {
        int filaTargetIndex = -1;

        // Filter connections to only include those originating from filaSource
        List<Connection> connections = network.stream()
                .filter(connection -> connection.getSource().equals(filaSource.getName()))
                .sorted(Comparator.comparingDouble(Connection::getProbability)) // Sort by connection probability
                .collect(Collectors.toList());

        double rand = Math.random();
        double cumulativeProbability = 0.0;

        for (Connection connection : connections) {
            cumulativeProbability += connection.getProbability();

            // System.out.println(connection.toString());

            // System.out.println("Rand: " + rand);
            // System.out.println("Prob: " + cumulativeProbability);

            if (cumulativeProbability > 1.0) {
                break; // Exit loop if cumulative probability exceeds 1.0
            }

            if (rand < cumulativeProbability) {
                filaTargetIndex = getIndexByName(connection.getTarget(), filas);
                break; // Once a target is selected, exit the loop
            }
        }

        // if (filaTargetIndex != -1) {
        // System.out.println("Target Selected: " +
        // filas.get(filaTargetIndex).getName());
        // } else {
        // System.out.println("No valid target found.");
        // }

        return filaTargetIndex; // Return -1 if no valid target is found
    }

    public static int getIndexByName(String name, ArrayList<Fila> filas) {
        for (int i = 0; i < filas.size(); i++) {
            if (filas.get(i).getName().equals(name)) {
                return i; // Return index if fila name matches
            }
        }
        return -1; // Return -1 if fila not found
    }

}

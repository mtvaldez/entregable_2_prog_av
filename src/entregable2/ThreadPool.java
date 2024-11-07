package entregable2;

import java.util.PriorityQueue;
import java.util.concurrent.*;


public class ThreadPool {
    public static PriorityQueue<Pedido> pagoQueue = new PriorityQueue<>();
    public static PriorityQueue<Pedido> empaquetadoQueue = new PriorityQueue<>();
    public static PriorityQueue<Pedido> envioQueue = new PriorityQueue<>();

    public static Semaphore mutexPago = new Semaphore(1);
    public static Semaphore mutexEmpaquetado = new Semaphore(1);
    public static Semaphore mutexEnvio = new Semaphore(1);



    public static void main(String[] args) {

        ExecutorService regularExecutorPago = Executors.newFixedThreadPool(4);
        ExecutorService regularExecutorEmpaquetado = Executors.newFixedThreadPool(4);
        ExecutorService regularExecutorEnvio = Executors.newFixedThreadPool(4);

//        ExecutorService urgenteExecutorPago = Executors.newFixedThreadPool(1);
//        ExecutorService urgenteExecutorEmpaquetado = Executors.newFixedThreadPool(1);
//        ExecutorService urgenteExecutorEnvio = Executors.newFixedThreadPool(1);

        for (int i = 1; i <= 100; i++) {
            boolean urgente = (i % 10 == 0);
            Pedido pedido = new Pedido(i, urgente);
            pagoQueue.add(pedido);
        }
        long startTime = System.nanoTime();
        while (true) {
            regularExecutorPago.execute(new Pago());
            regularExecutorEmpaquetado.execute(new Empaquetado());
            regularExecutorEnvio.execute(new Envio());

//            urgenteExecutorPago.execute(new Pago());
//            urgenteExecutorEmpaquetado.execute(new Empaquetado());
//            urgenteExecutorEnvio.execute(new Envio());

            if (pagoQueue.isEmpty() && empaquetadoQueue.isEmpty() && envioQueue.isEmpty()) {
                break;
            }
        }

        regularExecutorPago.shutdown();
        regularExecutorEmpaquetado.shutdown();
        regularExecutorEnvio.shutdown();

//        urgenteExecutorPago.shutdown();
//        urgenteExecutorEmpaquetado.shutdown();
//        urgenteExecutorEnvio.shutdown();

        try {
            regularExecutorPago.awaitTermination(4, TimeUnit.SECONDS);
            regularExecutorEmpaquetado.awaitTermination(4, TimeUnit.SECONDS);
            regularExecutorEnvio.awaitTermination(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime; // Tiempo en nanosegundos
        double elapsedSeconds = elapsedTime / 1_000_000_000.0; // Convertir a segundos

        System.out.println("Tiempo total de procesamiento: " + elapsedSeconds + " segundos");
    }
//-  3 threads: 45.0821 segundos
//-  4 threads: 18.9754 segundos
//-  5 threads: 15.0713 segundos
//-  100 threads: 0.4924 segundos

}

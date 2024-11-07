package entregable2;

import static entregable2.ThreadPool.envioQueue;
import static entregable2.ThreadPool.mutexEnvio;

public class Envio implements Runnable {
    private Pedido pedido;

    @Override
    public void run() {
        try {
            mutexEnvio.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.pedido = envioQueue.poll();

        if (pedido == null) {
            mutexEnvio.release();
            return;
        }
        mutexEnvio.release();
        System.out.println("Realizando envio..." + pedido.getId());
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

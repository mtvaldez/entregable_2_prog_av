package entregable2;

import static entregable2.ThreadPool.*;

public class Empaquetado implements Runnable {
    private Pedido pedido;

    @Override
    public void run() {
        try {
            mutexEmpaquetado.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.pedido = empaquetadoQueue.poll();

        if (pedido == null) {
            mutexEmpaquetado.release();
            return;
        }
        mutexEmpaquetado.release();
        System.out.println("Realizando empaquetado..." + pedido.getId());
        try {
            Thread.sleep(400);
            mutexEnvio.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        envioQueue.add(pedido);
        mutexEnvio.release();
    }
}

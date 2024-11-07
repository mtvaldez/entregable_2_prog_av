package entregable2;

import static entregable2.ThreadPool.*;

public class Pago implements Runnable {
    private Pedido pedido;

    @Override
    public void run() {
        try {
            mutexPago.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.pedido = pagoQueue.poll();
        if (pedido == null) {
            mutexPago.release();
            return;
        }

        mutexPago.release();
        System.out.println("Realizando pago..." + pedido.getId());
        try {
            Thread.sleep(400);
            mutexEmpaquetado.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        empaquetadoQueue.add(pedido);
        mutexEmpaquetado.release();
    }
}

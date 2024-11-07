package entregable2;

public class Pedido implements Comparable<Pedido> {

    private final int id;
    private final boolean urgente;
    private boolean empaquetado = false;
    private boolean pago = false;
    private boolean envio = false;
    public Pedido(int id, boolean urgente) {
        this.id = id;
        this.urgente = urgente;
    }

    public int getId() {
        return id;
    }
    public boolean isUrgente() {
        return urgente;
    }

    public boolean isEmpaquetado() {
        return empaquetado;
    }
    public boolean isPago() {
        return pago;
    }
    public boolean isEnvio() {
        return envio;
    }

    public void setEmpaquetado(boolean empaquetado) {
        this.empaquetado = empaquetado;
    }
    public void setPago(boolean pago) {
        this.pago = pago;
    }
    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    @Override
    public int compareTo(Pedido o) {
        if (this.urgente && !o.urgente) {
            return -1;
        } else if (!this.urgente && o.urgente) {
            return 1;
        }
        return Integer.compare(this.id, o.id);
    }
}

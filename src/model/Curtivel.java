package model;

/**
 * Define o comportamento mínimo de qualquer entidade que pode receber curtidas.
 */
public interface Curtivel {

    long getId();
    long getQtdCurtidas();
    void setQtdCurtidas(long qtdCurtidas);
}

package game;

import java.util.ArrayList;

public class TodasAcoes {

    ArrayList<Acao> acoes = new ArrayList<Acao>();

    public void criarAcoes() {
        for (int i = 0; i < 10; i++) {
            acoes.add(new Acao());
        }
    }

    public void imprimir() {
        for (int i = 0; i < 10; i++) {
            Acao a = acoes.get(i);
            System.out.print(" Ataque: " + a.ataque);
            System.out.print(" Defesa: " + a.defesa);
            System.out.print(" Chute: " + a.chute);
            System.out.print(" Soco: " + a.soco);
            System.out.print(" Poder: " + a.poder);
            System.out.println("\n");
        }
    }
}

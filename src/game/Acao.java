package game;

import java.util.Random;

public class Acao {

    public int ataque;
    public int soco;
    public int chute;
    public int poder;
    public int defesa;

    public Acao() {
        this.sortearValores();
    }

    public Acao(int ataque, int soco, int chute, int poder, int defesa) {
        this.ataque = ataque;
        this.soco = soco;
        this.chute = chute;
        this.poder = poder;
        this.defesa = defesa;
    }
    
    public void sortearValores() {

        Random sorteio = new Random();

        if (sorteio.nextInt(2) == 0) {
            this.defesa = 1;
        } else {
            this.ataque = 1;

            if (sorteio.nextInt(3) == 0) {
                this.chute = 1;
                this.soco = 0;
                this.poder = 0;
            } else if (sorteio.nextInt(3) == 1) {
                this.chute = 0;
                this.soco = 1;
                this.poder = 0;
            } else {
                this.chute = 0;
                this.soco = 0;
                this.poder = 1;
            }
        }
    }
}

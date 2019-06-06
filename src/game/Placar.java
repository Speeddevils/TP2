/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


public class Placar {
    
    private int vitorias;
    private int derrotas;
    private int empate;
    private Jogador jogador;
    
    public Placar() {
    }

    public Placar(Jogador jogador) {
        this.vitorias = 0;
        this.derrotas = 0;
        this.jogador = jogador;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void vitoria() {
        this.vitorias ++;
    }

    public int derrota() {
        return derrotas++;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public int getEmpate() {
        return empate;
    }

    public void empate() {
        this.empate++;
    }

    
    @Override
    public String toString() {
        return "Jogador:\t" + jogador 
                + "\nVitorias:\t" + vitorias + 
                "\nDerrotas:\t" + derrotas;
    }
    
}

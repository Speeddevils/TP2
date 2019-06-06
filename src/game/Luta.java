package game;

import dao.BaseDao;
import entidade.Base;
import javax.swing.JTextArea;

public class Luta {

    private Jogador jogador1;
    private Jogador jogador2;
    public static int ENERGIA = 10;
    private final Base base;
    private final JTextArea jTextArea;

    Luta(Jogador jogador1, Jogador jogador2, JTextArea jTextArea) {

        this.base = jogador1.getBase();
        //lutadores
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogador1.setAcao(jogador1.getAcao());
        this.jogador2.setAcao(jogador2.getAcao());
        this.jTextArea = jTextArea;
        
        //permite quebra de linha
        jTextArea.setLineWrap(true);
//        jTextArea.setWrapStyleWord(true);
    }


    /**
     * Atualiza linha do banco com o vencedor da luta de acordo com as acoes de
     * cada um
     *
     * @return jogador vencedor
     */
    public Jogador lutar() {

        //prepare to fight
        jTextArea.append(jogador1.toString());
        jTextArea.append("\t\t\t\t\t");
        jTextArea.append("Vs\t");
        jTextArea.append(jogador2.getNome());
        jTextArea.append("\n");
        jTextArea.append("********************************************** FIGHT ************************************************\n");

        //Inicia a batalha
        printBatalha(jogador1, jogador2);

        if (jogador1.poder() && !jogador2.poder()) {
            jogador2.perdeEnergiaPeloPorder();
            jTextArea.append(jogador1.getNome() + " lançou poder, máquina perdeu energia\n");
            printPerdaEnergia(jogador1, jogador2);

        } else if (jogador2.poder() && !jogador1.poder()) {
            jogador1.perdeEnergiaPeloPorder();
            jTextArea.append("\t\t\t\t\t\t\t" + jogador2.getNome() + " lançou poder " + jogador1.getNome() + " perdeu energia\n");
            printPerdaEnergia(jogador1, jogador2);

        } else if (jogador1.ataque(jogador2) && !jogador2.defesa(jogador1)) {
            jogador2.perdeEnergia();
            jTextArea.append(jogador1.getNome() + " atacou, máquina perdeu energia\n");
            printPerdaEnergia(jogador1, jogador2);

        } else if (jogador2.ataque(jogador1) && !jogador1.defesa(jogador2)) {
            jogador1.perdeEnergia();
            jTextArea.append("\t\t\t\t\t\t\t" + jogador2.getNome() + " atacou, " + jogador1.getNome() + " perdeu energia\n");
            printPerdaEnergia(jogador1, jogador2);

        } else if (jogador1.ataque(jogador2) && jogador2.defesa(jogador1)) {
            jTextArea.append("\t\t\t\t\t\t\t" + jogador2.getNome() + " defendeu\n");

        } else if (jogador2.ataque(jogador1) && jogador1.defesa(jogador2)) {
            jTextArea.append(jogador1.getNome() + " defendeu\n");

        } else {
            jTextArea.append("Golpe neutralizado\n");
            //maquina ou jogador defenderam
        }
        jTextArea.append("=====================================================================================================\n");

        Jogador win = null;

        if (jogador1.getEnergia() < jogador2.getEnergia()) {
            win = jogador2;
        } else if (jogador1.getEnergia() > jogador2.getEnergia()) {
            win = jogador1;
        } else {
            win = null; //empate
        }

        return win;
    }

    /**
     * Atualiza o vencedor no banco
     *
     * @param j Jogador
     */
    public void updateVencedor(Jogador j) {

        base.setPontosLutador1(jogador1.getPontos());
        base.setPontosLutador2(jogador2.getPontos());
        if (j != null) {
            jTextArea.append(j.getNome() + " Venceu !\n");
            jTextArea.append("=====================================================================================================\n");

            if (j.getEnergia() == ENERGIA) {
                jTextArea.append("*****************************************************************************************************\n");
                jTextArea.append("Vitória perfeita da " + j.getNome() +"\n");
                jTextArea.append("*****************************************************************************************************\n");
            }
            base.setVencedor(j.getNome());
        } else {
            jTextArea.append("Empate\n");
            base.setVencedor("Empate");
        }

        Base find = BaseDao.getInstance().getById(base.getId());

        find.setPontosLutador1(jogador1.getPontos());
        find.setPontosLutador2(jogador2.getPontos());
        find.setVencedor(base.getVencedor());

        //atualiza no banco
        BaseDao.getInstance().merge(find);
    }

    void printPerdaEnergia(Jogador j, Jogador m) {
        jTextArea.append(j.getNome() + " energia: " + j.getEnergia());
        jTextArea.append("\t\t\t\t\t");
        jTextArea.append(m.getNome() + " energia: " + m.getEnergia());
        jTextArea.append("\n");
    }

    public void printBatalha(Jogador j, Jogador m) {

        jTextArea.append("Ataque: " + j.getAcao().ataque + " Defesa: " + j.getAcao().defesa
                + " Chute: " + j.getAcao().chute + " Soco: " + j.getAcao().soco
                + " Poder: " + j.getAcao().poder);
        jTextArea.append("\t|\t");
        jTextArea.append("Ataque: " + m.getAcao().ataque + " Defesa: " + m.getAcao().defesa
                + " Chute: " + m.getAcao().chute + " Soco: " + m.getAcao().soco
                + " Poder: " + m.getAcao().poder);
        jTextArea.append("\n");

    }
}

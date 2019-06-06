package game;

import entidade.Base;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {

    private int pontos;
    private int energia;
    private List<Modalidade> modalidades;
    private String nome;
    private String categoria;
    private Modalidade modalidade;
    private Acao acao;
    private Base base;

    public Jogador(Base base, int i) {
        this.base = base;

        this.modalidades = new ArrayList<Modalidade>();
        if (i == 0) {

            this.nome = base.getNomeLutador1();
            this.modalidade = getModalidade(Integer.parseInt(base.getModalidadeLutador1()));

            this.acao = new Acao(// ataque, soco, chute, poder, defesa
                    Integer.parseInt(base.getAtaqueLutador1()),
                    Integer.parseInt(base.getSocoLutador1()),
                    Integer.parseInt(base.getChuteLutador1()),
                    Integer.parseInt(base.getPoderLutador1()),
                    Integer.parseInt(base.getDefesaLutador1()));

        } else {

            this.nome = base.getNomeLutador2();
            this.modalidade = getModalidade(Integer.parseInt(base.getModalidadeLutador2()));
            this.acao = new Acao(// ataque, soco, chute, poder, defesa
                    Integer.parseInt(base.getAtaqueLutador1()),
                    Integer.parseInt(base.getSocoLutador1()),
                    Integer.parseInt(base.getChuteLutador1()),
                    Integer.parseInt(base.getPoderLutador1()),
                    Integer.parseInt(base.getDefesaLutador1()));

        }
        this.modalidades.add(modalidade);
        this.energia = 10;
    }

    public Jogador(String nome, String categoria, Acao acao, int modalidade) {
        this.modalidades = new ArrayList<Modalidade>();
        this.modalidade = getModalidade(modalidade);
        this.modalidades.add(this.modalidade);
        this.nome = nome;
        this.categoria = categoria;
        this.acao = acao;

    }

    public Jogador(String nome) {
        this.modalidade = new Boxe();
        this.modalidades = new ArrayList<Modalidade>();
        this.modalidades.add(modalidade);
        this.energia = 10;
        this.nome = nome;
    }

    private Modalidade getModalidade(int i) {

        if (i == 0) {
            return new Boxe();
        }

        if (i == 1) {
            this.setPontos(1000);
            return new JiuJitsu();
        }

        if (i == 2) {
            this.setPontos(2000);
            return new MuayThai();
        }

        if (i == 3) {
            this.setPontos(3000);
            return new Wrestling();
        }

        if (i == 4) {
            this.setPontos(4000);
            return new Karate();
        }

        return new Boxe();
    }

    void perdeEnergia() {
        this.energia--;
    }

    void perdeEnergiaPeloPorder() {
        this.energia = this.energia - (Luta.ENERGIA / 2);
        if (energia < 0) {
            energia = 0;//garante energia positiva
        }
    }

    //4. Soco e soco: ganha o jogador com mais habilidade (modalidades de lutas)
    //5. Chute e chute: ganha o jogador com mais habilidade (modalidades de lutas)
    //Minha regra 
    //  Soco e Chute => Ganha quem chutou
    boolean ataque(Jogador lutador) {
        Acao aj = this.acao;
        Acao am = lutador.getAcao();
        int hj = this.modalidades.size();
        int hm = lutador.getModalidades().size();

        if (aj.ataque == 1) {
            return true;
        } else if (aj.ataque == 1 && am.ataque == 1
                || aj.ataque == 1 && am.defesa == 1) {
            return false;
        } else if (aj.soco == 1 && aj.soco == 1 || aj.chute == 1 && aj.chute == 1) {
            if (hj > hm) {
                return true;
            }
            return false;
        } else if (aj.chute == 1 || aj.soco == 1) {
            //quem chutou que ganha (regra nao documentada)
            return true;
        } else if (aj.soco == 1 && aj.chute == 1) {
            //chute perde pra soco
            return false;
        }
        return false;
    }

    //Soco e defesa: nenhum jogador perde ponto
    //2. Chute e defesa: nenhum jogador perde ponto
    //3. Defesa e defesa: nenhum jogador perde ponto
    boolean defesa(Jogador lutador) {
        Acao aj = this.acao;
        Acao am = lutador.getAcao();
        //se ele defendeu e nao lancou o poder
        if (aj.defesa == 1 && am.poder == 0) {
            return true;
        }
        //lancou o poder
        return false;
    }

    //6. Poder contra qualquer outro golpe: ganha o jogador que deu o poder
    boolean poder() {

        Acao aj = this.acao;
        //Quando um jogador tiver uma ação ele poderá escolher 
        //      uma das modalidades que ele possuir,
        //para o poder da mÃ¡quina a modalidade deve ser aleatÃ³ria.
        if (aj.poder == 1) {

            //vai ser tudo aleatório
            //pq fazer isso selecionavel é bobagem
            if (this.modalidades.size() > 1) {

                Random r = new Random();
                int x = r.nextInt(this.modalidades.size());
                this.modalidade = this.modalidades.get(x);
            }

            return true;
        }
        return false;
    }

    public List<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public int getEnergia() {
        return energia;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;

        int contModalidades = this.modalidades.size();
        //modalidaed 1
        if (contModalidades == 0 && this.pontos >= 1000) {
            this.modalidades.add(new JiuJitsu());
        } else if (contModalidades == 1 && this.pontos >= 2000) {
            this.modalidades.add(new MuayThai());
        } else if (contModalidades == 2 && this.pontos >= 3000) {
            this.modalidades.add(new Wrestling());
        } else if (contModalidades == 3 && this.pontos >= 4000) {
            this.modalidades.add(new Karate());
        } else {
            //todas a categorias foi concedidas
        }
    }

    boolean isEnergia() {
        return this.energia > 0;
    }

    public String getNome() {
        return nome;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public Acao getAcao() {
        return acao;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome;
    }

}

package entidade;

import game.Acao;
import game.Jogador;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Classe que recebe os dados vindo do arquivo txt mapeado para o banco de dados
 *
 * Persistida no Banco
 */
@Entity
@Table(name = "Base")
public class Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String contadorLuta;
    @Column
    private String nomeLutador1;
    @Column
    private String categoriaLutador1;
    @Column
    private String paisLutador1;
    @Column
    private String sexoLutador1;
    @Column
    private String defesaLutador1;
    @Column
    private String ataqueLutador1;
    @Column
    private String socoLutador1;
    @Column
    private String chuteLutador1;
    @Column
    private String poderLutador1;
    @Column
    private String modalidadeLutador1;
    @Column
    private String nomeLutador2;
    @Column
    private String categoriaLutador2;
    @Column
    private String paisLutador2;
    @Column
    private String sexoLutador2;
    @Column
    private String defesaLutador2;
    @Column
    private String ataqueLutador2;
    @Column
    private String socoLutador2;
    @Column
    private String chuteLutador2;
    @Column
    private String poderLutador2;
    @Column
    private String modalidadeLutador2;
    @Column
    private int liga;

    @Column
    private String vencedor;

    @Column
    private int pontosLutador1;

    @Column
    private int pontosLutador2;

    @Transient
    private Jogador jogador1;

    @Transient
    private Jogador jogador2;

    public Base() {
        this.pontosLutador1 = 0;
        this.pontosLutador2 = 0;
    }

    public Base(String line) {

        String[] split = line.split(";");

//         try {
//            //primeira posicao é vazia
//            this.id = Integer.parseInt(split[1]);
//             System.out.println(id);
//        } catch (Exception e) {
//        } 
        this.contadorLuta = split[2];
        this.nomeLutador1 = split[3];
        this.categoriaLutador1 = split[4];
        this.paisLutador1 = split[5];
        this.sexoLutador1 = split[6];
        
        this.defesaLutador1 = split[7];
        this.ataqueLutador1 = split[8];
        this.socoLutador1 = split[9];
        this.chuteLutador1 = split[10];
        this.poderLutador1 = split[11];
        
        this.modalidadeLutador1 = split[12];

        this.nomeLutador2 = split[13];
        this.categoriaLutador2 = split[14];
        this.paisLutador2 = split[15];
        this.sexoLutador2 = split[16];
        
        this.defesaLutador2 = split[17];
        this.ataqueLutador2 = split[18];
        this.socoLutador2 = split[19];
        this.chuteLutador2 = split[20];
        this.poderLutador2 = split[21];
        
        this.modalidadeLutador2 = split[22];

        this.jogador1 = new Jogador(this.nomeLutador1,
                this.categoriaLutador1,
                new Acao(
                        Integer.parseInt(this.ataqueLutador1),
                        Integer.parseInt(this.socoLutador1),
                        Integer.parseInt(this.chuteLutador1),
                        Integer.parseInt(this.poderLutador1),
                        Integer.parseInt(this.defesaLutador1)),
                Integer.parseInt(this.modalidadeLutador1)
        );
        this.jogador2 = new Jogador(this.nomeLutador2,
                this.categoriaLutador2,
                new Acao(
                        Integer.parseInt(this.ataqueLutador2),
                        Integer.parseInt(this.socoLutador2),
                        Integer.parseInt(this.chuteLutador2),
                        Integer.parseInt(this.poderLutador2),
                        Integer.parseInt(this.defesaLutador2)
                ),
                Integer.parseInt(this.modalidadeLutador2)
        );

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContadorLuta() {
        return contadorLuta;
    }

    public void setContadorLuta(String contadorLuta) {
        this.contadorLuta = contadorLuta;
    }

    public String getNomeLutador1() {
        return nomeLutador1;
    }

    public void setNomeLutador1(String nomeLutador1) {
        this.nomeLutador1 = nomeLutador1;
    }

    public String getCategoriaLutador1() {
        return categoriaLutador1;
    }

    public void setCategoriaLutador1(String categoriaLutador1) {
        this.categoriaLutador1 = categoriaLutador1;
    }

    public String getPaisLutador1() {
        return paisLutador1;
    }

    public void setPaisLutador1(String paisLutador1) {
        this.paisLutador1 = paisLutador1;
    }

    public String getSexoLutador1() {
        return sexoLutador1;
    }

    public void setSexoLutador1(String sexoLutador1) {
        this.sexoLutador1 = sexoLutador1;
    }

    public String getDefesaLutador1() {
        return defesaLutador1;
    }

    public void setDefesaLutador1(String defesaLutador1) {
        this.defesaLutador1 = defesaLutador1;
    }

    public String getAtaqueLutador1() {
        return ataqueLutador1;
    }

    public void setAtaqueLutador1(String ataqueLutador1) {
        this.ataqueLutador1 = ataqueLutador1;
    }

    public String getSocoLutador1() {
        return socoLutador1;
    }

    public void setSocoLutador1(String socoLutador1) {
        this.socoLutador1 = socoLutador1;
    }

    public String getChuteLutador1() {
        return chuteLutador1;
    }

    public void setChuteLutador1(String chuteLutador1) {
        this.chuteLutador1 = chuteLutador1;
    }

    public String getPoderLutador1() {
        return poderLutador1;
    }

    public void setPoderLutador1(String poderLutador1) {
        this.poderLutador1 = poderLutador1;
    }

    public String getModalidadeLutador1() {
        return modalidadeLutador1;
    }

    public void setModalidadeLutador1(String modalidadeLutador1) {
        this.modalidadeLutador1 = modalidadeLutador1;
    }

    public String getNomeLutador2() {
        return nomeLutador2;
    }

    public void setNomeLutador2(String nomeLutador2) {
        this.nomeLutador2 = nomeLutador2;
    }

    public String getCategoriaLutador2() {
        return categoriaLutador2;
    }

    public void setCategoriaLutador2(String categoriaLutador2) {
        this.categoriaLutador2 = categoriaLutador2;
    }

    public String getPaisLutador2() {
        return paisLutador2;
    }

    public void setPaisLutador2(String paisLutador2) {
        this.paisLutador2 = paisLutador2;
    }

    public String getSexoLutador2() {
        return sexoLutador2;
    }

    public void setSexoLutador2(String sexoLutador2) {
        this.sexoLutador2 = sexoLutador2;
    }

    public String getDefesaLutador2() {
        return defesaLutador2;
    }

    public void setDefesaLutador2(String defesaLutador2) {
        this.defesaLutador2 = defesaLutador2;
    }

    public String getAtaqueLutador2() {
        return ataqueLutador2;
    }

    public void setAtaqueLutador2(String ataqueLutador2) {
        this.ataqueLutador2 = ataqueLutador2;
    }

    public String getSocoLutador2() {
        return socoLutador2;
    }

    public void setSocoLutador2(String socoLutador2) {
        this.socoLutador2 = socoLutador2;
    }

    public String getChuteLutador2() {
        return chuteLutador2;
    }

    public void setChuteLutador2(String chuteLutador2) {
        this.chuteLutador2 = chuteLutador2;
    }

    public String getPoderLutador2() {
        return poderLutador2;
    }

    public void setPoderLutador2(String poderLutador2) {
        this.poderLutador2 = poderLutador2;
    }

    public String getModalidadeLutador2() {
        return modalidadeLutador2;
    }

    public void setModalidadeLutador2(String modalidadeLutador2) {
        this.modalidadeLutador2 = modalidadeLutador2;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public int getLiga() {
        return liga;
    }

    public void setLiga(int liga) {
        this.liga = liga;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public int getPontosLutador1() {
        return pontosLutador1;
    }

    public void setPontosLutador1(int pontosLutador1) {
        this.pontosLutador1 = pontosLutador1;
    }

    public int getPontosLutador2() {
        return pontosLutador2;
    }

    public void setPontosLutador2(int pontosLutador2) {
        this.pontosLutador2 = pontosLutador2;
    }
    

    @Override
    public String toString() {
        return "ArquivoDTO{" + "id=" + id + ", contadorLuta=" + contadorLuta + ", nomeLutador1=" + nomeLutador1 + ", categoriaLutador1=" + categoriaLutador1 + ", paisLutador1=" + paisLutador1 + ", sexoLutador1=" + sexoLutador1 + ", defesaLutador1=" + defesaLutador1 + ", ataqueLutador1=" + ataqueLutador1 + ", socoLutador1=" + socoLutador1 + ", chuteLutador1=" + chuteLutador1 + ", poderLutador1=" + poderLutador1 + ", modalidadeLutador1=" + modalidadeLutador1 + ", nomeLutador2=" + nomeLutador2 + ", categoriaLutador2=" + categoriaLutador2 + ", paisLutador2=" + paisLutador2 + ", sexoLutador2=" + sexoLutador2 + ", defesaLutador2=" + defesaLutador2 + ", ataqueLutador2=" + ataqueLutador2 + ", socoLutador2=" + socoLutador2 + ", chuteLutador2=" + chuteLutador2 + ", poderLutador2=" + poderLutador2 + ", modalidadeLutador2=" + modalidadeLutador2 + '}';
    }

}

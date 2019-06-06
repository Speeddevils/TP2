/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Base;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ui.TableModelExecutorQuery;

public class BaseDao {

    private static BaseDao instance;
    protected EntityManager entityManager;
    private static boolean stdout = false;//log na saida padrao

    //Singleton
    public static BaseDao getInstance() {
        if (instance == null) {
            instance = new BaseDao();
        }
        return instance;
    }

    private BaseDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("LUTASPU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Base getById(final int id) {
        return entityManager.find(Base.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Base> findAll() {
        return entityManager.createQuery("FROM " + Base.class.getName()).getResultList();
    }

    public List<Base> findLiga(String liga) {
        return entityManager.createQuery("FROM " + Base.class.getName() + " where liga = " + liga).getResultList();
    }

    public void persist(Base o) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
            if (stdout) {
                System.out.println("Save successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Base o) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(o);
            entityManager.getTransaction().commit();
            if (stdout) {
                System.out.println("Update successfully " + o.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Base o) {
        try {
            entityManager.getTransaction().begin();
            o = entityManager.find(Base.class, o.getId());
            entityManager.remove(o);
            entityManager.getTransaction().commit();
            if (stdout) {
                System.out.println("Removed " + o.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final int id) {
        try {
            Base o = getById(id);
            remove(o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    Lutadores vencedores de cada luta
    public TableModelExecutorQuery vencedoresLuta() {

        String query = "select id AS 'Luta'\n, "
                + "nomeLutador1 AS 'Lutador1'\n,"
                + "nomeLutador2 AS 'Lutador2'\n,"
                + "vencedor AS 'Vencedor'\n "
                + "FROM lutas.base where vencedor is not null";

        return new TableModelExecutorQuery(executeQuery(query));
    }

//    Lutas ocorridas em cada liga (2015, 2016, 2017, 2018 e 2019)
    public TableModelExecutorQuery lutasByLiga() {

        String query = "SELECT count(id) AS 'Lutas'\n, liga AS 'Liga' FROM lutas.base group by liga";
        return new TableModelExecutorQuery(executeQuery(query));
    }

//    Pontos ganhos para cada jogador em cada luta
    public TableModelExecutorQuery pontosByJogador() {
        String query = "SELECT  	\n"
                + "	max(pontosLutador1) AS 'PontosJogador1',\n"
                + "	max(pontosLutador2) AS 'PontosJogador2',\n"
                + "	nomeLutador1 AS 'Lutador 1',\n"
                + "	nomeLutador2 AS 'Lutador 2'\n"
                + "FROM lutas.base\n"
                + "group by pontosLutador1,\n"
                + "		pontosLutador2,\n"
                + "		nomeLutador1,\n"
                + "		nomeLutador2";
        return new TableModelExecutorQuery(executeQuery(query));
    }

//    Número de vitórias por país
    public TableModelExecutorQuery vitoriasByPais() {
        String query = "SELECT  	\n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "	paisLutador1 AS 'Pais'\n"
                + "FROM lutas.base\n"
                + "where vencedor = nomeLutador1 \n"
                + "group by paisLutador1 \n"
                + "\n"
                + "UNION        \n" //union nao duplica
                + "SELECT  	\n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "	paisLutador2 AS 'Pais'\n"
                + "FROM lutas.base\n"
                + "where vencedor = nomeLutador2\n"
                + "group by vencedor,\n"
                + "		paisLutador2";
        return new TableModelExecutorQuery(executeQuery(query));

    }

//    Número de vitórias por jogador
    public TableModelExecutorQuery vitoriasByJogador() {

        String query = "SELECT  	\n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "    vencedor   AS 'Vencedor' \n"
                + "FROM lutas.base\n"
                + "where  nomeLutador1 = vencedor\n"
                + "group by paisLutador1\n"
                + "union  \n"
                + "SELECT  	\n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "    vencedor  AS 'Vencedor' \n"
                + "FROM lutas.base\n"
                + "where  nomeLutador2 = vencedor\n"
                + "group by paisLutador2";
        return new TableModelExecutorQuery(executeQuery(query));

    }

//    Maior vencedor por categoria
    public TableModelExecutorQuery vencedorByCategoria() {
        String query = "SELECT  	\n"
                + "    vencedor AS 'Vencedor',\n"
                + "    categoriaLutador1 AS 'Categoria'\n"
                + "FROM lutas.base\n"
                + "where  nomeLutador1 = vencedor\n"
                + "group by categoriaLutador1\n"
                + "union \n"
                + "SELECT  	\n"
                + "    vencedor AS 'Vencedor',\n"
                + "    categoriaLutador2 AS 'Categoria'\n"
                + "FROM lutas.base\n"
                + "where  nomeLutador2 = vencedor\n"
                + "group by categoriaLutador2\n";
        return new TableModelExecutorQuery(executeQuery(query));

    }

//    Número de lutas computadas no total
    public TableModelExecutorQuery countLutas() {
        String query = "SELECT count(id) AS 'Total Lutas' FROM lutas.base;";
        return new TableModelExecutorQuery(executeQuery(query));
    }

//    Nomes dos lutadores, categoria, país, sexo e quantidade de vitórias (Ordem crescente de vitórias de categoria)
    public TableModelExecutorQuery infoJogadores() {

        String query = "SELECT  \n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "	nomeLutador1 AS 'Lutador', \n"
                + "    categoriaLutador1 AS 'Categoria',\n"
                + "	paisLutador1 AS 'Pais', \n"
                + "	sexoLutador1 AS 'Sexo' \n"
                + "FROM lutas.base\n"
                + "where vencedor = nomeLutador1\n"
                + "group by\n"
                + "	vencedor,\n"
                + "    nomeLutador1, \n"
                + "    categoriaLutador1 ,\n"
                + "	paisLutador1, \n"
                + "	sexoLutador1 \n"
                + "    union \n"
                + "SELECT  \n"
                + "	count(vencedor) AS 'Vitorias',\n"
                + "	nomeLutador2 AS 'Lutador', \n"
                + "    categoriaLutador2 AS 'Categoria', \n"
                + "	paisLutador2 AS 'Pais', \n"
                + "	sexoLutador2 AS 'Sexo'\n"
                + "FROM lutas.base\n"
                + "where vencedor = nomeLutador2\n"
                + "group by\n"
                + "	vencedor,\n"
                + "    nomeLutador2, \n"
                + "    categoriaLutador2,\n"
                + "	paisLutador2, \n"
                + "	sexoLutador2";

        return new TableModelExecutorQuery(executeQuery(query));
    }

    public Connection getConexao() {

        //Configuração padrão de conexão com o banco de dados.
        //inicia as variaveis estaticas da classe
        //regra para conectar usando o URL fornecido pelo PropertiesFlp
        //url default
        String URL = "jdbc:mysql://127.0.0.1/lutas";
        String USER = "root";
        String PASSWORD = "toor";

        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conectando no MySQL...");

            return connection;

            //status da conexão
        } catch (RuntimeException | SQLException ex) {

            System.err.println("Erro de conexÃ£o");
            return null;
        }

    }

    private ResultSet executeQuery(String script) {

        Connection conexao = getConexao();

        try {

            System.out.println("Query MySQL");

            PreparedStatement ps = conexao.prepareStatement(script);

            return ps.executeQuery();

//            Statement stm = conexao.createStatement();
//            stm.execute(script);
//            stm.close();
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

}

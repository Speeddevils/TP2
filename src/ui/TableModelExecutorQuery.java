/*
 * Copyright (c) 2013, Filipe Rezende Campos. Todos os direitos reservados.
 * FLP SOFTWARE/CONFIDENCIAL. O Uso estÃ¡ sujeito aos termos.
 *
 * 
 *
 */
package ui;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Implementação de um {@link TableModel} para renderizar o {@link ResultSet} de
 * uma consulta ao banco
 *
 * @author Filipe Campos
 */
public class TableModelExecutorQuery extends AbstractTableModel {

    /**
     * Classe interna utilizada para armazenar o nome de uma coluna e a classe
     * Java correspondente ao tipo de dado da mesma
     */
    private static class Column {

        /**
         * A classe Java do tipo de dado da coluna
         */
        public final Class<?> CLASS;

        /**
         * O nome da coluna
         */
        public final String NAME;

        /**
         * Instancia um objeto {@link Column} com o tipo de dado e nome
         * informados
         *
         * @param type A classe Java correspondente ao tipo de dado da coluna
         * @param name O nome da coluna
         */
        public Column(final String name, final Class<?> type) {
            NAME = name;
            CLASS = type;
        }
    }

    /**
     * Classe interna utilizada para armazenar os valores de um registro da
     * tabela
     */
    private static class Row {

        /**
         * Os valores de cada coluna do registro
         */
        public final Object[] VALUES;

        /**
         * Instancia um objeto {@link Row} para o {@link ResultSet} informado
         *
         * @param rs O {@link ResultSet} da linha a ser processada
         * @throws SQLException
         */
        public Row(final ResultSet rs) throws SQLException {

            try {
                int columns = rs.getMetaData().getColumnCount();
                VALUES = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    VALUES[i - 1] = rs.getObject(i);
                }

            } catch (SQLException ex) {

                System.out.println("Falha ao obter os dados do script" + ex.getMessage());

                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Aviso", JOptionPane.ERROR_MESSAGE);
                throw ex;
            }
        }
    }

    // Classes serializável, declarar este atributo para não gerar warnings  
    private static final long serialVersionUID = 1L;

    // lista de colunas da tabela  
    private final List<Column> columns;
    // lista de registros da tabela  
    private final List<Row> lines;
    //status de edicao do modelo
    private boolean status;
    //se o query executado apresenta resultados
    private boolean dataResultSet;

    /**
     * Instancia um {@link ResultSetTableModel} para o {@link ResultSet}
     * informado
     *
     * @param rs O {@link ResultSet} que a {@link JTable} deste modelo irá
     * renderizar
     */
    public TableModelExecutorQuery(final ResultSet rs) {

        this.columns = new ArrayList<>();
        lines = new ArrayList<>();

        try {
            final ResultSetMetaData md = rs.getMetaData();

            final int count = md.getColumnCount();

            if (count == 0) {
                this.dataResultSet = false;
                return;
            } else {
                this.dataResultSet = true;
            }

            for (int i = 1; i <= count; i++) {
                try {

                    columns.add(new Column(md.getColumnName(i), Class.forName(md.getColumnClassName(i))));

                } catch (ClassNotFoundException ex) {

                    JOptionPane.showMessageDialog(null, "Falha indexar o nome das colunas do script executado"
                            + ex.getMessage()
                            + Arrays.toString(ex.getStackTrace()),
                            "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            }

            while (rs.next()) {

                lines.add(new Row(rs));

            }
        } catch (SQLException ex) {
            Logger.getLogger(TableModelExecutorQuery.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Falha ao executar o query");
        }
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).CLASS;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int column) {
        return columns.get(column).NAME;
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return lines.get(rowIndex).VALUES[columnIndex];
    }

    /**
     * @param rowIndex
     * @param columnIndex
     * @return o status de controle de edição.
     */
    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return status;
    }

    /**
     * Seta o status de controle de edição da tabela.
     * <p/>
     * Obs: O modelo de tabela dever ser setado antes da chamada desse método.
     *
     * @param status
     */
    public void setStatus(boolean status) {

        this.status = status;
    }

    /**
     * Limpa os dados do modelo de tabela.
     */
    public void limpar() {

        try {
            lines.clear();

            columns.clear();

        } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(null, "Falha ao limpar os dado da tabela",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);

        }
        fireTableDataChanged();

    }

    /**
     *
     * @return true se a lista possue dados caso contrário false.
     */
    public boolean isVazio() {

        return lines.isEmpty();
    }

    /**
     *
     * @return true se o resultSet possue dados caso contrário false.
     */
    public boolean isDataResultSet() {

        return dataResultSet;
    }
    
      /**
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        int rowsCount = this.lines.size() - 1;
        int columnsCount = this.columns.size() - 1;

        //se a coluna maior que o indice existe ou menor que zero
        if (columnIndex > columnsCount & columnIndex < 0) {

            JOptionPane.showMessageDialog(null, "Acesso a uma coluna fora do índice da tabela."
                    + "[" + columnIndex + "]" + "[" + rowIndex + "]",
                    "Aviso", JOptionPane.ERROR_MESSAGE);

        } else {

            //se a coluna maior que o indice existe ou menor que zero
            if (rowIndex > rowsCount && rowIndex < 0) {

                JOptionPane.showMessageDialog(null, "Acesso a linha fora do índice da tabela."
                        + "[" + columnIndex + "]" + "[" + rowIndex + "]",
                        "Aviso", JOptionPane.ERROR_MESSAGE);

            } else {

            }
        }

    }*/

}

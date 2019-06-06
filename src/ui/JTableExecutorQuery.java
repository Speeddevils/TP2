/**
 * Copyright (c) 2013, Filipe Rezende Campos. Todos os direitos reservados. FLP
 * SOFTWARE/CONFIDENCIAL. O Uso está sujeito aos termos.
 *
 *
 *
 *
 */
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class JTableExecutorQuery extends JTable {

    //Declara��o de variav�is
    private TableModelExecutorQuery modelo;
    //Fim da declara��o de variav�is
    /**
     * Cria um JTable
     */
    public JTableExecutorQuery() {
        inicializacao();

    }

    private void inicializacao() {
        
        //tamanho da tabela
        this.setPreferredScrollableViewportSize(new Dimension(500, 200));
        //largura das linhas
        this.setRowHeight(25);
        //matem o ajuste da coluna setado pelo usu�rio
        this.setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    public void setModeloRender(JTableExecutorQuery tabela) {

        TableCellRendererExecutorQuery renderer = new TableCellRendererExecutorQuery(tabela);

        setModeloRenderer(renderer);

        setModeloVisualDeColunas(renderer);
    }

    private void setModeloVisualDeColunas(TableCellRendererExecutorQuery renderer) {

        if (renderer == null) {
            throw new IllegalArgumentException("N�o � possiv�l setar um modelo visual de coluna nulo.");
        }

        //largura das linhas
        this.setRowHeight(25);

        //largura do cabecalho
        renderer.setLarguraCabecalho(28);

        renderer.setTamanhoMaxColuna(0, 150);
        renderer.setTamanhoMinColuna(0, 30);

    }

    /**
     * Seleciona todas a linhas da coluna especifica.
     *
     * @param column
     */
    public void selecionarLinhas(int column) {

        //inicio
        int inicio = this.getRowCount() - this.getRowCount();
        //fim
        int fim = this.getRowCount() - 1;

        //se houve pelo menos uma linha
        if (inicio >= 0) {

            //selecione todas as linhas do inicio ----->    ao fim
            setRowSelectionInterval(inicio, fim);
            //daquela coluna
            setColumnSelectionInterval(column, column);
        }
    }

    /**
     * Seleciona todas as linhas e colunas da tabela.
     */
    public void selecionarLinhasEColunas() {

        //inicio
        int inicio = this.getRowCount() - this.getRowCount();
        //fim
        int fim = this.getRowCount() - 1;

        if (inicio >= 0) {
            //selecione todas as linhas da tabela
            this.setRowSelectionInterval(inicio, fim);
        }

    }

    /**
     * Habilita\Desabilita a op��o de sele��o de uma c�lula por vez, se status
     * for true.
     *
     * @param status
     */
    public void setModoSelecaoUnicaDeCelula(boolean status) {

        //matem foco na tabela 
        this.requestFocus();

        // faz com que somente as colunas sejam selecionadas ou n�o
        this.setColumnSelectionAllowed(status);

        //  so � possiv�l selecionar uma �nica linha por vez. 
        //this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //selecionar uma linha por vez com intervalo.  
        //this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //somente uma �nica coluna por vez 
        //this.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ////slecionar somente uma coluna por vez com intervalo
        //this.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //habilita e desabilita a sele��o da c�lula (n�o o foco a sele��o)
        //this.setCellSelectionEnabled(true);
    }

    /**
     * Habilitar\Desabilitar a op��o de organizar o cabe�alho.
     *
     * @param status
     */
    public void setReorganizarCabecalho(boolean status) {

        //bloquea troca de colunas de lugar
        this.getTableHeader().setReorderingAllowed(status);

    }

    /**
     * Habilitar\Desabilitar a op��o de redimensionar as colunas.
     *
     * @param status
     */
    public void setRedimensionarColunas(boolean status) {

        //bloquear a as colunas sejam redimensionadas
        this.getTableHeader().setResizingAllowed(status);
    }

    /**
     * Seta o modelo de renderiza��o da tabela.
     *
     * @param renderer
     */
    private void setModeloRenderer(TableCellRendererExecutorQuery renderer) {

        if (renderer == null) {
            throw new IllegalArgumentException("N�o � possiv�l setar um renderer null.");
        }

        for (int i = 0; i < this.getColumnCount(); i++) {

            TableColumn coluna = this.getColumn(this.getColumnName(i));

            TableCellRenderer rendererOrig = coluna.getCellRenderer();

            if (rendererOrig == null) {
                rendererOrig = this.getDefaultRenderer(this.getColumnClass(i));
            }

            renderer = new TableCellRendererExecutorQuery(rendererOrig);

            coluna.setCellRenderer(renderer);
        }

    }

    public void setModelo(TableModelExecutorQuery modelo) {

        this.modelo = modelo;

        this.setModel(modelo);
    }

    /**
     * Modelo de tabela onde s�o indexados os query do banco de dados.
     *
     * @return O modelo de tabela.
     */
    public TableModelExecutorQuery getModelo() {

        return modelo;
    }

    /**
     * Cria um renderer customizado. Alinhamento de c�lulas e uma cor para a
     * linha quando selecionada.
     *
     */
    private class TableCellRendererExecutorQuery extends DefaultTableCellRenderer {

        /**
         * Construtor padr�o para iniciar a tabela.
         *
         * @param tabela
         */
        public TableCellRendererExecutorQuery(JTable tabela) {

            this.tabela = tabela;
        }

        /**
         * Construtor padr�o para iniciar o renderer.
         *
         * @param rendererOriginal
         */
        public TableCellRendererExecutorQuery(TableCellRenderer rendererOriginal) {
            this.rendererOriginal = rendererOriginal;
        }

        /**
         * Personaliza o renderer
         *
         * @param table
         * @param value
         * @param isSelected
         * @param hasFocus
         * @param row
         * @param column
         * @return Um render personalizado.
         * <li/> Texto: Centralizado
         * <li/> Linha selecionada: Azul escuro
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {

            Color corAzulClaro = new Color(153, 204, 255);

            Component cell = //se for diferente de nulo
                    (rendererOriginal != null)
                    //use o modelo passado por paramentro
                    ? rendererOriginal.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                    // se nao for referenciado use o modelo padrao
                    : super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            //se a celula for selecionada 
            if (isSelected) {
                //seta a linha de azul
                cell.setBackground(corAzulClaro);

            } else {
                //default sem cor
                cell.setBackground(null);
            }

            if (cell instanceof JLabel) {

                int alinhamento = JLabel.CENTER;

                JLabel rotulo = (JLabel) cell;

                rotulo.setHorizontalAlignment(alinhamento);
            }

            return cell;
        }

        /**
         * Seta a largura do cabe�alho.
         *
         * @param largura
         */
        public void setLarguraCabecalho(int largura) {

            DefaultTableCellRenderer renderer;

            if (this.tabela != null) {

                JTableHeader header = this.tabela.getTableHeader();

                //centralizando o cabecalho da tabela
                renderer = (DefaultTableCellRenderer) this.tabela.getTableHeader().getDefaultRenderer();

                //definindo posicao do cabecalho
                renderer.setHorizontalAlignment(SwingConstants.CENTER);

                //largura do cabecalho
                header.setPreferredSize(new Dimension(0, largura));

            } else {

                JOptionPane.showMessageDialog(null, "Falha ao setar a largura do cabe�alho.\n"
                        + "Tabela n�o foi referenciada", "Advert�ncia", JOptionPane.ERROR_MESSAGE);

            }
        }

        /**
         * Habilita\Desabilita o ajuste de tamanho autom�tico das colunas.
         *
         * @param AJUSTE
         */
        public void setAjusteTamanhoColuna(int AJUSTE) {

            tabela.setAutoResizeMode(AJUSTE);
        }

        /**
         * Seta o tamanho da coluna espec�fica.
         *
         * @param columnIndex
         * @param tamanho
         */
        public void setTamanhoDaColuna(int columnIndex, int tamanho) {

            if (tabela != null) {

                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                //seta a o tamanho da largura da coluna
                columnModel.getColumn(columnIndex).setPreferredWidth(tamanho);

                //tabela.getColumnModel().getColumn(columnIndex).setPreferredWidth(tamanho);
            } else {

                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho maximo da coluna: "
                        + columnIndex + " tamanho: " + tamanho, "Advertência", JOptionPane.ERROR_MESSAGE);

            }
        }

        /**
         * Seta o tamanhos de todas as colunas.
         *
         * @param tamanho
         */
        public void setTamanhoDasColunas(int tamanho) {

            if (tabela != null) {
                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                for (int j = 0; j < tabela.getColumnCount(); ++j) {
                    //seta a o tamanho da largura da coluna
                    columnModel.getColumn(j).setMaxWidth(tamanho);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho maximo das colunas",
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Seta o tamanho m�ximo da coluna espec�fica.
         *
         * @param columnIndex
         * @param tamanho
         */
        public void setTamanhoMaxColuna(int columnIndex, int tamanho) {

            if (tabela != null) {

                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                //redenrizando a posicao dos valores das cheques 
                //seta a o tamanho da largura da coluna
                columnModel.getColumn(columnIndex).setMaxWidth(tamanho);

            } else {

                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho maximo da coluna"
                        + columnIndex + " tamanho " + tamanho,
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);

            }
        }

        /**
         * Seta o tamanho m�ximo de todas as colunas.
         *
         * @param tamanho
         */
        public void setTamanhoMaxColunas(int tamanho) {

            if (tabela != null) {
                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                for (int j = 0; j < tabela.getColumnCount(); ++j) {
                    //seta a o tamanho da largura da coluna
                    columnModel.getColumn(j).setMaxWidth(tamanho);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho maximo das colunas",
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Seta o tamanho min�mo das colunas.
         *
         * @param tamanho
         */
        public void setTamanhoMinColunas(int tamanho) {

            if (tabela != null) {
                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                for (int j = 0; j < tabela.getColumnCount(); ++j) {
                    //seta a o tamanho da largura da coluna
                    columnModel.getColumn(j).setMinWidth(tamanho);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao posicionar os dados da tabela",
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Seta o tamanho min�mo da coluna espec�fica.
         *
         * @param columnIndex
         * @param tamanho
         */
        public void setTamanhoMinColuna(int columnIndex, int tamanho) {

            if (tabela != null) {

                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                //redenrizando a posicao dos valores das cheques 
                //seta a o tamanho da largura da coluna
                columnModel.getColumn(columnIndex).setMinWidth(tamanho);

            } else {

                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho da coluna da tabela",
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);

            }
        }

        /**
         * Seta a largura da coluna espec�fica.
         *
         * @param columnIndex
         * @param tamanho
         */
        public void setLarguraDaColuna(int columnIndex, int tamanho) {
            if (tabela != null) {

                //modelo de coluna
                TableColumnModel columnModel = tabela.getColumnModel();

                //seta a o tamanho da largura da coluna
                columnModel.getColumn(columnIndex).setPreferredWidth(tamanho);

            } else {
                JOptionPane.showMessageDialog(null, "Falha ao setar o tamanho da coluna da tabela",
                        "Advert�ncia", JOptionPane.ERROR_MESSAGE);
            }
        }
        //Declara��o de variav�is para customzacao
        protected JTable tabela;
        protected TableCellRenderer rendererOriginal;
        //Fim da declara��o

        //http://www.roseindia.net/java/example/java/swing/CustomCellRenderer.shtml
    }

}

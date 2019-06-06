package util;

import dao.BaseDao;
import entidade.Base;
import game.Luta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

public class FileUtil {
    
    /**
     * Popula o banco de dados com base nos arquivos txt
     * @throws FileNotFoundException 
     */
    public static void populateDatabase() throws FileNotFoundException {
        System.out.println("Populando base");
        File dir = new File(new File("").getAbsolutePath(), "data");
        
        if(!dir.exists()) {
        	throw new FileNotFoundException("Base de dados não encontrado");
        }
        
        FileUtil util = new FileUtil();
        List<File> files = util.listFiles(dir);

        for (File file : files) {
            //todos os arquivos txt
            if (file.toString().endsWith(".txt") && file.toString().contains("2010")) {
                try {
                    List<String> data = util.readFile(file);

                    for (String line : data) {
                        Base o = new Base(line);
                        o.setLiga(Integer.parseInt(file.getName()
                                .replace("liga", "")
                                .replace(".txt", "")));
                        //salva o registro no banco
                        BaseDao.getInstance().persist(o);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                //break;//um arquivo para teste

            }
        }
    }

    /**
     * Lista de arquivo utilizada internamente
     */
    private final List<File> listFile;

    public FileUtil() {
        this.listFile = new ArrayList<File>();
    }

    /**
     * Retorna todos os arquivos e subpasta do arquivo informado.
     * <br/>
     * Utiliza chamada recursiva
     *
     * @param file Arquivo (Recursivo)
     * @return uma lista com os arquivos do diretório.
     */
    public List<File> listFiles(File file) {

        if (!file.exists()) {

            System.err.println("Falha ao listar arquivos.");
            return listFile;
        }

        //retorna todos arquivos e diretorios do arquivo informado (subpastas e seus arquivos não serão listas.
        File[] files = file.listFiles();

        //vai add em em fila ao inves da pilha
        //Ex:
        //c:\dir
        //c:\dir\subdir
        //c:\dir\subdir\subdir
        listFile.add(file);

        if (files != null) {

            for (File dir : files) {
                listFiles(dir);
            }
        }

        return listFile;

    }

    /**
     * Ler os dados do arquivo
     *
     * @param file
     * @return Lista de string
     * @throws FileNotFoundException
     */
    public List<String> readFile(File file) throws FileNotFoundException {

        List<String> dados = new ArrayList<String>();

        if (!file.exists()) {

            throw new FileNotFoundException("Arquivo " + file.toString() + " nÃ£o existe");
        } else {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                //construtor que recebe o objeto do tipo arquivo
                fr = new FileReader(file);

                //construtor que recebe o objeto do tipo FileReader
                br = new BufferedReader(fr);

                //equanto houver mais linhas
                while (br.ready()) {

                    //lê a proxima linha
                    String linha = br.readLine();

                    dados.add(linha);
                }

            } catch (IOException ex) {

                System.out.println("Falha ao ler o arquivo: \"" + file + "\"");

            } finally {

                //se o arquivo foi criado com sucesso
                if (fr != null && br != null) {
                    try {

                        fr.close();
                        br.close();

                    } catch (IOException ex) {

                        ex.printStackTrace();
                    }
                }
            }
            System.out.println("Leitura concluída com sucesso.");
        }
        return dados;

    }

    

    public static void esperar() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

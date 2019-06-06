package game;

import dao.BaseDao;
import entidade.Base;

import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.JTextArea;
import util.FileUtil;

public class Liga {

	private final List<Base> base;

	public Liga() throws FileNotFoundException {

		// popula a base
		FileUtil.populateDatabase();

		// agora preciso recupera os jogadores de cada liga
		// quero somente a liga de 2010 para testar
		this.base = BaseDao.getInstance().findAll();

	}

	public Liga(JTextArea jTextArea1) throws FileNotFoundException {
		this();
	}

	public void popular(JTextArea jTextArea) {
		for (Base base1 : base) {
			// crie os jogadores com base nos dados do banco
			Jogador jogador1 = new Jogador(base1, 0);
			Jogador jogador2 = new Jogador(base1, 1);

			// FIGHT
			Luta luta = new Luta(jogador1, jogador2, jTextArea);

			// retorna o ganhador
			Jogador win = luta.lutar();

			// atualiza a linha do banco
			luta.updateVencedor(win);
		}
	}
}

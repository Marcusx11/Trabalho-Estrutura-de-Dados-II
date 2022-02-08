package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.TuplaVerticeDuracao;

public class Grafo {
	public int numeroVertices;
	public int numeroArestas;
	public boolean ehDigrafo;
	public ArrayList<Aresta>[] listaAdjacencias;

	@SuppressWarnings("unchecked")
	public Grafo(int numeroVertices, boolean ehDigrafo) {
		this.numeroVertices = numeroVertices;
		this.numeroArestas = 0;
		this.ehDigrafo = ehDigrafo;
		this.listaAdjacencias = new ArrayList[numeroVertices];

		for (int i = 0; i < this.numeroVertices; i++) {
			this.listaAdjacencias[i] = new ArrayList<Aresta>();
		}
	}

	public void insereAresta(int origem, int destino) {
		if ((origem >= 0 && origem < this.numeroVertices) && (destino >= 0 && destino < this.numeroVertices)) {
			this.listaAdjacencias[origem].add(new Aresta(destino));
			this.numeroArestas++;

			if (!this.ehDigrafo) {
				this.listaAdjacencias[destino].add(new Aresta(origem));
			}
		}
	}

	public void insereAresta(int origem, int destino, int qtdVoos, int numeroParadas, int duracaoVoo) {
		if ((origem >= 0 && origem < this.numeroVertices) && (destino >= 0 && destino < this.numeroVertices)) {
			this.listaAdjacencias[origem].add(new Aresta(destino, qtdVoos, numeroParadas, duracaoVoo));
			this.numeroArestas++;

			if (!this.ehDigrafo) {
				this.listaAdjacencias[destino].add(new Aresta(origem, qtdVoos, numeroParadas, duracaoVoo));
			}
		}
	}

	public boolean caminhoEntroAeroportos(int src, int dst, List<Integer> caminhoVertices, boolean[] visitados) {
		if (visitados[src])
			return false;
		if (src == dst)
			return true;

		visitados[src] = true;

		for (Aresta vizinho : this.listaAdjacencias[src]) {
			if (caminhoEntroAeroportos(vizinho.verticeDestino, dst, caminhoVertices, visitados)) {
				caminhoVertices.add(src);
				return true;
			}
		}

		return false;
	}

	public List<Integer> voosDiretos(int aeroporto, boolean isVoos) {
		if (!isVoos)
			return null;
		List<Integer> voosDir = new ArrayList<Integer>();

		for (Aresta a : this.listaAdjacencias[aeroporto]) {
			voosDir.add(a.verticeDestino);
		}

		return voosDir;
	}

	private TuplaVerticeDuracao retornaNoMaisPromissor(List<TuplaVerticeDuracao> lista) {
		if (!lista.isEmpty()) {
			int menorDuracao = lista.get(0).duracao;
			int index = lista.get(0).vertice;

			for (TuplaVerticeDuracao v : lista) {
				if (v.duracao < menorDuracao) {
					menorDuracao = v.duracao;
					index = v.vertice;
				}
			}

			// Removendo-se o elemento da lista
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).vertice == index) {
					lista.remove(i);
				}
			}

			return new TuplaVerticeDuracao(index, menorDuracao);
		}

		return null;
	}

	public int vooMenorDuracao(int src, int dst, List<Integer> verticesCaminho) {
		boolean[] visitados = new boolean[this.numeroVertices];
		int[] duracoesMinimas = new int[this.numeroVertices];
		Arrays.fill(duracoesMinimas, Integer.MAX_VALUE);
		duracoesMinimas[src] = 0;

		List<TuplaVerticeDuracao> filaPrioridade = new ArrayList<TuplaVerticeDuracao>();
		filaPrioridade.add(new TuplaVerticeDuracao(src, 0));

		// Percorrendo-se a fila de prioridade
		while (!filaPrioridade.isEmpty()) {
			TuplaVerticeDuracao verticeAtual = retornaNoMaisPromissor(filaPrioridade);
			visitados[verticeAtual.vertice] = true;

			if (duracoesMinimas[verticeAtual.vertice] < verticeAtual.duracao)
				continue;

			// Percorrendo-se as adjacências do vértice atual
			for (Aresta a : this.listaAdjacencias[verticeAtual.vertice]) {
				if (visitados[a.verticeDestino])
					continue;

				int novaDuracao = duracoesMinimas[verticeAtual.vertice] + a.duracaoVoo;
				if (novaDuracao < duracoesMinimas[a.verticeDestino]) {
					if (!verticesCaminho.contains(verticeAtual.vertice)) {
						verticesCaminho.add(verticeAtual.vertice);
					}

					duracoesMinimas[a.verticeDestino] = novaDuracao;
					filaPrioridade.add(new TuplaVerticeDuracao(a.verticeDestino, duracoesMinimas[a.verticeDestino]));
				}
			}

			// Chegou-se no vértice de destino
			if (verticeAtual.vertice == dst)
				return duracoesMinimas[dst];

		}

		return Integer.MAX_VALUE;
	}

	public double[] rotasMenorDuracaoEntreAeroportos(int src) {
		double[] duracoesMinimas = new double[this.numeroVertices];
		Arrays.fill(duracoesMinimas, Double.POSITIVE_INFINITY);
		boolean[] visitados = new boolean[this.numeroVertices];
		duracoesMinimas[src] = 0;

		List<TuplaVerticeDuracao> filaPrioridade = new ArrayList<TuplaVerticeDuracao>();
		filaPrioridade.add(new TuplaVerticeDuracao(src, 0));

		// Percorrendo-se a fila de prioridade
		while (!filaPrioridade.isEmpty()) {
			TuplaVerticeDuracao verticeAtual = retornaNoMaisPromissor(filaPrioridade);
			visitados[verticeAtual.vertice] = true;

			if (duracoesMinimas[verticeAtual.vertice] < verticeAtual.duracao)
				continue;

			// Percorrendo-se as adjacências do vértice atual
			for (Aresta a : this.listaAdjacencias[verticeAtual.vertice]) {
				if (visitados[a.verticeDestino])
					continue;

				double novaDuracao = duracoesMinimas[verticeAtual.vertice] + a.duracaoVoo;
				if (novaDuracao < duracoesMinimas[a.verticeDestino]) {
					duracoesMinimas[a.verticeDestino] = novaDuracao;
					filaPrioridade
							.add(new TuplaVerticeDuracao(a.verticeDestino, (int) duracoesMinimas[a.verticeDestino]));
				}
			}

		}

		return duracoesMinimas;
	}

	public Grafo geraGrafoVooOtimizado() {
		Grafo novoGrafo = new Grafo(this.numeroVertices, true);

		boolean[] visitados = new boolean[this.numeroVertices];
		int[] duracoesMinimas = new int[this.numeroVertices];
		Arrays.fill(duracoesMinimas, Integer.MAX_VALUE);
		
		for (int i = 0; i < this.numeroVertices; i++) {
			int duracaoMinima = 0;
			int w = -1;
			if (!this.listaAdjacencias[i].isEmpty()) {
				duracaoMinima = this.listaAdjacencias[i].get(0).duracaoVoo;
				w = this.listaAdjacencias[i].get(0).verticeDestino;
			}
			
			for (Aresta a : this.listaAdjacencias[i]) {
				if (visitados[a.verticeDestino]) continue; 
				
				if (a.duracaoVoo < duracaoMinima) {
					duracaoMinima = a.duracaoVoo;
					w = a.verticeDestino;
				}
			}
			
			novoGrafo.insereAresta(i, w, 0, 0, duracaoMinima);
			visitados[i] = true;
		}

		return novoGrafo;
	}

	@Override
	public String toString() {
		String retorno = "";

		retorno += "-- GRAFO LISTA DE ADJACÊNCIAS --\n";

		for (int i = 0; i < this.numeroVertices; i++) {
			retorno += "[ " + i + " ] -> ";
			for (Aresta a : this.listaAdjacencias[i]) {
				retorno += "( " + a.verticeDestino + " ) -> ";
			}
			retorno += "NULL\n";
		}

		return retorno;
	}

}

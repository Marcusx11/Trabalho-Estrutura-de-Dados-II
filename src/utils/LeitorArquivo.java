package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import models.Grafo;

public class LeitorArquivo {

	@SuppressWarnings("finally")
	public static Grafo[] retornaGrafoFromArquivo(String arquivo) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		Grafo rotasRetorno = null;
		Grafo voosRetorno = null;

		try {
			String linha = br.readLine();
			int c = 0;
			boolean podeContar = false;
			boolean isRotasAdjacencias = false;
			boolean isVoosAdjacencias = false;

			while (linha != null) {
				linha = br.readLine();
				if (linha == null)
					break;

				// Instanciando os grafos após contagem dos vértices
				if (podeContar && linha.equals("!")) {
					podeContar = false;
					rotasRetorno = new Grafo(c, false);
					voosRetorno = new Grafo(c, true);
					isRotasAdjacencias = true;
					continue;
				}

				// Contando-se os vértices
				if (podeContar) {
					c++;
				}

				if (linha.equals("v=")) {
					podeContar = true;
					continue;
				}

				if (isRotasAdjacencias && linha.equals("!")) {
					isRotasAdjacencias = false;
					continue;
				}
				// Adicionando adjacências das rotas
				if (isRotasAdjacencias) {
					String[] vertices = linha.split(" ");
					Vertice vPartida = Vertice.valueOf(vertices[0]);
					Vertice vDestino = Vertice.valueOf(vertices[1]);
					rotasRetorno.insereAresta(vPartida.aeroporto, vDestino.aeroporto);
					continue;
				}

				// Adicionando adjacências dos voos
				if (linha.equals("%=")) {
					isVoosAdjacencias = true;
					continue;
				}
				if (isVoosAdjacencias) {
					String[] informacoesVoos = linha.split("\\s+");

					// 1° CASO - airline e flight separados mas sem Meal especificada
					if (informacoesVoos[0].length() <= 2 && informacoesVoos.length == 13) {
						Vertice vPartida = Vertice.valueOf(informacoesVoos[2]);
						Vertice vDestino = Vertice.valueOf(informacoesVoos[4]);

						int qtdVoos = Integer.parseInt(informacoesVoos[1]);
						int paradas = Integer.parseInt(informacoesVoos[6]);

						// Calculando distância
						String[] partida = informacoesVoos[3].split("[A-Z]+|\\\\d+");
						int tempoPartida = Integer.parseInt(partida[0]);

						String[] destino = informacoesVoos[5].split("[A-Z]+|\\\\d+");
						int tempoChegada = Integer.parseInt(destino[0]);

						int duracaoVoo = Math.abs(tempoChegada - tempoPartida);

						voosRetorno.insereAresta(vPartida.aeroporto, vDestino.aeroporto, qtdVoos, paradas, duracaoVoo);
						continue;

						// 2° CASO - Airline e flight juntos mas COM Meal especificada
					} else if (informacoesVoos[0].length() > 2 && informacoesVoos.length == 13) {
						Vertice vPartida = Vertice.valueOf(informacoesVoos[1]);
						Vertice vDestino = Vertice.valueOf(informacoesVoos[3]);

						// Calculando quantidade de voos
						String[] qtdVoosEAirline = informacoesVoos[0].split("[A-Z]+|\\\\d+");
						int qtdVoos = Integer.parseInt(qtdVoosEAirline[1]);

						int paradas = Integer.parseInt(informacoesVoos[6]);

						// Calculando distância
						String[] partida = informacoesVoos[2].split("[A-Z]+|\\\\d+");
						int tempoPartida = Integer.parseInt(partida[0]);

						String[] destino = informacoesVoos[4].split("[A-Z]+|\\\\d+");
						int tempoChegada = Integer.parseInt(destino[0]);

						int duracaoVoo = Math.abs(tempoChegada - tempoPartida);

						voosRetorno.insereAresta(vPartida.aeroporto, vDestino.aeroporto, qtdVoos, paradas, duracaoVoo);
						continue;

						// 3° CASO - Airline e flight juntos mas SEM Meal especificada
					} else if (informacoesVoos[0].length() > 2 && informacoesVoos.length == 12) {
						Vertice vPartida = Vertice.valueOf(informacoesVoos[1]);
						Vertice vDestino = Vertice.valueOf(informacoesVoos[3]);

						// Calculando quantidade de voos
						String[] qtdVoosEAirline = informacoesVoos[0].split("[A-Z]+|\\\\d+");
						int qtdVoos = Integer.parseInt(qtdVoosEAirline[1]);

						int paradas = Integer.parseInt(informacoesVoos[5]);

						// Calculando distância
						String[] partida = informacoesVoos[2].split("[A-Z]+|\\\\d+");
						int tempoPartida = Integer.parseInt(partida[0]);

						String[] destino = informacoesVoos[4].split("[A-Z]+|\\\\d+");
						int tempoChegada = Integer.parseInt(destino[0]);

						int duracaoVoo = Math.abs(tempoChegada - tempoPartida);

						voosRetorno.insereAresta(vPartida.aeroporto, vDestino.aeroporto, qtdVoos, paradas, duracaoVoo);
						continue;

						// CASO GERAL - Airline e Flight separadas e com Meal especificada
					} else {
						Vertice vPartida = Vertice.valueOf(informacoesVoos[2]);
						Vertice vDestino = Vertice.valueOf(informacoesVoos[4]);

						int qtdVoos = Integer.parseInt(informacoesVoos[1]);
						int paradas = Integer.parseInt(informacoesVoos[7]);

						// Calculando distância
						String[] partida = informacoesVoos[3].split("[A-Z]+|\\\\d+");
						int tempoPartida = Integer.parseInt(partida[0]);

						String[] destino = informacoesVoos[5].split("[A-Z]+|\\\\d+");
						int tempoChegada = Integer.parseInt(destino[0]);

						int duracaoVoo = Math.abs(tempoChegada - tempoPartida);

						voosRetorno.insereAresta(vPartida.aeroporto, vDestino.aeroporto, qtdVoos, paradas, duracaoVoo);
						continue;
					}

				}

			}

		} catch (Exception e) {
			System.out.println("Houve algum erro ao ler o arquivo!");
			e.printStackTrace();
		} finally {
			br.close();
			Grafo[] retorno = new Grafo[2];
			retorno[0] = rotasRetorno;
			retorno[1] = voosRetorno;
			return retorno;
		}
	}
}

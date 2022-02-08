import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import models.Grafo;
import utils.LeitorArquivo;
import utils.Vertice;

public class Main {

	private static String mostraTodosVertices() {
		String retorno = "";

		for (Vertice vertice : Vertice.values()) {
			retorno += "(" + vertice.aeroporto + ") - " + vertice.nomeCompleto + "\t";
		}

		return retorno;
	}

	private static void mostrarCaminhoEntreRotas(Scanner leitor, Grafo rotas) {
		int aeroporto1 = -1;
		int aeroporto2 = -1;
		System.out.print("## Escolha 1 dos aeroportos abaixo" + " (Selecione um número correspondente) ##\n"
				+ mostraTodosVertices() + "\n-> ");
		aeroporto1 = leitor.nextInt();

		System.out.print("## Escolha outro aeroportos dos que estão abaixo"
				+ " (Selecione outro número correspondente) ##\n" + mostraTodosVertices() + "\n-> ");
		aeroporto2 = leitor.nextInt();

		// Tratamento de erro para algum número de aeroporto não correspondente
		if ((aeroporto1 >= 0 && aeroporto1 < rotas.numeroVertices)
				&& (aeroporto2 >= 0 && aeroporto2 < rotas.numeroVertices)) {
			List<Integer> caminhoVertices = new ArrayList<Integer>();
			boolean[] visitados = new boolean[rotas.numeroVertices];

			if (rotas.caminhoEntroAeroportos(aeroporto1, aeroporto2, caminhoVertices, visitados)) {
				Collections.reverse(caminhoVertices);
				caminhoVertices.add(aeroporto2);
				String path = "[ ";

				for (int i : caminhoVertices) {
					Vertice v = Vertice.valueOf(i);
					if (v != null) {
						path += "(" + v.aeroporto + ") - " + v.nomeCompleto;
					}
					path += ", ";
				}
				path += " ]\n\n";

				System.out.println(path);
			} else {
				System.out.println("Não há um caminho entre os vértices citados.\n\n");
			}
			return;
		}

		System.out.println("Insira números de aeroporto válidos!\n\n");
	}

	private static void mostrarVoosDiretos(Scanner leitor, Grafo voos) {
		int aeroporto = -1;
		System.out.print("## Escolha 1 dos aeroportos abaixo" + " (Selecione um número correspondente) ##\n"
				+ mostraTodosVertices() + "\n-> ");
		aeroporto = leitor.nextInt();
		String adj = "";

		// Tratamento de erro para algum número de aeroporto não correspondente
		if (aeroporto >= 0 && aeroporto < voos.numeroVertices) {
			List<Integer> voosDiretos = voos.voosDiretos(aeroporto, true);
			voosDiretos = new ArrayList<>(new HashSet<>(voosDiretos));

			// Mostrando casa aeroporto com conexão direta do aeroporto lido
			for (int i : voosDiretos) {
				Vertice v = Vertice.valueOf(i);
				if (v != null) {
					adj += "(" + v.aeroporto + ") - " + v.nomeCompleto + "\t";
				}
			}
			System.out.println(adj + "\n\n");
			return;
		}

		System.out.println("Insira números de aeroporto válidos!\n\n");
	}

	private static void determinarViagemMenorDuracao(Scanner leitor, Grafo voos) {
		int aeroporto1 = -1;
		int aeroporto2 = -1;
		System.out.print("## Escolha 1 dos aeroportos abaixo" + " (Selecione um número correspondente) ##\n"
				+ mostraTodosVertices() + "\n-> ");
		aeroporto1 = leitor.nextInt();

		System.out.print("## Escolha outro aeroportos dos que estão abaixo"
				+ " (Selecione outro número correspondente) ##\n" + mostraTodosVertices() + "\n-> ");
		aeroporto2 = leitor.nextInt();

		// Tratamento de erro para algum número de aeroporto não correspondente
		if ((aeroporto1 >= 0 && aeroporto1 < voos.numeroVertices)
				&& (aeroporto2 >= 0 && aeroporto2 < voos.numeroVertices)) {

			List<Integer> verticesRetorno = new ArrayList<Integer>();
			int duracaoMinima = voos.vooMenorDuracao(aeroporto1, aeroporto2, verticesRetorno);
			verticesRetorno.add(aeroporto2);

			if (duracaoMinima == Integer.MAX_VALUE) {
				System.out.println("Não há um caminho entre os aeroportos definidos.\n\n");
				return;
			}

			System.out.println("Segue abaixo o caminho mínimo: ");
			String adj = "[ ";

			for (int i : verticesRetorno) {
				Vertice v = Vertice.valueOf(i);
				if (v != null) {
					adj += "(" + v.aeroporto + ") - " + v.nomeCompleto + ",\t";
				}
			}
			System.out.println(adj + "] \n\n");
			System.out.println("Duração total de voo: " + duracaoMinima + "\n\n");
			return;
		}

		System.out.println("Insira números de aeroporto válidos!\n\n");
	}

	private static void definirVooDuracaoMinimaEntreTodos(Scanner leitor, Grafo voos) {
		int aeroporto = -1;
		System.out.print("## Escolha 1 dos aeroportos abaixo" + " (Selecione um número correspondente) ##\n"
				+ mostraTodosVertices() + "\n-> ");
		aeroporto = leitor.nextInt();
		String adj = "";
		// Tratamento de erro para algum número de aeroporto não correspondente
		if (aeroporto >= 0 && aeroporto < voos.numeroVertices) {
			double[] duracoes = voos.rotasMenorDuracaoEntreAeroportos(aeroporto);
			adj += "\n\nSegue abaixo as durações mínimas de rotas: \n\n";

			for (int i = 0; i < duracoes.length; i++) {
				if (duracoes[i] == Double.POSITIVE_INFINITY) {
					adj += "Não há caminho do aeroporto escolhido para este.\n";
				} else {
					Vertice v = Vertice.valueOf(i);
					adj += "(" + v.aeroporto + ") - " + v.nomeCompleto + " -> Duração de voo até este aeroporto: "
							+ duracoes[i] + "\n";
				}
			}

			System.out.println(adj);
			return;
		}

		System.out.println("Insira números de aeroporto válidos!\n\n");
	}
	
	private static void determinarCaminhoEntreAeroportos(Scanner leitor, Grafo grafo) {
		int aeroporto1 = -1;
		int aeroporto2 = -1;
		System.out.print("## Escolha 1 dos aeroportos abaixo" + " (Selecione um número correspondente) ##\n"
				+ mostraTodosVertices() + "\n-> ");
		aeroporto1 = leitor.nextInt();

		System.out.print("## Escolha outro aeroportos dos que estão abaixo"
				+ " (Selecione outro número correspondente) ##\n" + mostraTodosVertices() + "\n-> ");
		aeroporto2 = leitor.nextInt();
		String str = "";

		// Tratamento de erro para algum número de aeroporto não correspondente
		if ((aeroporto1 >= 0 && aeroporto1 < grafo.numeroVertices)
				&& (aeroporto2 >= 0 && aeroporto2 < grafo.numeroVertices)) {
			List<Integer> caminhoVertices = new ArrayList<Integer>();
			boolean[] visitados = new boolean[grafo.numeroVertices];
			if (grafo.caminhoEntroAeroportos(aeroporto1, aeroporto2, caminhoVertices, visitados)) {
				Collections.reverse(caminhoVertices);
				caminhoVertices.add(aeroporto2);
				
				if (caminhoVertices.size() <= 2) {
					Vertice v1 = Vertice.valueOf(aeroporto1);
					Vertice v2 = Vertice.valueOf(aeroporto2);
					
					str += "É possível ir de (" + v1.aeroporto +
							") - " + v1.nomeCompleto + " até (" + v2.aeroporto
							+ ") - " + v2.nomeCompleto + " sem trocar de aeroporto.";
				} else {
					str += "\n\nSegue abaixo o caminho entre os aeroportos definidos."
							+ "\nNeste caso foi necessário realizar a troca de aeroportos.\n[ ";
					for (int i : caminhoVertices) {
						Vertice v = Vertice.valueOf(i);
						if (v != null) {
							str += "(" + v.aeroporto + ") - " + v.nomeCompleto + ",\t";
						}
					}
				}
			}
			
			System.out.println(str + "] \n\n");
			return;
		}
		
		System.out.println("Insira números de aeroporto válidos!\n\n");
	}
	
	public static void gerarGrafoPercursoOtimizado(Scanner leitor, Grafo voos) {
		Grafo g = voos.geraGrafoVooOtimizado();
		
		System.out.println("## Segue abaixo o novo grafo gerado: " + g.toString() + "\n\n");
	}

	public static void main(String[] args) {
		try {
			Grafo[] grafos = LeitorArquivo.retornaGrafoFromArquivo("./src/utils/Flights_USA.txt");
			Grafo rotas = grafos[0];
			Grafo voos = grafos[1];
			int opcaoMenu = -1;
			int opcaoGrafo = -1;
			Scanner leitor = new Scanner(System.in);

			// TODO -> tirar grafo de teste no final do trabalho
			Grafo teste = new Grafo(5, true);
			teste.insereAresta(0, 1, 0, 0, 4);
			teste.insereAresta(0, 2, 0, 0, 1);
			teste.insereAresta(1, 3, 0, 0, 1);
			teste.insereAresta(2, 3, 0, 0, 5);
			teste.insereAresta(2, 1, 0, 0, 2);
			teste.insereAresta(3, 4, 0, 0, 3);

			do {
				System.out.print("## Escolha um dos grafos abaixo:\n1-ROTAS\n2-VOOS\n3-Sair do programa\n-> ");
				opcaoGrafo = leitor.nextInt();

				if (opcaoGrafo == 1) {
					System.out.print("## Escolha uma das opções abaixo:\n" + "" + "1 - Mostrar grafo\n"
							+ "2 - Mostrar caminho entre 2 aeroportos\n"
							+ "3 - Determinar se é possível atingir um aeroporto a partir de outro especificado\n"
							+ "7 - Sair do programa\n-> ");
					opcaoMenu = leitor.nextInt();

					switch (opcaoMenu) {
					case 1:
						System.out.println("## GRAFO DE ROTAS ##\n" + rotas.toString() + "\n\n");
						break;
					case 2:
						mostrarCaminhoEntreRotas(leitor, rotas);
						break;
					case 3:
						determinarCaminhoEntreAeroportos(leitor, rotas);
						break;
					}

				} else if (opcaoGrafo == 2) {
					System.out.print("## Escolha uma das opções abaixo:\n" + "1 - Mostrar grafo\n"
							+ "2 - Mostrar voos diretos a partir de um aeroporto\n"
							+ "3 - Determinar viagem de menor custo entre 2 aeroportos\n"
							+ "4 - Determinar viagem de menor duração de 1 aeroporto para todos os demais\n"
							+ "5 - Determinar se é possível atingir um aeroporto a partir de outro especificado\n"
							+ "6 - Gerar grafo de voos com percurso otimizado\n"
							+ "7 - Sair do programa\n-> ");
					opcaoMenu = leitor.nextInt();

					switch (opcaoMenu) {
					case 1:
						System.out.println("## GRAFO DE VOOS ##\n" + voos.toString() + "\n\n");
						break;
					case 2:
						mostrarVoosDiretos(leitor, voos);
						break;
					case 3:
						determinarViagemMenorDuracao(leitor, voos);
						break;
					case 4:
						definirVooDuracaoMinimaEntreTodos(leitor, voos);
						break;
					case 5:
						determinarCaminhoEntreAeroportos(leitor, voos);
						break;
					case 6:
						gerarGrafoPercursoOtimizado(leitor, voos);
						break;
					}
				}

			} while (opcaoMenu != 7 && opcaoGrafo != 3);

		} catch (IOException e) {
			System.out.println("Não foi possível encontrar o arquivo.");
			e.printStackTrace();
		}
	}
}

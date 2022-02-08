package models;

public class Aresta {
	public int verticeDestino;
	public int qtdVoos;
	public int numeroParadas;
	public int duracaoVoo;
	
	public Aresta(int verticeDestino) {
		this.verticeDestino = verticeDestino;
	}
	
	public Aresta(int verticeDestino, int qtdVoos, int numeroParadas, int duracaoVoo) {
		this.verticeDestino = verticeDestino;
		this.qtdVoos = qtdVoos;
		this.numeroParadas = numeroParadas;
		this.duracaoVoo = duracaoVoo;
	}
}

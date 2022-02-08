package utils;

public enum Vertice {
	ABQ(0, "Albuquerque, New Mexico"), ATL(1, "Atlanta, Georgia"), BNA(2, "Nashville, Tennessee"),
	BOS(3, "Boston, Massachusetts"), DCA(4, "Washington D.C. (National)"),
	DEN(5, "Denver, Colorado"), DFW(6, "Dallas/Fort Worth, Texas"), DTW(7, "Detroit, Michigan"),
	HOU(8, "Houston, Texas (Hobby)"), JFK(9, "New York (Kennedy)"), LAX(10, "Los Angeles, California"),
	MIA(11, "Miami, Florida"), MSP(12, "Minneapolis/St Paul, Minnesota"), MSY(13, "New Orleans, Louisiana"),
	ORD(14, "Chicago, Illinoiss"), PHL(15, "Philadelphia, Pennsilvania/Wilmington, Delaware"),
	PHX(16, "Phoenix, Arizona"), PVD(17, "Providence/Newport, Rhode Island"),
	RDU(18, "Raleigh/Durham, North Carolina"), SEA(19, "Seattle/Tacoma, Washington"),
	SFO(20, "San Francisco, California"), STL(21, "St Louis, Missouri"), TPA(22, "Tampa, Florida");

	public int aeroporto;
	public String nomeCompleto;

	private Vertice(int n, String nomeCompleto) {
		this.aeroporto = n;
		this.nomeCompleto = nomeCompleto;
	}

	private Vertice(int n) {
		this.aeroporto = n;
	}
	
	public static Vertice valueOf(int value) {
		for (Vertice v : Vertice.values()) {
			if (v.aeroporto == value) {
				return v;
			}
		}
		
		return null;
    }
}

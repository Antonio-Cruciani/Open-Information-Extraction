package Strutture;

import java.util.HashSet;
import java.util.Set;

public class TupleTable {
	Set<String> pagesVisited = new HashSet<String>();
	int i;

	public TupleTable(Set<String> pagesVisited, int i) {

		this.pagesVisited = pagesVisited;
		this.i = i;
	}

	public Set<String> getPagesVisited() {
		return pagesVisited;
	}

	public void setPagesVisited(Set<String> pagesVisited) {
		this.pagesVisited = pagesVisited;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}

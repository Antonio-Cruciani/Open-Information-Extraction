package spidy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Strutture.TupleTable;

public class Table {
	public static TupleTable LoadTable(String path) {
		Set<String> pagesVisited = new HashSet<String>();
		TupleTable tupla = new TupleTable(pagesVisited, 0);
		String fileName = path + "table.txt";
		ArrayList<String> Table = new ArrayList();
		String TableLine = null;

		int i, c;
		c = 0;
		i = 0;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Filosofia : carico tutto in memoria, ci lavoro ampliando la tabella e poi la
			// sostituisco con la precedente
			// è brutto come modus operandi ma è molto più facile da fare
			while ((TableLine = bufferedReader.readLine()) != null) {
				if (c == 0) {
					String linea[] = TableLine.split("\n");
					// System.out.println(linea[0]);
					i = Integer.parseInt(linea[0]);
				} else if (c > 0) {

					String linee[] = TableLine.split("\t");
					// System.out.println(linee[1]);
					Table.add(linee[0]);
				}
				c += 1;
			}
			Integer l = 0;
			for (String str : Table) {
				if (l != 0) {
					pagesVisited.add(str);
					System.out.print(str + "\n");
					// this.
				}
				l += 1;

			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
		tupla.setI(i);
		tupla.setPagesVisited(pagesVisited);
		return (tupla);
	}

	public static void WriteTable(ArrayList<String> Table, int i, String path) {
		// Scrivo la tabella

		String fileName = path + "table.txt";
		try {
			/*
			 * String FN = "/Users/antonio/Pages/table.txt"; File file = new File(FN);
			 * 
			 * 
			 * file.createNewFile();
			 */
			FileWriter writer = new FileWriter(fileName);
			writer.write(Integer.toString(i));
			writer.write("\n");
			for (String str : Table) {
				if (str != "\n") {
					writer.write(str);
					writer.write("\n");
				}

				// System.out.println(str);
			}

			writer.close();

			System.out.println("Tabella Scritta");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

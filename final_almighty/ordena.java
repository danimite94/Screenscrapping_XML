package final_almighty;

import generated.Citation;
import generated.Citations;
import generated.Publication;
import generated.Publications;

import java.util.ArrayList;

public class ordena {

	public static ArrayList<Citation> citac(Publication raes) {

		ArrayList<Integer> ordemano1 = new ArrayList<Integer>();
		ordemano1.clear();

		ArrayList<Citation> citorganizadas = new ArrayList<Citation>();

		for (int i1 = 0; i1 < raes.getCitations().getCitation().size(); i1++) {

			Citation cit = raes.getCitations().getCitation().get(i1);

			int ano = cit.getDate();

			if (ordemano1.isEmpty()) {
				ordemano1.add(0, ano);
				citorganizadas.add(0, cit);

			} else {
				for (int j = 0; j < ordemano1.size(); j++) {

					if (ano < ordemano1.get(j)) {
						if (ordemano1.size() == j + 1) {

							ordemano1.add(j + 1, ano);
							citorganizadas.add(j + 1, cit);
							break;
						}

						continue;
					} else if (ano == ordemano1.get(j)) {

						ordemano1.add(j, ano);
						citorganizadas.add(j, cit);
						break;
					} else {
						ordemano1.add(j, ano);
						citorganizadas.add(j, cit);
						break;
					}
				}

			}

		}

		return citorganizadas;
	}

}

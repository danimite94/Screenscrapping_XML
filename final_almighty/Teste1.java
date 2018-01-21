package final_almighty;

import generated.Ano;
import generated.Citation;
import generated.Citations;
import generated.ObjectFactory;
import generated.Publication;
import generated.Publications;
import generated.Researcher;
import generated.TotalCitAno;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Teste1 {

	public static void main(String[] args) throws JAXBException, IOException {
		try {
			Scanner in = new Scanner(System.in);

			System.out.println("Bem-Vindo!");
			System.out.print("Escolha inicio para range de anos: ");

			while (!in.hasNextDouble()) {
				System.out
						.println("valor introduzido está invalido, tem de introduzir valor inteiro");
				System.out.println("ponha um novo valor:");
				in.next();
			}
			int init = in.nextInt();

			in.nextLine();

			System.out.println("Escolha final para range de anos ");

			while (!in.hasNextDouble()) {
				System.out
						.println("valor introduzido está invalido, tem de introduzir valor inteiro");
				System.out.println("ponha um novo valor:");
				in.next();
			}
			int ending = in.nextInt();

			in.nextLine();

			System.out
					.println("Escreva titulos das citacoes que pretende ignorar separando-os correctamente através do uso de virgulas ");
			String tituloscitacoes = in.nextLine();
			String[] tituloscitacoes1 = tituloscitacoes.split(",");

			System.out
					.println("Escreva nomes dos autores dos quais não pretende receber as citações associadas separando-os correctamente através do uso de virgulas ");
			String autorescitacoes = in.nextLine();
			String[] autorescitacoes1 = autorescitacoes.split(",");

			ObjectFactory def = new ObjectFactory();
			Researcher laranjeiro = def.createResearcher();

			int citacoesporautor = 0;

			Document doc = Jsoup.connect(
					"https://eden.dei.uc.pt/~cnl/scholar/index.html").get();

			Elements link_pub = doc.select("[href*=view_citation]");

			Publications publicacoes = def.createPublications();

			laranjeiro.setPublications(publicacoes);

			for (Element link : link_pub) {

				String link_new = link.attr("abs:href");

				Document doc_2 = Jsoup.connect(link_new).get();
				Element cit = doc_2.select("[href*=1.html]").first();

				if (cit != null) {

					Publication publi = def.createPublication();

					publi.setTitle(link.text());
					publicacoes.getPublication().add(publi);

					Elements fields = doc_2
							.select("#gsc_table [class=gsc_field]");

					Elements values = doc_2
							.select("#gsc_table [class=gsc_value]");

					int a = fields.size();

					for (int i = 0; i < a; i++) {

						if (fields.get(i).text().equals("Authors")) {

							publi.setAuthors(values.get(i).text());

						}

						if (fields.get(i).text().equals("Publication date")) {

							publi.setPublicationDate(values.get(i).text());

						}

						if (fields.get(i).text().equals("Description")) {

							publi.setDescription(values.get(i).text());

						}

					}

					String link_citedby = cit.attr("abs:href");
					Document doc_3 = Jsoup.connect(link_citedby).get();

					Elements cites = doc_3.select("[onclick*=window.open]");

					Citations totalcit = def.createCitations();

					publi.setCitations(totalcit);

					int quantidade = 0;

					for (Element cite : cites) {

						String link_cite = cite.attr("abs:onclick");
						String[] chicago_imp = link_cite.split("'");

						String chicagoi = "https://eden.dei.uc.pt/~cnl/scholar/"
								+ chicago_imp[1];
						Document doc_4 = Jsoup.connect(chicagoi).get();
						Element chicago = doc_4.getElementById("gs_cit2");

						String m = chicago.text();

						int datacitacao = 0;

						if (m.contains("\"")) {

							String[] vv = m.split("\"");

							if (vv.length > 2) {

								int e = vv[2].length();

								int sum = 0;

								int[] datasss = { 0, 0, 0, 0 };

								int l = 3;
								int numero = e - 1;
								int n = 0;

								for (int i = numero; i > 1; i--) {

									if (vv[2].charAt(i) == '0'
											|| vv[2].charAt(i) == '1'
											|| vv[2].charAt(i) == '2'
											|| vv[2].charAt(i) == '3'
											|| vv[2].charAt(i) == '4'
											|| vv[2].charAt(i) == '5'
											|| vv[2].charAt(i) == '6'
											|| vv[2].charAt(i) == '7'
											|| vv[2].charAt(i) == '8'
											|| vv[2].charAt(i) == '9') {

										sum++;

										datasss[l] = Character
												.getNumericValue(vv[2]
														.charAt(i));

										l--;

									}

									if (sum == 4) {

										String data1 = String
												.valueOf(datasss[0])
												+ String.valueOf(datasss[1])
												+ String.valueOf(datasss[2])
												+ String.valueOf(datasss[3]);

										datacitacao = Integer.parseInt(data1);

										if (datacitacao > 2016
												|| datacitacao < 2005) {
											sum = 0;
											n++;
											i = numero - 1 - n;
											l = 3;
											continue;
										} else {

											break;

										}

									}

								}

								String titl = vv[1];

								boolean f = false;

								for (String i : tituloscitacoes1) {

									String h = titl;

									if (h.equals(i)) {

										f = true;

									}
								}
								boolean g = false;

								for (String t : autorescitacoes1) {

									String[] c = vv[0].split(",");

									for (int o = c.length; o > 0; o--) {

										if (c[o - 1] == t) {

											g = true;

										}
									}
								}

								if (f == false & g == false
										& datacitacao > init
										& datacitacao < ending) {

									Citation citacao = def.createCitation();
									citacao.setTitle(titl);
									citacao.setDate(datacitacao);
									totalcit.getCitation().add(citacao);
									quantidade++;

								}

							}

						}

					}

					totalcit.setQuantity(quantidade);
					citacoesporautor = citacoesporautor + quantidade;
					quantidade = 0;

				}

			}

			laranjeiro.setTotalNumCitAutor(citacoesporautor);

			ArrayList<Integer> datas = new ArrayList<Integer>();

			for (int j = 0; j < publicacoes.getPublication().size(); j++) {

				ArrayList<Citation> newarray1 = ordena.citac(publicacoes
						.getPublication().get(j));

				publicacoes.getPublication().get(j).getCitations()
						.getCitation().clear();

				for (int k = 0; k < newarray1.size(); k++) {

					publicacoes.getPublication().get(j).getCitations()
							.getCitation().add(newarray1.get(k));

					int x = newarray1.get(k).getDate();

					datas.add(x);

				}

			}

			TotalCitAno tca = def.createTotalCitAno();

			for (int i = 0; i < datas.size(); i++) {

				Ano annee = def.createAno();

				int w = datas.get(i);

				datas.remove(i);

				int value = 1;

				for (int p = 0; p < datas.size(); p++) {

					if (datas.get(p) == w) {

						datas.remove(p);
						p--;

						value++;

					}
				}

				annee.setId(w);
				annee.setNum(value);

				tca.getAno().add(i, annee);
				laranjeiro.setTotalCitAno(tca);

			}

			TransformerFactory tf = TransformerFactory.newInstance();
			StreamSource xslt = new StreamSource(
					"src/final_almighty/finalissimo.xsl");
			Transformer transformer = tf.newTransformer(xslt);

			// Source
			JAXBContext jaxb = JAXBContext.newInstance(Researcher.class);
			JAXBSource source = new JAXBSource(jaxb, laranjeiro);

			// Result
			File finalhtml = new File("src/final_almighty/final_html.html");
			StreamResult result = new StreamResult(finalhtml);

			// Transform
			transformer.transform(source, result);

			// td a span

		} catch (JAXBException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

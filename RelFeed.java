//package informationRetreival;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RelFeed {

	public static void main(String[] args) throws FileNotFoundException {

		//HashMap<String, Integer> files = new HashMap<String, Integer>();
		HashMap<String, ArrayList<Integer>> words = new HashMap();

		putDoc(list);
		int choose;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your choice");
		System.out.println("1. Print entire list");
		System.out.println("2. Find tf-idf of the term");

		choose = in.nextInt();

		if(choose == 1) {

			readWord(words);
		}
		else if(choose == 2) {

			searchWord(words);
		}
		//readWord(files, words);
	}

	public static void readWord(HashMap<String, ArrayList<Integer>> words) throws FileNotFoundException {

		ArrayList<String> stopwords = new ArrayList<String>();
		Scanner wordfile;
		Scanner stopword = new Scanner(new FileReader("hindistop"));
		String word;

		while(stopword.hasNext()) {

			String stop = stopword.next();
			stopwords.add(stop);
		}

		for(int i=1; i<=docs.length; i++) {

			try {

				//System.out.println(i + " : " + docs[i-1]);
				wordfile = new Scanner(new FileReader("hindi_original/" + docs[i-1]));
			}
			catch(FileNotFoundException e) {

				System.err.println(e);
				return;
			}

			while(wordfile.hasNext()) {

				word = wordfile.next();
				word = word.toLowerCase();

				//count = getCount(word, files) + 1;
				//files.put(word, count);

				int flag = 1;
				for(int j=0; j<word.length(); j++) {

					if(word.contains("0") || word.contains("1")|| word.contains("2") || word.contains("3")|| word.contains("4") || word.contains("5")|| word.contains("6") || word.contains("7") ||word.contains("8") || word.contains("9"))
					{
							flag = 0;
					}
					if(word.startsWith(".") || word.startsWith(",") || word.startsWith("<") || word.startsWith("-"))
					{
						word = word.substring(1,word.length());
					}
					if(word.endsWith(".") || word.endsWith(",") || word.endsWith(">") || word.endsWith("-"))
					{
						word = word.substring(word.length()-1);
					}
				}

				if(!stopwords.contains(word) && flag==1) {

					Stemmer stemmer = new Stemmer();
					stemmer.add(word.toCharArray(), word.length());
					stemmer.stem();
					word = stemmer.toString();

					if(words.containsKey(word)) {

						ArrayList<Integer> index = words.get(word);
						if(!index.contains(i)) {

							index.add(i);
							words.put(word, index);
						}
					}
					else {

						ArrayList<Integer> index = new ArrayList<Integer>();
						index.add(i);
						words.put(word, index);
					}
				}
			}
		}

		System.out.println(words);
	}

	public static int getCount(String word, HashMap<String, Integer> files) {

		if(files.containsKey(word)) {

			System.out.println(files.get(word));
			return files.get(word);
		}
		else {

			return 0;
		}
	}

	public static void putDoc(File[] list) {

		for(int i=0; i<list.length; i++) {

			docs[i] = list[i].getName();
			System.out.println(docs[i]);
		}
	}

	public static void searchWord(HashMap<String, ArrayList<Integer>> words) throws FileNotFoundException {

		ArrayList<String> stopwords = new ArrayList<String>();
		Scanner wordfile;
		Scanner stopword = new Scanner(new FileReader("hindistop"));
		String word;

		while(stopword.hasNext()) {

			String stop = stopword.next();
			stopwords.add(stop);
		}

		for(int i=1; i<=docs.length; i++) {

			try {

				//System.out.println(i + " : " + docs[i-1]);
				wordfile = new Scanner(new FileReader("hindi_original/" + docs[i-1]));
			}
			catch(FileNotFoundException e) {

				System.err.println(e);
				return;
			}

			while(wordfile.hasNext()) {

				word = wordfile.next();
				word = word.toLowerCase();

				//count = getCount(word, files) + 1;
				//files.put(word, count);

				int flag = 1;
				for(int j=0; j<word.length(); j++) {

					if(word.contains("0") || word.contains("1")|| word.contains("2") || word.contains("3")|| word.contains("4") || word.contains("5")|| word.contains("6") || word.contains("7") ||word.contains("8") || word.contains("9"))
					{
							flag = 0;
					}
					if(word.startsWith(".") || word.startsWith(",") || word.startsWith("<") || word.startsWith("-"))
					{
						word = word.substring(1,word.length());
					}
					if(word.endsWith(".") || word.endsWith(",") || word.endsWith(">") || word.endsWith("-"))
					{
						word = word.substring(word.length()-1);
					}
				}

				if(!stopwords.contains(word) && flag==1) {

					Stemmer stemmer = new Stemmer();
					stemmer.add(word.toCharArray(), word.length());
					stemmer.stem();
					word = stemmer.toString();

					if(words.containsKey(word)) {

						ArrayList<Integer> index = words.get(word);
						if(!index.contains(i)) {

							index.add(i);
							words.put(word, index);
						}
					}
					else {

						ArrayList<Integer> index = new ArrayList<Integer>();
						index.add(i);
						words.put(word, index);
					}
				}
			}
		}

		System.out.println("Enter Query.. : ");
		Scanner in = new Scanner(System.in);
		String query = in.next();

		System.out.println(words.get(query));
		ArrayList<Integer> posting_list= new ArrayList<Integer>(words.get(query));
		ArrayList<Integer> term_frequency = new ArrayList<Integer>();
		ArrayList<Integer> total_word = new ArrayList<Integer>();

		for(int i=0;i<docs.length;i++) {

			FileReader dummyreader= new FileReader(new File("hindi_original/" + docs[i]));
			Scanner newfile=new Scanner(dummyreader);
			int count=0;
			int count1=0;
			while (newfile.hasNext()) {
				String nfile=newfile.next().toLowerCase();
				++count;
				if(nfile.equals(query))
				++count1;
			}

			term_frequency.add(count1);
			total_word.add(count);
		}

		Integer count_doc = 0;

		for(int i=0; i<term_frequency.size(); i++) {

			if(term_frequency.get(i) != 0) {

				count_doc++;
			}
		}
		Integer total_documents = docs.length;
		Integer where_it_occur;
		double tf;
		ArrayList<Double> tf_idf =new ArrayList<Double>();

		where_it_occur = count_doc;
		System.out.println("Total number of documents : " + total_documents);
		System.out.println("Number of document term \"" + query + "\" occur : " + where_it_occur);

		double idf = Math.log(total_documents/(double)where_it_occur);
		System.out.println("Inverse Document Frequency is " + idf);

		System.out.println();
		double[] tfidf = new double[where_it_occur];
		int j=0;

		for(int i=0; i<term_frequency.size(); i++) {

			if(term_frequency.get(i) != 0) {

				tf = (double) term_frequency.get(i)/total_word.get(i);
				System.out.println((i+1) + " : " + docs[i]);
				System.out.println("Term in document " + docs[i] + " : " + term_frequency.get(i));
				System.out.println("Total no of terms in document " + docs[i] + " : " + total_word.get(i));
				System.out.println("Term Frequency is " + tf);
				System.out.println("tf-idf is " + Math.round((tf * idf)*1000.0)/1000.0);
				tfidf[j] = (tf * idf);
				j++;
			}
		}

		System.out.println("Rank			Score			docid			title");
		int k;

		for(int l=where_it_occur; l>=0; l--) {
			for(int i=0; i<where_it_occur-1; i++) {

				k = i + 1;

				if(tfidf[i] < tfidf[k]) {

					double temp = tfidf[i];
					String temp0 = docs[i];
					tfidf[i] = tfidf[k];
					docs[i] = docs[k];
					tfidf[k] = temp;
					docs[k] = temp0;
				}
			}
		}

		HashMap<Integer, String> store = new HashMap<Integer, String>();
		for(int i=0; i<where_it_occur; i++) {

			System.out.println((i+1) + "		" + tfidf[i] + "		" + docs[i]);
			store.put((i+1), docs[i]);
		}

		System.out.println("Enter number of relevant docs : ");
		int relno = in.nextInt();
		ArrayList<Integer> rel = new ArrayList<Integer>(relno);
		ArrayList<Integer> inrel = new ArrayList<Integer>();
		System.out.println("Enter relevant docs in relevance order :");
		for(int i=0; i<relno; i++) {

			System.out.println("Doc " + (i+1));
			rel.add(in.nextInt());
			System.out.println(rel.get(i));
		}

		int cnt = 0;
		for(int i=1; i<=where_it_occur; i++) {

			int check = 0;
			for(int n=0; n<relno; n++) {

				if(rel.get(n) == (i)) {

					check = 1;
				}
			}

			if(check == 0) {

				inrel.add(i);
				System.out.println(inrel.get(cnt));
				cnt++;
			}
		}

		System.out.println("Relevant Documents");
		ArrayList<Integer> newrank = new ArrayList<Integer>(where_it_occur);
		for(int i=0; i<relno; i++) {

			newrank.add(rel.get(i));
		}

		int inrelno = where_it_occur-relno;
		for(int i=0; i<inrelno; i++) {

			newrank.add(inrel.get(i));
		}

		for(int i=0; i<where_it_occur; i++) {

			System.out.println((i+1) + " - " + store.get(newrank.get(i)));
		}
	}

	static File homedir = new File("/home/sysadmin/Documents/Lab/ass1/hindi_original");
	static File[] list = homedir.listFiles();
	static String[] docs = new String[list.length];
}

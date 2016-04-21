//package informationRetreival;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class index {

	public static void main(String[] args) throws FileNotFoundException {

		//HashMap<String, Integer> files = new HashMap<String, Integer>();
		HashMap<String, ArrayList<Integer>> words = new HashMap();

		putDoc(list);

		System.out.println("Enter Choice : ");
		System.out.println("1. View entire list.\t2. Search Word");
		Scanner choice = new Scanner(System.in);
		Integer choi = choice.nextInt();

		if(choi == 1) {

			readWord(words);
		}
		else if(choi == 2) {

			System.out.println("Enter Query : ");
			Scanner in = new Scanner(System.in);
			String term = in.next();
			getDetails(words, term);
		}
		else {

			return;
		}

		//getDetails(words);
	}

	public static void getDetails(HashMap<String, ArrayList<Integer>> words, String term) {

		ArrayList<Integer> count = new ArrayList<Integer>();
		Scanner wordfile;

		System.out.println("Documents searched are :");

		for(int i=1; i<=docs.length; i++) {

			try {

				//System.out.println(i + " : " + docs[i-1]);
				wordfile = new Scanner(new FileReader("hindi_original/" + docs[i-1]));
			}
			catch(FileNotFoundException e) {

				System.err.println(e);
				return;
			}

			Integer counter = 0;

			while(wordfile.hasNext()) {

				String word = wordfile.next();
				word = word.toLowerCase();

				if(word.equals(term)) {

					counter++;
				}

			}

			//System.out.println(counter);
			count.add(counter);
		}

		System.out.println("Index of \"" + term + "\" ---> ");
		for(int j=0; j<docs.length; j++) {

			if(count.get(j) != 0) {

				System.out.println(j+1 + " : " + count.get(j));
			}
		}
		//System.out.println(count);
	}

	public static void readWord(HashMap<String, ArrayList<Integer>> words) throws FileNotFoundException {

		ArrayList<String> stopwords = new ArrayList<String>();
		Scanner wordfile;
		Scanner stopword = new Scanner(new FileReader("hindistop"));
		String word;
		//FileWriter out = new FileWriter(new File("index.txt"));

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
		//out.write(words);
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

	static File homedir = new File("/home/sysadmin/Documents/Lab/ass1/hindi_original");
	static File[] list = homedir.listFiles();
	static String[] docs = new String[list.length];
}

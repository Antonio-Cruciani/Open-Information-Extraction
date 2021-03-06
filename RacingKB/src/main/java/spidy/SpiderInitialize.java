package spidy;

import java.io.FilePermission;
import java.security.AccessController;
import java.util.Arrays;
import java.util.List;



public class SpiderInitialize {

	public static void  SpiderInitialization() {
		String path = "Pages/";
		// String path1 = "/Users/antonio/Pages/table.txt";
		String actions = "read,write";
		// Table table = new Table();

		try {

			AccessController.checkPermission(new FilePermission(path, actions));

			// AccessController.checkPermission(new FilePermission(path1, actions));

			System.out.println("You have read/write permition to use : " + path);

		} catch (SecurityException e) {

			System.out.println("You have read/write permition to use : " + path);

		}
		int i;
		List<String> words = Arrays.asList("F1", "Ferrari", "race", "pilot", "racing team", "race track",
				"racing driver", " Formula One racing car");
		String[] Words = words.toArray(new String[words.size()]);
		i = 0;
		for (String lineTokens : words) {
			Words[i] = lineTokens;
			i += 1;
		}

		// table.LoadTable(path);
		Spider spider = new Spider();
		SpiderJournal spider_journal = new SpiderJournal();
		SpiderJournal spider_BBC = new SpiderJournal();
		//PostProcessing PostP = new PostProcessing();
		spider.search("https://en.wikipedia.org/wiki/Category:Formula_One", Words, i);
		spider_journal.search("https://www.motorsport.com/f1/news/", Words, i);
		spider_BBC.search("https://www.bbc.com/sport/formula1", Words, i);		// TODO Auto-generated constructor stub
	}

}

package racing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class readFile {

	static String readFile1(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				// sb.append(" ");
				System.out.println(line);
				System.out.println("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static String readFile3(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		int i = 0;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
				System.out.println(i);
				i++;
			}
			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	public static String readFile2(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	
	public static void creaCartella() throws InterruptedException{
		 File file = new File("Pages");
		if(file.exists()) {
		 String[] children = file.list();
		    for (int i = 0; i < children.length; i++)
		    {
		       new File(file, children[i]).delete();
		    }
		 
		 file.delete();
		}
		TimeUnit.SECONDS.sleep(1);

		 File file2 = new File("Pages");
	        if (!file2.exists()) {
	        	
	            if (file2.mkdir()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }
	}
	public static String ReadTextFileExample(String file) throws IOException {

		String contents = Files.lines(Paths.get(file)).collect(Collectors.joining("\n"));
		// System.out.println(contents);
		return contents;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String ciao = readFile2("page_2.txt", StandardCharsets.UTF_8);
		System.out.println(ciao);
	}

}

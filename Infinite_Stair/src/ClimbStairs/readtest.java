package ClimbStairs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class readtest {

	public static void main(String[] args) {
		String climbKey = "";
		String changeKey = "88";
		try {
			File writeFile = new File("files/keySettings.txt");
			if(!writeFile.exists())
				writeFile.createNewFile();
			FileWriter fw = new FileWriter(writeFile);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(changeKey + "\n");
			writer.write("90");
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		try {
			File readFile = new File("files/keySettings.txt");
			FileReader filereader = new FileReader(readFile);
			BufferedReader bufReader = new BufferedReader(filereader);
			climbKey = bufReader.readLine();
			changeKey = bufReader.readLine();
			bufReader.close();
		} catch(IOException e){
			System.out.println("error");
		}
		System.out.println(climbKey);
		System.out.println(changeKey);
	}
}

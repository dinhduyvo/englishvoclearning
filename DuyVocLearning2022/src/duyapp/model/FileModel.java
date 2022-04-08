package duyapp.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import duyapp.dtos.Word;

public class FileModel implements IDataModel {

	private String connectionString = "";

	@Override
	public List<Word> getList() throws Exception {
		List<Word> wordList = new ArrayList<>();

		Reader reader = new InputStreamReader(new FileInputStream(connectionString), "utf-8");
		BufferedReader r = new BufferedReader(reader);

		String line;
		int i = 1;
		while ((line = r.readLine()) != null) {
			Word w = new Word();
			w.sortIndex = i;
			String[] data = line.split(",");
			w.word = data[0];
			w.meaning = data.length > 1 ? data[1] : "";
			w.description = data.length > 2 ? data[2] : "";
			w.remember = data.length > 3 ? Boolean.valueOf(data[3]) : false;
			
			wordList.add(w);
			i++;
			
		}

		r.close();
		reader.close();

		return wordList;
	}

	@Override
	public boolean createConnection(String conn) {
		connectionString = conn;
		return true;
	}

	@Override
	public boolean saveData(List<Word> wordList) throws Exception {

		StringBuilder saveData = new StringBuilder();
		for (Word word : wordList) {
			saveData.append(word.word + "," + word.meaning + "," + word.description + "," + word.remember);
			saveData.append(System.lineSeparator());
		}
		Writer reader;
		try {
			reader = new FileWriter(connectionString, StandardCharsets.UTF_8);
			reader.write(saveData.toString());
			reader.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

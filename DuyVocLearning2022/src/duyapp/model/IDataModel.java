package duyapp.model;

import java.util.List;

import duyapp.dtos.Word;

public interface IDataModel {
	
	boolean createConnection(String conn);
	List<Word> getList() throws Exception;
	boolean saveData(List<Word> wordList) throws Exception;
}

package br.com.amil.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.com.amil.service.api.ImportFileService;

public class ImportFileServiceImpl implements ImportFileService {

	@Override
	public InputStream importFile() {
		try {
			return new FileInputStream("game.log");
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo game.log não encontrado.");
			return null;
		}
	}

}

package br.com.amil.service.impl;

import java.io.InputStream;

import br.com.amil.builder.GameBuilder;
import br.com.amil.model.Game;
import br.com.amil.service.api.GameService;

public class GameServiceImpl implements GameService {

	private GameBuilder gameBuilder = new GameBuilder();

	@Override
	public Game play(InputStream inputStream) {
		return gameBuilder.builderGame(inputStream);
	}

}

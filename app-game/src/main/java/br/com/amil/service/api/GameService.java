package br.com.amil.service.api;

import java.io.InputStream;

import br.com.amil.model.Game;
import br.com.amil.model.Match;

public interface GameService {

	Game play(InputStream inputStream);
}

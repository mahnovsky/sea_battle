package com.evhenii.seabattle;

public class BasePlayer {

	protected Game _game;
	protected Map _map;

	public BasePlayer( Game game ) {

		_game = game;
		_map = new Map();
	}

	public void process() {

		_map.draw();
	}
}
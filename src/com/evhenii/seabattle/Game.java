package com.evhenii.seabattle;


public class Game {

	private GameState _state;
	private BasePlayer[] _players;
	private int _current_index;
	private boolean _is_game_over;

	private void next_step() {

		++_current_index ;

		if( _current_index >= _players.length ) {
			_current_index = 0;
		}
	}

	public Game() {

		_state = GameState.Fill;

		_players = new BasePlayer[2];
		_players[0] = new Player( this );
		_players[1] = new BasePlayer( this ); // BotPlayer

		_current_index = 0;
		_is_game_over = false;
	}

	public GameState get_state() {

		return _state;
	}

	public void process() {

		_players[_current_index].process();

		next_step();
	}

	public void set_game_over() {

		_is_game_over = true;
	}

	public boolean is_game_over() {

		return _is_game_over;
	}

}
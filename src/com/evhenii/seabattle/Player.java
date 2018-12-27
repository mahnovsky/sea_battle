package com.evhenii.seabattle;

public class Player extends BasePlayer implements InputListener {

	private Input _input;

	public Player( Game game ) {

		super( game );

		_input = new Input( this );
	}

	public void make_ship(int x, int y, int decks, Orientation o) {

		System.out.println( "make_ship x, y (" + x + ", " + y + ") " + decks );

		Ship.make( _map, DeckCount.value_of(decks), o, new Point( x, y ) );
	}

	public void attack( int x, int y ) {

	}

	public void quit_game() {

		_game.set_game_over();
	}

	@Override
	public void process() {

		super.process();

		_input.process( _game.get_state() );
	}
}
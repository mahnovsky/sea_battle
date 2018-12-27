package com.evhenii.seabattle;

public class Player extends BasePlayer implements InputListener {

	private Input _input;

	public Player( Game game ) {

		super( game );

		_input = new Input( this );
	}

	public boolean add_new_ship( DeckCount decks, Orientation orient, Point start_coord ) {

		System.out.println( "make_ship x, y (" + start_coord.x + ", " + start_coord.y + ") " + decks );

		add_ship( orient, decks, start_coord );
		
		return get_free_places(decks) > 0;
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
	
	public void auto_fill() {
	
		_filler.fill();
	}
}
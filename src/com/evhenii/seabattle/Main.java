package com.evhenii.seabattle;

// 1. Map
// 2. Ship
// 3. Deck
// 4. Player
// 5. Game

class Main {

	public static void main( String [] args ) throws Exception {

		Game game = new Game() ;

		do {

			game.process();
		}
		while( !game.is_game_over() );
	}

}
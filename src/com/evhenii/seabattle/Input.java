package com.evhenii.seabattle;

import java.util.Scanner;

/**
 * tasks (28.12.2018):
 *  - auto place ships
 *  - input message
 *  + improve ships placement
 *  - other improvements 
**/
public class Input {

	private Scanner _scan;
	private InputListener _listener;
	private final String _base_fill_message = "input %d-deck ship in format %s:";
	private String _current_message;
	private int _current_ship_deck;

	public Input(InputListener listener) {

		_scan = new Scanner(System.in);
		_listener = listener;
		_current_ship_deck = 1;
		
		update_message();
	}

	void update_message() {
	
		String format = _current_ship_deck > 1 ? "(x,y;orientation)" : "(x,y)";
		
		_current_message = String.format( _base_fill_message, _current_ship_deck, format );
	}
	
	public void process(GameState state) {

		System.out.println(_current_message);
		
		String in = _scan.nextLine();

		System.out.print( in );

		boolean quit_game = "q".equals(in) || "quit".equals(in);

		if( _listener != null ) {

			if ( quit_game ) {
				
				_listener.quit_game();

				return;
			}

			if( state == GameState.Fill ) {

				fill_process( in );
			}
			else if( state == GameState.Battle ) {

				do_process(in);
			}
		}
	}

	private Point parse_coords(String in) {

		String [] coords = in.split(",");
		int x = -1;
		int y = -1;

		if( coords.length >= 2 ) {

			char symbol = Character.toLowerCase( coords[0].charAt(0) );
			x = symbol - 'a'; // a - 55 --> 0 - 9
			y = Integer.parseInt( coords[1] ) - 1;
		}

		return new Point(x, y);
	}

	private void fill_process(String in) {

		// A-J, 1-10; h/v; 1-4
		// B, 4; H; 3
		String [] chunks = in.split(";");
		
		if( chunks.length == 1 && "auto".equals(in) )
		{
			_listener.auto_fill();
		}
		
		DeckCount dc = DeckCount.value_of( _current_ship_deck );

		if( chunks.length >= 1 ) {

			Point c = parse_coords(chunks[0]);
			Orientation orient = Orientation.None;
			if( _current_ship_deck == 1 )
			{
				orient = Orientation.Horizontal;
			}
			else if( "H".equals( chunks[1] ) || "h".equals( chunks[1] ) ) {

				orient = Orientation.Horizontal;
			}
			else if( "V".equals( chunks[1] ) || "v".equals( chunks[1] ) ) {

				orient = Orientation.Vertical;
			}

			if( orient != Orientation.None && c.x >= 0 && c.y >= 0 ) {

				if( !_listener.add_new_ship( dc, orient, new Point(c.x, c.y) ) ) {
					
					++_current_ship_deck;
					update_message();
				}
			}
		}
	}

	private void do_process( String in ) {
		
		Point c = parse_coords(in);

		if( c.x >= 0 && c.y >= 0 ) {

			_listener.attack(c.x, c.y);
		}
	}
}
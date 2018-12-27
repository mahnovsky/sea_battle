package com.evhenii.seabattle;

public class Ship {

	private Deck [] _decks;
	private DeckCount _deck_count;
	private Orientation _orientation;
	private Point[] _coords;

	public Ship( ) {

		_decks = null;
		_deck_count = DeckCount.Invalid;
		_orientation = Orientation.None;
		_coords = null;
	}

	public Ship( Map map, DeckCount deck_count, Orientation orientation, Point[] coords ) {

		_deck_count = deck_count;
		_orientation = orientation;
		_coords = coords;

		_decks = new Deck[deck_count.get_value()];

		for( int i = 0; i < _decks.length; ++i ) {

			Deck deck = new Deck( coords[i], 'O', 'X' );

			_decks[i] = deck;

			map.add_object( deck );
		}
	}

	public boolean is_valid() {

		return _deck_count != DeckCount.Invalid &&
			   _orientation != Orientation.None &&
			   _coords != null;
	}

	public boolean is_alive() {

		boolean is_alive = false;

		for( Deck deck : _decks ) {

			if( deck.is_alive() ) {

				is_alive = true;
			}
		}

		return is_alive;
	}

	public static Ship make( Map map, DeckCount deck_count, Orientation orientation, Point start_coord ) {

		Point step = null;

		if( orientation == Orientation.Horizontal ) {

			step = new Point( 1, 0 );
		}
		else if ( orientation == Orientation.Vertical ) {

			step = new Point( 0, 1 );
		}
		else {

			return null;
		}

		boolean is_posible_place = true;

		Point[] coords = new Point[deck_count.get_value()];
		Point position = new Point( start_coord.x, start_coord.y );

		for( int i = 0; i < deck_count.get_value(); ++i ) {

			is_posible_place = map.is_valid_coord( position ) && 
							!map.is_collide( position ) &&
							!map.has_neighbours( position );

			if( !is_posible_place ) {
				break;
			}

			coords[i] = new Point( position.x, position.y );

			position.x = position.x + step.x;
			position.y = position.y + step.y;
		}

		if( is_posible_place ) {

			return new Ship( map, deck_count, orientation, coords );
		}

		return null;		
	}
}
package com.evhenii.seabattle;

import java.util.logging.Logger;
import java.util.ArrayList;

interface IMapObject {

	Point get_position();

	char get_view();
}

public class Map {

	public static Logger log = Logger.getLogger( Map.class.getName() );

	public final int With = 10;
	public final int Height = 10;

	private char [] _head;
	private char [][] _cells;

	private IMapObject[][] _objects;

	Point[] _neighbours;

	public Map() {

		_head = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		_cells = new char[With] [Height];

		_objects = new IMapObject[With][Height];

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				_cells[x][y] = ' ';
			}
		}

		_neighbours = new Point[8];
		_neighbours[0] = new Point( -1, -1 );
		_neighbours[1] = new Point( -1, 0 );
		_neighbours[2] = new Point( -1, 1 );
		_neighbours[3] = new Point( 0, 1 );
		_neighbours[4] = new Point( 1, 1 );
		_neighbours[5] = new Point( 1, 0 );
		_neighbours[6] = new Point( 1, -1 );
		_neighbours[7] = new Point( 0, -1 );
	}

	public void add_object(IMapObject object) {

		Point position = object.get_position();

		if ( is_valid_coord( position ) ) {

			_objects[position.x][position.y] = object;
		}
		else {

			log.warning( "can't add map object" );
		}
	}

	public boolean is_valid_coord( Point point ) {

		return point.x >= 0 && point.x < With && point.y >= 0 && point.y < Height;
	}

	public boolean is_collide( Point position ) {

		return _objects[position.x][position.y] != null;
	}

	public boolean has_neighbours( Point position ) {

		boolean result = false;

		for( Point p : _neighbours ) {

			Point neighbour = new Point( position.x + p.x, position.y + p.y );

			if( is_valid_coord( neighbour ) && _objects[neighbour.x][neighbour.y] != null ) {

				result = true;
				break;
			}	
		}

		return result;
	}

	public void clean() {

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				_cells[x][y] = ' ';
			}
		}
	}

	private void update() {

		clean();

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				IMapObject object = _objects[x][y];

				if( object != null ) {

					Point position = object.get_position();

					_cells[position.x][position.y] = object.get_view();
				}
			}
		}
	}

	public void draw() {

		update();

		int coordX = 0;
		int coordY = 1;

		System.out.print( ' ' );
		for( int x = 0; x < With; ++x ) {

			System.out.print( ' ' );
			System.out.print( _head [coordX] );

			coordX += 1;
		}

		System.out.println();

		for(  int y = 0; y < Height; ++y ) {

			System.out.print( coordY++ );

			for( int x = 0; x < With; ++x ) {

				if( !(x == 0 && coordY == 11) ) {
					System.out.print( ' ' );
				}

				System.out.print( _cells[x][y] );
			}

			System.out.println('|');
		}
	}
}
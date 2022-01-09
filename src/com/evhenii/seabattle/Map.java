package com.evhenii.seabattle;

import java.util.logging.Logger;
import java.util.ArrayList;

interface IMapObject {

	Point get_position();

	char get_view();
}

public class Map {

	public static Logger log = Logger.getLogger( Map.class.getName() );

	public static final int With = 10;
	public static final int Height = 10;

	private char [] _head;
	private char [][] _cells;
	private char [][] _map_view;

	private IMapObject[][] _objects;

	Point[] _neighbours;

	public Map() {

		_head = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		_cells = new char[Height][With];
		_map_view = new char[Height + 1] [(With + 1) * 2];

		_objects = new IMapObject[Height][With];

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				_cells[y][x] = ' ';
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

			_objects[position.y][position.x] = object;
		}
		else {

			log.warning( "can't add map object" );
		}
	}

	public boolean is_valid_coord( Point point ) {

		return point.x >= 0 && point.x < With && point.y >= 0 && point.y < Height;
	}

	public boolean is_collide( Point position ) {

		return _objects[position.y][position.x] != null;
	}

	public boolean has_neighbours( Point position ) {

		boolean result = false;

		for( Point p : _neighbours ) {

			Point neighbour = new Point( position.x + p.x, position.y + p.y );

			if( is_valid_coord( neighbour ) && _objects[neighbour.y][neighbour.x] != null ) {

				result = true;
				break;
			}	
		}

		return result;
	}

	public void clean() {

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				_cells[y][x] = ' ';
			}
		}
	}

	private void update() {

		clean();

		for(  int y = 0; y < Height; ++y ) {

			for( int x = 0; x < With; ++x ) {

				IMapObject object = _objects[y][x];

				if( object != null ) {

					Point position = object.get_position();

					_cells[position.y][position.x] = object.get_view();
				}
			}
		}
	}

	public void draw() {

		update();

		int coordX = 0;
		int coordY = 1;

		//System.out.print( ' ' );
		_map_view[0][0] = ' ';
		_map_view[0][1] = ' ';
		int width = (With + 1) * 2;
		for( int x = 2; x < width; x += 2 ) {

			_map_view[0][x] = _head[coordX];
			_map_view[0][x+1] = ' ';

			coordX += 1;
		}
		
		for(  int y = 0; y < Height; ++y ) {

			String row_number = String.valueOf(coordY);
			_map_view[coordY][0] = row_number.charAt(0);
			
			if( coordY > 9 ) {
				_map_view[coordY][1] = row_number.charAt(1);
			}
			else {
				_map_view[coordY][1] = ' ';
			}
			coordX = 2;
			for( int x = 0; x < With; ++x ) {

				//if( !(x == 0 && coordY == 11) ) {
					
					
				//}

				_map_view[coordY][coordX++] = _cells[y][x];
				_map_view[coordY][coordX++] = ' ';
			}
			++coordY;
			//System.out.println('|');
		}
		
		for(int y = 0; y < (Height+1); ++y){
			
			for(int x = 0; x < width; ++x){
			
				System.out.print(_map_view[y][x]);
			}
			System.out.println();
		}
	}
}
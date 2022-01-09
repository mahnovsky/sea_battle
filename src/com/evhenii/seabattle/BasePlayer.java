package com.evhenii.seabattle;

public class BasePlayer {

	protected Game _game;
	protected Map _map;
	
	// OneP -> 4, TwoP -> 3, ThreeP -> 2, FourP -> 1
	protected java.util.Map<DeckCount, Integer> _ship_counter;
	protected java.util.List<Ship> _ships;
	
	protected int _max_ships;
	protected int _alive_ships;
	
	protected ShipFiller _filler;

	public BasePlayer( Game game ) {

		_game = game;
		_map = new Map();
		
		init();
	}
	
	public final void init() {
		
		_max_ships = 0;
		_alive_ships = 0;
		_filler = new ShipFiller(this);
		
		_ship_counter = new java.util.HashMap<DeckCount, Integer>();
		for(int i = 1; i < DeckCount.values().length; ++i) {
			
			int ship_count = 5 - i;
			
			_ship_counter.put( DeckCount.value_of( i ), ship_count );
			
			// _max_ships = _max_ships + ship_count;
			_max_ships += ship_count;
		}
		
		if( _max_ships > 0 ) {
			_ships = new java.util.ArrayList<Ship>();
		}
	}

	// return free places for ships
	public int get_free_places( DeckCount dc ) {
		
		return _ship_counter.get( dc );
	}
	
	public boolean is_posible_place(Orientation orient, DeckCount dc, Point start_coord) {
		
		Point step = orient.get_direction();

		boolean is_posible_place = true;

		Point position = new Point( start_coord.x, start_coord.y );

		for( int i = 0; i < dc.get_value(); ++i ) {

			is_posible_place = _map.is_valid_coord( position ) && 
							!_map.is_collide( position ) &&
							!_map.has_neighbours( position );

			if( !is_posible_place ) {
				break;
			}

			position.x = position.x + step.x;
			position.y = position.y + step.y;
		}

		return is_posible_place;
	}
	
	public boolean add_ship( Orientation orient, DeckCount dc, Point start_coord ) {
		
		int free_places = get_free_places( dc );
			
		if( free_places > 0 )
		{
			Point[] coords = Ship.get_coords_for_ship(_map, dc, orient, start_coord);
			
			if( coords == null ) {
				
				return false;
			}
			
			Ship ship = new Ship( _map, dc, orient, coords );
			
			_ships.add( ship );
			
			//free_places = free_places - 1;
			free_places -= 1;
			_ship_counter.replace( dc, free_places );
			++_alive_ships;
			
			return true;
		}
		
		return false;
	}

	public void process() {

		_map.draw();
	}
	
	public boolean is_map_filled() {
		
		return _max_ships == _ships.size();
	}
	
	public int get_alive_ships() {
		
		return _alive_ships;
	}
	
	public Map get_map() {
		
		return _map;
	}
}
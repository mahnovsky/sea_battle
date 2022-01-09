package com.evhenii.seabattle;

import java.util.Random;

/**
 * 
 * - need to fix freeze, rework fill algorithm
 *
 */
public class ShipFiller {

	private BasePlayer _player;
	
	public ShipFiller(BasePlayer player) {
		
		_player = player;
	}
	
	public void fill( ) {
		
		Random rand = new Random();
		java.util.List<Point> map_points = new java.util.ArrayList<Point>();
		
		for( int x = 0; x < Map.With; ++x ) {
			
			for( int y = 0; y < Map.Height; ++y ) {
				
				map_points.add( new Point( x, y ) );
			}
		}
		
		
		for(int i = 1; i < DeckCount.values().length; ++i) {
			
			DeckCount dc = DeckCount.value_of(i);
			
			while( _player.get_free_places(dc) > 0 ) {
			
				Orientation orient = rand.nextInt(2) > 0 ? Orientation.Horizontal
						: Orientation.Vertical;
				
				do
				{
					// we get number from 0 to map_points.size()
					int index = rand.nextInt( map_points.size() );
				
					Point coord = map_points.get( index );
					
					map_points.remove( index );
					
					Orientation reverse_orient = Orientation.reverse( orient );
					
					if( _player.is_posible_place( reverse_orient, dc, coord ) )
					{
						_player.add_ship( reverse_orient, dc, coord );
						
						break;
					}
					else if( _player.is_posible_place( orient, dc, coord) ) {
						
						_player.add_ship( orient, dc, coord );
						
						break;
					}
				}
				while( !map_points.isEmpty() );
			}
		}
	}
}

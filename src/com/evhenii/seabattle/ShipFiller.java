package com.evhenii.seabattle;

import java.util.Random;

public class ShipFiller {

	private BasePlayer _player;
	
	public ShipFiller(BasePlayer player) {
		
		_player = player;
	}
	
	public void fill( ) {
		
		Random rand = new Random();  
		
		for(int i = 1; i < DeckCount.values().length; ++i) {
			
			DeckCount dc = DeckCount.value_of(i);
			
			while( _player.get_free_places(dc) > 0 ) {
			
				Orientation orient = rand.nextInt(2) > 0 ? Orientation.Horizontal
						: Orientation.Vertical;
				
				do
				{
					int x = rand.nextInt(10);
					int y = rand.nextInt(10);
				
					Point coord = new Point( x, y );
					
					if( _player.is_posible_place(orient, dc, coord) )
					{
						_player.add_ship( orient, dc, coord );
						
						break;
					}
				}
				while( true );
			}
		}
	}
}

package com.evhenii.seabattle;

public enum DeckCount {

	Invalid(-1), One(1), Two(2), Three(3), Four(4);

    private final int value;

    private static DeckCount [] _map;
    static {
    	_map = new DeckCount[5];

    	int counter = 0;

    	for (DeckCount d : DeckCount.values()) {
            _map[ counter++ ] = d;
        }
    }

    private DeckCount( int value ) {
        this.value = value;
    }

    public int get_value() {
        return value;
    }

    public static DeckCount value_of( int i ) {

    	if( i >= 0 && i < 5 ){

    		return _map[i];
    	}

    	return Invalid;
    }
}
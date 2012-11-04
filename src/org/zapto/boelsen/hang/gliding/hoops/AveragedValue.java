package org.zapto.boelsen.hang.gliding.hoops;

import java.util.LinkedList;

public final class AveragedValue
{
    private LinkedList< Float > queue__;
    private final int size__;
    
    public AveragedValue( int _size )
    {
        size__ = _size;
        queue__ = new LinkedList< Float >();
    }
    
    public void add( float _new )
    {
        queue__.add( _new );
        if( queue__.size() > size__ )
            queue__.remove();
    }
    
    public float getAverage()
    {
        final LinkedList< Float > queue = (LinkedList< Float >)queue__.clone();
        float sum = 0.0f;
        
        while( ! queue.isEmpty() )
            sum += queue.remove();
        
        return sum / queue__.size();
    }
}

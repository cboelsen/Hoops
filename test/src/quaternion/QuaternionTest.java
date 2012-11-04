package quaternion;

import org.zapto.boelsen.hang.gliding.hoops.Player;

import android.test.AndroidTestCase;
import rajawali.math.Quaternion;

public class QuaternionTest
    extends AndroidTestCase
{
    public void setUp() throws Exception
    {
        super.setUp();
    }
    
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testFromEuler1()
    {
        Quaternion q1 = Player.q_from_euler( 10, 20, 30 );
        Quaternion q2 = new Quaternion();
        q2.fromEuler( -110, -120, -130 );
        
        assertEquals( q1.w, q2.w );
        assertEquals( q1.x, q2.x );
        assertEquals( q1.y, q2.y );
        assertEquals( q1.z, q2.z );
    }
    
    public void testFromEuler2()
    {
        Quaternion q1 = Player.q_from_euler( 0, 0, 0 );
        Quaternion q2 = new Quaternion();
        q2.fromEuler( 10, 20, 30 );
        
        assertEquals( q1.w, q2.w );
        assertEquals( q1.x, q2.x );
        assertEquals( q1.y, q2.y );
        assertEquals( q1.z, q2.z );
    }
}

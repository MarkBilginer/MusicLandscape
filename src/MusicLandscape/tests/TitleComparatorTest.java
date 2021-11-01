// **************************************************
//		
//       git.rev = 232
//  git.revision = 5758f042c648661b29a7471f428d9556f8ed5e72
//         stage = LW
//
// ***************************************************
package MusicLandscape.tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Comparator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import MusicLandscape.entities.*;
import MusicLandscape.util.comparators.*;


/**
 * 
 * @author TeM
 * @version 232
 * @Stage LW
 *
 */
public class TitleComparatorTest {

	@Test(description="checks if the interface java.util.Comparator has been used")
	public void checkType(){
		TitleComparator com= new TitleComparator();
		assertTrue((com instanceof Comparator), "you should implement generic interface Comparator<Track>");
		
	}
	  @Test(dataProvider="title")
	  public void compare(String t1, String t2, int res) {

		  TitleComparator com= new TitleComparator();
		  
		  int result= com.compare(new Track(t1), new Track(t2));
		  
		  if(res>0){
			  assertTrue(result>0, "first should be bigger than second");  
		  }else if(res==0){
			  assertTrue(result==0, "both should be equal");
		  }else if(res<0){
			  assertTrue(result<0, "first should be smaller than second");
		  }
	  }
	  
	  @DataProvider(name = "title")
		private static Object[][] title() {
			return new Object[][] {
					{ "same", "same", 0}, 
					{"same", "other", 4}, 
					{"other", "same", -4} 
					};
		}

	  @Test(description="checks return value of toString")
	  public void testtoString() {
		  TitleComparator com= new TitleComparator();
		  assertEquals(com.toString(),"by title", "toString() should return another value" );
	  }
}

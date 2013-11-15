package chap3Master;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class HamcrestTest {

	private List<String> values;
	@Before
	public void setUpList(){
		values=new ArrayList<String>();
		values.add("x");
		values.add("y");
		values.add("z");
	}
	
	@Test
	public void testWithoutHamcrest(){
		assertFalse(values.contains("one")||values.contains("two") || values.contains("three"));
	}
	
	@Test
	public void testWithHamcrest(){
		//TODO
//		assertThat(values,hasItem(anyOf(equalToIgnoringCase("One"),equalTo("two"),equalTo("three"))));
//		assertThat(values,hasItem(anyOf("One")));
	}
}

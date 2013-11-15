package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class ParameterizedTest {

	private double expected;
	private double valueOne;
	private double valueTwo;
	
	@Parameters
	public static Collection<Double[]> getTestParameters(){
		return Arrays.asList(new Double[][]{
				{2.0,1.0,1.0},
				{3.0,2.0,1.0},
				{4.0,3.0,1.0}
		});
	}
	
	public ParameterizedTest(double expected,double valueOne,double valueTwo){
		this.expected=expected;
		this.valueOne=valueOne;
		this.valueTwo=valueTwo;
	}
	
	@Test
	public void sum(){
		Calculator calc=new Calculator();
		assertEquals(expected,calc.add(valueOne, valueTwo),0);
	}
}

package worldBank_mapreduce;

/**
 * @author Job Mwesigwa
 * Gets the values from the mapper and finds the sum which it uses to find the mean/ average
 * then the results are sent to the output file
 */

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

	/**
	 * @param key
	 * @param values
	 * @param context
	 */
	public void reduce(Text key, Iterable<FloatWritable> values, Context context)
			throws IOException, InterruptedException {
		
		//Using the float because of the values from the table
		//sum and ave are the sum and average of the vales respectively
		FloatWritable val = new FloatWritable();
		float sum =0, ave = 0;
		
		//values in iterable. the values are the result after shuffle
		for(FloatWritable value: values)
			{
				//summing the vales 
				sum += value.get();
				
				//getting the average of the values 
				ave = sum/10;
				
				//value is set to average 
				val.set(ave);
			}
		
		//results to be written to the output value
		context.write(key, val);
	}

}

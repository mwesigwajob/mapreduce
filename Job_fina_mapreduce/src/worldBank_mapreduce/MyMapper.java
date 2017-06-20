package worldBank_mapreduce;

/**
 * @author Job Mwesigwa
 * This the mapper of the Job to be done
 * It receives a line from the file and splits it using the comma ","
 * Checks for the indicator, if the indicator is age depedence ratio, then it uses the country name as the key
 * takes in 10 values that go with on key which it then send to the reducer to calculate its average
 */

import java.io.IOException;


import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MyMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

	@Override
	/**
	 * @param key
	 * @param vale
	 * @param context
	 */
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		//we need an output key and an output value
				Text myOutputKey = new Text();
				
				//create an array of strings from the line read
				String[] words = value.toString().split(",");
				
				//only consider lines that have more than 60 items inside to avoid exemptions 
					if (words.length>=60){
						
						//Selecting the age dependence ratio with its code "SP.POP.DPND",
						//the other part ensures that the cells have some percentages in them
						if (words[3].equalsIgnoreCase("SP.POP.DPND") && !words[4].equals(""))
							{
							
							//loop to get data from 1960 to 1970 (10 years)
							for (int i =4; i<14;i++){
								//ensuring we don't take in empty strings
								if (!words[i].equals("")){
									
									//converting the percentage value to float and storing it in the output value
									FloatWritable myOutputValue = new FloatWritable(Float.parseFloat(words[i]));
									
									// the Key stays constant for the 10 values that are entered 
									//The country name is taken in as the Key
									myOutputKey.set(words[0]);
									
									//10 vales are sent to the producer to find the average but with the same key
									context.write(myOutputKey, myOutputValue);}
								}
							}
						}
	}

}

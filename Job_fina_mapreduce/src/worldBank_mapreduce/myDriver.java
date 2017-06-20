package worldBank_mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import worldBank_mapreduce.MyMapper;
import worldBank_mapreduce.MyReducer;


public class myDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// Use programArgs array to retrieve program arguments.
		String[] programArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		Job job = new Job(conf);
		job.setJarByClass(myDriver.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		// TODO: Update the input path for the location of the inputs of the map-reduce job.
		FileInputFormat.addInputPath(job, new Path(programArgs[0]));
		// TODO: Update the output path for the output directory of the map-reduce job.
		FileOutputFormat.setOutputPath(job, new Path(programArgs[1]));

		// Submit the job and wait for it to finish.
		job.waitForCompletion(true);
		// Submit and return immediately: 
		// job.submit();
	}

}

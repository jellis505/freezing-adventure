package big_data.big_data1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemBasedRec {
	
	public static void run() throws NumberFormatException, IOException, TasteException{
		// I don't know Java, so everything is going in the 
    	// main function here
    	
    	// Files Used
    	String artist_names_file = "/Users/joeellis/Documents/git/freezing-adventure/Yahoo_Datasets/ydata-ymusic-artist-names-v1_0.txt";
    	String user_ratings_file = "/Users/joeellis/Documents/git/freezing-adventure/Yahoo_Datasets/ydata-ymusic-user-artist-ratings-v1_0.txt";
    	String user_ratings_short_file = "/Users/joeellis/Documents/git/freezing-adventure/Yahoo_Datasets/ydata-ymusic-user-artist-ratings-v1_0_short.txt";
    	
    	// Read in the artist names file
    	BufferedReader reader = new BufferedReader(new FileReader(artist_names_file));
    	String line = null;
    	Map<Integer,String> artist_dic = new HashMap<Integer, String>();
    	while ((line = reader.readLine()) != null) 
    	{
    	    String[] parts = line.split("\t");
    	    int id = Integer.parseInt(parts[0]);
    	    artist_dic.put(id, parts[1]);
    	}
    	System.out.println("Loaded Artist Names");
    	
    	// Now create the Datamodel and the given similarities that we want
    	// The short model has only five data points and is used for debugging
    	System.out.println("Loading Model");
    	DataModel model = new FileDataModel(new File(user_ratings_short_file));
    	System.out.println("Loaded the Data Model");
    	
    	// This section here does the actual recommendation
    	// Similarity Metrics
    	ItemSimilarity sim = new PearsonCorrelationSimilarity(model);
    	
    	// Recommender Model
    	ItemBasedRecommender rec = new GenericItemBasedRecommender(model, sim);
    	System.out.println("Initialized Recommender");
    	
    	// Now output some of the recommendations of the users
    	/* THIS OUTPUT IS NOT USED HERE
    	int[] test_lists = {1000125, 1006373, 1006978};
    	for (int i = 0; i < 3; i ++)
    	{
    		// This is the recommender for each user
    		System.out.println("Item:" + test_lists[i]);
    		List<RecommendedItem> list_of_recs = rec.recommend(test_lists[i],3);
    		
    		// Now here is the Recommended Item, for this user
    		//  OUTPUT TO THE SCREEN
    		for (RecommendedItem rec_item : list_of_recs)
    		{
    			int item_id = (int) rec_item.getItemID();
    			float val = rec_item.getValue();
    			System.out.println(artist_dic.get(item_id) + " value:" + val);	
    		}
    	} 
    	*/
    	
    	
    	// Now let's test some of our data here, and see how well we perform
    	// Initialize the Recommender Evaluator
    	RecommenderEvaluator eval = new AverageAbsoluteDifferenceRecommenderEvaluator();
    	
    	// The evaluator only takes a recommender builder so we need to use that.
    	RecommenderBuilder recBuild = new RecommenderBuilder() 
    	{
    		public Recommender buildRecommender(DataModel model) throws TasteException
    		{
    			// These are the same recommender functions from above
    			ItemSimilarity sim = new PearsonCorrelationSimilarity(model);
    			return new GenericItemBasedRecommender(model, sim);
    		}
    		
    	};
    	
    	double score = eval.evaluate(recBuild, null, model, 0.8, 0.2);
    	System.out.println("Recommender Score: " + score);	
	}

}

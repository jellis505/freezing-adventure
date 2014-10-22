package big_data.big_data1;

// Package Import Standard Java Packages
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

// Mahout Imports For Recommendation
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
// Imports for Evaluation
import org.apache.mahout.cf.taste.impl.eval.*;
import org.apache.mahout.cf.taste.eval.*;




public class App 
{		
    public static void main( String[] args ) throws Exception
    {
    	// This is the User Based code
    	//UserBasedRec UserRec = new UserBasedRec();
    	//UserRec.run();
    	
    	// This is the Item based Recommendation
    	ItemBasedRec ItemRec = new ItemBasedRec();
    	ItemRec.run();
    	
    	
    }    
}

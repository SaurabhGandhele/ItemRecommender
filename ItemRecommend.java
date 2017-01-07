package com.predictionmarketing.recommender; 


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommend {

	public static void main(String args[])
	{
		try {
			
			DataModel dm = new FileDataModel(new File("data/movies.csv"));
			
			ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
			
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("data/output.csv"));
			
			int x=1;
			for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();)
			{
				long ItemId = items.nextLong();
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(ItemId, 5);
				
				for(RecommendedItem recommendation : recommendations)
				{
					System.out.println(ItemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
					bw.write(ItemId + "," + recommendation.getItemID() + "," + recommendation.getValue() + "\n");
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Error received");
			
			e.printStackTrace();
			
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Taste Error received");
			
			e.printStackTrace();
			
		}
	}
}

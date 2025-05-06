package main.java.domain.adapter;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import main.java.domain.IStorage;
import unibo.basicomm23.utils.CommUtils;

public class AdapterStorageMongo extends AdapterStorage implements IStorage {
	  private MongoClient mongoClient;
	  private MongoCollection<Document> collection;
	
	public AdapterStorageMongo(String mongourl) {
		  //ASSUMPTION: mongourl != null
		  CommUtils.outcyan("Using AdapterStorageMongo | mongoUrl="+mongourl);
		  //potrebbe essere: url = "mongodb://mongosrv:27017";
		   mongoClient = MongoClients.create(mongourl); 
	      // Accedere al database
		   MongoDatabase database = mongoClient.getDatabase("cargodb");
	      // Accedere a una collezione (crea se non esiste)
		   collection = database.getCollection("products");		  
		   CommUtils.outcyan("AdapterStorageMongo | Documents collection num: " + collection.estimatedDocumentCount()); //
	}


	@Override
	public void createItem(int id, String jsonRep) {
		  CommUtils.outmagenta("AdapterStorageMongo | createProduct   " + jsonRep );
		  
//		  Document productdoc = new Document("_id", new ObjectId());
//		  productdoc.append("productId", id);
//		  productdoc.append("productJson", jsonRep);
		  
		  
		  
	        Document document = new Document("productId", id);
	        document.append("jsonRep", jsonRep);
			collection.insertOne(document);
	}

	@Override  
	public String getItem(int id) {
	    //LOG.debug("/product return the found product for productId={}", productId);
//		System.out.println("AdapterStorageMongo | getItem: " + id);	
        // 2. Mongo Read
        FindIterable<Document> myDocs = collection.find( new Document("productId", id) );
        Document myDoc = myDocs.first();
//        MongoCursor<Document> myDocsIter = myDocs.iterator();
//        while( myDocsIter.hasNext() ) {
//        	CommUtils.outgreen("... "+myDocsIter.next());
//        }
//         System.out.println("AdapterStorageMongo | Document myDoc: " + myDoc);		  
		if (myDoc == null) {
			return null;
		} 
		else {
			return myDoc.getString("jsonRep");
			// System.out.println("AdapterStorageMongo | found product: " + myDoc );
			//return ""+new Product( myDoc.getInteger("id"), myDoc.getString("name"), myDoc.getInteger("weight") );
		} 
	}
	
	public Document getDocument(int id) {
        FindIterable<Document> myDocs = collection.find( new Document("productId", id) );
        Document myDoc = myDocs.first();
        return myDoc;
	}

	@Override
	public List<String> getAllItems(){
        List<String> documentList = new ArrayList<>();
        
        // Trova tutti i documenti nella collezione
        FindIterable<Document> findIterable = collection.find();

        // Aggiungi ogni documento alla lista
        for (Document doc : findIterable) {
            documentList.add( "'"+doc.getString("jsonRep")+"'");
        }

        return documentList;
	} 

	@Override 
	public boolean deleteItem(int productId) {
		Document myDoc = getDocument(productId);
		if( myDoc == null ) return false;
        DeleteResult answer = collection.deleteOne( myDoc );  //new Document("id", productId));
        CommUtils.outcyan("Document deleted id=" + answer.getDeletedCount() );
        return answer.getDeletedCount() == 1;
	}
	
	@Override 
	public int getItemNum() {		 
		return (int) collection.countDocuments();
	}
	
 

}

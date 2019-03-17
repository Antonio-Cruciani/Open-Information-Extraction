package it.uniroma.ia.dao;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;


public class QueryDao {


	@SuppressWarnings("null")
	public Map<Integer,String> getResult(String sog){
		String rdf4jServer = "http://localhost:7200/";
		String repositoryID = "kgracing";
		Repository repo = new HTTPRepository(rdf4jServer, repositoryID);
		repo.initialize();
		Map<Integer, String> risp = new HashMap<Integer, String>();
		String soggetto = sog;
		try (RepositoryConnection conn = repo.getConnection()) {
			   String queryString = " PREFIX ex: <http://example.org/>  select * where { <http://example.org/"+soggetto+"> ?p ?o .} limit 1000  ";
			   TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			    tupleQuery.evaluate();
			    try (TupleQueryResult result = tupleQuery.evaluate()) {
			  
			    	List<String> bindingNames = result.getBindingNames();
			    	int i=0;
			    	while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	  // rispo.add(bindingSet);
			    	   Value firstValue = bindingSet.getValue(bindingNames.get(0));
			    	   Value secondValue = bindingSet.getValue(bindingNames.get(1));
			    	  String preddd=firstValue.toString();
			    	  String oggg=secondValue.toString();
			    	  String union = preddd+"*****"+oggg;
			    	    risp.put(i, union);
			    	i++;
			    	   System.out.println(i);
			    	   System.out.println(union);
			    	   System.out.println("*");
			    	   System.out.println("*");
			    	}
			    
			    }
		return risp;
		
	}
}
	
	public Map<Integer,String> getResult2(String pred,String obj){
		String rdf4jServer = "http://localhost:7200/";
		String repositoryID = "kgracing";
		Repository repo = new HTTPRepository(rdf4jServer, repositoryID);
		repo.initialize();
		Map<Integer, String> risp = new HashMap<Integer, String>();
		String predicato = pred;
		String ogg = obj;

		try (RepositoryConnection conn = repo.getConnection()) {
			   String queryString = " PREFIX ex: <http://example.org/>  select * where { ?s <http://example.org/"+predicato+"> <http://example.org/"+ogg+"> .} ORDER BY RAND() limit 1000  ";
			   TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			    tupleQuery.evaluate();
			    try (TupleQueryResult result = tupleQuery.evaluate()) {
			  
			    	List<String> bindingNames = result.getBindingNames();
			    	int i=0;
			    	while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	  // rispo.add(bindingSet);
			    	   Value firstValue = bindingSet.getValue(bindingNames.get(0));
			    	   
			    	  String preddd=firstValue.toString();
			    	
			    	    risp.put(i, preddd);
			    	i++;
			    	   System.out.println(i);
			    	   System.out.println(preddd);
			    	   System.out.println("*");
			    	   System.out.println("*");
			    	}
			    
			    }
		return risp;
		
	}
}
	
	
	public Map<Integer,String> getResult3(String pred){
		String rdf4jServer = "http://localhost:7200/";
		String repositoryID = "kgracing";
		Repository repo = new HTTPRepository(rdf4jServer, repositoryID);
		repo.initialize();
		Map<Integer, String> risp = new HashMap<Integer, String>();
		String predicato = pred;
		try (RepositoryConnection conn = repo.getConnection()) {
			   String queryString = " PREFIX ex: <http://example.org/>  select * where {  ?s <http://example.org/"+predicato+"> ?o .} limit 1000  ";
			   TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			    tupleQuery.evaluate();
			    try (TupleQueryResult result = tupleQuery.evaluate()) {
			  
			    	List<String> bindingNames = result.getBindingNames();
			    	int i=0;
			    	while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	  // rispo.add(bindingSet);
			    	   Value firstValue = bindingSet.getValue(bindingNames.get(0));
			    	   Value secondValue = bindingSet.getValue(bindingNames.get(1));
			    	  String soggetto=firstValue.toString();
			    	  String oggetto=secondValue.toString();
			    	  String union = soggetto+"*****"+oggetto;
			    	    risp.put(i, union);
			    	i++;
			    	   System.out.println(i);
			    	   System.out.println(union);
			    	   System.out.println("*");
			    	   System.out.println("*");
			    	}
			    
			    }
		return risp;
		
	}
}
	
	
	
	
}
package main.java;

import org.json.simple.parser.JSONParser;

public class ToProlog {

	public static String cvtString( String s) {
		try {
			new JSONParser().parse( s );
			return "'"+s+"'";
		}catch(Exception e) {
			//if( isAtomic(s) )
			if( s.contains(",")) return "'"+s+"'";
			else return s;
		}
		
	}
}

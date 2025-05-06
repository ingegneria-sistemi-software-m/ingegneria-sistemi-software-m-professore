package main.java.domain.adapter;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import unibo.basicomm23.utils.CommUtils;
/*
 * No more used ?
 */
public class PropUtils {

	public static String get(String key) {
		Properties props = new Properties();
		Path envFile = Paths.get("./cargoservice.properties");
		try {
			InputStream inputStream = Files.newInputStream(envFile);
			props.load(inputStream);
			
			String keyVal = (String) props.get(key);
			CommUtils.outblue("PropUtils - check " + key + " value="+ keyVal);
			return keyVal  ;
		}catch(Exception e) {
			CommUtils.outred("PropUtils - does not find: " +e.getMessage());
			return "localhost";
		}
		
	}
	
	public static void main(String[] args) {
		CommUtils.outmagenta( ""+PropUtils.get("TESTRAM") );
	}
}

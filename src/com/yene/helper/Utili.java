package com.yene.helper;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utili {

    /*
     * This Pattern will match on either quoted text or text between space, including
     * whitespace, and accounting for beginning and end of line.
     */
    //private final Pattern csvPattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
    private final Pattern csvPattern = Pattern.compile("\"([^\"]*)\"|(?<= |^)([^ ]*)(?: |$)");  
    private ArrayList<String> allMatches = null;        
    private Matcher matcher = null;
    
    public Utili() {                
        allMatches = new ArrayList<String>();
        matcher = null;
      
    }

    public ArrayList<String> parse(String csvLine) {
        matcher = csvPattern.matcher(csvLine);
        allMatches.clear();
        String match;
        while (matcher.find()) {
            match = matcher.group(1);
            if (match!=null) {
                    allMatches.add(match);
            }
            else {
                    allMatches.add(matcher.group(2));
            }
        }
        ArrayList<String> results = new ArrayList<String>();
		for(int i = 0 ; i < allMatches.size(); i++ ) {
			//if(!allMatches.get(i).equals("")) {
				results.add(allMatches.get(i).trim());
				System.out.print(allMatches.get(i).trim());
			//}
		}
        if (results.size() > 0) {
                return results;
        }
        else {
                return new ArrayList<String>();
        }                       
    }   
}
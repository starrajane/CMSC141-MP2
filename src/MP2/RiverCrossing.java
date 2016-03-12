package MP2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Furton
 */
public class RiverCrossing {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        FileInputStream read_file = new FileInputStream("C://mp2.in");
	BufferedReader br = new BufferedReader(new InputStreamReader(read_file));
        FileWriter fw = new FileWriter("MP2.out");
        
        String input;
        
        while ((input = br.readLine()) != null) {
            if(crossRiver(input))
                fw.write("OK\n");
            else
                fw.write("NG\n");
	}

        br.close();
        fw.close();
    }
    
    public static boolean verifyState(String s){
        //splits the string forming an array for easy checking of invalid states
        String[] strArr = s.split("_");
        
        for(String str : strArr){
            if(str.equals("LR") || str.equals("RL") || str.equals("CR") ||
                    str.equals("RC") || str.equals("LRC") || str.equals("LCR") ||
                    str.equals("RLC") || str.equals("RCL") || str.equals("CLR") ||
                    str.equals("CRL")){
                return false;
            }
        }
        return true;
    }
    
    public static boolean crossRiver(String s) throws IOException{
        //initialized the state to "MLRC_"
        String currentState = "MLRC_";
        char cross;
        boolean bank = false;
        int i=0;
        
        for(; i < s.length() ; i++){
            cross = s.charAt(i);
            
            if(cross == 'R'){
                //bbcs with her pet goes to the west bank
                //bank=false means that the bbcs and her pet
                //is on the east bank
                if(i%2 == 0 && bank==false && verifyLocation(currentState, 'R')){
                    currentState = currentState.replace("R", "");
                    currentState = currentState.replace("M", "");
                    currentState = currentState + "MR";
                    bank=true;
                }
                //goes to east bank
                //bank=true means that the bbcs and her pet
                //is on the west bank
                if(i%2 == 1 && bank==true && verifyLocation(currentState, 'R')){
                    currentState = currentState.replace("R", "");
                    currentState = currentState.replace("M", "");
                    currentState = "MR" + currentState;
                    bank = false;
                }
                //checks if the location of bbcs and her pet is valid
                //and the current state as well
                //then sets i to s.length() to stop the loop
                if(!(verifyLocation(currentState, 'R')) || !(verifyState(currentState))){
                    i = s.length();
                    break;
                }
            }
            
            if(cross == 'L'){
                if(i%2 == 0 && bank==false && verifyLocation(currentState, 'L')){
                    currentState = currentState.replace("L", "");
                    currentState = currentState.replace("M", "");
                    currentState = currentState + "ML";
                    bank=true;
                }
                
                if(i%2 == 1 && bank==true && verifyLocation(currentState, 'L')){
                    currentState = currentState.replace("L", "");
                    currentState = currentState.replace("M", "");
                    currentState = "ML" + currentState;
                    bank = false;
                }
                
                if(!(verifyLocation(currentState, 'L')) || !(verifyState(currentState))){
                    i = s.length();
                    break;
                }
            }
            
            if(cross == 'C'){
                if(i%2 == 0 && bank==false && verifyLocation(currentState, 'C')){
                    currentState = currentState.replace("C", "");
                    currentState = currentState.replace("M", "");
                    currentState = currentState + "MC";
                    bank=true;
                }
                
                if(i%2 == 1 && bank==true && verifyLocation(currentState, 'C')){
                    currentState = currentState.replace("C", "");
                    currentState = currentState.replace("M", "");
                    currentState = "MC" + currentState;
                    bank = false;
                }
                
                if(!(verifyLocation(currentState, 'C')) || !(verifyState(currentState))){
                    i = s.length();
                    break;
                }
            }
            
            if(cross == 'N'){
                if(i%2 == 0 && bank==false && (currentState.indexOf('M') < currentState.indexOf('_'))){
                    currentState = currentState.replace("M", "");
                    currentState = currentState + "M";
                    bank=true;
                }
                
                if(i%2 == 1 && bank==true && (currentState.indexOf('M') > currentState.indexOf('_'))){
                    currentState = currentState.replace("M", "");
                    currentState = "M" + currentState;
                    bank = false;
                }
                
                if(!(verifyState(currentState))){
                    i = s.length();
                    break;
                }
            }
        }
        if(verifyState(currentState) && currentState.startsWith("_")){
            System.out.println("OK");
            return true;
        }
        else{
            System.out.println(s + " NG");
            return false;
        }
        
    }
    
    public static boolean verifyLocation(String s, char animal){
        //checks if both bbcs and her pet is on the same side of the river
        if((s.indexOf('M') < s.indexOf('_')) && (s.indexOf(animal) < s.indexOf('_')))
            return true;
        if((s.indexOf('M') > s.indexOf('_')) && (s.indexOf(animal) > s.indexOf('_')))
            return true;
        return false;
    }
}
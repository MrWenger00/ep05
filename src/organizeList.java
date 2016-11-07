/**@author Flávio Prado
 * @author Guilherme Maeda
 * @author Guilherme Wenger
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class organizeList {
	public static cityOfOrigin organize() {
		String pathToFile = "entrada.txt";
        File fl = new File(pathToFile);        
        String line;
    	cityOfOrigin newListOfCitys = new cityOfOrigin();
    	originNode newOrigin = new originNode();
    	newListOfCitys.head.next = newOrigin;
            	
        try {
            BufferedReader br = new BufferedReader(new FileReader(fl));
            line = br.readLine();
            line = line.trim();
            String[] citys = line.split(";");
            newOrigin.cityName = citys[0];
            newOrigin.next = null;
            connectionNode newConnection = new connectionNode();
            newConnection.cityName = citys[1];
            newOrigin.connections = newConnection;
            listOfConnections connections = new listOfConnections();
            connections.head = newOrigin;
            
            for (int i = 2; i < citys.length; i++) {
				connectionNode connection = new connectionNode();
				connection.cityName = citys[i];
				connections.insert(connection,newOrigin);
			}
            
            while(br.ready()){
            	line = br.readLine();
            	line = line.trim();
                String[] city = line.split(";");
                originNode origin = new originNode(); 
                origin.cityName = city[0];
                newListOfCitys.insert(origin, newListOfCitys.head);
                origin.next = null;
                connectionNode newConnectionNode = new connectionNode();
                newConnectionNode.cityName = city[1];
                origin.connections = newConnectionNode;
                listOfConnections newConnections = new listOfConnections();
                newConnections.head = origin;
                
                for (int i = 2; i < city.length; i++) {
    				connectionNode connection = new connectionNode();
    				connection.cityName = city[i];
    				connections.insert(connection,origin);
    			}
            }
            
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(organizeList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(organizeList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return newListOfCitys;
	}
	
	public static String search(cityOfOrigin list, String wanted){
		originNode auxiliary = new originNode();
		String citys ="";
		boolean found = false;
		auxiliary = list.head.next;
		if (list.head.next != null) {
			while(!found){
				String aux = Normalizer.normalize(auxiliary.cityName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
				aux = aux.replace(" ", "");
				wanted = Normalizer.normalize(wanted, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
				wanted = wanted.replace(" ", "");
				if(aux.equalsIgnoreCase(wanted)){
					found = true;
					connectionNode auxiliaryNode = new connectionNode();
					auxiliaryNode = auxiliary.connections;
					while(auxiliaryNode!=null){
						citys +=auxiliaryNode.cityName+","; 
						auxiliaryNode = auxiliaryNode.next;
					}
				}
				if(auxiliary.next != null){
					auxiliary = auxiliary.next;
				}else{
					found = true;
				}
			}
		}
			
		if(!citys.equals("")){
			return citys;
		}else{
			return "";
		}
	}
	
}

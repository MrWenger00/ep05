/**@author Flávio Prado
 * @author Guilherme Maeda
 * @author Guilherme Wenger
 * 
 */

public class listOfConnections {
	public originNode head;
	
	public originNode insert(connectionNode node, originNode head){
		connectionNode auxliaryNode;
        
        if (head.connections == null){            
            head.connections = node;
            return head;            
        } else {            
        	auxliaryNode = head.connections;            
            while(auxliaryNode.next != null) {                
            	auxliaryNode = auxliaryNode.next;                
            }            
            auxliaryNode.next = node;
            return head;
        }
	}
}

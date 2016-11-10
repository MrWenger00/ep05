/**@author Flávio Prado
 * @author Felipe Silva
 * @author Guilherme Maeda
 * @author Guilherme Wenger
 * 
 */

public class cityOfOrigin {
	public originNode head = new originNode();
	
	public originNode insert(originNode node, originNode head){
		originNode auxliaryNode;
        
        if (head.next == null){            
            head.next = node;
            return head;            
        } else {            
        	auxliaryNode = head;            
            while(auxliaryNode.next != null) {                
            	auxliaryNode = auxliaryNode.next;                
            }            
            auxliaryNode.next = node;
            return head;
        }
	}
}

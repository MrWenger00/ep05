/**@author Flávio Prado
 * @author Guilherme Maeda
 * @author Guilherme Wenger
 * 
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class searchScreen {
	
	public static JFrame menu;
	public static JPanel screen;
	public static JLabel lbCity;
	public static JLabel lbMessage;
	public static JTextField originCity;
	public static JButton btSearch;
	public static JTable jtTable;
	public static DefaultTableModel dtmTable;
	public static JScrollPane scroll;
	public static String[] column = new String[]{"Conexões"};
	public static cityOfOrigin newList = new cityOfOrigin();
	
	
	public static void main(String[] args){

		newList = organizeList.organize();
		createScreen();
		
	}
	
	public static void createScreen(){
		menu = new JFrame("Pesquisar conexões");
		menu.setSize(400, 300);
		menu.setLocationRelativeTo(null);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		menu.setResizable(false);
		
		startingComponents();
	}
	
	public static void startingComponents(){
		try {
			String lookAndFeel = UIManager.getSystemLookAndFeelClassName(); 
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			// erro
		}
		
		screen= new JPanel();
		screen.setSize(400, 400);
		screen.setLayout(null);
		screen.setBackground(Color.lightGray);
		screen.setVisible(true);		
		
		lbCity = new JLabel("Origem:");
		lbCity.setSize(100, 20);
		lbCity.setLocation(10, 30);
		lbCity.setVisible(true);
		
		lbMessage = new JLabel("Informe a cidade de origem e clique em pesquisar ou tecle ENTER ");
		lbMessage.setSize(400, 20);
		lbMessage.setLocation(10, 05);
		lbMessage.setVisible(true);
				
		originCity = new JTextField ();
		originCity.setSize(200,25);
		originCity.setLocation(70, 30);
		originCity.setVisible(true);
		
		btSearch = new JButton("Pesquisar");
		btSearch.setSize(100, 20);
		btSearch.setLocation(280, 30);
		btSearch.setVisible(true);
				
		dtmTable = new DefaultTableModel(column, 0){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	
		jtTable = new JTable();
		jtTable.setBounds(10,55, 380, 200);
		jtTable.setModel(dtmTable);
		
		screen.setVisible(true);
		
		scroll = new JScrollPane(jtTable);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 55, 380, 200);
		scroll.setViewportView(jtTable);
		expose(); 
	}
	
	public static void expose(){
		menu.setVisible(true);
		menu.add(screen);
		screen.add(lbMessage);
		screen.add(lbCity);
		screen.add(originCity);
		screen.add(btSearch);
		screen.add(scroll);
		actions();
	
	}
	//ações da tela
	public static void actions(){
		originCity.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				/*método sem implementação necessária*/
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				/*método sem implementação necessária*/
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					btSearch.doClick();
				}
			}
		});
		btSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTable(jtTable);
				String origin = originCity.getText().trim();
				if(validateString(origin)){
					JOptionPane.showMessageDialog(null,
							origin+" não é um nome válido! Verifique o Texto digitado e tente novamente.");
					originCity.grabFocus();
				}else{
					String[] connections = organizeList.search(newList, origin).split(",");				
					if(!connections[0].equals("")){
						for (int i = 0; i < connections.length; i++) {
							if(connections[i]!=null && connections[i]!=""){
								String[] add = new String[]{connections[i]};
								dtmTable.addRow(add);
							}
						}
					}else{
						JOptionPane.showMessageDialog(null,
								origin+" não possui conexões.");
						originCity.grabFocus();
						
					}
				}
			}
		});
	}
	//esse método limpa a JTable
	public static void clearTable(JTable table){
		while (table.getModel().getRowCount() > 0) {//executa equanto a Jtable possuir linhas
			((DefaultTableModel) table.getModel()).removeRow(0);//remove a linha
		}
	}
	//esse método verifica se foram digitados valores incorretos como numeros ou pontuação
	public static boolean validateString(String text){
		boolean result = false;
		text = text.replace(" ", "");
		text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");//remover acentuação
		String[] aux = text.split("");
		for (int i = 0; i < text.length(); i++) {
			if(!aux[i].matches("[a-zA-Z]")){
				result = true;
			}
		}
		return result;
	}
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextEditor extends JFrame implements ActionListener{

	JTextArea textArea; 
	JLabel fontLabel; 
	JButton textColourButton; 
	JScrollPane scrollPane; 
	JComboBox fontBox; 
	JSpinner textSizeSpinner; 
	
	JMenu menu; 
	JMenuBar menuBar; 
	JMenuItem openItem;
	JMenuItem exitItem;
	JMenuItem saveItem;
	
	
	//Text Area
	TextEditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Fady's Text Editor");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Times New Roman",Font .PLAIN, 20));
		
		//ScrollBar
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(475, 475));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Font Size Alteration
		fontLabel = new JLabel("Font:");
		
		textSizeSpinner = new JSpinner();
		textSizeSpinner.setPreferredSize(new Dimension(50, 25));
		textSizeSpinner.setValue(20);
		textSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) textSizeSpinner.getValue()));
				
			}
			
		});
		
		//Font Color Alteration
		textColourButton = new JButton("Colour");
		textColourButton.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Times New Roman");
		
		//MenuBar
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		openItem = new JMenuItem("Open"); 
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit"); 
		
		//Item listeners
		openItem.addActionListener(this);
		exitItem.addActionListener(this);
		saveItem.addActionListener(this);
		
		//Add Items to Menu
		//Add Menu to MenuBar
		menu.add(openItem);  
		menu.add(saveItem); 
		menu.add(exitItem);
		menuBar.add(menu); 
		
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel); 
		this.add(textSizeSpinner); 
		this.add(textColourButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
	}
	
	//Color/Font Alteration Function
	//Menu Item function
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==textColourButton) {
			JColorChooser colourChooser = new JColorChooser();
			
			Color colour = colourChooser.showDialog(null,"Select a Colour ",Color.black);
			
			textArea.setForeground(colour);
		}
		
		if(e.getSource()==fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		
		//Open function
		if(e.getSource()==openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int feedback = fileChooser.showOpenDialog(null);
			
			//Check File
			if(feedback == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null; 
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n"; 
							textArea.append(line);
						}
					}
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
		}
		//Save function
		if(e.getSource()==saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(".")); 
			
			int feedback = fileChooser.showSaveDialog(null);
			
			//File saver/directory selection
			if(feedback == JFileChooser.APPROVE_OPTION) {
				File file; 
				PrintWriter fileOut = null; 
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		//Exit function
		if(e.getSource()==exitItem) {
			System.exit(0);
		}
		
		
		
	}

}

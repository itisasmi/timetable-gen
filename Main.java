import java.awt.*;
import java.util.*;
import java.awt.event.*;    
import javax.swing.*; 
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;


class Main{  
public static void main(String[] args) throws IOException { 


    Map<String,Batch> B = new HashMap<String,Batch>();
    Map<String,Course> CO = new HashMap<String,Course>();
    Map<String,Professor> PR = new HashMap<String,Professor>();

    JFrame f=new JFrame();  
    JFrame s=new JFrame();  
    JPanel home=new JPanel();
    JLabel l1=new JLabel("Welcome to Timetable Generator");
    l1.setBounds(350,30,850,180);
    l1.setFont(new Font("TimesRoman",Font.BOLD,30));
    l1.setForeground(Color.white);
    BufferedImage image = ImageIO.read(new File("logo.jpg"));
    JLabel label = new JLabel(new ImageIcon(image));
    label.setSize(600,500);
    label.setBounds(350,20,500,600);
    home.setLayout(null);
    home.setBackground(Color.black);
    home.add(label);
    home.add(l1);


   
    JPanel view=new JPanel(); 
    String[] Timet={"Select",};
    String[] profs={"Select"};  
    String[] credits={"1","2","3","4"};
    String[] course={"Select"};
    JComboBox cr=new JComboBox(credits);
    JComboBox cb=new JComboBox(Timet); 
    JComboBox pf=new JComboBox(profs);
    JComboBox co=new JComboBox(course);
    JTabbedPane t=new JTabbedPane();
	JLabel vl=new JLabel("Timetable");
	vl.setBounds(530,5,250,70);
	vl.setForeground(Color.white);
    vl.setFont(new Font("TimesRoman",Font.BOLD,20)); 
	final String timet[][] = { {"Monday","","","","BREAK","","",""},    
                          {"Tuesday","","","","BREAK","","",""},    
                          {"Wednesday","","","","BREAK","","",""},
                          {"Thursday","","","","BREAK","","",""}, 
                          {"Friday","","","","BREAK","","",""}, 
                          {"Saturday","","","","BREAK","","",""}};
	String column[]={"","1","2","3","4","5","6","7"};
    JTable jt=new JTable(timet,column);
    jt.setSize(1000,100);
    JScrollPane sp=new JScrollPane(jt);
    sp.setSize(1000,150);
    sp.setBounds(100,150,1000,125);
    cb.setBounds(475,70,100,20);
    pf.setBounds(600,70,100,20);
    cb.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
    	    for(int i=0; i<6; i++) {
    	        for(int j=0; j<7; j++) {
    	            if (j == 3) {
    	                timet[i][j+1] = "BREAK";
    	            } else {
    	                Batch selectedBatch = B.get(cb.getItemAt(cb.getSelectedIndex()).toString());
    	                if (selectedBatch != null) {
    	                    timet[i][j+1] = selectedBatch.a[i][j];
    	                } else {
    	                    timet[i][j+1] = "ERROR: Batch not found";
    	                }
    	            }
    	        }
    	    }
    	    view.revalidate();
    	    view.repaint();  
    	}

    });
    pf.addActionListener(new ActionListener(){  
	    public void actionPerformed(ActionEvent e){  
            for(int i=0;i<6;i++){
                for(int j=0;j<7;j++){
			        timet[i][j+1]=PR.get(pf.getItemAt(pf.getSelectedIndex()).toString()).a[i][j];
                }
            }
            view.revalidate();
            view.repaint();  
        }
    });
    view.setLayout(null);
    view.setBackground(Color.black);
	view.add(vl); 
	view.add(cb);
    view.add(pf);
    jt.setVisible(true);
    sp.setVisible(true);
    view.add(sp);
    

    JPanel createBatch=new JPanel();
    JLabel cb1= new JLabel("Name");
    cb1.setFont(new Font("TimesRoman",Font.BOLD,20)); 
    cb1.setBounds(50,100,200,50);
    cb1.setForeground(Color.white);
    JTextField cb2 = new JTextField();
    cb2.setBounds(250,100,200,50);
    JLabel cb3 =new JLabel("Courses");
    cb3.setFont(new Font("TimesRoman",Font.BOLD,20)); 
    cb3.setBounds(50,275,200,50);
    cb3.setForeground(Color.white);
    JComboBox cb4 = new JComboBox(course);
    cb4.setBounds(250,275,200,50);
    JComboBox cb7 = new JComboBox(profs);
    cb7.setBounds(550,275,200,50);
    JButton cb5 = new JButton("ADD");
    cb5.setBounds(850,275,200,50);
    cb5.setBackground(Color.white);
    cb5.setForeground(Color.black);
    JButton cb6 = new JButton("Create");
    cb6.setBounds(550,100,200,50);
    cb6.setBackground(Color.white);
    cb6.setForeground(Color.black);
    cb6.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            cb.addItem(cb2.getText());
            B.put(cb2.getText(),new Batch());
            B.get(cb2.getText()).setName(cb2.getText());
            JOptionPane.showMessageDialog(s,"A new batch "+cb2.getText()+" has been created");
        }
    });
    cb5.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(cb4.getSelectedIndex()!=0 && cb7.getSelectedIndex()!=0){
                if(B.get(cb2.getText()).add(CO.get(cb4.getItemAt(cb4.getSelectedIndex()).toString()),PR.get(cb7.getItemAt(cb7.getSelectedIndex()).toString()))){
                    JOptionPane.showMessageDialog(s,"A new course has been added to the batch "+cb2.getText()+"\n"+"Course: "+cb4.getItemAt(cb4.getSelectedIndex()).toString()+"\n"+"Professor: "+cb7.getItemAt(cb7.getSelectedIndex()).toString());
                }
                else{
                    JOptionPane.showMessageDialog(s,"Periods are insufficient for adding this course to the batch");
                }
            }
            else{
                JOptionPane.showMessageDialog(s,"Select a valid course and valid professor");
            }
        }
    });
    cb4.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            cb7.removeAllItems();
            cb7.addItem("Select");
            for(int i=0;i<CO.get(cb4.getItemAt(cb4.getSelectedIndex()).toString()).getProfsSize();i++){
                cb7.addItem(CO.get(cb4.getItemAt(cb4.getSelectedIndex()).toString()).getProfsItem(i));
            }
        }
    });
    createBatch.setLayout(null);
    createBatch.setBackground(Color.black);
    createBatch.add(cb1);
    createBatch.add(cb2);
    createBatch.add(cb3);
    createBatch.add(cb4);
    createBatch.add(cb5);
    createBatch.add(cb6);
    createBatch.add(cb7);


    
    JPanel createProfessor=new JPanel();
    JLabel cp1=new JLabel("Name");
    cp1.setFont(new Font("TimesRoman",Font.BOLD,20));
    cp1.setForeground(Color.white);
    cp1.setBounds(50,100,200,50);
    JTextField cp2=new JTextField();
    cp2.setBounds(250,100,200,50);
    JLabel cp3=new JLabel("Course");
    cp3.setFont(new Font("TimesRoman",Font.BOLD,20));
    cp3.setBounds(50,275,200,50);
    cp3.setForeground(Color.white);
    JComboBox cp4=new JComboBox(course);
    cp4.setBounds(250,275,200,50);
    JButton cp5=new JButton("Add");
    cp5.setBackground(Color.white);
    cp5.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(cp4.getSelectedIndex()!=0){
                CO.get(cp4.getItemAt(cp4.getSelectedIndex()).toString()).addProfs(cp2.getText());
                PR.get(cp2.getText()).addCourses(cp4.getItemAt(cp4.getSelectedIndex()).toString());
                JOptionPane.showMessageDialog(s,"The course "+cp4.getItemAt(cp4.getSelectedIndex()).toString()+" has been assigned to "+cp2.getText());
            }
            else{
                JOptionPane.showMessageDialog(s,"Select a valid course!");
            }
        }
    });
    cp5.setBounds(550,275,200,50);
    JButton cp6=new JButton("Create");
    cp6.setBounds(550,100,200,50);
    cp6.setBackground(Color.white);
    cp6.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            pf.addItem(cp2.getText());
            cb7.addItem(cp2.getText());
            PR.put(cp2.getText(),new Professor());
            PR.get(cp2.getText()).setName(cp2.getText());
            JOptionPane.showMessageDialog(s,"A new professor "+cp2.getText()+" has been created");

        }
    });
    createProfessor.setLayout(null);
    createProfessor.setBackground(Color.black);
    createProfessor.add(cp1);
    createProfessor.add(cp2);
    createProfessor.add(cp3);
    createProfessor.add(cp4);
    createProfessor.add(cp5);
    createProfessor.add(cp6);



	JPanel createCourse=new JPanel();
    JLabel cc1= new JLabel("Name");
    cc1.setFont(new Font("TimesRoman",Font.BOLD,20));
    cc1.setBounds(50,100,200,50);
    cc1.setForeground(Color.white);
    JTextField cc2 = new JTextField();
    cc2.setBounds(250,100,200,50);
    JLabel cc3 = new JLabel("Hours");
    cc3.setFont(new Font("TimesRoman",Font.BOLD,20));
    cc3.setBounds(50,275,200,50);
    cc3.setForeground(Color.white);
    JComboBox cc4 = new JComboBox(credits);
    cc4.setBounds(250,275,200,50);
    JButton cc5 = new JButton("Create");
    cc5.setBounds(250,450,200,50);
    cc5.setBackground(Color.white);
    cc5.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){ 
            cb4.addItem(cc2.getText());
            cp4.addItem(cc2.getText());
            CO.put(cc2.getText(),new Course());
            CO.get(cc2.getText()).setName(cc2.getText());
            CO.get(cc2.getText()).setCredits(Integer.parseInt(cc4.getItemAt(cc4.getSelectedIndex()).toString()));
            JOptionPane.showMessageDialog(s,"A new course has been created"+"\n"+"Course: "+cc2.getText()+"\n"+"Credits: "+Integer.parseInt(cc4.getItemAt(cc4.getSelectedIndex()).toString()));        
        }
    });
    createCourse.setLayout(null);
    createCourse.setBackground(Color.black);
    createCourse.add(cc1);
    createCourse.add(cc2);
    createCourse.add(cc3);
    createCourse.add(cc4);
    createCourse.add(cc5);



    t.add("Home",home);  
    t.add("View",view);  
    t.add("Create Batch",createBatch);
    t.add("Create Course",createCourse);
	t.add("Create Professor",createProfessor);
    t.setBackground(Color.gray);  
    f.add(t);  
    f.setSize(1500,800);  
    t.setBounds(50,50,1200,600); 
    f.setLayout(null); 
    f.setVisible(true);
	  
}
}  












   


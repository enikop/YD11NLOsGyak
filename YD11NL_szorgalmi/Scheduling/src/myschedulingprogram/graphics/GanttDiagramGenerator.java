package myschedulingprogram.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
 

public class GanttDiagramGenerator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int[][] data;
	private int width;
 
    public GanttDiagramGenerator(String[] vaxis, int[][] num, int width) { //konstruktor param�terek: tengelyc�mek, sz�mlista, sz�less�g=legutols� processz befejez�si ideje
        super("Gantt Diagram");
        this.data=num;
        this.width = width;
        ArrayList<JLabel> numbers=new ArrayList<JLabel>();
        String[] axisName=vaxis;
        
        //Egys�gek sz�m�t�sa, megad�sa
        //egy egyseg 500/teljes hossz
        //alapesetben 500 k�ppont sz�les lesz a diagram, ha nem t�l hossz�ak a k�r�lfordul�si id�k
    	int unit = (int)Math.ceil(500.0/width); 
    	int marginLeft=120;
    	int marginTop=52;
    	
    	//JFrame alaptulajdons�gai
        setSize(unit*width+250, marginTop+100+data.length*15);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        
        
        ArrayList<JLabel> verticalAxisLabels = new ArrayList<JLabel>();
        
        //a f�gg�leges tengely elemeinek felt�lt�se, ki�r�sa
        for(int i=0; i<axisName.length; i++) {
        	verticalAxisLabels.add(new JLabel(axisName[i]));
    		verticalAxisLabels.get(i).setBounds(45, i*15, 40, 40);
    		verticalAxisLabels.get(i).setFont(new Font(Font.SERIF, Font.PLAIN,  10));
    		add(verticalAxisLabels.get(i));
        }
        
        //a v�zszintes tengely felt�lt�se, ki�r�sa
        for(int i=0; i<data.length; i++) {
        	numbers.add(new JLabel(String.valueOf(data[i][0])));
    		numbers.get(numbers.size()-1).setBounds(marginLeft+data[i][0]*unit-10, marginTop+data.length*15, 40, 20); //numbers.size()-1, mert 2 dimenzi� van
    		numbers.get(numbers.size()-1).setFont(new Font(Font.SERIF, Font.PLAIN,  10));
        	for(int j=1; j<data[0].length; j++) {
        		if(data[i][j]!=0) {
        			numbers.add(new JLabel(String.valueOf(data[i][j])));
        			numbers.get(numbers.size()-1).setBounds(marginLeft+data[i][j]*unit-10, marginTop+data.length*15, 40, 20);
        			numbers.get(numbers.size()-1).setFont(new Font(Font.SERIF, Font.PLAIN,  10));
        		}
        	}
        }
        //hozz�adjuk a JFramehez a lista elemeit
        for (int i = 0; i < numbers.size(); i++) {
            add(numbers.get(i));
        }
    }
    
    //grafika l�trehoz�sa
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
    
    //vonalrajzol� f�ggv�ny
    void drawLines(Graphics g) {
    	int unit = (int)Math.ceil(500.0/width);
    	int marginLeft=120;
    	int marginTop=52;
        Graphics2D g2d = (Graphics2D) g;
        
        //diagram vonalai
        for(int i=0; i<data.length; i++) {
        	for(int j=1; j<data[0].length; j++) {
        		if(data[i][j]!=0) { //
        			if(j%2==0)
            			g2d.setColor(new Color(0, 102, 102)); //a fut�sid�k jel�l�se
            		else //a v�rakoz�sok jel�l�se piros
            			g2d.setColor(Color.RED);
        			//vonal hossza = �br�zoland� id�tartam hossza * egys�g
        			//vonalak vertik�lisan egym�st�l 15 k�ppontnyira
            		g2d.drawLine(marginLeft+data[i][j-1]*unit, marginTop+i*15, marginLeft+data[i][j]*unit, marginTop+i*15);
        		}
        	}
        }
        
 
    }
 
 
}
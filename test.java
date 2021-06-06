import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;
class test{
	static JFrame frame;
	static Container c;
	public static void main(String[] args) {
    frame=new JFrame("CANNON");
    c=frame.getContentPane();
    c.setLayout(null);
    c.setBackground(Color.yellow);
    frame.setVisible(true);
    frame.setSize(1100,770);
    frame.setLocationRelativeTo(null);
    move panel=new move();
    frame.addKeyListener(panel);
    c.add(panel);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.validate();

	}
}

class move extends JPanel implements Runnable,KeyListener{
int ball_x=0,ball_y=425;
Toolkit tool;
double dx,dy;
int speed=20;
double time;
int angle=45;
Thread t;
double dist=0;
int block_x1=800,block_y1=410;
int cloud_x1=0,cloud_y1=0;
int cloud_x2=150,cloud_y2=80;
int cannon_x=0;
int cannon_y=0;
boolean status;
double gravity=0.45;
Rectangle ball_rect,block_rect;
JScrollBar set_angel,set_speed;
public void paint(Graphics g){
super.paint(g);
tool=Toolkit.getDefaultToolkit();
Image i1=tool.getImage("pic.png");
g.drawImage(i1,0,0,this);
Image i2=tool.getImage("cloud.png");
g.drawImage(i2,cloud_x1,cloud_y1,this);
g.drawImage(i2,cloud_x2,cloud_y2,this);
g.setColor(Color.red);
g.fillOval(ball_x,ball_y,30,30);
g.drawLine(0,485,1050,485);
g.setColor(Color.black);
g.setFont(new Font("Arial",Font.BOLD,25));
g.setColor(Color.cyan);
g.drawString("ANGEL="+angle,510,517);
g.drawString("POWER="+speed,510,557);
Graphics2D g2=(Graphics2D)g;
g2.setStroke(new BasicStroke(42));
g2.setColor(Color.black);
g2.drawLine(0,440,cannon_x,cannon_y);
g2.setColor(Color.magenta);
g2.fillOval(-3,425,50,50);
g2.fillRect(block_x1,block_y1,100,50);
    }


move(){   
this.setBounds(15,15,1050,695);
this.setBackground(Color.black);
this.setLayout(null);
cannon_x=2+(int)(90*Math.cos(Math.toRadians(angle)));
cannon_y=455-(int)(90*Math.sin(Math.toRadians(angle)));
new Thread(){
public  void run(){    
while(true){
cloud_x1+=1;
cloud_x2+=1;
try{Thread.sleep(10);}catch(Exception e){}
if(cloud_x1==950)
    cloud_x1=0;
if(cloud_x2==950)
    cloud_x2=0;
repaint();    
    }
}}.start();

set_angel=new JScrollBar(JScrollBar.HORIZONTAL,angle,0,10,90);
set_angel.setBounds(0,495,500,30);
set_angel.setBackground(Color.black);
set_angel.addAdjustmentListener(new AdjustmentListener(){
public  void adjustmentValueChanged(AdjustmentEvent ev){
angle=set_angel.getValue();
cannon_x=0+(int)(90*Math.cos(Math.toRadians(angle)));
cannon_y=455-(int)(90*Math.sin(Math.toRadians(angle)));

repaint();
    }
});
set_speed=new JScrollBar(JScrollBar.HORIZONTAL,speed,0,15,20);
set_speed.setBounds(0,535,500,30);
set_speed.setBackground(Color.black);
set_speed.addAdjustmentListener(new AdjustmentListener(){
public  void adjustmentValueChanged(AdjustmentEvent ev){
speed=set_speed.getValue();
repaint();
    }
});

this.add(set_angel);
this.add(set_speed);
   }
public   void run(){
dx=speed*Math.cos(Math.toRadians(angle));
dy=speed*Math.sin(Math.toRadians(angle));
while(status==true){
dy-=gravity;
ball_x+=dx;
ball_y-=dy;
ball_rect=new Rectangle(ball_x,ball_y,30,30);
block_rect=new Rectangle(block_x1,block_y1,100,50);
if(ball_rect.intersects(block_rect)){
        power_detect();
    }
try{Thread.sleep(10);}catch(Exception e){}
repaint();
if(ball_y>=425){
    status=false;
           }
       }
   }
public  void keyReleased(KeyEvent  key){}
public  void keyTyped(KeyEvent  key){}
public  void keyPressed(KeyEvent  key){  
if(key.getKeyCode()==KeyEvent.VK_SPACE){    
status=true;
ball_x=0;
ball_y=425;
t=new Thread(this);
t.start();
         }    
       } 
public  void power_detect(){
if(speed==20)
    block_x1+=10;
if(speed==19)
    block_x1+=8;
if(speed==18)
    block_x1+=6;
if(speed==17)
    block_x1+=4;
if(speed==16)
    block_x1+=3;
if(speed==15)
    block_x1+=2;
   }     
}
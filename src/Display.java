import java.awt.*;    // Using AWT's Graphics and Color
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*; // Using Swing's components and containers
	 
	/**
	 * A Bouncing Ball: Running animation via a custom thread
	 */
public class Display extends JFrame {
	   // Define named-constants
	   private static final int CANVAS_WIDTH = 920;
	   private static final int CANVAS_HEIGHT = 920;
	   private static final int UPDATE_INTERVAL = 50; // milliseconds
	 
	   private TileMap t;  // the drawing canvas (an inner class extends JPanel)
	   MouseAdapter mouseAdapter;
	   int numTilesOnASide;
	   
	   // Attributes of moving object
	   private int x = 100;     // top-left (x, y)
	   private int y = 100;
	   private int size = 50;  // width and height
	   private int xSpeed = 3;  // moving speed in x and y directions
	   private int ySpeed = 5;  // displacement per step in x and y
	   
	   private int mouseX;
	   private int mouseY;
	   private int tileX;
	   private int tileY;
	   TileMap view;
	   
	   // Constructor to setup the GUI components and event handlers
	   public Display() {
		  numTilesOnASide = 10;
	      t = new TileMap(numTilesOnASide,numTilesOnASide);
	      t.setTileSize(CANVAS_WIDTH/numTilesOnASide);
	      view = t;
	      
	      //MouseAdapter listens to mouse
	      mouseAdapter = new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) {
	        	  System.out.println("Click.");
	        	  //Left Click Actions
	        	  if(SwingUtilities.isLeftMouseButton(me)){
	            System.out.println("Left Click."); 
	            view.unselectTile(tileY, tileX);
	            mouseX=me.getX();
      		  	mouseY=me.getY();
      		  	tileX=(int)((mouseX/(double)CANVAS_WIDTH)*numTilesOnASide);	        		  
      		  	tileY=(int)((mouseY/(double)CANVAS_WIDTH)*numTilesOnASide);
      		  	view.selectTile(tileY,tileX);  
	        	  }
	        	  //Right Click Actions
	        	  else if(SwingUtilities.isRightMouseButton(me)){
	        		  System.out.println("Right Click.");
	        		  view.unselectTile(tileY, tileX);
	        		  System.out.println("X: "+me.getX()+"\t Y:"+me.getY());
	        		  mouseX=me.getX();
	        		  mouseY=me.getY();
	        		  tileX=(int)((mouseX/(double)CANVAS_WIDTH)*numTilesOnASide);	        		  
	        		  tileY=(int)((mouseY/(double)CANVAS_WIDTH)*numTilesOnASide);
	        		  view.selectTile(tileY,tileX);    		  
	        	      doPop(me);
	        	  }
	        	  else System.out.println("What?");
	          }
	          
	        };
	      
	      //setUp
	      updateSetUp();

	      // Create a new thread to run update at regular interval
	      Thread updateThread = new Thread() {
	         @Override
	         public void run() {
	            while (true) {
	               update();   // update the (x, y) position
	               //updateSetUp();
	               repaint();  // Refresh the JFrame. Called back paintComponent()
	               view.repaint();
	               try {
	                  // Delay and give other thread a chance to run
	                  Thread.sleep(UPDATE_INTERVAL);  // milliseconds
	               } catch (InterruptedException ignore) {}
	            }
	         }
	      };
	      updateThread.start(); // called back run()
	   }
	 
	   // Update the (x, y) position of the moving object
	   public void update() {
	      x += xSpeed;
	      y += ySpeed;
	      if (x > CANVAS_WIDTH - size || x < 0) {
	         xSpeed = -xSpeed;
	      }
	      if (y > CANVAS_HEIGHT - size || y < 0) {
	         ySpeed = -ySpeed;
	      }
	   }
	   public void updateSetUp(){
		   	view.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		   	view.repaint();
		      this.setContentPane(view);
		      addMouseListener(mouseAdapter);
		      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		      this.pack();
		      this.setTitle("Bouncing Ball");
		      this.setVisible(true);
		      revalidate();
	   }
	   
	   //Pop up menu when you rightclick
	   private void doPop(MouseEvent e){
		   RightClickPopUp menu = new RightClickPopUp();
	        menu.show(e.getComponent(), e.getXOnScreen(), e.getYOnScreen());
	    }
	   
	   class RightClickPopUp extends JPopupMenu {
		    JMenuItem explore;
		    JMenuItem ZoomOut;
		    JMenuItem ZoomIn;
		    public RightClickPopUp(){
		    	explore = new JMenuItem(new AbstractAction("Explore") {
		    	    public void actionPerformed(ActionEvent e) {
		    	        System.out.println("EXPLORE!");
		    	    }});
		    	explore.setEnabled(false);
		        add(explore);
		        ZoomOut = new JMenuItem(new AbstractAction("Zoom Out") {
		    	    public void actionPerformed(ActionEvent e) {
		    	        System.out.println("Zoom Out.");
		    	        view=t;
		    	        updateSetUp();
		    	    }});
		        add(ZoomOut);
		        ZoomIn = new JMenuItem(new AbstractAction("Zoom In") {
		    	    public void actionPerformed(ActionEvent e) {
		    	        System.out.println("Zoom In. X:"+tileX+"\t Y:"+tileY);
		    	        //Determine what tile the mouse is on
		    	        numTilesOnASide = 5;
		    	        view = view.getSubMap(tileX, tileY, numTilesOnASide, numTilesOnASide);
		    	        updateSetUp();
		    	    }});
		        add(ZoomIn);
		    }
		}
	   
	   
	   // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
	   class DrawCanvas extends JPanel {
	      //@Override

	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);  // paint parent's background
	         g.setColor(Color.RED);
	         g.fillOval(x, y, 500, 500);  // draw a circle
	         
	         //g.drawImage(icon,x,y,null);
	      }
	   }
	 
	   // The entry main method
	   public static void main(String[] args) {
	      // Run GUI codes in Event-Dispatching thread for thread safety
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new Display(); // Let the constructor do the job
	         }
	      });
	   }
	   protected void paintComponent(Graphics g) {
		   super.paintComponents(g);
	   }
	   
	}



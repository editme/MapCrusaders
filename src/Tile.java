//This will be the main class for the tiles seen in MapCruisade
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Tile extends JPanel{
	
	//Private defining variables
	int myX;
	int myY;
	int myWidth;
	int myHeight;
	int tileType;
	
	//Imaging Variables
	BufferedImage tileSet=null;
	Image myImage;
	double aspectRatio=10/32.0;
	private int resizeX;
	private int resizeY;
	
	//Sprites
	int numSprites = 3;
	Sprite[] sprites=new Sprite[numSprites];
	Sprite background;
	int pos[][];
	boolean selected=false;
	Sprite overlay;
	//Sorting class
	Quicksort q=new Quicksort();

	//Constructors
public Tile(int x, int y, int w,int h, int type){
	myX=x;
	myY=y;
	myWidth=w;
	myHeight=h;
	tileType=type;
	
	//Resize variable = sprites based on pixel sizes
	resizeX=(int)(myWidth*aspectRatio);
	resizeY=(int)(myHeight*aspectRatio);

	//Temporary arrays to establish new locations for sprites
	int[] Xs=new int[numSprites];
	int[] Ys=new int[numSprites];
	
	//Retrieve tileSet image from drive
	try {
	    tileSet = ImageIO.read(new File("C:\\Users\\seanh\\Desktop\\Fun\\Programing\\Map Cruisade\\TileSet.png"));
	} catch (IOException e) {
		System.err.print("Error: File Not Found");
	}
	//if the tile is a meadow paint with nature sprites
	if(tileType==1){
	for(int k=0;k<numSprites;k++){
		Xs[k]=(int)(Math.random()*(myWidth-resizeX));
		Ys[k]=(int)(Math.random()*(myHeight-resizeY));
	}
	//Sort the Ys so that field of view is maintained 
	//(objects in the top are painted first so that they look behind)
	Ys=q.getSortedArray(Ys);
	
	//Instantiate Sprites
	for(int k=0;k<numSprites;k++){
		sprites[k]=new Sprite(Xs[k],Ys[k],getSprite((int)(Math.random()*9)+1));
		//sprites[k].resize(resizeX, resizeY);
	}
	//The background is an open field
	background = new Sprite(0,0,getSprite(0));
	}
	//if the Tile is a mountain, paint with a mountain
	else if(tileType==2){
		background = new Sprite(0,0,getSprite(-1));
	}
	//if the tile is a town, paint with the town tile
	else if(tileType==3){
		switch((int)(Math.random()*2+1)){
		case 1: background = new Sprite(0,0,getSprite(-2)); break;
		case 2: background = new Sprite(0,0,getSprite(-3)); break;
		}
	}
}
//Retrieves a sprite by cutting the tile set image based on desired sprite int
public Image getSprite(int TileType){
	if(tileSet!=null){
		BufferedImage icon=tileSet;
		switch(TileType){
		case -3: icon = icon.getSubimage(133, 0, 32, 32);
			break;
		case -2: icon = icon.getSubimage(100, 0, 32, 32);
			break;
		//Mountain
		case -1: icon = icon.getSubimage(67, 0, 32, 32);
			break;
		//Meadow
		case 0: icon = icon.getSubimage(34, 0, 32, 32);
		  break;
		//Large Tree
	  	 case 1: icon = icon.getSubimage(0, 0, 10, 10);
	  		  break;
	  	//Medium Tree
	  	 case 2: icon = icon.getSubimage(0,11,10,10);
	  	 		break;
	  	//Small Tree
	  	 case 3: icon = icon.getSubimage(0, 22, 10, 10);
	  		  break;
	  	//Blueberry Bush
	  	 case 4: icon = icon.getSubimage(11, 0, 10, 10);
		  break;
		  //RedBerry Bush
	  	case 5: icon = icon.getSubimage(11, 11, 10, 10);
		  break;
		  //Large Rocks
	  	case 6: icon = icon.getSubimage(11, 22, 10, 10);
		  break;
	  	  //Medium Rocks
	  	case 7: icon = icon.getSubimage(22, 0, 10, 10);
		  break;
		  //Small Rocks
	  	case 8: icon = icon.getSubimage(22, 11, 10, 10);
		  break;
		  //Very Small Tree
	  	case 9: icon = icon.getSubimage(22, 22, 10, 10);
		  break;
	  	  }
	   	return icon;
	}
	else return null;
}

public Image getOverlay(int i){
	if(tileSet!=null){
		BufferedImage icon=tileSet;
	switch(i){
	case 1:  icon = icon.getSubimage(67, 33, 32, 32);
	break;
	case 2: icon = icon.getSubimage(34, 33, 32, 32);
	break;
	}
	return icon;

}else return null;
}

//Paint
public void paintComponent(Graphics g){
	super.paintComponent(g);
	//draw the background regardless of type
    g.drawImage(background.getImage(),0,0,myWidth,myHeight,null);
    
    if(sprites[0]!=null){
	for(int k=0;k<sprites.length;k++)
		g.drawImage(sprites[k].getImage(),sprites[k].getX(),sprites[k].getY(),resizeX,resizeY,null);
    }
    if(selected){
    	g.drawImage((getOverlay(1)),0,0,myWidth,myHeight,null);
    }
}
public void setSize(int s){
	
	if(sprites[0]!=null){
		for(int k=0;k<sprites.length;k++){
			sprites[k].resize(myWidth,s);
		}	
	}
	myWidth=s;
	resizeX=(int)(myWidth*aspectRatio);
	
	
	myHeight=s;
	resizeY=(int)(myHeight*aspectRatio);

}
public void select(){selected=true;}
public void unselect(){selected=false;}
public boolean isSelected(){return selected;}

}




import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

//This will be a way to organize and easily display many tiles at once
public class TileMap extends JPanel {
	Tile[][] tiles;
	int tileSize = 240;
	private int myWidth;
	private int myHeight;
//Constructors
public TileMap(int width, int height){
	tiles = new Tile[width][height];
	myWidth=width;
	myHeight=height;
	setLayout(new GridLayout(width, height));
	for(int k=0;k<tiles.length;k++){
		for(int j=0;j<tiles[0].length;j++){
			
			//if the tile is on the boarder it should be a mountain
			if(k==0||j==0||k==tiles.length-1||j==tiles.length-1){
				tiles[k][j]=new Tile(k,j,tileSize,tileSize,2);
			}
			else{
			//else, There is a 1 in 7 chance of a tile being a mountain tile
			if((int)(Math.random()*10)==1){
			tiles[k][j]=new Tile(k,j,tileSize,tileSize,2);
			}
			//1/10 chance of being a town
			else if((int)(Math.random()*10)==1){
				tiles[k][j]=new Tile(k,j,tileSize,tileSize,3);
			}
			else{
			tiles[k][j]=new Tile(k,j,tileSize,tileSize,1);
			}
		}
			this.add(tiles[k][j]);

		}
	}
}
public TileMap(Tile[][] t){
	tiles=t;
	setLayout(new GridLayout(tiles.length, tiles[0].length));
}
//METHODS
//Return a small portion of the tiles array in a new tileMap
public TileMap getSubMap(int x, int y, int width, int height){
	System.out.println("SubMapping...");
	Tile[][] t = new Tile[width][height];
	
	if(zoomable(width,height)){
		System.out.println("Is Zoomable...");	
		width/=2;
		height/=2;
		
	if(width==1&&height==1){
		System.out.println("Too Small");
	t[0][0]=tiles[x][y];
	return new TileMap(t);
	}
	else if(x+width>=tiles.length-1||y+height>=tiles[0].length-1){
		System.out.println("Too Close 1");
		x=tiles.length-1-width;
		y=tiles[0].length-1-height;
	}
	else if(x-width<=0||y-height<=0){		
		System.out.println("Too Close 2");
		x=width;
		y=height;
	}

	for(int k=x-width, tempx=0;k<=x+width;k++, tempx++){
		for(int j=y-height, tempy=0;j<=y+height;j++, tempy++){
			//System.out.println("k: "+k+"\t tempx: "+tempx+"\t j: "+j+"\t tempy: "+tempy);
			t[tempx][tempy]=tiles[k][j];
		}
	}
	return new TileMap(t);
	}
	System.out.println("Not Zoomable...");

	return this;
}
public void selectTile(int x, int y){tiles[x][y].select();}
public void unselectTile(int x, int y){tiles[x][y].unselect();}

//Painting
public void paintComponent(Graphics g){
	super.paintComponent(g);
	repaint();
}

//Accessor Methods
public void setTileSize(int s){
	tileSize=s;
	for(int k=0;k<tiles.length;k++){
		for(int j=0;j<tiles[0].length;j++){
			tiles[k][j].setSize(tileSize);
		}
	}
}
//Used in getSubMap(...) method to determine if a subMap is possible
public boolean zoomable(int x,int y){
	if(x>=myWidth||y>=myHeight)
		return false;
	return true;
}
}

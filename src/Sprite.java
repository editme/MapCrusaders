import java.awt.*;

//this will serve as the main information holder for all textures
public class Sprite {
	int myX;
	int myY;
	double myResizeValue;
	Image myImage;
public Sprite(int x,int y, Image i){
	myX=x;
	myY=y;
	myImage=i;
}
public Image getImage(){return myImage;}
public int getX(){return myX;}
public int getY(){return myY;}

public void resize(int old, int neu){
	myResizeValue=neu;
	myX=(int)(neu*(myX/((double)old)));
	myY=(int)(neu*(myY/((double)old)));
	
	//myImage = myImage.getScaledInstance(x, y, 2);
}

}

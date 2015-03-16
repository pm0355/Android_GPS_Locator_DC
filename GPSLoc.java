package com.example.gpsloc;




import com.example.gpsloc.GPSLoc.Coor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
 
public class GPSLoc extends Activity {
	//public boolean yus = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    // do not print 
        setContentView(new BitmapView(this));
        
    }
 
    public class BitmapView extends View implements Runnable {
        public BitmapView(Context context) {
            super(context);
        	initializeGPS();
        
        	 mycoordlist = new LinkedList<Coor>();
             Thread t = new Thread(this);
             t.start();      
        
        }
        public void run(){
        	while(true){
        		 double y=  getLatitude()*(500/(DC_WEST-DC_EAST))+220.34882836636;
                 double x=  getLongitude()*(1000/(DC_NORTH-DC_SOUTH))+340.55634298208; //+366.55634298208
                
        		Coor coord = new Coor(x,y);
        		   mycoordlist.add(coord);
                try{
                	Thread.sleep(10000);
                }
                catch(InterruptedException e){
                	return;
                }
               
            
                

               
    		
        	}
        	
        }
        double yum =-77.028819 +220.34882836636; 
		 double tum = 38.917380 +366.55634298208;
		
        
        LinkedList<Coor> mycoordlist;
        @Override
        protected void onDraw(Canvas canvas) {
         
        
         canvas.scale(getWidth()/1000f, getHeight()/1000f);
        	Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dcmap);
            canvas.drawColor(Color.BLACK);
            int h = 100; // Height in pixels
            int w = 900; // Width in pixels    
            Bitmap scaled = Bitmap.createScaledBitmap(bmp, 1000, 1000, true);
         //canvas.scale((canvas.getWidth()/2000f), (canvas.getWidth()/1400f));
            canvas.drawBitmap(scaled, 0, 0, new Paint());
         //        
            
      
           
                Paint p = new Paint();
                Paint z = new Paint();
                z.setAntiAlias(true);
                z.setColor(Color.CYAN);
            	z.setStyle(Paint.Style.STROKE); 
    			z.setStrokeWidth(2.5f);
                p.setAntiAlias(true);
    			p.setColor(Color.RED);
    			
    			p.setStyle(Paint.Style.STROKE); 
    			p.setStrokeWidth(20.5f);
    			for(int i = 0; i<mycoordlist.length(); i++){
    			
    			
    			Coor init = (Coor) mycoordlist.get(0);
    			
    			Coor c = (Coor) mycoordlist.get(i);
    			//updated coordinate from linkedlist
    			canvas.drawCircle((float)c.longint,(float)c.lat, 7, p);
    			
   
    			//line drawn from one to another
    			//canvas.drawLine((float)init.longint, (float)init.lat, (float)c.longint, (float)c.lat, z)
    			
    			//sample coordinate Bens ChiliBowl w/ line
    			Coor b = new Coor (yum,tum);
    			
    			canvas.drawCircle((float)b.longint,(float)b.lat, 7, p);
    			canvas.drawLine((float)init.longint, (float)init.lat, (float)b.longint, (float)b.lat, z );
    			}
            
                
            }
        
        }
        static final double DC_WEST=-77.119789, DC_EAST=-76.909399, DC_NORTH=38.99554, DC_SOUTH=38.79163;//y, x
 	   //AU is 38.936989,-77.090637 	   
	   private LocationManager lm;
	   private void initializeGPS()
	   {
		   lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		   lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, new LocationListener(){
			public void onLocationChanged(Location location) {}
			public void onProviderDisabled(String provider) {}
			public void onProviderEnabled(String provider) {}
			public void onStatusChanged(String provider, int status, Bundle extras) {}});		   
	   }
	   
	   
	   private double getLatitude()
	   {
		   if (lm==null || lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)==null)
			   return 0;
		   return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
	   }
	   private double getLongitude()
	   {
		   if (lm==null || lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)==null)
			   return 0;
		   return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
	   }


        
	   public class Coor {
		    
			double lat;
			double longint;
			
		    Coor(double la, double lo){
		        lat = la;
		        longint = lo;	
		    }
	
			public String printCoor(){
		    	return "lat,lon: "+this.lat+","+this.longint;
		    }
		}


public class LinkedList<Coor>
{
	private LinkedListElement start;
	private LinkedListElement end;
	private int counter;
	
	public LinkedList()
	{
		start=null;
		end=null;
		counter=0;
		
	}
	public void add(Coor coor)
	{
		LinkedListElement newElement;
		newElement=new LinkedListElement();
		newElement.value= coor;
		
		//empty list?
		if(start==null)
		{
			start=newElement;
			end=newElement;
		}
		else
		{
			end.next=newElement;
			end=newElement;
		}
		counter++;
	}
	
	public Coor get(int index)
	{
		LinkedListElement stepper=start;
		for(int i=0; i<index; i++)
		{
			//out of bounds safeguard
			if(stepper==null)
			return null;
			stepper=stepper.next;

		}
		return (Coor)stepper.value;	
	}
	public int length()
	{
		return counter;
	}
	
	private class LinkedListElement<Coor>
	{
		Coor value;
		LinkedListElement next;
	}

}

}



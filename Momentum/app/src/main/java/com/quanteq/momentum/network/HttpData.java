package com.quanteq.momentum.network;


import java.util.Hashtable;


public class HttpData {
      private String content;
      public Hashtable cookies = new Hashtable();
      public Hashtable headers = new Hashtable();
      
      public String getResponse(){
    	  return this.content;
      }
      
      public void setResponse(String content){
    	  this.content = content;
      }
}

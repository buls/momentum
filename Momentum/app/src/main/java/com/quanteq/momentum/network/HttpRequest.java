package com.quanteq.momentum.network;
 
import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.util.Log;

public class HttpRequest {

    @SuppressWarnings("unchecked")
	public static HttpData get(String sUrl) {
        HttpData ret = new HttpData();
        String str;
        StringBuffer buff = new StringBuffer();
        URLConnection con;
    
        try {
            URL url = new URL(sUrl);
            con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()),8000);
            while ((str = in.readLine()) != null) {
                buff.append(str);
            }
            ret.setResponse(buff.toString());
 
            /*Map<String, List<String>> headers = con.getHeaderFields();
            Set<Entry<String, List<String>>> hKeys = headers.entrySet();
            for (Iterator<Entry<String, List<String>>> i = hKeys.iterator(); i.hasNext();) {
                Entry<String, List<String>> m = i.next();

                ret.headers.put(m.getKey(), m.getValue().toString());
                if (m.getKey().equals("set-cookie"))
                    ret.cookies.put(m.getKey(), m.getValue().toString());
            }*/
        } catch (Exception e) {
            Log.e("HttpRequest", e.toString());
        } finally{
        	con = null;
        }

        return ret;
    }
 
 
    @SuppressWarnings("unchecked")
	public InputStream getImage(String sUrl) {
        HttpData ret = new HttpData();
        String str;
        StringBuffer buff = new StringBuffer();
        URLConnection con;
        InputStream stream = null;
    
        try {
            URL url = new URL(sUrl);
            con = url.openConnection();

            HttpURLConnection httpConnection = (HttpURLConnection) con;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }

        } catch (Exception e) {
            Log.e("HttpRequest", e.toString());
        } finally{
        	con = null;
        }

        return stream;
    }
 
    public static HttpData post(String sUrl, Hashtable<String, String> ht) throws Exception {
        String key;
        StringBuffer data = new StringBuffer();
        Enumeration<String> keys = ht.keys();
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            data.append(URLEncoder.encode(key, "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(ht.get(key), "UTF-8"));
            data.append("&amp;");
        }
        return HttpRequest.post(sUrl, data.toString());
    }
       
        public static HttpData post(String sUrl, String data) {
            StringBuffer ret = new StringBuffer();
            HttpData dat = new HttpData();
            String header;
            try {
                    // Send data
                    URL url = new URL(sUrl);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    // Get the response

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                            ret.append(line);
                    }
                    Log.i("ERROR", line);
                    wr.close();
                    rd.close();
            } catch (Exception e) {
                    Log.i("ERROR", "ERROR IN CODE:"+e.getMessage());
                System.out.println("httprequest: " + e.toString());
            }
            dat.setResponse(ret.toString());
            return dat;
        }

}

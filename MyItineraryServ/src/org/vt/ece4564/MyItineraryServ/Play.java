package org.vt.ece4564.MyItineraryServ;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Play extends HttpServlet{
	
	JSONArray jArray = new JSONArray();
	
	public static void main(String[] args) throws Exception{
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/play");
		server.setHandler(context);

		server.start();
		server.join();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write(jArray.toString());
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		String rating = req.getParameter("rating");
		String comment = req.getParameter("comment");
		String loc = req.getParameter("loc");
		String temp = req.getParameter("temp");
		String light = req.getParameter("light");
		
		JSONObject jObj = new JSONObject();
		
		try {
			jObj.put("rating", rating);
			jObj.put("comment", comment);
			jObj.put("location", loc);
			jObj.put("temperature", temp);
			jObj.put("light", light);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
		
		jArray.put(jObj);
		resp.getWriter().write("New location added!");
	}
}

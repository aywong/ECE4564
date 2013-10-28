package org.vt.ece4564.MyItineraryServ;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.*;

public class Main extends HttpServlet{
	
	JSONArray jArray = new JSONArray();

	public static void main(String[] args) throws Exception{
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/eat");
		server.setHandler(context);

		server.start();
		server.join();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write("please look for eat, play or sleep");
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write("please look for eat, play or sleep");
	}
}

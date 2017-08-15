/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import connection.DbConnection;
/**
 *
 * @author Natnael Getachew
 */
public class DictServlet extends HttpServlet {
    private final DbConnection con = new DbConnection();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inpTerm = (request.getParameter("inpTerm")!=null) ? request.getParameter("inpTerm").toString().trim():"";
        //System.out.println(inpTerm);
        
        if(inpTerm == ""){
            return;
        }
        response.setContentType("application/json;charset=UTF-8");
        Statement stmt;
        Connection c=con.openConnection();
        //if(con.openConnection() != null)
        //out.println("yesss Connected");
        JSONObject jsonObj = new JSONObject();
        
        try (PrintWriter out = response.getWriter()) {

            try{
                // Execute query
                stmt = c.createStatement();
                String sql;
                sql = "SELECT * FROM entries WHERE word = '"+inpTerm+"'";
                ResultSet rs = stmt.executeQuery(sql);
                ArrayList list = new ArrayList();
                
                //Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    JSONObject tempObj = new JSONObject();
                    tempObj.put("word", rs.getString("word"));
                    tempObj.put("wordtype", rs.getString("wordtype"));
                    tempObj.put("definition", rs.getString("definition"));
                    list.add(tempObj);                    
                }

                jsonObj.put("results", list);
                // Clean up environment
                rs.close();
                stmt.close();
                con.closeConnection(c);
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }
            
            out.println(jsonObj);
            
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}

package quizz.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import quizz.domain.Asset;
import quizz.model.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/assets")
public class AssetsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            // Update an asset
            Asset asset = new Asset(
                    Integer.parseInt(request.getParameter("id")),
                    Integer.parseInt(request.getParameter("userid")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("value"))
            );

            boolean result = new DBManager().updateAsset(asset);

            response.setContentType("text/plain; charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(result
                        ? "Update asset successfully."
                        : "Error updating asset!");
            }

        } else if ("getAll".equals(action)) {
            // Retrieve all assets for a user
            int userid = Integer.parseInt(request.getParameter("userid"));

            List<Asset> assets = new DBManager().getUserAssets(userid);

            response.setContentType("application/json; charset=UTF-8");
            JSONArray jsonAssets = new JSONArray();
            for (Asset asset : assets) {
                JSONObject jObj = new JSONObject();
                jObj.put("id", asset.getId());
                jObj.put("userid", asset.getUserid());
                jObj.put("description", asset.getDescription());
                jObj.put("value", asset.getValue());
                jsonAssets.add(jObj);
            }

            try (PrintWriter out = response.getWriter()) {
                out.println(jsonAssets.toJSONString());
            }
        } else {
            // No valid action supplied
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If you later want to handle POST (e.g. create), you can do it here
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}

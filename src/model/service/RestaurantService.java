package model.service;

import gui.SearchPanel;
import model.entity.Customer;
import model.entity.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

// TODO : 공지기능 - 휴무, 손님과 대화 기능 , 등록, 수정, 검색
public class RestaurantService {
    private DBManager dbManager = new DBManager();
    private String query;
    private Statement stmt = null;
    private ResultSet rs;
    private JTabbedPane jTabbedPane;
    private Connection conn = dbManager.getCon();
    ArrayList<Restaurant> list = new ArrayList<>();
    private Restaurant restaurant = new Restaurant();
    String clientId = "jpxuvy6o4g";  //clientId
    String clientSecret = "mDz94S9McdSVNWUdXsmiZ7iIq78pib9EF1BCfeGL";  //clientSecret

    // 1. 가게 등록 서비스
    public void create(Customer customer, JTextField name,JTextField phone,JTextField category,JTextField locate){
        String resName = name.getText();
        String resPhone = phone.getText();
        String resCategory = category.getText();
        String location = locate.getText();
        String cust_id = customer.getCusId();
        PreparedStatement psmt = null;
        PreparedStatement psmt2 = null;
        try {
            System.out.println(resName + resPhone + resCategory + location);
            query = "INSERT INTO restaurant(res_name,res_phone,category,location,cust_id,open) VALUES(?,?,?,?,?,1)";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, resName);
            psmt.setString(2, resPhone);
            psmt.setString(3, resCategory);
            psmt.setString(4, location);
            psmt.setString(5,cust_id);
            psmt.executeUpdate();

            query = "UPDATE Customer SET auth = 2 WHERE cust_id = ?";
            psmt2 = conn.prepareStatement(query);
            psmt2.setString(1,customer.getCusId());
            psmt2.executeUpdate();
            customer.setAuth(2);

            System.out.println("가게 등록이 완료되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    // 2. 가게정보 수정 오픈상태 +  공지(
    public void update(Integer resId,String description){
        PreparedStatement psmt = null;
        try {
            query = "UPDATE Restaurant SET description = ? WHERE res_id LIKE ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1,description);
            psmt.setInt(2,resId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    //이름으로 원하는 레스토랑을 찾음
    public ArrayList<Restaurant> searchByName(String name){
        PreparedStatement psmt = null;
        try {
            query = "SELECT * FROM restaurant WHERE res_name = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, name);
            rs = psmt.executeQuery();
            while(rs.next()) { // 일치하면 restaurant 정보값 세팅
                restaurant = new Restaurant();
                restaurant.setData(rs.getInt("res_id"),rs.getString("res_name"),rs.getString("res_phone"),
                        rs.getString("category"),rs.getString("location"),rs.getBoolean("open"),rs.getString("description"));
            list.add(restaurant);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt,rs);
        }
        return list;
    }

    //카테고리로 원하는 음식점을 찾음
    public ArrayList<Restaurant> searchByCategory(String category){
        PreparedStatement psmt = null;
        try {
            query = "SELECT * FROM restaurant WHERE category = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, category);
            rs = psmt.executeQuery();
            while (rs.next()) { // 일치하면 로그인하는 customer의 정보값 세팅
                restaurant = new Restaurant();
                restaurant.setData(rs.getInt("res_id"),rs.getString("res_name"),rs.getString("res_phone"),
                        rs.getString("category"),rs.getString("location"),rs.getBoolean("open"),rs.getString("description"));
                list.add(restaurant);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt,rs);
        }
        return list;
    }

    public ArrayList<Restaurant> searchByCust(String custId){
        PreparedStatement psmt = null;
        try {
            query = "SELECT * FROM restaurant WHERE cust_id = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, custId);
            rs = psmt.executeQuery();
            while(rs.next()) { // 일치하면 restaurant 정보값 세팅
                restaurant = new Restaurant();
                restaurant.setData(rs.getInt("res_id"),rs.getString("res_name"),rs.getString("res_phone"),
                        rs.getString("category"),rs.getString("location"),rs.getBoolean("open"),rs.getString("description"));
                list.add(restaurant);
            }
        } catch (SQLException e){
            //JOptionPane.showMessageDialog(jTabbedPane,"권한이 없습니다");
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt,rs);
        }
        return list;
    }

    public void naverMaps(String address, SearchPanel findResPanel){
        String x = "";
        String y = "";
       // convert(address);
        System.out.println(address);

        try {
            String addr = URLEncoder.encode(address, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
            URL url = new URL(apiURL);
            System.out.println(url);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json; charset=UTF-8");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(responseCode + "\n" + response);
            br.close();

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject)parser.parse(response.toString());
            JSONArray jArr = (JSONArray) object.get("addresses");
            for (int i = 0; i < jArr.size(); i++) {
                JSONObject temp = (JSONObject) jArr.get(i);
                x = (String)temp.get("x");
                y = (String)temp.get("y");
            }

            // TODO : java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0 -> JSONArray의 크기가 정해져있지않은데 0번요소를 참조하려해서 에러.
            // TODO : JSONArray에 대한 공부
            System.out.println("x : " + x + " , "+"y : " + y);
            setMaps(x,y,findResPanel);

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void setMaps(String x, String y, SearchPanel findResPanel) {
        try {
            String addr = URLEncoder.encode(x + " " + y, "UTF-8");  //주소입력
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=300&h=300&markers=type:d|size:mid|pos:"+addr + "|color:0xFF6355";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;

            // TODO : 방식 검토필요
            if (responseCode == 200) {
                InputStream is = con.getInputStream();

                int read = 0;
                byte[] bytes = new byte[1024];

                // 랜덤 파일명으로 파일 생성
                String tempName = Long.valueOf(new Date().getTime()).toString();
                File file = new File( tempName + ".jpg");	// 파일 생성.

                file.createNewFile();

                OutputStream out = new FileOutputStream(file);

                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);	// 파일 작성
                }

                is.close();
                ImageIcon img = new ImageIcon(file.getName());
                findResPanel.mapLabel.setIcon(img);
                file.delete();

            } else {	// 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
            }

        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public Boolean findById(String id) {
        String cust_id = id;
        String sql = "select * from customer where cust_id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Boolean isExist = false;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cust_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {// true면 가입 불가
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(pstmt,rs);
        }
        return isExist;
    }

    public void updateOpen(Integer resId){
        PreparedStatement psmt = null;
        try {
            query = "UPDATE Restaurant SET open = TRUE WHERE res_id LIKE ?";
            psmt = conn.prepareStatement(query);
            psmt.setInt(1, resId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    public void updateClose(Integer resId){
        PreparedStatement psmt = null;
        try {
            query = "UPDATE Restaurant SET open = FALSE WHERE res_id LIKE ?";
            psmt = conn.prepareStatement(query);
            psmt.setInt(1, resId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import static java.time.temporal.TemporalQueries.localTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Arwa
 */
public class Rating {

    /**
     * @param args the command line arguments
     */
    static Connection con;

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5433/billing";
            con = DriverManager.getConnection(url, "postgres", "5433");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Rating.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text File", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        String filePath = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile().getPath();
//            System.out.println("You chose to open this file: "
//                    + filePath);
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            try {
                String fields[] = line.split(",");
                String msisdn = fields[0];
                String cno = "";
                long no = 0;
                if (msisdn.length() > 14) {
                    cno = msisdn.split("﻿")[1];
                    no = Long.parseLong(cno);
//                    System.out.println(no);

                } else if (msisdn.length() == 14) {
                    cno = msisdn;
                    no = Long.parseLong(msisdn);
//                    System.out.println(cno);
                }

                ResultSet rs = con.createStatement().executeQuery("select customerid from customer where msisdn=" + no + " ");
                rs.next();
                int uid = rs.getInt(1);
                int sid = Integer.parseInt(fields[2]);
                int used_units = Integer.parseInt(fields[3]);
                LocalTime local = LocalTime.parse(fields[4], DateTimeFormatter.ofPattern("HH:mm:ss"));
                int hour = local.get(ChronoField.CLOCK_HOUR_OF_DAY);
                int minute = local.get(ChronoField.MINUTE_OF_HOUR);
                int second = local.get(ChronoField.SECOND_OF_MINUTE);
                Time time = new Time(hour, minute, second);
                PreparedStatement st = con.prepareStatement("insert into rated_cdr (billed,external_charge,destination_no,destination_url,used_units,serviceid,start_time,cid,rate) values(?,?,?,?,?,?,?,?,?)");
                st.setBoolean(1, false);
                st.setDouble(2, Double.parseDouble(fields[5]));
                if (sid != 3) {
                    long dest_no = Long.parseLong(fields[1]);
                    st.setLong(3, dest_no);
                    st.setString(4, " ");
                } else {
                    String url = fields[1];
                    st.setInt(3, 0);
                    st.setString(4, url);
                }
                st.setInt(5, used_units);
                st.setInt(6, sid);
                st.setTime(7, time);
                st.setInt(8, uid);
                st.setInt(9, 0);
                st.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        br.close();
    }
}

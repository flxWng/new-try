package Testarea;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Add_website {

        static boolean answer;
        static List Webnamen = new ArrayList();
        static List Password = new ArrayList();
        static List Filewebnames = new ArrayList();
        static List PWnames = new ArrayList();

    static String webchoice = "";

    public static boolean display(String currentchoice){
        // read the pw and website inputs from the .txt files
        String currUserFilename = currentchoice + "_sites.txt";
        String currUserPws = currentchoice + "_passwords.txt";
        Filewebnames = shows_websiteList.read(currUserFilename);
        PWnames = shows_websiteList.read(currUserPws);

        //if filename exists, return true. Then don't create new file
        //writefile.write(currentchoice,currentpw);
        Stage window = new Stage();
            //block user interaction with other windows until this is resolved
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(currentchoice + "'s mainpage");
            window.setMinWidth(200);

            //gridpane
            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10, 10, 10, 10));
            layout.setVgap(8);
            layout.setHgap(8);
            Label label = new Label(currentchoice);
            GridPane.setConstraints(label,6,2);
//------------------- ADD WEBSITE ----------------------------------------------
            Button addweb = new Button("Add Website");
            GridPane.setConstraints(addweb,5,3);
            addweb.setOnAction(e->{
                websiteeingabe(currentchoice);
            });

//------------------ GET PASSWORD -----------------------------------------
            Button getpw = new Button("Get Password");
            GridPane.setConstraints(getpw,5,4);
            getpw.setOnAction(e->{
                Filewebnames = shows_websiteList.read(currUserFilename);
                PWnames = shows_websiteList.read(currUserPws);
                webchoice = show_websites();
                String requestedPW = checkifavailable(webchoice);
                String Message = "The password for " + webchoice + " is:\n" + requestedPW;
                JOptionPane.showMessageDialog(null,Message,"" ,JOptionPane.PLAIN_MESSAGE);
            });
//---------------------- Display All Websites ---------------------------------
            Button displ_web = new Button("Display Websitelist");
            GridPane.setConstraints(displ_web,5,5);
            displ_web.setOnAction(e->{
                Filewebnames = shows_websiteList.read(currUserFilename);
                PWnames = shows_websiteList.read(currUserPws);
                shows_websiteList.display(Webnamen,currentchoice);
            });
//------------------------ Window Options -----------------------------------------

            layout.getChildren().addAll(addweb,getpw,displ_web);
            Scene scene = new Scene(layout, 300,300);
            window.setScene(scene);
            window.showAndWait();

            return answer;
        }

 //-------------------------------- FUNCTIONS ---------------------------------------------
    private static String show_websites() {
        Object selectedValue;
            String CurrentChoice = "";
            String[] Liste = (String[]) Filewebnames.toArray(new String[0]);
            selectedValue = JOptionPane.showInputDialog(null,
                    "Choose one", "Websites",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    Liste, Liste[0]);
            CurrentChoice = "" + selectedValue;
            return CurrentChoice;
    }
    private static void websiteeingabe(String currentUser){
        webeingabe(currentUser);
        pweingabe(currentUser);

    }
    private static String pweingabe(String currPW) {
        String pweingabe = JOptionPane.showInputDialog("Enter Password", "Password");
        // write to file
        String FilenameU = currPW + "_passwords.txt";
        writefile.write(FilenameU,pweingabe);
        return pweingabe;
    }
    private static String webeingabe(String CurrU) {
        String seiteeingabe = JOptionPane.showInputDialog("Enter Website", "Website");
        String FilenamePW = CurrU + "_sites.txt";
        writefile.write(FilenamePW,seiteeingabe);
        return seiteeingabe;
    }

    private static String checkifavailable(String webname) {
        String password_for_site ="Error";
        String[] Webliste = (String[]) Filewebnames.toArray(new String[0]);
        String[] PWliste = (String[]) PWnames.toArray(new String[0]);
        int laenge = Filewebnames.size();
        try {
            for (int i = 0; i < laenge; i++) {
                if (webname.equals(Webliste[i])) {
                    password_for_site = PWliste[i];
                    break;
                }
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Not available");
        }
        return password_for_site;
    }
    //ende

}




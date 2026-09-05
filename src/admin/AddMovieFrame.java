package admin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import utils.AppState;
import ui.Dashboard;
import utils.UITheme;

public class AddMovieFrame extends JFrame {

    JTextField idField, titleField, genreField, languageField;
    JTextField yearField, ratingField, durationField, posterField;
    JTextArea descriptionArea;

    public AddMovieFrame() {

        setTitle("Add Movie");
        setSize(600,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UITheme.BG_DARK);
        getContentPane().setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("Add New Movie");
        title.setFont(UITheme.fontTitle(26));
        title.setForeground(UITheme.ACCENT);
        title.setBounds(180,20,300,40);

        panel.add(title);

        String[] labels = {
                "ID","Title","Genre","Language",
                "Year","Rating","Duration","Poster File"
        };

        JTextField[] fields = new JTextField[8];

        for(int i=0;i<labels.length;i++){

            JLabel l = new JLabel(labels[i]);
            l.setBounds(40,80+i*55,120,25);
            l.setForeground(UITheme.TEXT_GRAY);
            l.setFont(UITheme.fontBody(14));

            JTextField t = UITheme.textField();
            t.setBounds(170,78+i*55,330,34);

            panel.add(l);
            panel.add(t);

            fields[i]=t;

        }

        idField=fields[0];
        titleField=fields[1];
        genreField=fields[2];
        languageField=fields[3];
        yearField=fields[4];
        ratingField=fields[5];
        durationField=fields[6];
        posterField=fields[7];

        JLabel desc = new JLabel("Description");
        desc.setBounds(40,530,120,25);
        desc.setForeground(UITheme.TEXT_GRAY);
        desc.setFont(UITheme.fontBody(14));

        descriptionArea = new JTextArea();
        descriptionArea.setBackground(UITheme.FIELD_BG);
        descriptionArea.setForeground(UITheme.TEXT_WHITE);
        descriptionArea.setCaretColor(UITheme.TEXT_WHITE);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(descriptionArea);
        sp.setBounds(170,530,330,80);
        sp.setBorder(new UITheme.RoundedLineBorder(UITheme.FIELD_BORDER, 10, 10));

        JButton save = UITheme.primaryButton("Save Movie");
        save.setBounds(200,625,200,42);

        save.addActionListener(e->saveMovie());

        panel.add(desc);
        panel.add(sp);
        panel.add(save);

        add(panel);

        setVisible(true);

    }

    private void saveMovie(){

        try{

            BufferedWriter bw =
        new BufferedWriter(
                new FileWriter("data/movies.txt", true));

// Ensure the new movie starts on a new line


bw.write(
    idField.getText()+"|"+
    titleField.getText()+"|"+
    genreField.getText()+"|"+
    languageField.getText()+"|"+
    yearField.getText()+"|"+
    ratingField.getText()+"|"+
    durationField.getText()+"|"+
    descriptionArea.getText()+"|"+
    posterField.getText()
);

bw.newLine();
            

            bw.close();
            if (utils.AppState.getDashboard() != null) {
                System.out.println(AppState.getDashboard());
    utils.AppState.getDashboard().refreshMovies();
}

            JOptionPane.showMessageDialog(this,
                    "Movie Added Successfully!");

            dispose();

        }catch(Exception ex){

            ex.printStackTrace();

        }
        

    }
    

}
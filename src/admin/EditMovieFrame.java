package admin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import utils.UITheme;

public class EditMovieFrame extends JFrame {

    private JComboBox<String> movieBox;

    private JTextField idField;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField languageField;
    private JTextField yearField;
    private JTextField ratingField;
    private JTextField durationField;
    private JTextField posterField;

    private JTextArea descriptionArea;

    private ArrayList<String> movieLines = new ArrayList<>();

    public EditMovieFrame() {

        setTitle("Edit Movie");
        setSize(650,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        JLabel select = new JLabel("Select Movie");
        select.setBounds(30,20,120,25);
        select.setForeground(UITheme.TEXT_GRAY);
        select.setFont(UITheme.fontBody(14));

        movieBox = new JComboBox<>();
        movieBox.setBounds(160,20,420,30);

        add(select);
        add(movieBox);

        loadMovies();

        String[] labels = {
                "ID",
                "Title",
                "Genre",
                "Language",
                "Year",
                "Rating",
                "Duration",
                "Poster"
        };

        JTextField[] fields = new JTextField[8];

        int y = 70;

        for(int i=0;i<labels.length;i++){

            JLabel l = new JLabel(labels[i]);
            l.setBounds(30,y,120,25);
            l.setForeground(UITheme.TEXT_GRAY);
            l.setFont(UITheme.fontBody(14));

            JTextField t = UITheme.textField();
            t.setBounds(160,y-2,420,34);

            add(l);
            add(t);

            fields[i]=t;

            y+=50;
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
        desc.setBounds(30,y,120,25);
        desc.setForeground(UITheme.TEXT_GRAY);
        desc.setFont(UITheme.fontBody(14));

        add(desc);

        descriptionArea = new JTextArea();
        descriptionArea.setBackground(UITheme.FIELD_BG);
        descriptionArea.setForeground(UITheme.TEXT_WHITE);
        descriptionArea.setCaretColor(UITheme.TEXT_WHITE);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane sp = new JScrollPane(descriptionArea);
        sp.setBounds(160,y,420,90);
        sp.setBorder(new UITheme.RoundedLineBorder(UITheme.FIELD_BORDER, 10, 10));

        add(sp);

        JButton update = UITheme.primaryButton("Update Movie");
        update.setBounds(220,610,200,42);

        add(update);

        movieBox.addActionListener(e->loadSelectedMovie());

        update.addActionListener(e->updateMovie());

        if(movieBox.getItemCount()>0){
            movieBox.setSelectedIndex(0);
            loadSelectedMovie();
        }

        setVisible(true);
    }

    private void loadMovies(){

        try{

            BufferedReader br =
                    new BufferedReader(new FileReader("data/movies.txt"));

            String line;

            while((line=br.readLine())!=null){

                movieLines.add(line);

                String[] data=line.split("\\|");

                if(data.length>=2){

                    movieBox.addItem(data[1]);

                }

            }

            br.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

    private void loadSelectedMovie(){

        int index = movieBox.getSelectedIndex();

        if(index==-1) return;

        String[] data = movieLines.get(index).split("\\|");

        idField.setText(data[0]);
        titleField.setText(data[1]);
        genreField.setText(data[2]);
        languageField.setText(data[3]);
        yearField.setText(data[4]);
        ratingField.setText(data[5]);
        durationField.setText(data[6]);
        descriptionArea.setText(data[7]);
        posterField.setText(data[8]);

    }

    private void updateMovie(){

        int index = movieBox.getSelectedIndex();

        if(index==-1) return;

        String line =
                idField.getText()+"|"+
                titleField.getText()+"|"+
                genreField.getText()+"|"+
                languageField.getText()+"|"+
                yearField.getText()+"|"+
                ratingField.getText()+"|"+
                durationField.getText()+"|"+
                descriptionArea.getText()+"|"+
                posterField.getText();

        movieLines.set(index,line);

        try{

            BufferedWriter bw =
                    new BufferedWriter(
                            new FileWriter("data/movies.txt"));

            for(String s:movieLines){

                bw.write(s);
                bw.newLine();

            }

            bw.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Movie Updated Successfully!");

            dispose();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}
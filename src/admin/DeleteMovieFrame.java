package admin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import utils.UITheme;

public class DeleteMovieFrame extends JFrame {

    private JComboBox<String> movieBox;
    private ArrayList<String> movieLines;

    public DeleteMovieFrame() {

        setTitle("Delete Movie");
        setSize(500,260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        JLabel label = new JLabel("Select Movie");
        label.setBounds(30,30,120,25);
        label.setForeground(UITheme.TEXT_GRAY);
        label.setFont(UITheme.fontBody(14));

        movieBox = new JComboBox<>();
        movieBox.setBounds(150,30,280,32);

        JButton delete = UITheme.primaryButton("Delete Movie");
        delete.setBounds(150,100,200,42);

        movieLines = new ArrayList<>();

        loadMovies();

        delete.addActionListener(e -> deleteMovie());

        add(label);
        add(movieBox);
        add(delete);

        setVisible(true);
    }

    private void loadMovies() {

        try {

            BufferedReader br =
                    new BufferedReader(new FileReader("data/movies.txt"));

            String line;

            while((line = br.readLine()) != null){

                movieLines.add(line);

                String[] data = line.split("\\|");

                if(data.length >= 2){

                    movieBox.addItem(data[1]);

                }

            }

            br.close();

        } catch(Exception e){

            e.printStackTrace();

        }

    }

    private void deleteMovie(){

        int index = movieBox.getSelectedIndex();

        if(index == -1)
            return;

        movieLines.remove(index);

        try{

            BufferedWriter bw =
                    new BufferedWriter(new FileWriter("data/movies.txt"));

            for(String line : movieLines){

                bw.write(line);
                bw.newLine();

            }

            bw.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Movie Deleted Successfully!");

            dispose();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}
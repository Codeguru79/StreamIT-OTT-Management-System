package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import utils.UITheme;

public class ViewUsersFrame extends JFrame {

    public ViewUsersFrame() {

        setTitle("Registered Users");
        setSize(700,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UITheme.BG_DARK);

        String[] columns = {"Name","Email"};

        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);
        table.setBackground(UITheme.BG_CARD);
        table.setForeground(UITheme.TEXT_WHITE);
        table.setGridColor(UITheme.FIELD_BORDER);
        table.setRowHeight(28);
        table.setFont(UITheme.fontBody(14));
        table.setSelectionBackground(UITheme.ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(UITheme.BG_TOPBAR);
        table.getTableHeader().setForeground(UITheme.TEXT_WHITE);
        table.getTableHeader().setFont(UITheme.fontTitle(14));

        try{

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("data/users.txt"));

            String line;

            while((line=br.readLine())!=null){

                String[] data=line.split("\\|");

                if(data.length>=2){

                    model.addRow(new Object[]{
                            data[0],
                            data[1]
                    });

                }

            }

            br.close();

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users.");

        }

        JScrollPane scroll = new JScrollPane(table);
        UITheme.styleScrollBar(scroll);
        scroll.getViewport().setBackground(UITheme.BG_CARD);
        add(scroll);

        setVisible(true);

    }

}
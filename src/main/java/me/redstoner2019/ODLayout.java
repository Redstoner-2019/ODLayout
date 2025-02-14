package me.redstoner2019;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ODLayout implements LayoutManager {

    private List<Column> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();
    private HashMap<Component,Bounds> register = new HashMap<>();
    private int pad = 0;
    public boolean debug = false;

    public void addColumn(Column column){
        columns.add(column);
    }
    public void addRow(Row row){
        rows.add(row);
    }

    public void removeColumn(Column column){
        columns.remove(column);
    }

    public void removeRow(Row row){
        rows.remove(row);
    }

    public void removeColumn(int i){
        columns.remove(i);
    }

    public void removeRow(int i){
        rows.remove(i);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    /**
     * Register Component for the layout, defining the row/column
     * @param c Component to register
     * @param p Position, containing the id's of the row/column
     */
    public void registerComponent(Component c, Position p){
        registerComponent(c,p,p);
    }

    /**
     * Register Component for the layout, defining the row/column
     * @param c Component to register
     * @param p1 Position, containing the id's of the row/column
     * @param p2 Position, containing the id's of the row/column
     */
    public void registerComponent(Component c, Position p1, Position p2){
        register.put(c,new Bounds(p1,p2));
    }

    public int getPad() {
        return pad;
    }

    public void setPad(int pad) {
        this.pad = pad;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        HashMap<String, Rectangle> boundMap = new HashMap<>();

        int width = parent.getWidth() - 2 * pad;
        int height = parent.getHeight() - 2 * pad;

        int rowFixedWidth = 3*pad;
        int columnFixedWidth = 3*pad;

        int rowVariable = 0;
        int columnVariable = 0;

        for(Row row : rows) if(row.lengthType == LengthType.PIXEL){
            rowFixedWidth+=row.getLengthInt()+(pad);
        }else if(row.lengthType == LengthType.PERCENT){
            rowFixedWidth+=(int)(row.getLength() * height)+(pad);
        } else rowVariable++;

        for(Column column : columns) if(column.lengthType == LengthType.PIXEL){
            columnFixedWidth+=column.getLengthInt()+(pad);
        }else if(column.lengthType == LengthType.PERCENT){
            columnFixedWidth+=(int)(column.getLength() * width)+(pad);
        } else columnVariable++;

        int x = 0;
        int y = 0;

        int start = 0;
        int end = 0;

        for(Row row : rows){
            start = end;

            if(row.lengthType == LengthType.PIXEL){
                end = start + row.getLengthInt();
            }else if(row.lengthType == LengthType.PERCENT){
                end = start + (int)(row.getLength() * height);
            } else {
                end = start + ((height - rowFixedWidth) / rowVariable);
            }

            start+=pad;
            end+=2*pad;

            row.setStart(start);
            row.setEnd(end);
        }

        end = 0;

        for(Column column : columns){
            start = end;

            if(column.lengthType == LengthType.PIXEL){
                end = start + column.getLengthInt();
            }else if(column.lengthType == LengthType.PERCENT){
                end = start + (int)(column.getLength() * width);
            } else {
                end = start + ((width - columnFixedWidth) / columnVariable);
            }

            start+=pad;
            end+=2*pad;

            column.setStart(start);
            column.setEnd(end);
        }

        for(Row row : rows){
            x=0;
            for(Column column : columns){
                int x0 = (int) column.getStart();
                int y0 = (int) row.getStart();
                int w0 = (int) (column.getEnd() - column.getStart());
                int h0 = (int) (row.getEnd() - row.getStart());

                boundMap.put(x + " " + y,new Rectangle(x0,y0,w0,h0));
                x++;
            }
            y++;
        }

        if(debug){
            register.clear();
            parent.removeAll();
            Random random = new Random(69);
            for (int row = 0; row < rows.size(); row++) {
                for (int coulumn = 0; coulumn < columns.size(); coulumn++) {
                    JLabel label = new JLabel(coulumn + " / " + row);
                    parent.add(label);
                    label.setBackground(new Color(random.nextInt(128,255),random.nextInt(128,255),random.nextInt(128,255)));
                    label.setBorder(BorderFactory.createLineBorder(label.getBackground()));
                    label.repaint();
                    registerComponent(label,new Position(coulumn,row));
                }
            }
        }

        for(Component c : parent.getComponents()){
            if(register.get(c) == null){
                c.setBounds(0,0,0,0);
                continue;
            }

            Position p1 = register.get(c).getPos1();
            Position p2 = register.get(c).getPos2();

            Rectangle r1 = boundMap.getOrDefault(p1.toString(), new Rectangle(0,0));
            Rectangle r2 = boundMap.getOrDefault(p2.toString(), new Rectangle(0,0));

            int x0 = (int) Math.min(r1.getX(),r2.getX());
            int y0 = (int) Math.min(r1.getY(),r2.getY());

            int x1 = (int) Math.max(r1.getX() + r1.getWidth(),r2.getX() + r2.getWidth());
            int y1 = (int) Math.max(r1.getY() + r1.getHeight(),r2.getY() + r2.getHeight());

            int width0 = x1-x0;
            int height0 = y1-y0;

            Rectangle r = new Rectangle(x0,y0,width0,height0);
            c.setBounds(r);
            c.revalidate();
            c.repaint();
        }

        parent.setMinimumSize(new Dimension(columnFixedWidth,rowFixedWidth));
    }
}
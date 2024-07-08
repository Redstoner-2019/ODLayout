package me.redstoner2019;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ODLayout implements LayoutManager {

    private List<Column> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();
    private HashMap<Component,Position> register = new HashMap<>();

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
        register.put(c,p);
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

        int width = parent.getWidth();
        int height = parent.getHeight();

        int rowFixedWidth = 0;
        int columnFixedWidth = 0;

        int rowVariable = 0;
        int columnVariable = 0;

        for(Row row : rows) if(row.lengthType == LengthType.PIXEL){
            rowFixedWidth+=row.getLengthInt();
        }else if(row.lengthType == LengthType.PERCENT){
            rowFixedWidth+=(int)(row.getLength() * height);
        } else rowVariable++;

        for(Column column : columns) if(column.lengthType == LengthType.PIXEL){
            columnFixedWidth+=column.getLengthInt();
        }else if(column.lengthType == LengthType.PERCENT){
            columnFixedWidth+=(int)(column.getLength() * width);
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

        for(Component c : parent.getComponents()){
            Rectangle r = boundMap.getOrDefault(register.get(c).toString(),new Rectangle(0,0,0,0));
            c.setBounds(r);
            c.revalidate();
            c.repaint();
        }

        parent.setMinimumSize(new Dimension(columnFixedWidth,rowFixedWidth));
    }
}
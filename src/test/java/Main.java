import me.redstoner2019.*;

import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main().init();
    }
    public void init(){
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);

        ODLayout layout = new ODLayout();

        layout.debug = false;

        layout.setPad(10);

        //layout.addColumn(new Column(20, LengthType.PIXEL));
        layout.addColumn(new Column(Lengths.VARIABLE));
        layout.addColumn(new Column(Lengths.VARIABLE));
        //layout.addColumn(new Column(20, LengthType.PIXEL));

        layout.addRow(new Row(20, LengthType.PIXEL));
        layout.addRow(new Row(Lengths.VARIABLE));
        layout.addRow(new Row(20, LengthType.PIXEL));

        JButton topLeft = new JButton("topLeft");
        JButton topMiddle = new JButton("topMiddle");
        JButton topRight = new JButton("topRight");

        JButton middleLeft = new JButton("middleLeft");
        JButton middleMiddle = new JButton("middleMiddle");
        JButton middleRight = new JButton("middleRight");

        JButton bottomLeft = new JButton("bottomLeft");
        JButton bottomMiddle = new JButton("bottomMiddle");
        JButton bottomRight = new JButton("bottomRight");

        add(topLeft);
        add(topMiddle);
        add(topRight);
        add(middleLeft);
        add(middleMiddle);
        add(middleRight);
        add(bottomLeft);
        add(bottomMiddle);
        add(bottomRight);

        layout.registerComponent(topLeft,new Position(0,0));
        layout.registerComponent(topMiddle,new Position(1,0));
        layout.registerComponent(topRight,new Position(2,0));
        layout.registerComponent(middleLeft,new Position(0,1));
        layout.registerComponent(middleMiddle,new Position(1,1));
        layout.registerComponent(middleRight,new Position(2,1));
        layout.registerComponent(bottomLeft,new Position(0,2));
        layout.registerComponent(bottomMiddle,new Position(1,2));
        layout.registerComponent(bottomRight,new Position(2,2));

        setLayout(layout);
        revalidate();
    }
}